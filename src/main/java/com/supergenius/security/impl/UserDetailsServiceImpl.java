package com.supergenius.security.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.supergenius.model.Authority;
import com.supergenius.model.User;
import com.supergenius.service.IUserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author : zuoyu
 * @project : management-security
 * @description : 认证实现
 * @date : 2019-11-26 09:53
 **/
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final IUserService iUserService;

    public UserDetailsServiceImpl(IUserService iUserService) {
        this.iUserService = iUserService;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = iUserService.loadUserByUsername(userName);
        if (user == null){
            throw new UsernameNotFoundException("用户名或密码错误");
        }
        List<Authority> authorities = iUserService.selectAuthoritiesById(user.getUserId());
        return user.setAuthorityBeans(authorities);
    }
}
