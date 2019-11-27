package com.supergenius.mapper;

import com.supergenius.model.Authority;
import com.supergenius.model.Role;
import com.supergenius.model.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.supergenius.model.vo.ContentStructure;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 安全用户表 Mapper 接口
 * </p>
 *
 * @author zuoyu
 * @since 2019-11-22
 */
@Repository
public interface UserMapper extends BaseMapper<User> {

    /**
     * 根据用户名查询是否有该记录
     * @param userSecurityName -用户名
     * @return - boolean
     */
    boolean isExistsByUserSecurityName(String userSecurityName);

    /**
     * 根据用户名称查询该用户
     * @param userName - 用户名
     * @return User
     */
    User loadUserByUsername(String userName);

    /**
     * 根据Id查询对应权限
     * @param userId -
     * @return List<String>
     */
    List<Authority> selectAuthoritiesById(Serializable userId);

    /**
     * 根据Id查询对应的角色
     * @param userId -
     * @return - List<Role>
     */
    List<Role> selectRolesById(Serializable userId);

    /**
     * 根据Id查询菜单
     * @param userId -
     * @return -
     */
    List<ContentStructure> getContentStructuresByUserId(Serializable userId);
}
