package com.supergenius.service;

import com.supergenius.model.UserRole;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Set;

/**
 * <p>
 * 用户角色中间表 服务类
 * </p>
 *
 * @author zuoyu
 * @since 2019-11-22
 */
public interface IUserRoleService extends IService<UserRole> {

    /**
     * 根据userId修改其角色
     * @param userId -
     * @param roleIds -
     * @return - boolean
     */
    boolean updateRole(Integer userId, Set<Integer> roleIds);
}
