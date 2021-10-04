package com.naruse.shopping.portal.web;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

//默认扫描当前模块下的包
@SpringBootApplication(scanBasePackages = {"com.naruse.shopping"})
@MapperScan("com.naruse.shopping.ums.mapper")
public class NaruseShoppingPortalWebApplication {
    public static void main(String[] args) {
        SpringApplication.run(NaruseShoppingPortalWebApplication.class, args);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
