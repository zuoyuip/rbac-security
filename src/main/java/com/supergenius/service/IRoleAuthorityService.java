package com.supergenius.service;

import com.supergenius.model.RoleAuthority;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 角色权限中间表 服务类
 * </p>
 *
 * @author zuoyu
 * @since 2019-11-22
 */
public interface IRoleAuthorityService extends IService<RoleAuthority> {

    /**
     * 根据roleId修改其权限
     * @param roleId -
     * @param authorityIds -
     * @return boolean
     */
    boolean updateAuthorities(Integer roleId, List<Integer> authorityIds);
}
