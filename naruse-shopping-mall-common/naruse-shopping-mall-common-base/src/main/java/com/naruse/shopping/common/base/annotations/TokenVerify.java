package com.naruse.shopping.common.base.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 检查token是否合法
 * 默认为检查
 * 可以用在方法、类上
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface TokenVerify {
    /** 是否校验token **/
    boolean required() default true;
}
