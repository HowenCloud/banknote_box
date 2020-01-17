package com.ixilink.banknote_box.login.controller;

import com.ixilink.banknote_box.common.exception.BusinessException;
import com.ixilink.banknote_box.common.model.Code;
import com.ixilink.banknote_box.common.model.Result;
import com.ixilink.banknote_box.login.parameter_model.LoginModel;
import com.ixilink.banknote_box.login.service.LoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @description: 登录模块
 * @author 张俊
 * @date 2019-11-07 15:29
 */
@Log4j2
@RestController
@Api(value = "/", description = "登录模块")
@RequestMapping("/")
public class LoginController {

    @Resource
    private LoginService loginService;

    @PostMapping(value = "login")
    @ResponseBody
    @ApiOperation(value = "登录",notes = "",httpMethod = "POST")
    public Result login(@RequestBody LoginModel loginModel, HttpServletRequest request, HttpServletResponse response){
        try {
            String token = loginService.login(loginModel, request);
            Result result = new Result(Code.SUCCESS.getCode(),Code.SUCCESS.getMessage());
            result.getData().put("token",token);
            result.getData().put("needLogin",request.getSession().getAttribute("needLogin"));
            return result;
        }catch (BusinessException e){
            return new Result(e.getCode(),e.getErrorMessage());
        }catch (Exception e){
            return new Result(Code.SYSTEM_ERROR.getCode(),Code.SYSTEM_ERROR.getMessage());
        }
    }

    @PostMapping(value = "partnerLogin")
    @ResponseBody
    @ApiOperation(value = "二次登录",notes = "",httpMethod = "POST")
    public Result partnerLogin(@RequestBody LoginModel loginModel, HttpServletRequest request, HttpServletResponse response){
        try {
            String token = loginService.partnerLogin(loginModel,request);
            Result result = new Result(Code.SUCCESS.getCode(),Code.SUCCESS.getMessage());
            result.getData().put("token",token);
            result.getData().put("needLogin",2);
            return result;
        }catch (BusinessException e){
            return new Result(e.getCode(),e.getErrorMessage());
        }catch (Exception e){
            return new Result(Code.SYSTEM_ERROR.getCode(),Code.SYSTEM_ERROR.getMessage());
        }
    }

    @DeleteMapping(value = "logout")
    @ResponseBody
    @ApiOperation(value = "退出登录",notes = "",httpMethod = "DELETE")
    public Result logout(HttpServletRequest request, HttpServletResponse response){
        try {
            loginService.logout(request);
            return new Result(Code.SUCCESS.getCode(),Code.SUCCESS.getMessage());
        }catch (BusinessException e){
            return new Result(e.getCode(),e.getErrorMessage());
        }catch (Exception e){
            return new Result(Code.SYSTEM_ERROR.getCode(),Code.SYSTEM_ERROR.getMessage());
        }
    }


}
