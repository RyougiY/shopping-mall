package com.naruse.shopping.portal.web.advice;

import com.naruse.shopping.common.base.result.ResultObject;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ArithmeticException.class)
    public ResultObject customException() {
        return ResultObject.getFailedBuilder().msg("算术错误！").build();
    }

    @ExceptionHandler(RuntimeException.class)
    public ResultObject globalException(RuntimeException exception) {
        return ResultObject.getFailedBuilder().msg(exception.getMessage()).build();
    }
}
