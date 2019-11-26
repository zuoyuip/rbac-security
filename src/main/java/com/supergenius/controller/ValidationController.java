package com.supergenius.controller;

import com.supergenius.model.Authority;
import com.supergenius.service.IUserService;
import org.springframework.http.ResponseEntity;
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
@RestController
@RequestMapping("validation")
public class ValidationController {

    private final IUserService iUserService;

    public ValidationController(IUserService iUserService) {
        this.iUserService = iUserService;
    }

    @GetMapping
    public ResponseEntity<List<Authority>> seeTimeStamp(){
        List<Authority> authorities = iUserService.selectAuthoritiesById(2);
        return ResponseEntity.ok(authorities);
    }
}
