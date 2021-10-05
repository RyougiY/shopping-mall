package com.naruse.shopping.portal.web.controller;

import com.ramostear.captcha.HappyCaptcha;
import com.wf.captcha.SpecCaptcha;
import com.wf.captcha.utils.CaptchaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/easy-captcha")
public class EasyCaptchaController {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @GetMapping("/generateCode")
    public void generateCode(HttpServletRequest request, HttpServletResponse response) {
        try {
            CaptchaUtil.out(request, response);

            // 算术
//			ArithmeticCaptcha arithmeticCaptcha = new ArithmeticCaptcha(200,50);
//			// 3个数的运算
//			arithmeticCaptcha.setLen(3);
//			arithmeticCaptcha.text();
//
//			CaptchaUtil.out(arithmeticCaptcha,request,response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/verify")
    public String verify(String verifyCode, HttpServletRequest request) {
        boolean pass = CaptchaUtil.ver(verifyCode, request);
        if (pass) {
            CaptchaUtil.clear(request);
            return "验证通过！";
        }
        return "验证码输入错误！";
    }

    @GetMapping("/generateCode-redis")
    public Map<String, String> generateCodeRedis(HttpServletRequest request, HttpServletResponse response) {
        SpecCaptcha specCaptcha = new SpecCaptcha(100, 50);

        String text = specCaptcha.text();

        String uuid = UUID.randomUUID().toString();

//        String sessionId = request.getSession().getId();

        stringRedisTemplate.opsForValue().set(uuid,text);

//        try {
//            CaptchaUtil.out(specCaptcha, request, response);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        String codePicture = specCaptcha.toBase64();
        Map<String,String> map = new HashMap<>();
        map.put("uuid",uuid);
        map.put("base64",codePicture);

        return map;
    }

    @GetMapping("/verify-redis")
    public String verifyRedis(String verifyCode, HttpServletRequest request, String uuid) {
        String c = stringRedisTemplate.opsForValue().get(uuid);

        if (verifyCode.equals(c)) {
            HappyCaptcha.remove(request);
            return "通过";
        }

        return "不通过";
    }

}
