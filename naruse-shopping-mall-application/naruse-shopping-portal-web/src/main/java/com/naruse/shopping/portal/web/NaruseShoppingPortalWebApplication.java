package com.naruse.shopping.portal.web;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//默认扫描当前模块下的包
@SpringBootApplication(scanBasePackages = {"com.naruse.shopping"})
@MapperScan("com.naruse.shopping.ums.mapper")
public class NaruseShoppingPortalWebApplication {
    public static void main(String[] args) {
        SpringApplication.run(NaruseShoppingPortalWebApplication.class, args);
    }
}
