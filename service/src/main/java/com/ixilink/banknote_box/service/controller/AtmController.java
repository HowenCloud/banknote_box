package com.ixilink.banknote_box.service.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ixilink.banknote_box.common.exception.BusinessException;
import com.ixilink.banknote_box.common.model.Code;
import com.ixilink.banknote_box.common.model.Result;
import com.ixilink.banknote_box.common.pojo.EquipmentAtm;
import com.ixilink.banknote_box.service.parameter_model.equipment.FindATM;
import com.ixilink.banknote_box.service.service.AtmService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @description:
 * @author: 张俊
 * @date: 2019-11-22 16:40
 */
@Log4j2
@RestController
@RequestMapping("atm")
@Api(value = "atm", description = "设备管理")
public class AtmController {

    @Resource
    private AtmService atmService;

    @PostMapping("")
    @ApiOperation(value = "查询ATM机",notes = "",httpMethod = "POST")
    public Result getAtm(@RequestBody FindATM param, HttpServletRequest request, HttpServletResponse response){
        try {
            PageHelper.startPage(param.getPageNum(), param.getPageSize());
            List<EquipmentAtm> centerControl = atmService.getAtm(param,request,response);
            Result result = new Result(Code.SUCCESS.getCode(),Code.SUCCESS.getMessage());
            result.getData().put("total",((Page<?>) centerControl).getTotal());
            result.getData().put("centerControl",centerControl);
            return result;
        }catch (BusinessException e){
            return new Result(e.getCode(),e.getErrorMessage());
        }catch (Exception e){
            return new Result(Code.SYSTEM_ERROR.getCode(),Code.SYSTEM_ERROR.getMessage());
        }
    }


    @GetMapping("getAtmByEquipment")
    @ApiOperation(value = "查询集中控制器下的ATM机",notes = "",httpMethod = "GET")
    public Result getAtmByEquipment(@Param("controllerId") Integer controllerId, HttpServletRequest request, HttpServletResponse response){
        try {
            List<EquipmentAtm> atms = atmService.getAtmByEquipment(controllerId,request,response);
            Result result = new Result(Code.SUCCESS.getCode(),Code.SUCCESS.getMessage());
            result.getData().put("atms",atms);
            return result;
        }catch (BusinessException e){
            return new Result(e.getCode(),e.getErrorMessage());
        }catch (Exception e){
            return new Result(Code.SYSTEM_ERROR.getCode(),Code.SYSTEM_ERROR.getMessage());
        }
    }


    @GetMapping("getAtmByLine")
    @ApiOperation(value = "查询线路下的ATM机",notes = "",httpMethod = "GET")
    public Result getAtmByLine(@Param("controllerId") Integer controllerId, HttpServletRequest request, HttpServletResponse response){
        try {
            List<EquipmentAtm> atms = atmService.getAtmByLine(controllerId,request,response);
            Result result = new Result(Code.SUCCESS.getCode(),Code.SUCCESS.getMessage());
            result.getData().put("atms",atms);
            return result;
        }catch (BusinessException e){
            return new Result(e.getCode(),e.getErrorMessage());
        }catch (Exception e){
            return new Result(Code.SYSTEM_ERROR.getCode(),Code.SYSTEM_ERROR.getMessage());
        }
    }


}
