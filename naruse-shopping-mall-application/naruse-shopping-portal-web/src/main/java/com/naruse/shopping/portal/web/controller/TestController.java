package com.naruse.shopping.portal.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/testGet")
    public String testGet(String appId, String name) {
        System.out.println("appid：" + appId);
        System.out.println("name：" + name);
        return "Hi, get!";
    }

    @PostMapping("/testPost")
    public String testPost() {
        return "Hi, post!";
    }
}
