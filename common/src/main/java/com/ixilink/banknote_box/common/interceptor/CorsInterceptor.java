package com.ixilink.banknote_box.common.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * description: 跨域配置
 * author: 张俊
 * date: 2019-11-06 12:39
 */
public class CorsInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with,Authorization,Content-Type,platformType,token");
        response.setHeader("Access-Control-Allow-Methods","PUT,POST,GET,DELETE,OPTIONS,PATCH");
        response.setHeader("X-Powered-By","Jetty");
//        response.setHeader("Access-Control-Allow-Credentials","true");
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }
}
