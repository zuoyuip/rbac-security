package com.supergenius.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.supergenius.model.RoleAuthority;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * 角色权限中间表 Mapper 接口
 * </p>
 *
 * @author zuoyu
 * @since 2019-11-22
 */
@Repository
public interface RoleAuthorityMapper extends BaseMapper<RoleAuthority> {

    /**
     * 根据roleId查询其权限
     *
     * @param roleId -
     * @return Set<Integer>
     */
    List<Integer> selectAuthoritiesByRoleId(Serializable roleId);

    /**
     * 根据角色Id和权限Id删除数据
     * @param roleAuthorities -
     * @return boolean
     */
    boolean deleteRoleAuthorities(@Param("roleAuthorities") List<RoleAuthority> roleAuthorities);
}
