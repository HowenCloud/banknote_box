package com.ixilink.banknote_box.message.controller;

import com.ixilink.banknote_box.common.exception.BusinessException;
import com.ixilink.banknote_box.common.model.Code;
import com.ixilink.banknote_box.common.model.Result;
import com.ixilink.banknote_box.message.service.OutLineInventoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @description:
 * @author: 张皓峰
 * @date: 2019-12-09 10:32
 */
@Log4j2
@RestController
@RequestMapping("outLineInventory")
@Api(value = "平板钞箱出库", description = "平板钞箱出库模块")
public class OutLineInventoryController {
    //GET, POST（多参数）, PUT（添加）, PATCH(更新部分), DELETE（删除）

    @Resource
    private OutLineInventoryService outLineInventoryService;

    @PostMapping("inventory")
    @ApiOperation(value = "线路盘点",notes = "id:出库线路ID； type：（0线路匹配，1交接岗，2加钞岗）；返回结果：inventoryState 状态，inventoryResult 盘点结果， normalResult 应有结果",httpMethod = "POST")
    public Result inventoryLine(@RequestBody Map<String,Integer> param, HttpServletRequest request, HttpServletResponse response){
        try {
            Map<String, Object> map = outLineInventoryService.inventoryLine(param.get("id"), param.get("type"), request, response);
            return new Result(Code.SUCCESS.getCode(), Code.SUCCESS.getMessage(),map);
        }catch (BusinessException e){
            return new Result(e.getCode(), e.getErrorMessage());
        }catch (Exception e){
            return new Result(Code.SYSTEM_ERROR.getCode(),Code.SYSTEM_ERROR.getMessage());
        }
    }

    @PatchMapping("startRecognitionFace")
    @ApiOperation(value = "开启人脸识别",notes = "id:出库任务ID； type 区域：（1 作业区，2 交接区）",httpMethod = "PATCH")
    public Result startRecognitionFace(@RequestBody Map<String,Integer> param, HttpServletRequest request, HttpServletResponse response){
        try {
            outLineInventoryService.startRecognitionFace(param, request, response);
            return new Result(Code.SUCCESS.getCode(), Code.SUCCESS.getMessage());
        }catch (BusinessException e){
            return new Result(e.getCode(), e.getErrorMessage());
        }catch (Exception e){
            return new Result(Code.SYSTEM_ERROR.getCode(),Code.SYSTEM_ERROR.getMessage());
        }
    }

    @PatchMapping("stopRecognitionFace")
    @ApiOperation(value = "关闭人脸识别",notes = "id:出库任务ID； type 区域：（1 作业区，2 交接区）",httpMethod = "PATCH")
    public Result stopRecognitionFace(@RequestBody Map<String,Integer> param, HttpServletRequest request, HttpServletResponse response){
        try {
            outLineInventoryService.stopRecognitionFace(param, request, response);
            return new Result(Code.SUCCESS.getCode(), Code.SUCCESS.getMessage());
        }catch (BusinessException e){
            return new Result(e.getCode(), e.getErrorMessage());
        }catch (Exception e){
            return new Result(Code.SYSTEM_ERROR.getCode(),Code.SYSTEM_ERROR.getMessage());
        }
    }

//    @PatchMapping("stopRecognitionFace")
//    @ApiOperation(value = "开启人脸识别",notes = "id:出库任务ID； type 区域：（1 作业区，2 交接区）",httpMethod = "PATCH")
//    public Result stopRecognitionFace(@RequestBody Map<String,Integer> param, HttpServletRequest request, HttpServletResponse response){
//        try {
//            outLineInventoryService.stopRecognitionFace(param, request, response);
//            return new Result(Code.SUCCESS.getCode(), Code.SUCCESS.getMessage());
//        }catch (BusinessException e){
//            return new Result(e.getCode(), e.getErrorMessage());
//        }catch (Exception e){
//            return new Result(Code.SYSTEM_ERROR.getCode(),Code.SYSTEM_ERROR.getMessage());
//        }
//    }

    @GetMapping("reloadRecognitionFace")
    @ApiOperation(value = "重新识别",notes = "type 区域：（1 作业区，2 交接区）",httpMethod = "GET")
    public Result reloadRecognitionFace(Integer type, HttpServletRequest request, HttpServletResponse response){
        try {
            outLineInventoryService.reloadRecognitionFace(type,request, response);
            return new Result(Code.SUCCESS.getCode(), Code.SUCCESS.getMessage());
        }catch (BusinessException e){
            return new Result(e.getCode(), e.getErrorMessage());
        }catch (Exception e){
            return new Result(Code.SYSTEM_ERROR.getCode(),Code.SYSTEM_ERROR.getMessage());
        }
    }

    @PatchMapping("handoverLine")
    @ApiOperation(value = "交接线路",notes = "id:出库任务ID； type：（0线路匹配，1交接岗，2加钞岗）",httpMethod = "PATCH")
    public Result handoverLine(@RequestBody Map<String,Integer> param, HttpServletRequest request, HttpServletResponse response){
        try {
            outLineInventoryService.handoverLine(param, request, response);
            return new Result(Code.SUCCESS.getCode(), Code.SUCCESS.getMessage());
        }catch (BusinessException e){
            return new Result(e.getCode(), e.getErrorMessage());
        }catch (Exception e){
            return new Result(Code.SYSTEM_ERROR.getCode(),Code.SYSTEM_ERROR.getMessage());
        }
    }

}
