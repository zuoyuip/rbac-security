package com.supergenius.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.supergenius.exception.CustomException;
import com.supergenius.mapper.UserRoleMapper;
import com.supergenius.model.UserRole;
import com.supergenius.service.IUserRoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
    public boolean updateRole(Integer userId, List<Integer> roleIds) {
//        在这里，取原角色的集合与新角色的差集进行删除
        List<Integer> oldRoleIds = userRoleMapper.selectRoleByUserId(userId);
        List<UserRole> oldUserRoles = oldRoleIds.stream().filter(roleId -> !roleIds.contains(roleId))
                .map(id -> new UserRole(userId, id)).collect(Collectors.toList());
        if (oldUserRoles.size() > 0) {
            if (!userRoleMapper.deleteUserRoles(oldUserRoles)) {
                throw new CustomException("更新角色错误：删除");
            }
        }
//        在这里，取新角色的集合与原角色的差集进行添加
        List<UserRole> newUserRoles = roleIds.stream().filter(roleId -> !oldRoleIds.contains(roleId))
                .map(id -> new UserRole(userId, id)).collect(Collectors.toList());
        if (newUserRoles.size() > 0) {
            if (!saveBatch(newUserRoles)) {
                throw new CustomException("更新角色错误：添加");
            }
        }
        return true;
    }

    @Override
    public boolean save(UserRole entity) {
        return super.save(entity.setUserRoleCreatTimeStamp(new Date()).setUserRoleIsDelete(false));
    }

    @Override
    public boolean saveBatch(Collection<UserRole> entityList) {
        return super.saveBatch(entityList.stream().map(authority ->
                authority.setUserRoleCreatTimeStamp(new Date()).setUserRoleIsDelete(false))
                .collect(Collectors.toList()));
    }
}
