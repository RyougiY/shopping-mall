package com.naruse.shopping.portal.web.controller;

import com.naruse.shopping.common.base.result.ResultObject;
import com.naruse.shopping.ums.entity.dto.UmsMemberLoginParamDTO;
import com.naruse.shopping.ums.entity.dto.UmsMemberRegisterParamDTO;
import com.naruse.shopping.ums.service.UmsMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/user-member")
public class UserMemberController {
    @GetMapping("/test")
    public String test(String str) {
        return str == null ? "welcome" : str;
    }

    @Autowired
    private UmsMemberService umsMemberService;

    @PostMapping("/register")
    public ResultObject register(@RequestBody @Valid UmsMemberRegisterParamDTO umsMemberRegisterParamDTO) {
        String resultStr = umsMemberService.register(umsMemberRegisterParamDTO);
        return ResultObject.getSuccessBuilder().data(resultStr).build();
    }

    @PostMapping("/login")
    public ResultObject login(@RequestBody @Valid UmsMemberLoginParamDTO umsMemberLoginParamDTO) {
        String resultStr = umsMemberService.login(umsMemberLoginParamDTO);
        return ResultObject.getSuccessBuilder().data(resultStr).build();
    }
}
