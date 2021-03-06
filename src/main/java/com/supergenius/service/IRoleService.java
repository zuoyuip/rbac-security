package com.supergenius.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.supergenius.model.Role;

import java.util.List;

/**
 * <p>
 * 角色表 服务类
 * </p>
 *
 * @author zuoyu
 * @since 2019-11-22
 */
public interface IRoleService extends IService<Role> {

    /**
     * 创建一个角色并给予权限
     *
     * @param role         -
     * @param authorityIds -
     * @return - boolean
     */
    boolean save(Role role, List<Integer> authorityIds);


}
