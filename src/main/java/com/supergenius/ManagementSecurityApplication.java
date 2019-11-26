package com.supergenius;

import com.supergenius.config.SwaggerConfig;
import com.supergenius.config.WebConfig;
import com.supergenius.security.SecurityConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author zuoyu
 */
@EnableTransactionManagement
@MapperScan(basePackages = {"com.supergenius.mapper"})
@SpringBootApplication
@Import(value = {WebConfig.class, SwaggerConfig.class, SecurityConfig.class})
public class ManagementSecurityApplication {

    public static void main(String[] args) {
        SpringApplication.run(ManagementSecurityApplication.class, args);
    }

}
