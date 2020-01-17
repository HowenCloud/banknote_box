package com.ixilink.banknote_box.common.jwt;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @description: 自定义token验证注解
 * @author: 张俊
 * @date: 2019-05-11 14:29
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface NeedToken {
    boolean required() default true;
}