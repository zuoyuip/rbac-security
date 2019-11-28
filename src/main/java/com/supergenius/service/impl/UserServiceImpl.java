package com.supergenius.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.supergenius.exception.CustomException;
import com.supergenius.mapper.UserMapper;
import com.supergenius.model.Authority;
import com.supergenius.model.Role;
import com.supergenius.model.User;
import com.supergenius.model.UserRole;
import com.supergenius.model.vo.ContentStructure;
import com.supergenius.model.vo.ContentVO;
import com.supergenius.model.vo.MenuVO;
import com.supergenius.service.IUserRoleService;
import com.supergenius.service.IUserService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 安全用户表 服务实现类
 * </p>
 *
 * @author zuoyu
 * @since 2019-11-22
 */
@Service
class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    private final PasswordEncoder PASSWORDENCODER = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    private final UserMapper userMapper;

    private final IUserRoleService iUserRoleService;

    UserServiceImpl(UserMapper userMapper, IUserRoleService iUserRoleService) {
        this.userMapper = userMapper;
        this.iUserRoleService = iUserRoleService;
    }


    @Override
    public User loadUserByUsername(String userName) {
        return userMapper.loadUserByUsername(userName);
    }

    @Override
    public List<Authority> selectAuthoritiesById(Serializable userId) {
        return userMapper.selectAuthoritiesById(userId);
    }

    @Override
    public List<Role> selectRolesById(Serializable userId) {
        return userMapper.selectRolesById(userId);
    }

    private List<ContentVO> contentBuilder(Map<String, List<ContentStructure>> contentGroup) {
        //        根据分组组装对象
        return contentGroup.keySet().parallelStream().map(key -> {
            ContentVO contentVO = new ContentVO(key);
            List<MenuVO> menuList = new ArrayList<>(contentGroup.get(key).size());
            menuList.addAll(contentGroup.get(key).stream().map(contentStructure ->
                    new MenuVO(contentStructure.getMenuName(), contentStructure.getUrl()))
                    .collect(Collectors.toList()));
            return contentVO.setMenuList(menuList);
        }).collect(Collectors.toList());
    }

    @Override
    public List<ContentVO> getContentsById(Serializable userId) {
        List<ContentStructure> contentStructures = userMapper.getContentStructuresByUserId(userId);
//        分组
        Map<String, List<ContentStructure>> contentGroup = contentStructures.stream()
                .collect(Collectors.groupingBy(ContentStructure::getContentName));
        return contentBuilder(contentGroup);
    }

    @Override
    public List<ContentVO> getContentsByUser(User user) {
        List<Authority> authorities = user.getAuthorityBeans();
        Map<String, List<ContentStructure>> contentGroup = authorities.stream().map(authority ->
                new ContentStructure(authority.getAuthorityName(), authority.getAuthorityMenu(),
                        authority.getAuthorityMenuUrl())).collect(Collectors.groupingBy(ContentStructure::getContentName));
        return contentBuilder(contentGroup);
    }

    @Override
    public boolean save(User user, List<Integer> roleIds) {
        if (!save(user)) {
            throw new CustomException("用户创建失败");
        }
        List<UserRole> userRoles = roleIds.stream().map(roleId ->
                new UserRole(user.getUserId(), roleId)).collect(Collectors.toList());
        if (!iUserRoleService.saveBatch(userRoles)) {
            throw new CustomException("角色分配异常");
        }
        return true;
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class,
            CustomException.class})
    public boolean save(User user) {
        if (userMapper.isExistsByUserSecurityName(user.getUserSecurityName())) {
            throw new CustomException("该用户已存在");
        }
        String formerlyPassWord = user.getUserPassWord();
        String encryptPassWord = PASSWORDENCODER.encode(formerlyPassWord);
        user.setUserIsAccountNonExpired(true).setUserIsAccountNonLocked(true)
                .setUserIsCredentialsNonExpired(true).setUserIsEnabled(true)
                .setUserPassWord(encryptPassWord).setUserCreatTimeStamp(new Date())
                .setUserIsDelete(false);
        return super.save(user);
    }


}
