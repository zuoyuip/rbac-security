package com.supergenius.service;

import com.supergenius.model.Authority;
import com.supergenius.model.Role;
import com.supergenius.model.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.supergenius.model.vo.ContentVO;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 安全用户表 服务类
 * </p>
 *
 * @author zuoyu
 * @since 2019-11-22
 */
public interface IUserService extends IService<User> {


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
     * 根据用户Id查询对应的目录结构
     * @param userId -
     * @return List<Content>
     */
    List<ContentVO> getContentsById(Serializable userId);

    /**
     * 创建用户同时赋予其角色
     * @param user - 用户
     * @param roleIds - 角色Id
     * @return boolean
     */
    boolean save(User user, List<Integer> roleIds);
}
