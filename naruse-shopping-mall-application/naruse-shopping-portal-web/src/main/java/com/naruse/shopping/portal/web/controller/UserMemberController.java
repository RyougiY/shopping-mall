package com.naruse.shopping.portal.web.controller;

import com.naruse.shopping.ums.entity.dto.UmsMemberLoginParamDTO;
import com.naruse.shopping.ums.entity.dto.UmsMemberRegisterParamDTO;
import com.naruse.shopping.ums.service.UmsMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user-member")
public class UserMemberController {
    @GetMapping("/test")
    public String test(String str) {
        return str == null ? "welcome" : str;
    }

    @Autowired
    private UmsMemberService umsMemberService;

    @GetMapping("/register")
    public String register(@RequestBody UmsMemberRegisterParamDTO umsMemberRegisterParamDTO) {
        return umsMemberService.register(umsMemberRegisterParamDTO);
    }

    @GetMapping("/login")
    public String login(@RequestBody UmsMemberLoginParamDTO umsMemberLoginParamDTO) {
        return umsMemberService.login(umsMemberLoginParamDTO);
    }
}
