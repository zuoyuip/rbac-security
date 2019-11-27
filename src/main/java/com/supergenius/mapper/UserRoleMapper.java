package com.supergenius.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.supergenius.model.UserRole;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;
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
    List<Integer> selectRoleByUserId(Serializable userId);

    /**
     * 根据用户Id和角色Id删除数据
     * @param userRoles -
     * @return - boolean
     */
    boolean deleteUserRoles(@Param("userRoles") List<UserRole> userRoles);
}
