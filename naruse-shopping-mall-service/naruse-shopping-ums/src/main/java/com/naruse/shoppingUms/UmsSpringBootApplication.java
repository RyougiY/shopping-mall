package com.naruse.shoppingUms;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.naruse.shoppingUms.mapper")
public class UmsSpringBootApplication {
    public static void main(String[] args) {
        SpringApplication.run(UmsSpringBootApplication.class, args);
    }
}
