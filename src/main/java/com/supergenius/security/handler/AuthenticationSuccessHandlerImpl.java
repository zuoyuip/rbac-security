package com.supergenius.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.supergenius.model.User;
import com.supergenius.model.vo.Content;
import com.supergenius.service.IUserService;
import com.supergenius.utils.Result;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author : zuoyu
 * @description : 登陆成功行为
 * @date : 2019-11-21 11:19
 **/
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Component
public class AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler {

    private final IUserService iUserService;

    public AuthenticationSuccessHandlerImpl(IUserService iUserService) {
        this.iUserService = iUserService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        User user = (User) authentication.getPrincipal();
        List<Content> contents = iUserService.getContentsById(user.getUserId());
        response.setContentType("application/json;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        ServletOutputStream servletOutputStream = response.getOutputStream();
        Result result = Result.detail("登陆成功", contents);
        byte[] bytes = new ObjectMapper().writeValueAsBytes(result);
        servletOutputStream.write(bytes);
        servletOutputStream.flush();
        servletOutputStream.close();
    }
}

