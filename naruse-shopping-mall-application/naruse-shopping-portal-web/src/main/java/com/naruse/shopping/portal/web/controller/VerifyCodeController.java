package com.naruse.shopping.portal.web.controller;

import com.naruse.shopping.portal.web.code.VerifyCode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

@RestController
@RequestMapping("/verify-code")
public class VerifyCodeController {

    @GetMapping("/generateCode")
    public void generateCode(HttpServletResponse response) throws IOException {
        VerifyCode verifyCode = new VerifyCode();
        BufferedImage image = verifyCode.getImage();
        String text = verifyCode.getText();
        ImageIO.write(image, "JPEG", response.getOutputStream());
    }

    @GetMapping("/verify")
    public String verify(String verifyCode, HttpServletRequest request) {

        return null;
    }
}
