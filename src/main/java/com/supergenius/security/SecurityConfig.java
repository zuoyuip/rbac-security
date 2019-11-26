package com.supergenius.security;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.supergenius.security.constants.SecurityConstants;
import com.supergenius.model.User;
import com.supergenius.service.IUserService;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.web.cors.CorsUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author : zuoyu
 * @description : 权限配置
 * @date : 2019-11-21 11:09
 **/
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final AuthenticationFailureHandler authenticationFailureHandler;
    private final AuthenticationSuccessHandler authenticationSuccessHandler;
    private final IUserService iUserService;

    public SecurityConfig(AuthenticationFailureHandler authenticationFailureHandler, AuthenticationSuccessHandler authenticationSuccessHandler, IUserService iUserService) {
        this.authenticationFailureHandler = authenticationFailureHandler;
        this.authenticationSuccessHandler = authenticationSuccessHandler;
        this.iUserService = iUserService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userName -> {
            LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
            userLambdaQueryWrapper.eq(User::getUserSecurityName, userName);
            return iUserService.getOne(userLambdaQueryWrapper);
        }).passwordEncoder(passwordEncoder());
        super.configure(auth);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .csrf().disable()
                .requestCache().disable()
                .authorizeRequests()
                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                .antMatchers().authenticated()
                .anyRequest().permitAll()
                .and()
                .formLogin().loginProcessingUrl(SecurityConstants.USER_LOGIN_URL)
                .usernameParameter(SecurityConstants.USER_LOGIN_USERNAME)
                .passwordParameter(SecurityConstants.USER_LOGIN_PASSWORD)
                .permitAll()
                .successHandler(authenticationSuccessHandler)
                .failureHandler(authenticationFailureHandler)
                .and()
                .rememberMe()
                .disable()
                .logout().logoutUrl(SecurityConstants.USER_LOGOUT_URL)
                .logoutSuccessHandler(new LogoutSuccessHandlerImpl()).clearAuthentication(true).permitAll()
                .and()
                .exceptionHandling()
                .accessDeniedHandler(new AccessDeniedHandlerImpl())
                .authenticationEntryPoint(new AuthenticationEntryPointImpl());
    }

    @Bean("passwordEncoder")
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }


    /**
     * 用来解决认证过的用户访问无权限资源时的异常.
     **/
    @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    static class AccessDeniedHandlerImpl implements AccessDeniedHandler {

        @Override
        public void handle(HttpServletRequest httpServletRequest,
                           HttpServletResponse httpServletResponse, AccessDeniedException e)
                throws IOException {
            httpServletResponse.setContentType("application/json;charset=utf-8");
            httpServletResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "对不起，您没有访问权限");
        }
    }

    /**
     * 用来解决匿名用户访问无权限资源时的异常.
     **/
    @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    static class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {

        @Override
        public void commence(HttpServletRequest httpServletRequest,
                             HttpServletResponse httpServletResponse, AuthenticationException e)
                throws IOException {
            httpServletResponse.setContentType("application/json;charset=utf-8");
            httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "您还没有登录，请先登录");
        }
    }

    /**
     * 注销成功的实现.
     **/
    @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    static class LogoutSuccessHandlerImpl implements LogoutSuccessHandler {

        @Override
        public void onLogoutSuccess(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse, Authentication authentication)
                throws IOException {
            httpServletResponse.setContentType("application/json;charset=utf-8");
            httpServletResponse.setStatus(HttpServletResponse.SC_OK);
            PrintWriter responseWriter = httpServletResponse.getWriter();
            responseWriter.write("{\"message\":\"注销成功\"}");
            responseWriter.flush();
            responseWriter.close();
        }
    }
}
