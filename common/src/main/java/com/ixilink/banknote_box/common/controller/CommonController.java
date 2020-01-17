package com.ixilink.banknote_box.common.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.ixilink.banknote_box.common.exception.BusinessException;
import com.ixilink.banknote_box.common.model.Code;
import com.ixilink.banknote_box.common.model.Result;
import com.ixilink.banknote_box.common.util.Helper;
import com.ixilink.banknote_box.common.util.RandomUtil;
import com.ixilink.banknote_box.common.util.RedisUtil;
import com.ixilink.banknote_box.common.util.ValidateCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @description:
 * @author: 张俊
 * @date: 2019-11-09 13:50
 */
@Log4j2
@RestController
@RequestMapping("/common")
@Api(value = "/common", description = "公共模块")
public class CommonController {

    @Resource
    private RedisUtil redisUtil;

    @GetMapping("getKey")
    @ApiOperation(value = "获取加密key",notes = "",httpMethod = "GET")
    public Result getKey(HttpServletRequest request, HttpServletResponse response){
        String userId;
        try {
            String token = request.getHeader("token");
            userId = JWT.decode(token).getAudience().get(0);
        }catch (Exception e){
            userId = null;
        }

        try {
            Result result = new Result(Code.SUCCESS.getCode(),Code.SUCCESS.getMessage());
            String key = RandomUtil.generateString(16);

            if (userId != null){
                redisUtil.getStringRedisTemplate().opsForValue().set("key-u-"+userId,key,10, TimeUnit.SECONDS);
            }
            request.getSession().setAttribute("key",key);
            result.getData().put("key",key);
            return result;
        } catch (Exception e){
            return new Result(Code.SYSTEM_ERROR.getCode(),Code.SYSTEM_ERROR.getMessage());
        }
    }

    @GetMapping("getValidateCode")
    @ApiOperation(value = "获取验证码",notes = "",httpMethod = "GET")
    public void getValidateCode(HttpServletRequest reqeust, HttpServletResponse response) throws IOException {
        response.setContentType("image/jpeg");
        // 禁止图像缓存。
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);

        HttpSession session = reqeust.getSession();

        ValidateCode vCode = new ValidateCode(100, 28, 4, 100);
        session.setAttribute(Helper.SESSION_CHECKCODE, vCode.getCode());
        vCode.write(response.getOutputStream());
    }

}
