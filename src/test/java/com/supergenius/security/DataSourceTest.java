package com.supergenius.security;

import com.supergenius.mapper.AuthorityMapper;
import com.supergenius.model.Authority;
import com.supergenius.model.Role;
import com.supergenius.model.User;
import com.supergenius.model.vo.Content;
import com.supergenius.service.IAuthorityService;
import com.supergenius.service.IRoleAuthorityService;
import com.supergenius.service.IRoleService;
import com.supergenius.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author : zuoyu
 * @project : management-security
 * @description : 数据库测试
 * @date : 2019-11-25 10:39
 **/
@Slf4j
class DataSourceTest extends ManagementSecurityApplicationTests {

    @Autowired
    private IAuthorityService iAuthorityService;

    @Autowired
    private IRoleService iRoleService;

    @Autowired
    private IUserService iUserService;

    @Autowired
    private IRoleAuthorityService iRoleAuthorityService;

    @Autowired
    private AuthorityMapper authorityMapper;

    @Test
    void authorityWrite() {
        List<Authority> authorities = Arrays.asList(
                new Authority("SYSTEM_INDEX", "首页", "系统首页", "system/index"),
                new Authority("ACCOUNT_SETTING", "首页", "账户设置", "account/setting"),
                new Authority("ROTATIONS_ENTREPRENEURIAL", "首页", "轮播图-创业端管理", "rotations/entrepreneurial"),
                new Authority("ROTATIONS_INVESTMENT", "首页", "轮播图-投资端管理", "rotations/investment"),
                new Authority("STARTUP_PROJECT", "项目管理", "创业项目管理", "project/startup"),
                new Authority("RESALE_PROJECT", "项目管理", "转售项目管理", "project/resale"),
                new Authority("INDUSTRY_TAG", "项目管理", "行业标签管理", "industry/tag"),
                new Authority("MEMBERS_ORDER", "订单管理", "会员订单管理", "order/members"),
                new Authority("SPONSOR_ORDER", "订单管理", "天才保荐人订单管理", "order/sponsor"),
                new Authority("REFEREES_ORDER", "订单管理", "天才推荐人订单管理", "order/referees"),
                new Authority("CONFERENCE_ORDER", "订单管理", "会议室预约订单管理", "order/conference"),
                new Authority("ROADSHOW_ORDER", "订单管理", "路演活动订单管理", "order/roadshow"),
                new Authority("TRAINING_ORDER", "订单管理", "企业培训订单管理", "order/training"),
                new Authority("VIDEO_ORDER", "订单管理", "视频订单管理", "order/video"),
                new Authority("ENTREPRENEUR_CONSUMER", "用户管理", "创业者会员管理", "consumer/entrepreneur"),
                new Authority("INVESTOR_CONSUMER", "用户管理", "投资者会员管理", "consumer/investor"),
                new Authority("SPONSOR_CONSUMER", "用户管理", "天才保荐人管理", "consumer/sponsor"),
                new Authority("REFERRER_CONSUMER", "用户管理", "天才推荐人管理", "consumer/referrer"),
                new Authority("CONVENER_CONSUMER", "用户管理", "天才召集人管理", "consumer/convener"),
                new Authority("FOREIGNER_CONSUMER", "用户管理", "非大陆会员管理", "consumer/foreigner"),
                new Authority("ORDINARY_CONSUMER", "用户管理", "普通用户管理", "consumer/ordinary"),
                new Authority("CONTENT_INCUBATOR", "孵化器管理", "孵化器内容管理", "incubator/content"),
                new Authority("COMPANY_INCUBATOR", "孵化器管理", "入住企业管理", "incubator/company"),
                new Authority("ENTREPRENEURIAL_INCUBATOR", "孵化器管理", "创业端轮播图管理", "incubator/rotations/entrepreneurial"),
                new Authority("INVESTMENT_INCUBATOR", "孵化器管理", "投资端轮播图管理", "incubator/rotations/investment"),
                new Authority("CONFERENCE_INCUBATOR", "孵化器管理", "会议室管理", "incubator/conference"),
                new Authority("ROADSHOW_INCUBATOR", "孵化器管理", "路演管理", "incubator/roadshow"),
                new Authority("COURSE_INCUBATOR", "孵化器管理", "课程管理", "incubator/course"),
                new Authority("CONTENT_DYNAMIC", "动态管理", "动态内容管理", "dynamic/content"),
                new Authority("CONTENT_FEEDBACK", "反馈管理", "反馈内容管理", "feedback/content"),
                new Authority("INVESTOR_FEEDBACK", "反馈管理", "举报投资人管理", "feedback/investor"),
                new Authority("PROJECT_FEEDBACK", "反馈管理", "举报项目管理", "feedback/project"),
                new Authority("INFORMATION_FEEDBACK", "反馈管理", "举报消息管理", "feedback/information"),
                new Authority("DYNAMIC_FEEDBACK", "反馈管理", "举报动态管理", "feedback/dynamic"),
                new Authority("TAG_FEEDBACK", "反馈管理", "举报标签管理", "feedback/tag"),
                new Authority("SMS_SETTING", "设置管理", "短信消息管理", "setting/sms"),
                new Authority("STATION_SETTING", "设置管理", "站内消息管理", "setting/station"),
                new Authority("USER_MANAGEMENT", "权限管理", "成员管理", "management/user"),
                new Authority("ROLE_MANAGEMENT", "权限管理", "角色管理", "management/role")
        );
        boolean isOK = iAuthorityService.saveBatch(authorities);
        log.info("result:\t" + isOK);
    }

    @Test
    void makeAnyMouse() {
        new Authority("ROLE_MANAGEMENT", "权限管理", "角色管理", "management/role");
    }

    @Test
    void makeRoleAdmin() {
        List<Integer> authorityIds = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10,
                11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30,
                31, 32, 33, 34, 35, 36, 37, 38, 39);
        boolean isOK = iRoleService.save(new Role("Admin"), authorityIds);
        log.info("result:\t" + isOK);
    }

    @Test
    void makeRoleUser() {
        List<Integer> authorityIds = Arrays.asList(16, 17, 18, 19, 20, 21);
        boolean isOK = iRoleService.save(new Role("User"), authorityIds);
        log.info("result:\t" + isOK);
    }

    @Test
    void makeRoleOpera() {
        List<Integer> authorityIds = Arrays.asList(8, 9, 10, 11, 12, 13);
        boolean isOK = iRoleService.save(new Role("Opera"), authorityIds);
        log.info("result:\t" + isOK);
    }

    @Test
    void makeRoleIncubator() {
        List<Integer> authorityIds = Arrays.asList(22, 23, 24, 25, 26, 27, 28);
        boolean isOK = iRoleService.save(new Role("Incubator"), authorityIds);
        log.info("result:\t" + isOK);
    }

    @Test
    void makeUserOperaIncubator() {
        User user = new User("opera", "123456", "Opera", "1003", "18739100873", "super@foxmail.com");
        boolean isOK = iUserService.save(user, Arrays.asList(3, 4));
        log.info("result:\t" + isOK);
    }


    @Test
    void makeUserAdmin() {
        User user = new User("admin", "iPadAir", "admin", "1001", "15838271463", "zuoyuip@foxmail.com");
        boolean isOK = iUserService.save(user);
        log.info("result:\t" + isOK);
    }

    @Test
    void makeUser() {
        User user = new User("user", "123456", "user", "1002", "13849069391", "youyiip@foxmail.com");
        boolean isOK = iUserService.save(user, Collections.singletonList(2));
        log.info("result:\t" + isOK);
    }

    @Test
    void updateAuthorities() {
        List<Integer> authorityIds = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10,
                11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30,
                31, 32, 33, 34, 35, 36, 37, 38, 39);
        boolean isOK = iRoleAuthorityService.updateAuthorities(1, authorityIds);
        log.info("result:\t" + isOK);
    }

    @Test
    void loginAuthorities() {
        iUserService.loadUserByUsername("admin").getAuthorities();
    }

    @Test
    void userMenus() {
        List<Content> contents = iUserService.getContentsById(2);
        contents.forEach(System.out::println);
    }

    @Test
    void testMenus() {
        boolean isHave = authorityMapper.isExistsByAuthorityNameOrMenu("SYSTEM_INDEX", "haha");
        System.out.println(isHave);
    }

    @Test
    void testSelectRoles(){
        iUserService.selectRolesById(5).forEach(System.out::println);
    }

}
