package com.supergenius.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.supergenius.exception.CustomException;
import com.supergenius.mapper.UserRoleMapper;
import com.supergenius.model.UserRole;
import com.supergenius.service.IUserRoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Set;

/**
 * <p>
 * 用户角色中间表 服务实现类
 * </p>
 *
 * @author zuoyu
 * @since 2019-11-22
 */
@Service
class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements IUserRoleService {

    private final UserRoleMapper userRoleMapper;

    public UserRoleServiceImpl(UserRoleMapper userRoleMapper) {
        this.userRoleMapper = userRoleMapper;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class,
            CustomException.class})
    public boolean updateRole(Integer userId, Set<Integer> roleIds) {
//        在这里，取原角色的集合与新角色的差集进行删除
        Set<Integer> oldRoleIds = userRoleMapper.selectRoleByUserId(userId);
        oldRoleIds.stream().filter(roleId -> !roleIds.contains(roleId)).forEach(id ->
                userRoleMapper.deleteRolesById(userId, id)
        );
//        在这里，取新角色的集合与原角色的差集进行添加
        roleIds.stream().filter(roleId -> !oldRoleIds.contains(roleId)).forEach(id ->
                userRoleMapper.insert(new UserRole().setUserId(userId).setRoleId(id)
                        .setUserRoleCreatTimeStamp(new Date()).setUserRoleIsDelete(false)));
        return true;
    }
}
