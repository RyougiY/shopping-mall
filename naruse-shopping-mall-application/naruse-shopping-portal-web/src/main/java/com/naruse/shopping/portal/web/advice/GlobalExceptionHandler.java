package com.naruse.shopping.portal.web.advice;

import com.baomidou.kaptcha.exception.KaptchaException;
import com.baomidou.kaptcha.exception.KaptchaIncorrectException;
import com.baomidou.kaptcha.exception.KaptchaNotFoundException;
import com.baomidou.kaptcha.exception.KaptchaTimeoutException;
import com.naruse.shopping.common.base.exceptions.ShoppingMallBusinessException;
import com.naruse.shopping.common.base.exceptions.ShoppingMallServerException;
import com.naruse.shopping.common.base.result.ResultObject;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ArithmeticException.class)
    public ResultObject customException() {
        return ResultObject.getFailedBuilder().msg("算术错误！").build();
    }

    @ExceptionHandler(ShoppingMallBusinessException.class)
    public ResultObject globalException(ShoppingMallBusinessException exception) {
        return ResultObject.getFailedBuilder().msg(exception.getMessage()).build();
    }

    @ExceptionHandler(ShoppingMallServerException.class)
    public ResultObject serverException(ShoppingMallServerException exception) {
        return ResultObject.getFailedBuilder().msg(exception.getMessage()).build();
    }

    @ExceptionHandler(KaptchaException.class)
    public ResultObject kaptchaException(KaptchaException e) {
        String message = null;
        if (e instanceof KaptchaIncorrectException) {
            message = "验证码错误";
        }else if (e instanceof KaptchaTimeoutException) {
            message = "验证码已过期";
        }else if (e instanceof KaptchaNotFoundException) {
            message = "验证码已被清除！";
        }
        return ResultObject.getFailedBuilder().msg(message).build();
    }
}
