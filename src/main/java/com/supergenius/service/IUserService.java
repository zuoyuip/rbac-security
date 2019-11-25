package com.supergenius.service;

import com.supergenius.model.Role;
import com.supergenius.model.User;
import com.baomidou.mybatisplus.extension.service.IService;

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
     * 创建一个用户并给予角色
     * @param entity -
     * @param roles -
     * @return - boolean
     */
    boolean save(User entity, List<Role> roles);
}
