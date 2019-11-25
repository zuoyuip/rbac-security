package com.supergenius.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.supergenius.model.Role;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

/**
 * <p>
 * 角色表 Mapper 接口
 * </p>
 *
 * @author zuoyu
 * @since 2019-11-22
 */
@Repository
public interface RoleMapper extends BaseMapper<Role> {

    /**
     * 根据角色Id查询是否有该记录
     *
     * @param roleId - 角色Id
     * @return - boolean
     */
    boolean isExistsByRoleId(Serializable roleId);

    /**
     * 根据角色名称查询是否有该记录
     * @param roleName - 角色名称
     * @return - boolean
     */
    boolean isExistsByRoleName(String roleName);
}
