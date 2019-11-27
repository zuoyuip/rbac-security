package com.supergenius.controller;

import com.supergenius.model.Authority;
import com.supergenius.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author : zuoyu
 * @project : management-security
 * @description : 验证权限
 * @date : 2019-11-26 18:25
 **/
@Api(value = "权限验证")
@RestController
@RequestMapping(value = "validation/")
public class ValidationController {

    private final IUserService iUserService;

    public ValidationController(IUserService iUserService) {
        this.iUserService = iUserService;
    }

    @GetMapping("system")
    @PreAuthorize("hasRole('SYSTEM_INDEX')")
    @ApiOperation(value = "获取所有权限列表（仅admin可调用）", response = Authority.class, ignoreJsonView = true)
    public ResponseEntity<List<Authority>> seeTimeStamp() {
        List<Authority> authorities = iUserService.selectAuthoritiesById(2);
        return ResponseEntity.ok(authorities);
    }

    @GetMapping("investor")
    @PreAuthorize("hasRole('INVESTOR_CONSUMER')")
    @ApiOperation(value = "验证权限（仅user、admin可调用）", response = String.class, ignoreJsonView = true)
    public ResponseEntity<String> seeInvestorConsumer() {
        return ResponseEntity.ok("您是user或admin用户");
    }

    @GetMapping("order")
    @PreAuthorize("hasRole('MEMBERS_ORDER')")
    @ApiOperation(value = "验证权限（仅opera、admin可调用）", response = String.class, ignoreJsonView = true)
    public ResponseEntity<String> seeMembersOrder() {
        return ResponseEntity.ok("您是opera或admin用户");
    }

    @GetMapping("many")
    @PreAuthorize("hasAnyRole('INVESTOR_CONSUMER', 'MEMBERS_ORDER')")
    @ApiOperation(value = "验证权限（opera、user、admin可调用）", response = String.class, ignoreJsonView = true)
    public ResponseEntity<String> seeManyRole() {
        return ResponseEntity.ok("您是opera或user或admin用户");
    }
}
