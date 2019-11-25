package com.supergenius.security.handler;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author : zuoyu
 * @description : 登陆失败的行为
 * @date : 2019-11-21 11:14
 **/
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Component
public class AuthenticationFailureHandlerImpl implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        if (exception.fillInStackTrace().getClass() == LockedException.class) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "您的账户已被锁定");
            return;
        }
        if (exception.fillInStackTrace().getClass() == DisabledException.class) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "您的账户已被禁用");
            return;
        }
        if (exception.fillInStackTrace().getClass() == AccountExpiredException.class) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "您的账户已过期");
            return;
        }
        if (exception.fillInStackTrace().getClass() == CredentialsExpiredException.class) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "您的凭证已过期");
            return;
        }
        if (exception.fillInStackTrace().getClass() == InternalAuthenticationServiceException.class) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "登录失败，密码或帐号错误");
            return;
        }
        if (exception.fillInStackTrace().getClass() == BadCredentialsException.class) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "登录失败，密码或帐号错误");
            return;
        }
        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "登录功能异常");
    }
}
