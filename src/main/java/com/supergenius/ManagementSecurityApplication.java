package com.supergenius;

import com.supergenius.security.SecurityConfig;
import com.supergenius.config.SwaggerConfig;
import com.supergenius.config.WebConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

/**
 * @author zuoyu
 */
@MapperScan(basePackages = {"com.supergenius.mapper"})
@SpringBootApplication
@Import(value = {WebConfig.class, SwaggerConfig.class, SecurityConfig.class})
public class ManagementSecurityApplication {

    public static void main(String[] args) {
        SpringApplication.run(ManagementSecurityApplication.class, args);
    }

}
