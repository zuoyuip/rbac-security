package com.supergenius.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.supergenius.model.UserRole;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.Set;

/**
 * <p>
 * 用户角色中间表 Mapper 接口
 * </p>
 *
 * @author zuoyu
 * @since 2019-11-22
 */
@Repository
public interface UserRoleMapper extends BaseMapper<UserRole> {
    /**
     * 根据userId查询其角色
     *
     * @param userId -
     * @return Set<Integer>
     */
    Set<Integer> selectRoleByUserId(Serializable userId);

    /**
     * 根据用户Id和角色Id删除数据
     *
     * @param userId - 用户Id
     * @param roleId - 角色Id
     * @return boolean
     */
    boolean deleteRolesById(@Param("userId") Serializable userId, @Param("roleId") Serializable roleId);
}
