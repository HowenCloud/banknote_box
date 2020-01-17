package com.ixilink.banknote_box.message.controller;

import com.ixilink.banknote_box.common.exception.BusinessException;
import com.ixilink.banknote_box.common.model.Code;
import com.ixilink.banknote_box.common.model.Result;
import com.ixilink.banknote_box.common.pojo.*;
import com.ixilink.banknote_box.message.common.FacePublicData;
import com.ixilink.banknote_box.message.service.ReadyMoneyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * description:
 * author: 余波
 * date: 2019-12-06 17:23
 */
@RestController
@RequestMapping("/box")
@Api(value = "/box", description = "")
public class ReadyMoneyController {
    @Resource
    private ReadyMoneyService readyMoneyService;

    @ApiOperation(value = "钞箱编号和类型查询", notes = "传入参数：activeRfid(钞箱有源编号)\n")
    @GetMapping(value = "selectNumberAndType/{activeRfid}")
    public Result selectNumber(@PathVariable("activeRfid")String activeRfid, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        try {
        readyMoneyService.selectNumberAndType(activeRfid,request);
        Result result = new Result(Code.SUCCESS.getCode(), Code.SUCCESS.getMessage());
        return result;
        }catch (BusinessException e){
            return new Result(e.getCode(), e.getErrorMessage());
        }catch (Exception e){
            return new Result(Code.SYSTEM_ERROR.getCode(),Code.SYSTEM_ERROR.getMessage());
        }
    }
    @PostMapping("scanInBox")
    @ApiOperation(value = "预装钞箱入库扫描",notes = "id  number\n"
            + "返回结果：readyMoneyApplyScan：扫描数据\n"
            + "readyMoneyApplyList ：展示list    \n"
            + "target：识别目标 ")
    public Result scanInBox(@RequestBody Map<String,Object> param ,HttpServletRequest request, HttpServletResponse response){
        try {
            param.put("storageTask","inStorageTask");
            param.put("storageTaskSupplement","inStorageTaskSupplement");
            param.put("storageTaskNum","inStorageTaskNum");
            param.put("mark","in");
            Map<String, Object> map = readyMoneyService.scanInBox(param, request, response);
            return new Result(Code.SUCCESS.getCode(), Code.SUCCESS.getMessage(),map);
        }catch (BusinessException e){
            return new Result(e.getCode(), e.getErrorMessage());
        }catch (Exception e){
            return new Result(Code.SYSTEM_ERROR.getCode(),Code.SYSTEM_ERROR.getMessage());
        }
    }
    @PostMapping("scanOutBox")
    @ApiOperation(value = "预装钞箱出库扫描",notes = "id number次数\n"
            + "返回结果：readyMoneyApplyScan：扫描数据\n"
            + "readyMoneyApplyList ：展示list    \n"
            + "target：识别目标 ")
    public Result scanOutBox(@RequestBody Map<String,Object> param , HttpServletRequest request, HttpServletResponse response){
        try {
            param.put("storageTask","outStorageTask");
            param.put("storageTaskSupplement","outStorageTaskSupplement");
            param.put("storageTaskNum","outStorageTaskNum");
            param.put("mark","out");
            Map<String, Object> map = readyMoneyService.scanInBox(param, request, response);
            return new Result(Code.SUCCESS.getCode(), Code.SUCCESS.getMessage(),map);
        }catch (BusinessException e){
            return new Result(e.getCode(), e.getErrorMessage());
        }catch (Exception e){
            return new Result(Code.SYSTEM_ERROR.getCode(),Code.SYSTEM_ERROR.getMessage());
        }
    }
    @GetMapping("startReadyMoneyFace")
    @ApiOperation(value = "开启人脸识别",notes = "无",httpMethod = "GET")
    public Result startRecognitionFace(HttpServletRequest request, HttpServletResponse response){
        try {
            readyMoneyService.startReadyMoneyFace(request, response);
            return new Result(Code.SUCCESS.getCode(), Code.SUCCESS.getMessage());
        }catch (BusinessException e){
            return new Result(e.getCode(), e.getErrorMessage());
        }catch (Exception e){
            return new Result(Code.SYSTEM_ERROR.getCode(),Code.SYSTEM_ERROR.getMessage());
        }
    }
    @ApiOperation(value = "入库交接", notes = "approvalPeople(五个交接人) id(任务id)")
    @PatchMapping(value = "inHandover")
    public Result inHandover(@RequestBody HandOverModel param, HttpServletRequest request, HttpServletResponse response){
        try {
            param.setMark("in");
            readyMoneyService.inHandover(param,request);
            return new Result(Code.SUCCESS.getCode(), Code.SUCCESS.getMessage());
        }catch (BusinessException e){
            return new Result(e.getCode(), Code.SYSTEM_ERROR.getMessage());
        }catch (Exception e){
            return new Result(Code.SYSTEM_ERROR.getCode(), Code.SYSTEM_ERROR.getMessage());
        }
    }
    @ApiOperation(value = "出库交接", notes = "approvalPeople(五个交接人) id(任务id)")
    @PatchMapping(value = "outHandover")
    public Result outHandover(@RequestBody HandOverModel param, HttpServletRequest request, HttpServletResponse response){
        try {
            param.setMark("out");
            readyMoneyService.inHandover(param,request);
            return new Result(Code.SUCCESS.getCode(), Code.SUCCESS.getMessage());
        }catch (BusinessException e){
            return new Result(e.getCode(), Code.SYSTEM_ERROR.getMessage());
        }catch (Exception e){
            return new Result(Code.SYSTEM_ERROR.getCode(), Code.SYSTEM_ERROR.getMessage());
        }
    }
    @ApiOperation(value = "关闭", notes = "")
    @GetMapping(value = "guanbi")
    public Result guanbi(HttpServletRequest request, HttpServletResponse response){
        try {
            FacePublicData.stopStream("0123B8-6BD664-3650EE");
            return new Result(Code.SUCCESS.getCode(), Code.SUCCESS.getMessage());
        }catch (BusinessException e){
            return new Result(e.getCode(), Code.SYSTEM_ERROR.getMessage());
        }catch (Exception e){
            return new Result(Code.SYSTEM_ERROR.getCode(), Code.SYSTEM_ERROR.getMessage());
        }
    }
}
