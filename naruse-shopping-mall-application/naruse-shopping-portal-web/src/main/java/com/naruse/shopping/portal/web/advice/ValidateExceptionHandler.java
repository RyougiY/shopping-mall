package com.naruse.shopping.portal.web.advice;

import com.naruse.shopping.common.base.result.ResultObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * 用于包装 接收的DTO的字段校验错误 信息
 */
@RestControllerAdvice
public class ValidateExceptionHandler extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        //每次只取第一个错误信息
        String validFieldExceptionMsg =
                ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage();

        return new ResponseEntity(ResultObject.builder()
                .code(101)
                .msg(validFieldExceptionMsg)
                .build()
        , HttpStatus.OK);
    }
}
