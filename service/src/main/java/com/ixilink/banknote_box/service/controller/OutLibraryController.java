package com.ixilink.banknote_box.service.controller;

import com.github.pagehelper.Page;
import com.ixilink.banknote_box.common.exception.BusinessException;
import com.ixilink.banknote_box.common.model.Code;
import com.ixilink.banknote_box.common.model.Result;
import com.ixilink.banknote_box.common.pojo.OutLibraryLine;
import com.ixilink.banknote_box.common.pojo.OutLibraryTask;
import com.ixilink.banknote_box.common.util.JsonUtil;
import com.ixilink.banknote_box.service.parameter_model.banknote_line.FindLineModel;
import com.ixilink.banknote_box.service.parameter_model.out_library.FindHandoverLineModel;
import com.ixilink.banknote_box.service.parameter_model.out_library.FindLineAtmModel;
import com.ixilink.banknote_box.service.parameter_model.out_library.FindTaskModel;
import com.ixilink.banknote_box.service.parameter_model.out_library.LibraryLineModel;
import com.ixilink.banknote_box.service.service.OutLibraryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: 张皓峰
 * @date: 2019-12-04 15:05
 */
@Log4j2
@RestController
@RequestMapping("outLibrary")
@Api(value = "钞箱出库", description = "钞箱出库模块")
public class OutLibraryController {

    @Resource
    private OutLibraryService outLibraryService;

    @PutMapping("importExcel")
    @ApiOperation(value = "导入线路",notes = "",httpMethod = "PUT")
    public Result importExcel(@RequestParam(value = "file", required = true) MultipartFile file, HttpServletRequest request, HttpServletResponse response){
        try {
            List<LibraryLineModel> list = outLibraryService.importExcel(file, request, response);
            Result result = new Result(Code.SUCCESS.getCode(), Code.SUCCESS.getMessage());
            result.getData().put("list",list);
            return result;
        }catch (BusinessException e){
            return new Result(e.getCode(), e.getErrorMessage());
        }catch (Exception e){
            return new Result(Code.SYSTEM_ERROR.getCode(),Code.SYSTEM_ERROR.getMessage());
        }
    }

    @PutMapping("addTask")
    @ApiOperation(value = "创建任务",notes = "",httpMethod = "PUT")
    public Result createTask(@RequestBody Map<String,List<LibraryLineModel>> param, HttpServletRequest request, HttpServletResponse response){
        try {
            outLibraryService.createTask(param,request,response);
            return new Result(Code.SUCCESS.getCode(),Code.SUCCESS.getMessage());
        }catch (BusinessException e){
            return new Result(e.getCode(), e.getErrorMessage());
        }catch (Exception e){
            return new Result(Code.SYSTEM_ERROR.getCode(),Code.SYSTEM_ERROR.getMessage());
        }
    }

    @PatchMapping("approvalTask")
    @ApiOperation(value = "审批任务",notes = "id:任务ID； approvalState：审批结果（1通过，2驳回）；approvalReason：审批理由",httpMethod = "PATCH")
    public Result approvalTask(@RequestBody Map<String,Object> param, HttpServletRequest request, HttpServletResponse response){
        try {
            outLibraryService.approvalTask(param,request,response);
            return new Result(Code.SUCCESS.getCode(),Code.SUCCESS.getMessage());
        }catch (BusinessException e){
            return new Result(e.getCode(), e.getErrorMessage());
        }catch (Exception e){
            return new Result(Code.SYSTEM_ERROR.getCode(),Code.SYSTEM_ERROR.getMessage());
        }
    }

    @PostMapping("findTask")
    @ApiOperation(value = "查询出库任务",notes = "",httpMethod = "POST")
    public Result findTask(@RequestBody FindTaskModel param, HttpServletRequest request, HttpServletResponse response){
        try {
            List<OutLibraryTask> tasks = outLibraryService.findTask(param,request,response);
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
    @GetMapping("findHandoverTask")
    @ApiOperation(value = "查询平板交接出库任务",notes = "参数说明：checkSchedule（0钞箱校验 1交接岗 2加钞岗）",httpMethod = "GET")
    public Result findHandoverTask(Integer checkSchedule, HttpServletRequest request, HttpServletResponse response){
        try {
            List<OutLibraryTask> tasks = outLibraryService.findHandoverTask(checkSchedule,request,response);
            Result result = new Result(Code.SUCCESS.getCode(), Code.SUCCESS.getMessage());
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
    public Result findTaskDetails(Integer id, HttpServletRequest request, HttpServletResponse response){
        try {
            Map<String,Object> task = outLibraryService.findTaskDetails(id,request,response);
            return new Result(Code.SUCCESS.getCode(), Code.SUCCESS.getMessage(),task);
        }catch (BusinessException e){
            return new Result(e.getCode(), e.getErrorMessage());
        }catch (Exception e){
            return new Result(Code.SYSTEM_ERROR.getCode(),Code.SYSTEM_ERROR.getMessage());
        }
    }

    @GetMapping("findTaskDetailsAll")
    @ApiOperation(value = "待校验任务钞箱各类数量",notes = "",httpMethod = "GET")
    public Result findTaskDetailsAll(HttpServletRequest request, HttpServletResponse response){
        try {
            Map<String,Object> task = outLibraryService.findTaskDetailsAll(request,response);
            return new Result(Code.SUCCESS.getCode(), Code.SUCCESS.getMessage(),task);
        }catch (BusinessException e){
            return new Result(e.getCode(), e.getErrorMessage());
        }catch (Exception e){
            return new Result(Code.SYSTEM_ERROR.getCode(),Code.SYSTEM_ERROR.getMessage());
        }
    }

//    @GetMapping("findTaskSchedule")
//    @ApiOperation(value = "任务详细进度",notes = "",httpMethod = "GET")
//    public Result findTaskSchedule(Integer id, HttpServletRequest request, HttpServletResponse response){
//        try {
//            Map<String,Object> task = outLibraryService.findTaskSchedule(id, request, response);
//            return new Result(Code.SUCCESS.getCode(), Code.SUCCESS.getMessage(),task);
//        }catch (BusinessException e){
//            return new Result(e.getCode(), e.getErrorMessage());
//        }catch (Exception e){
//            return new Result(Code.SYSTEM_ERROR.getCode(),Code.SYSTEM_ERROR.getMessage());
//        }
//    }

    @PostMapping("findLine")
    @ApiOperation(value = "查询出库线路列表",notes = "",httpMethod = "POST")
    public Result findLine(@RequestBody FindLineModel param, HttpServletRequest request, HttpServletResponse response){
        try {
            List<Map<String,Object>> lines = outLibraryService.findLine(param,request,response);
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
    @PostMapping("findBoxingLine")
    @ApiOperation(value = "查询出库配箱线路列表",notes = "",httpMethod = "POST")
    public Result findBoxingLine(@RequestBody FindLineModel param, HttpServletRequest request, HttpServletResponse response){
        try {
            List<Map<String,Object>> findBoxingLine = outLibraryService.findBoxingLine(param,request,response);
            Result result = new Result(Code.SUCCESS.getCode(), Code.SUCCESS.getMessage());
            result.getData().put("total",((Page<?>) findBoxingLine).getTotal());
            result.getData().put("list",findBoxingLine);
            return result;
        }catch (BusinessException e){
            return new Result(e.getCode(), e.getErrorMessage());
        }catch (Exception e){
            return new Result(Code.SYSTEM_ERROR.getCode(),Code.SYSTEM_ERROR.getMessage());
        }
    }
    @PostMapping("findHandoverLine")
    @ApiOperation(value = "查询平板交接线路列表",notes = "",httpMethod = "POST")
    public Result findHandoverLine(@RequestBody FindHandoverLineModel param, HttpServletRequest request, HttpServletResponse response){
        try {
            List<OutLibraryLine> lines = outLibraryService.findHandoverLine(param,request,response);
            Result result = new Result(Code.SUCCESS.getCode(), Code.SUCCESS.getMessage());
            result.getData().put("list",lines);
            return result;
        }catch (BusinessException e){
            return new Result(e.getCode(), e.getErrorMessage());
        }catch (Exception e){
            return new Result(Code.SYSTEM_ERROR.getCode(),Code.SYSTEM_ERROR.getMessage());
        }
    }

    @GetMapping("findLineDetails")
    @ApiOperation(value = "查看出库线路详情",notes = "",httpMethod = "GET")
    @ApiImplicitParam(name = "id", value = "线路id", required = true, dataType = "String", paramType="query")
    public Result findLineDetails(Integer id, HttpServletRequest request, HttpServletResponse response){
        try {
            Map<String,Object> tasks = outLibraryService.findLineDetails(id,request,response);
            return new Result(Code.SUCCESS.getCode(), Code.SUCCESS.getMessage(),tasks);
        }catch (BusinessException e){
            return new Result(e.getCode(), e.getErrorMessage());
        }catch (Exception e){
            return new Result(Code.SYSTEM_ERROR.getCode(),Code.SYSTEM_ERROR.getMessage());
        }
    }

    @PostMapping("findLineAtm")
    @ApiOperation(value = "查询出库线路ATM详情列表",notes = "",httpMethod = "POST")
    public Result findLineAtm(@RequestBody FindLineAtmModel param, HttpServletRequest request, HttpServletResponse response){
        try {
            List<Map<String,Object>> tasks = outLibraryService.findLineAtm(param,request,response);
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

    @PatchMapping("boxing")
    @ApiOperation(value = "提交配箱进度",notes = "id:出库线路ID； boxingState：配箱进度（0取消，1配箱，2完成）",httpMethod = "PATCH")
    public Result boxing(@RequestBody Map<String,Integer> param, HttpServletRequest request, HttpServletResponse response){
        try {
            outLibraryService.boxing(param,request,response);
            return new Result(Code.SUCCESS.getCode(), Code.SUCCESS.getMessage());
        }catch (BusinessException e){
            return new Result(e.getCode(), e.getErrorMessage());
        }catch (Exception e){
            return new Result(Code.SYSTEM_ERROR.getCode(),Code.SYSTEM_ERROR.getMessage());
        }
    }

    @GetMapping("details")
    @ApiOperation(value = "线路盘点钞箱详情",notes = "id:出库线路ID",httpMethod = "GET")
    @ApiImplicitParam(name = "id", value = "线路id", required = true, dataType = "String", paramType="query")
    public Result details(Integer id, HttpServletRequest request, HttpServletResponse response){
        try {
            Map<String,Object> result = outLibraryService.details(id, request, response);
            return new Result(Code.SUCCESS.getCode(), Code.SUCCESS.getMessage(),result);
        }catch (BusinessException e){
            return new Result(e.getCode(), e.getErrorMessage());
        }catch (Exception e){
            return new Result(Code.SYSTEM_ERROR.getCode(),Code.SYSTEM_ERROR.getMessage());
        }
    }


    @GetMapping("supplementBox")
    @ApiOperation(value = "补录钞箱查询",notes = "",httpMethod = "GET")
    @ApiImplicitParam(name = "number", value = "钞箱编号", required = true, dataType = "String", paramType="query")
    public Result supplementBox(String number, HttpServletRequest request, HttpServletResponse response){
        try {
            Map<String,Object> result = outLibraryService.supplementBox(number, request, response);
            return new Result(Code.SUCCESS.getCode(), Code.SUCCESS.getMessage(),result);
        }catch (BusinessException e){
            return new Result(e.getCode(), e.getErrorMessage());
        }catch (Exception e){
            return new Result(Code.SYSTEM_ERROR.getCode(),Code.SYSTEM_ERROR.getMessage());
        }
    }

    @PatchMapping("supplement")
    @ApiOperation(value = "补录",notes = "id:出库线路ID； number：钞箱编号",httpMethod = "PATCH")
    public Result supplement(@RequestBody Map<String,Object> param, HttpServletRequest request, HttpServletResponse response){
        try {
            outLibraryService.supplement(param, request, response);
            return new Result(Code.SUCCESS.getCode(), Code.SUCCESS.getMessage());
        }catch (BusinessException e){
            return new Result(e.getCode(), e.getErrorMessage());
        }catch (Exception e){
            return new Result(Code.SYSTEM_ERROR.getCode(),Code.SYSTEM_ERROR.getMessage());
        }
    }

    @PatchMapping("next")
    @ApiOperation(value = "交接到下一步到人脸识别",notes = "id:出库任务ID； type：（1交接岗，2加钞岗）",httpMethod = "PATCH")
    public Result next(@RequestBody Map<String,Integer> param, HttpServletRequest request, HttpServletResponse response){
        try {
            outLibraryService.next(param, request, response);
            return new Result(Code.SUCCESS.getCode(), Code.SUCCESS.getMessage());
        }catch (BusinessException e){
            return new Result(e.getCode(), e.getErrorMessage());
        }catch (Exception e){
            return new Result(Code.SYSTEM_ERROR.getCode(),Code.SYSTEM_ERROR.getMessage());
        }
    }

}
