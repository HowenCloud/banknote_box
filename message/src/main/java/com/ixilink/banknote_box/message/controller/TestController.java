package com.ixilink.banknote_box.message.controller;

import com.ixilink.banknote_box.common.util.ClientType;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * description:
 * author: 张俊
 * date: 2019-11-06 17:23
 */
@RestController
@RequestMapping("/test")
@Api(value = "/test", description = "测试模块")
public class TestController {

    @RequestMapping("/")
    @ApiOperation(value = "测试",notes = "",httpMethod = "GET")
    public String hello(HttpServletRequest request){
        System.out.println("来自"+ClientType.getTypeName(request)+"端的访问");
        if (ClientType.isFromMobile(request)) System.out.println("是移动终端");
        return "hello message";
    }

}
