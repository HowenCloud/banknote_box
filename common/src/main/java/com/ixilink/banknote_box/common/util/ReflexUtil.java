package com.ixilink.banknote_box.common.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @description: 反射调用方法工具
 * @author: 张皓峰
 * @date: 2019-12-21 16:51
 */
public class ReflexUtil {

    public static void set(Object object,String methodName,Object value) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException {
        Method method = object.getClass().getMethod(methodName,Class.forName(value.getClass().getCanonicalName()));
        method.invoke(object, value);
    }
    public static Object get(Object object,String methodName) throws Exception {
        Method method = object.getClass().getMethod(methodName);
        return method.invoke(object);
    }

}
