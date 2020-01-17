package com.ixilink.banknote_box.common.interceptor;

import com.ixilink.banknote_box.common.model.Code;
import com.ixilink.banknote_box.common.model.Result;
import com.ixilink.banknote_box.common.util.Helper;
import com.ixilink.banknote_box.common.util.JsonUtil;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * description:
 * author: 张俊
 * date: 2019-11-05 17:50
 */
public class ImageInterceptor implements HandlerInterceptor {



    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object object) throws Exception {
        // 如果不是映射到方法则进入页面验证
        if (!(object instanceof HandlerMethod)) {
            String servletPath = httpServletRequest.getServletPath();
            if (servletPath.contains("/images/") || servletPath.contains("/files/")) {
                String token = httpServletRequest.getHeader(Helper.SESSION_LOGIN_TOKEN);
                if (token == null || "".equals(token)) {
                    PrintWriter out = httpServletResponse.getWriter();
                    out.write(JsonUtil.obj2str(new Result(Code.NO_SECURITY.getCode(), Code.NO_SECURITY.getMessage())));
                    out.flush();
                    return false;
                }
            }
        }

        return true;
    }


    @Override
    public void postHandle(HttpServletRequest httpServletRequest,
                           HttpServletResponse httpServletResponse,
                           Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest,
                                HttpServletResponse httpServletResponse,
                                Object o, Exception e) throws Exception {
    }
}
