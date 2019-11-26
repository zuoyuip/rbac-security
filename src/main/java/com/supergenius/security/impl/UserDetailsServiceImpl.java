package com.supergenius.security.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.supergenius.model.User;
import com.supergenius.service.IUserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

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
        return iUserService.loadUserByUsername(userName);
    }
}
