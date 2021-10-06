package com.naruse.shopping.portal.web.controller.captchaStudying;

import com.baomidou.kaptcha.Kaptcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 验证码框架
 * 保存到session中的验证码
 * 可以改写成存到redis中
 */
@RestController
@RequestMapping("/kaptcha")
public class KaptchaController {
    @Autowired
    private Kaptcha kaptcha;

    @GetMapping("/generateCode")
    public void generateCode() {
        kaptcha.render();
    }

    @GetMapping("/verify")
    public String verify(String verifyCode) {
        try {
            boolean pass = kaptcha.validate(verifyCode);
            if (pass) {
                return "验证通过！";
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return "验证码输入错误！";
    }
}
