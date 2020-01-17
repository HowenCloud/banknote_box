package com.ixilink.banknote_box.service.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ixilink.banknote_box.common.exception.BusinessException;
import com.ixilink.banknote_box.common.model.Code;
import com.ixilink.banknote_box.common.model.Result;
import com.ixilink.banknote_box.common.pojo.BanknoteLine;
import com.ixilink.banknote_box.service.parameter_model.banknote_line.BanknoteLineModel;
import com.ixilink.banknote_box.service.parameter_model.banknote_line.FindLineModel;
import com.ixilink.banknote_box.service.service.BanknoteLineService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: 张俊
 * @date: 2019-11-21 16:46
 */
@Log4j2
@RestController
@RequestMapping("banknoteLine")
@Api(value = "banknoteLine", description = "线路管理")
public class BanknoteLineController {

    @Resource
    private BanknoteLineService banknoteLineService;

    @GetMapping("getUser")
    @ApiOperation(value = "获取加钞用户列表",notes = "",httpMethod = "GET")
    public Result getUser(HttpServletRequest request, HttpServletResponse response){
        try {
            List<Map<String,Object>> users = banknoteLineService.getUser(request,response);
            Result result = new Result(Code.SUCCESS.getCode(),Code.SUCCESS.getMessage());
            result.getData().put("users",users);
            return result;
        }catch (BusinessException e){
            return new Result(e.getCode(), e.getErrorMessage());
        }catch (Exception e){
            return new Result(Code.SYSTEM_ERROR.getCode(),Code.SYSTEM_ERROR.getMessage());
        }
    }

    @GetMapping("getName")
    @ApiOperation(value = "获取最新线路名称",notes = "",httpMethod = "GET")
    public Result getName(HttpServletRequest request, HttpServletResponse response){
        try {
            String name = banknoteLineService.getName(request,response);
            Result result = new Result(Code.SUCCESS.getCode(),Code.SUCCESS.getMessage());
            result.getData().put("name",name);
            return result;
        }catch (BusinessException e){
            return new Result(e.getCode(), e.getErrorMessage());
        }catch (Exception e){
            return new Result(Code.SYSTEM_ERROR.getCode(),Code.SYSTEM_ERROR.getMessage());
        }
    }

    @PutMapping("add")
    @ApiOperation(value = "添加线路",notes = "",httpMethod = "PUT")
    public Result addBanknoteLine(@RequestBody BanknoteLineModel param, HttpServletRequest request, HttpServletResponse response){
        try {
            banknoteLineService.addBanknoteLine(param,request,response);
            return new Result(Code.SUCCESS.getCode(),Code.SUCCESS.getMessage());
        }catch (BusinessException e){
            return new Result(e.getCode(), e.getErrorMessage());
        }catch (Exception e){
            return new Result(Code.SYSTEM_ERROR.getCode(),Code.SYSTEM_ERROR.getMessage());
        }
    }

    @PostMapping("find")
    @ApiOperation(value = "查询线路",notes = "",httpMethod = "POST")
    public Result getBanknoteLine(@RequestBody FindLineModel param, HttpServletRequest request, HttpServletResponse response){
        try {
            PageHelper.startPage(param.getPageNum(), param.getPageSize());
            List<BanknoteLine> banknoteLine = banknoteLineService.getBanknoteLine(param.getUsername(),request,response);
            Result result = new Result(Code.SUCCESS.getCode(),Code.SUCCESS.getMessage());
            result.getData().put("total",((Page<?>) banknoteLine).getTotal());
            result.getData().put("banknoteLine",banknoteLine);
            return result;
        }catch (BusinessException e){
            return new Result(e.getCode(),e.getErrorMessage());
        }catch (Exception e){
            return new Result(Code.SYSTEM_ERROR.getCode(),Code.SYSTEM_ERROR.getMessage());
        }
    }

    @DeleteMapping("remove/{id}")
    @ApiOperation(value = "删除线路",notes = "",httpMethod = "DELETE")
    public Result removeBanknoteLine(@PathVariable("id") Integer id, HttpServletRequest request, HttpServletResponse response){
        try {
            banknoteLineService.removeBanknoteLine(id,request,response);
            return new Result(Code.SUCCESS.getCode(),Code.SUCCESS.getMessage());
        }catch (BusinessException e){
            return new Result(e.getCode(),e.getErrorMessage());
        }catch (Exception e){
            return new Result(Code.SYSTEM_ERROR.getCode(),Code.SYSTEM_ERROR.getMessage());
        }
    }

    @PatchMapping("modify")
    @ApiOperation(value = "修改线路",notes = "",httpMethod = "PATCH")
    public Result modifyBanknoteLine(@RequestBody BanknoteLineModel param, HttpServletRequest request, HttpServletResponse response){
        try {
            banknoteLineService.modifyBanknoteLine(param,request,response);
            return new Result(Code.SUCCESS.getCode(),Code.SUCCESS.getMessage());
        }catch (BusinessException e){
            return new Result(e.getCode(),e.getErrorMessage());
        }catch (Exception e){
            return new Result(Code.SYSTEM_ERROR.getCode(),Code.SYSTEM_ERROR.getMessage());
        }
    }


}
