package com.ixilink.banknote_box.service.controller.app;

import com.ixilink.banknote_box.common.exception.BusinessException;
import com.ixilink.banknote_box.common.model.Code;
import com.ixilink.banknote_box.common.model.Result;
import com.ixilink.banknote_box.service.parameter_model.app.SubmitDepositBox;
import com.ixilink.banknote_box.service.service.AppLineService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @description:
 * @author: 张皓峰
 * @date: 2019-12-20 9:56
 */
@Log4j2
@RestController
@RequestMapping("appLine")
@Api(value = "Android端加钞线路模块", description = "手持机加钞任务模块的接口")
public class AppLineController {

    @Resource
    private AppLineService appLineService;

    @GetMapping("lineDetails")
    @ApiOperation(value = "获取加钞网点列表",notes = "",httpMethod = "GET")
    public Result lineDetails(HttpServletRequest request, HttpServletResponse response){
        try {
            Map<String,Object> data = appLineService.lineDetails(request,response);
            return new Result(Code.SUCCESS.getCode(),Code.SUCCESS.getMessage(),data);
        }catch (BusinessException e){
            return new Result(e.getCode(), e.getErrorMessage());
        }catch (Exception e){
            return new Result(Code.SYSTEM_ERROR.getCode(),Code.SYSTEM_ERROR.getMessage());
        }
    }

    @GetMapping("depositBox")
    @ApiOperation(value = "获取今日线路加钞钞箱",notes = "",httpMethod = "GET")
    public Result depositBox(HttpServletRequest request, HttpServletResponse response){
        try {
            Map<String,Object> data = appLineService.depositBox(request,response);
            return new Result(Code.SUCCESS.getCode(),Code.SUCCESS.getMessage(),data);
        }catch (BusinessException e){
            return new Result(e.getCode(), e.getErrorMessage());
        }catch (Exception e){
            return new Result(Code.SYSTEM_ERROR.getCode(),Code.SYSTEM_ERROR.getMessage());
        }
    }

    @PatchMapping("submitDepositBox")
    @ApiOperation(value = "提交加钞“确认加钞”任务",notes = "",httpMethod = "PATCH")
    public Result submitDepositBox(@RequestBody SubmitDepositBox param, HttpServletRequest request, HttpServletResponse response){
        try {
            appLineService.submitDepositBox(param,request,response);
            return new Result(Code.SUCCESS.getCode(),Code.SUCCESS.getMessage());
        }catch (BusinessException e){
            return new Result(e.getCode(), e.getErrorMessage());
        }catch (Exception e){
            return new Result(Code.SYSTEM_ERROR.getCode(),Code.SYSTEM_ERROR.getMessage());
        }
    }

    @GetMapping("atmDetails")
    @ApiOperation(value = "已加钞ATM详情",notes = "",httpMethod = "GET")
    public Result atmDetails(Integer id, HttpServletRequest request, HttpServletResponse response){
        try {
            Map<String,Object> data = appLineService.atmDetails(id,request,response);
            return new Result(Code.SUCCESS.getCode(),Code.SUCCESS.getMessage(),data);
        }catch (BusinessException e){
            return new Result(e.getCode(), e.getErrorMessage());
        }catch (Exception e){
            return new Result(Code.SYSTEM_ERROR.getCode(),Code.SYSTEM_ERROR.getMessage());
        }
    }


}
