package com.ixilink.banknote_box.common.util.annotation;

import java.lang.annotation.*;


@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SysLogAnnotation {
	String value() default "";
}
