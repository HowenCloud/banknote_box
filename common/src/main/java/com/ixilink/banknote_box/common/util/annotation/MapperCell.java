package com.ixilink.banknote_box.common.util.annotation;

import java.lang.annotation.*;


@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MapperCell {


    String cellName() default "";


    int order() default 0;
}
