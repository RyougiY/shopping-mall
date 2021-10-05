package com.naruse.shopping.portal.web.controller;

import com.ramostear.captcha.HappyCaptcha;
import com.ramostear.captcha.support.CaptchaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/happy-captcha")
public class HappyCaptchaController {

    @GetMapping("/generateCode")
    public void generateCode(HttpServletRequest request, HttpServletResponse response) {
        HappyCaptcha.require(request, response)
                .type(CaptchaType.ARITHMETIC)
                .build().finish();
    }

    @GetMapping("/verify")
    public String verify(String verifyCode, HttpServletRequest request) {
        boolean pass = HappyCaptcha.verification(request, verifyCode, true);
        if (pass) {
            HappyCaptcha.remove(request);
            return "验证通过！";
        }
        return "验证码输入错误！";
    }
}
