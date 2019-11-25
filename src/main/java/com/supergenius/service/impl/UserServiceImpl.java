package com.supergenius.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.supergenius.exception.CustomException;
import com.supergenius.mapper.RoleMapper;
import com.supergenius.mapper.UserMapper;
import com.supergenius.mapper.UserRoleMapper;
import com.supergenius.model.Role;
import com.supergenius.model.User;
import com.supergenius.model.UserRole;
import com.supergenius.service.IUserService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

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
    private final RoleMapper roleMapper;
    private final UserRoleMapper userRoleMapper;

    UserServiceImpl(UserMapper userMapper, RoleMapper roleMapper, UserRoleMapper userRoleMapper) {
        this.userMapper = userMapper;
        this.roleMapper = roleMapper;
        this.userRoleMapper = userRoleMapper;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class,
            CustomException.class})
    public boolean save(User user, List<Role> roles) {
        if (!save(user)) {
            throw new CustomException("用户创建失败！");
        }
        AtomicInteger count = new AtomicInteger(0);
        roles.forEach(role -> {
            if (!roleMapper.isExistsByRoleId(role.getRoleId())) {
                throw new CustomException("角色不存在！");
            }
            count.addAndGet(userRoleMapper.insert(new UserRole().setUserId(user.getUserId())
                    .setRoleId(role.getRoleId()).setUserRoleCreatTimeStamp(new Date())
                    .setUserRoleIsDelete(false)));
        });
        if (count.get() < roles.size()) {
            throw new CustomException("用户角色分配失败");
        }
        return true;
    }

    @Override
    public boolean save(User user) {
        if (userMapper.isExistsByUserSecurityName(user.getUserSecurityName())) {
            throw new CustomException("该用户已存在！");
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
