package com.naruse.shopping.portal.web.controller.captchaStudying;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

/**
 * 手写的验证码
 * 保存在redis中，方便扩展
 * 过期时间1分钟
 */
@RestController
@RequestMapping("/verify-code")
public class VerifyCodeController {

    @GetMapping("/generateCode")
    public Map<String, String> generateCode(HttpServletRequest request) throws IOException {
        return null;
    }

    @GetMapping("/verify")
    public String verify(String uuid, String verifyCode) {
        return null;
    }
}
