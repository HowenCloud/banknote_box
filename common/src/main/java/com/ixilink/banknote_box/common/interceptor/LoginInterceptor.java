package com.ixilink.banknote_box.common.interceptor;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.*;
import com.ixilink.banknote_box.common.exception.BusinessException;
import com.ixilink.banknote_box.common.jwt.JwtToken;
import com.ixilink.banknote_box.common.jwt.PassToken;
import com.ixilink.banknote_box.common.model.Code;
import com.ixilink.banknote_box.common.pojo.User;
import com.ixilink.banknote_box.common.util.JsonUtil;
import com.ixilink.banknote_box.common.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;

/**
 * description: 登录拦截器
 * author: 张俊
 * date: 2019-11-05 19:19
 */
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    @Resource
    private RedisUtil redisUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        String servletPath = request.getServletPath();
        String token = request.getHeader("token");                   // 从 http 请求头中取出 token
        String platformType = request.getHeader("platformType");     //平台：  1：web端   2：Android   3：iOS  4：平板
        // 如果不是映射到方法则则直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();

        if (method.isAnnotationPresent(PassToken.class)) {
            PassToken passToken = method.getAnnotation(PassToken.class);
            if (passToken.required()) {
                return true;
            }
        }
        /*
        else if (method.isAnnotationPresent(NeedToken.class)) {
            NeedToken userLoginToken = method.getAnnotation(NeedToken.class);
            if (userLoginToken.required()) {
                verify(token,platformType);
                return true;
            }
        }*/
        else {
            verify(token,platformType);
        }

        return true;
    }

    private void verify(String token, String platformType) {
        if (platformType==null || "".equals(platformType)){
            throw new BusinessException(Code.VERIFY_ERROR.getCode(),"未知平台或设备");
        }
        // 执行认证
        if (token == null) {
            throw new BusinessException(Code.TOKEN_FIND_ERROR.getCode(),"无token，请先登录");
        }

        //验证token是否规范正确
        JwtToken.verifyToken(token);

        // 获取 token 中的 userId
        String userId = null;
        try {
            userId = JWT.decode(token).getAudience().get(0);
        } catch (JWTDecodeException j) {
            throw new BusinessException(Code.TOKEN_FIND_ERROR.getCode(),"token失效，请重新登录");
        }

        if (!redisUtil.hHasKey("users", "u-" + userId)) {
            throw new BusinessException(Code.TOKEN_FIND_ERROR.getCode(),"用户不存在，请重新登录");
        }
        else {
            String userStr = (String)redisUtil.getStringRedisTemplate().opsForHash().get("users", "u-" + userId);
            User user = (User) JsonUtil.str2Obj(userStr, User.class);
            if (user.getState().equals(2)) {
                throw new BusinessException(Code.TOKEN_FIND_ERROR.getCode(), "该账户已被注销");
            }
            if (user.getState().equals(3)) {
                throw new BusinessException(Code.TOKEN_FIND_ERROR.getCode(), "该账户已被冻结，请先解冻再尝试操作");
            }
        }

        if (!redisUtil.hHasKey("tokens", platformType + "-" + userId)){
            throw new BusinessException(Code.TOKEN_FIND_ERROR.getCode(),"无效的token，请先登录");
        }
        else {
            String online_token = (String) redisUtil.getStringRedisTemplate().opsForHash().get("tokens", platformType + "-" + userId);
            if (!token.equals(online_token)) {
                throw new BusinessException(Code.TOKEN_FIND_ERROR.getCode(),"账号在其他地方登录，被迫下线，请重新登录");
            }
        }


    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }
}
