package com.ixilink.banknote_box.service.controller;

import com.github.pagehelper.Page;
import com.ixilink.banknote_box.common.exception.BusinessException;
import com.ixilink.banknote_box.common.model.Code;
import com.ixilink.banknote_box.common.model.Result;
import com.ixilink.banknote_box.common.model.UserSimple;
import com.ixilink.banknote_box.common.pojo.InLibraryTask;
import com.ixilink.banknote_box.service.parameter_model.banknote_line.FindLineModel;
import com.ixilink.banknote_box.service.parameter_model.in_library.FindAtmModel;
import com.ixilink.banknote_box.service.parameter_model.in_library.FindBoxModel;
import com.ixilink.banknote_box.service.parameter_model.in_library.FindTaskModel;
import com.ixilink.banknote_box.service.parameter_model.in_library.SupplementBox;
import com.ixilink.banknote_box.service.service.InLibraryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
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
 * @author: 张皓峰
 * @date: 2020-01-10 14:20
 */
@Log4j2
@RestController
@RequestMapping("inLibrary")
@Api(value = "钞箱入库", description = "钞箱入库模块")
public class InLibraryController {

    @Resource
    private InLibraryService inLibraryService;



    @PostMapping("findTask")
    @ApiOperation(value = "查询入库任务",notes = "",httpMethod = "POST")
    public Result findTask(@RequestBody FindTaskModel param, HttpServletRequest request, HttpServletResponse response){
        try {
            List<InLibraryTask> tasks = inLibraryService.findTask(param,request,response);
            Result result = new Result(Code.SUCCESS.getCode(), Code.SUCCESS.getMessage());
            result.getData().put("total",((Page<?>) tasks).getTotal());
            result.getData().put("list",tasks);
            return result;
        }catch (BusinessException e){
            return new Result(e.getCode(), e.getErrorMessage());
        }catch (Exception e){
            return new Result(Code.SYSTEM_ERROR.getCode(),Code.SYSTEM_ERROR.getMessage());
        }
    }

    @GetMapping("findTaskDetails")
    @ApiOperation(value = "查看任务详情",notes = "",httpMethod = "GET")
    @ApiImplicitParam(name = "id", value = "任务id", required = true, dataType = "String", paramType="query")
    public Result findInTaskDetails(Integer id, HttpServletRequest request, HttpServletResponse response){
        try {
            Map<String,Object> task = inLibraryService.findInTaskDetails(id,request,response);
            return new Result(Code.SUCCESS.getCode(), Code.SUCCESS.getMessage(),task);
        }catch (BusinessException e){
            return new Result(e.getCode(), e.getErrorMessage());
        }catch (Exception e){
            return new Result(Code.SYSTEM_ERROR.getCode(),Code.SYSTEM_ERROR.getMessage());
        }
    }

    @GetMapping("getLineUser")
    @ApiOperation(value = "获取任务下的加钞人员下拉列表",notes = "",httpMethod = "GET")
    @ApiImplicitParam(name = "id", value = "任务id", required = true, dataType = "String", paramType="query")
    public Result getLineUser(Integer id, HttpServletRequest request, HttpServletResponse response){
        try {
            List<UserSimple> userList = inLibraryService.getLineUser(id,request,response);
            Result result = new Result(Code.SUCCESS.getCode(), Code.SUCCESS.getMessage());
            result.getData().put("userList",userList);
            return result;
        }catch (BusinessException e){
            return new Result(e.getCode(), e.getErrorMessage());
        }catch (Exception e){
            return new Result(Code.SYSTEM_ERROR.getCode(),Code.SYSTEM_ERROR.getMessage());
        }
    }

    @PostMapping("findLine")
    @ApiOperation(value = "查询入库线路列表",notes = "",httpMethod = "POST")
    public Result findInLine(@RequestBody FindLineModel param, HttpServletRequest request, HttpServletResponse response){
        try {
            List<Map<String,Object>> lines = inLibraryService.findInLine(param,request,response);
            Result result = new Result(Code.SUCCESS.getCode(), Code.SUCCESS.getMessage());
            result.getData().put("total",((Page<?>) lines).getTotal());
            result.getData().put("list",lines);
            return result;
        }catch (BusinessException e){
            return new Result(e.getCode(), e.getErrorMessage());
        }catch (Exception e){
            return new Result(Code.SYSTEM_ERROR.getCode(),Code.SYSTEM_ERROR.getMessage());
        }
    }

    @GetMapping("findLineDetails")
    @ApiOperation(value = "查看线路详情",notes = "",httpMethod = "GET")
    @ApiImplicitParam(name = "id", value = "线路id", required = true, dataType = "String", paramType="query")
    public Result findInLineDetails(Integer id, HttpServletRequest request, HttpServletResponse response){
        try {
            Map<String,Object> task = inLibraryService.findInLineDetails(id,request,response);
            return new Result(Code.SUCCESS.getCode(), Code.SUCCESS.getMessage(),task);
        }catch (BusinessException e){
            return new Result(e.getCode(), e.getErrorMessage());
        }catch (Exception e){
            return new Result(Code.SYSTEM_ERROR.getCode(),Code.SYSTEM_ERROR.getMessage());
        }
    }

    @PostMapping("findAtm")
    @ApiOperation(value = "查询入库线路下的ATM列表",notes = "",httpMethod = "POST")
    public Result findAtm(@RequestBody FindAtmModel param, HttpServletRequest request, HttpServletResponse response){
        try {
            List<Map<String,Object>> lines = inLibraryService.findAtm(param,request,response);
            Result result = new Result(Code.SUCCESS.getCode(), Code.SUCCESS.getMessage());
            result.getData().put("total",((Page<?>) lines).getTotal());
            result.getData().put("list",lines);
            return result;
        }catch (BusinessException e){
            return new Result(e.getCode(), e.getErrorMessage());
        }catch (Exception e){
            return new Result(Code.SYSTEM_ERROR.getCode(),Code.SYSTEM_ERROR.getMessage());
        }
    }

    @PostMapping("findNoDepositAtm")
    @ApiOperation(value = "查询未加钞的ATM列表",notes = "",httpMethod = "POST")
    public Result findNoDepositAtm(@RequestBody FindAtmModel param, HttpServletRequest request, HttpServletResponse response){
        try {
            List<Map<String,Object>> lines = inLibraryService.findNoDepositAtm(param,request,response);
            Result result = new Result(Code.SUCCESS.getCode(), Code.SUCCESS.getMessage());
            result.getData().put("total",((Page<?>) lines).getTotal());
            result.getData().put("list",lines);
            return result;
        }catch (BusinessException e){
            return new Result(e.getCode(), e.getErrorMessage());
        }catch (Exception e){
            return new Result(Code.SYSTEM_ERROR.getCode(),Code.SYSTEM_ERROR.getMessage());
        }
    }

    @PostMapping("findNoDepositBox")
    @ApiOperation(value = "查询未加钞的钞箱列表",notes = "",httpMethod = "POST")
    public Result findNoDepositBox(@RequestBody FindBoxModel param, HttpServletRequest request, HttpServletResponse response){
        try {
            List<Map<String,Object>> lines = inLibraryService.findNoDepositBox(param,request,response);
            Result result = new Result(Code.SUCCESS.getCode(), Code.SUCCESS.getMessage());
            result.getData().put("total",((Page<?>) lines).getTotal());
            result.getData().put("list",lines);
            return result;
        }catch (BusinessException e){
            return new Result(e.getCode(), e.getErrorMessage());
        }catch (Exception e){
            return new Result(Code.SYSTEM_ERROR.getCode(),Code.SYSTEM_ERROR.getMessage());
        }
    }

    @GetMapping("getBox")
    @ApiOperation(value = "接收线路钞箱",notes = "",httpMethod = "GET")
    @ApiImplicitParam(name = "id", value = "线路id", required = true, dataType = "String", paramType="query")
    public Result getBox(Integer id, HttpServletRequest request, HttpServletResponse response){
        try {
            inLibraryService.getBox(id,request,response);
            return new Result(Code.SUCCESS.getCode(), Code.SUCCESS.getMessage());
        }catch (BusinessException e){
            return new Result(e.getCode(), e.getErrorMessage());
        }catch (Exception e){
            return new Result(Code.SYSTEM_ERROR.getCode(),Code.SYSTEM_ERROR.getMessage());
        }
    }

    @GetMapping("findHandoverTask")
    @ApiOperation(value = "查询平板交接任务列表",notes = "",httpMethod = "GET")
    public Result findHandoverTask(HttpServletRequest request, HttpServletResponse response){
        try {
            List<InLibraryTask> lines = inLibraryService.findHandoverTask(request,response);
            Result result = new Result(Code.SUCCESS.getCode(), Code.SUCCESS.getMessage());
            result.getData().put("list",lines);
            return result;
        }catch (BusinessException e){
            return new Result(e.getCode(), e.getErrorMessage());
        }catch (Exception e){
            return new Result(Code.SYSTEM_ERROR.getCode(),Code.SYSTEM_ERROR.getMessage());
        }
    }

    @GetMapping("findHandoverLine")
    @ApiOperation(value = "查询平板交接线路列表",notes = "",httpMethod = "GET")
    public Result findHandoverLine(Integer taskId, HttpServletRequest request, HttpServletResponse response){
        try {
            List<Map<String,Object>> lines = inLibraryService.findHandoverLine(taskId,request,response);
            Result result = new Result(Code.SUCCESS.getCode(), Code.SUCCESS.getMessage());
            result.getData().put("list",lines);
            return result;
        }catch (BusinessException e){
            return new Result(e.getCode(), e.getErrorMessage());
        }catch (Exception e){
            return new Result(Code.SYSTEM_ERROR.getCode(),Code.SYSTEM_ERROR.getMessage());
        }
    }

    @GetMapping("findHandoverTakeBox")
    @ApiOperation(value = "查询平板入库卸出钞箱详情",notes = "",httpMethod = "GET")
    public Result findHandoverTakeBox(Integer lineId, HttpServletRequest request, HttpServletResponse response){
        try {
            List<Map<String,Object>> lines = inLibraryService.findHandoverTakeBox(lineId,request,response);
            Result result = new Result(Code.SUCCESS.getCode(), Code.SUCCESS.getMessage());
            result.getData().put("list",lines);
            return result;
        }catch (BusinessException e){
            return new Result(e.getCode(), e.getErrorMessage());
        }catch (Exception e){
            return new Result(Code.SYSTEM_ERROR.getCode(),Code.SYSTEM_ERROR.getMessage());
        }
    }

    @GetMapping("findHandoverNoDeposit")
    @ApiOperation(value = "查询平板入库未加钞箱详情",notes = "",httpMethod = "GET")
    public Result findHandoverDeposit(Integer lineId, HttpServletRequest request, HttpServletResponse response){
        try {
            Map<String, Object> data = inLibraryService.findHandoverNoDeposit(lineId,request,response);
            return new Result(Code.SUCCESS.getCode(), Code.SUCCESS.getMessage(),data);
        }catch (BusinessException e){
            return new Result(e.getCode(), e.getErrorMessage());
        }catch (Exception e){
            return new Result(Code.SYSTEM_ERROR.getCode(),Code.SYSTEM_ERROR.getMessage());
        }
    }

    @PatchMapping("supplement")
    @ApiOperation(value = "钞箱补录",notes = "",httpMethod = "PATCH")
    public Result supplementIn(@RequestBody SupplementBox param, HttpServletRequest request, HttpServletResponse response){
        try {
            inLibraryService.supplementIn(param,request,response);
            return new Result(Code.SUCCESS.getCode(), Code.SUCCESS.getMessage());
        }catch (BusinessException e){
            return new Result(e.getCode(), e.getErrorMessage());
        }catch (Exception e){
            return new Result(Code.SYSTEM_ERROR.getCode(),Code.SYSTEM_ERROR.getMessage());
        }
    }

}
