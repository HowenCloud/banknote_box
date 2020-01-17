package com.ixilink.banknote_box.service.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ixilink.banknote_box.common.exception.BusinessException;
import com.ixilink.banknote_box.common.model.Code;
import com.ixilink.banknote_box.common.model.Result;
import com.ixilink.banknote_box.common.pojo.Equipment;
import com.ixilink.banknote_box.common.pojo.EquipmentAtm;
import com.ixilink.banknote_box.common.util.OnlineTest;
import com.ixilink.banknote_box.service.parameter_model.equipment.EquipmentCenterControllerModel;
import com.ixilink.banknote_box.service.parameter_model.equipment.EquipmentModel;
import com.ixilink.banknote_box.service.service.EquipmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: 张俊
 * @date: 2019-11-18 20:20
 */
@Log4j2
@RestController
@RequestMapping("equipment")
@Api(value = "equipment", description = "设备管理")
public class EquipmentController {

    @Resource
    private EquipmentService equipmentService;

    @GetMapping("ping")
    @ApiOperation(value = "测试ip是否在线",notes = "",httpMethod = "GET")
    public Result ping(String ip, HttpServletRequest request, HttpServletResponse response){
        try {
            boolean pass = OnlineTest.isIp(ip);
            if (pass){
                pass = OnlineTest.ping(ip);
                if (pass){
                    return new Result(Code.SUCCESS.getCode(),Code.SUCCESS.getMessage());
                }else {
                    return new Result(Code.MESSAGE_ERROR.getCode(), "ip不在线");
                }
            }
            return new Result(Code.MESSAGE_ERROR.getCode(), "请输入规范的ip地址");
        }catch (Exception e){
            return new Result(Code.SYSTEM_ERROR.getCode(),Code.SYSTEM_ERROR.getMessage());
        }
    }


    @PutMapping("readerWriter")
    @ApiOperation(value = "添加有源双频RFID读写器",notes = "",httpMethod = "PUT")
    public Result addReaderWriter(@RequestBody EquipmentModel param, HttpServletRequest request, HttpServletResponse response){
        try {
            equipmentService.addReaderWriter(param,request,response);
            return new Result(Code.SUCCESS.getCode(),Code.SUCCESS.getMessage());
        }catch (BusinessException e){
            return new Result(e.getCode(), e.getErrorMessage());
        }catch (Exception e){
            return new Result(Code.SYSTEM_ERROR.getCode(),Code.SYSTEM_ERROR.getMessage());
        }
    }

    @PostMapping("readerWriter")
    @ApiOperation(value = "查询有源双频RFID读写器",notes = "",httpMethod = "POST")
    public Result getReaderWriter(@RequestBody EquipmentModel equipmentModel, HttpServletRequest request, HttpServletResponse response){
        try {
            PageHelper.startPage(equipmentModel.getPageNum(), equipmentModel.getPageSize());
            List<Equipment> readerWriter = equipmentService.getReaderWriter(equipmentModel,request,response);
            Result result = new Result(Code.SUCCESS.getCode(),Code.SUCCESS.getMessage());
            result.getData().put("total",((Page<?>) readerWriter).getTotal());
            result.getData().put("readerWriter",readerWriter);
            return result;
        }catch (BusinessException e){
            return new Result(e.getCode(),e.getErrorMessage());
        }catch (Exception e){
            return new Result(Code.SYSTEM_ERROR.getCode(),Code.SYSTEM_ERROR.getMessage());
        }
    }

    @DeleteMapping("readerWriter/{id}")
    @ApiOperation(value = "删除有源双频RFID读写器",notes = "",httpMethod = "DELETE")
    public Result removeReaderWriter(@PathVariable("id") Integer id, HttpServletRequest request, HttpServletResponse response){
        try {
            equipmentService.removeReaderWriter(id,request,response);
            return new Result(Code.SUCCESS.getCode(),Code.SUCCESS.getMessage());
        }catch (BusinessException e){
            return new Result(e.getCode(),e.getErrorMessage());
        }catch (Exception e){
            return new Result(Code.SYSTEM_ERROR.getCode(),Code.SYSTEM_ERROR.getMessage());
        }
    }

    @PatchMapping("readerWriter")
    @ApiOperation(value = "修改有源双频RFID读写器",notes = "",httpMethod = "PATCH")
    public Result modifyReaderWriter(@RequestBody EquipmentModel param, HttpServletRequest request, HttpServletResponse response){
        try {
            equipmentService.modifyReaderWriter(param,request,response);
            return new Result(Code.SUCCESS.getCode(),Code.SUCCESS.getMessage());
        }catch (BusinessException e){
            return new Result(e.getCode(),e.getErrorMessage());
        }catch (Exception e){
            return new Result(Code.SYSTEM_ERROR.getCode(),Code.SYSTEM_ERROR.getMessage());
        }
    }


    @PutMapping("centerControl")
    @ApiOperation(value = "添加RFID集中控制器",notes = "",httpMethod = "PUT")
    public Result addCenterControl(@RequestBody EquipmentCenterControllerModel param, HttpServletRequest request, HttpServletResponse response){
        try {
            equipmentService.addCenterControl(param,request,response);
            return new Result(Code.SUCCESS.getCode(),Code.SUCCESS.getMessage());
        }catch (BusinessException e){
            return new Result(e.getCode(), e.getErrorMessage());
        }catch (Exception e){
            return new Result(Code.SYSTEM_ERROR.getCode(),Code.SYSTEM_ERROR.getMessage());
        }
    }

    @PostMapping("centerControl")
    @ApiOperation(value = "查询RFID集中控制器",notes = "",httpMethod = "POST")
    public Result getCenterControl(@RequestBody EquipmentModel equipmentModel, HttpServletRequest request, HttpServletResponse response){
        try {
            PageHelper.startPage(equipmentModel.getPageNum(), equipmentModel.getPageSize()," id DESC ");
            List<Equipment> centerControl = equipmentService.getCenterControl(equipmentModel,request,response);
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

    @GetMapping("centerControlAtm")
    @ApiOperation(value = "查询RFID集中控制器下的ATM机",notes = "",httpMethod = "GET")
    public Result getCenterControlAtms(@Param("id") Integer id, HttpServletRequest request, HttpServletResponse response){
        try {
            Equipment centerControl = equipmentService.getCenterControl(id, request, response);
            List<EquipmentAtm> centerControlAtms = equipmentService.getCenterControlAtms(id,request,response);
            Result result = new Result(Code.SUCCESS.getCode(),Code.SUCCESS.getMessage());
            result.getData().put("centerControl",centerControl);
            result.getData().put("atms",centerControlAtms);
            return result;
        }catch (BusinessException e){
            return new Result(e.getCode(),e.getErrorMessage());
        }catch (Exception e){
            return new Result(Code.SYSTEM_ERROR.getCode(),Code.SYSTEM_ERROR.getMessage());
        }
    }

    @DeleteMapping("centerControl/{id}")
    @ApiOperation(value = "删除RFID集中控制器",notes = "",httpMethod = "DELETE")
    public Result removeCenterControl(@PathVariable("id") Integer id, HttpServletRequest request, HttpServletResponse response){
        try {
            equipmentService.removeCenterControl(id,request,response);
            return new Result(Code.SUCCESS.getCode(),Code.SUCCESS.getMessage());
        }catch (BusinessException e){
            return new Result(e.getCode(),e.getErrorMessage());
        }catch (Exception e){
            return new Result(Code.SYSTEM_ERROR.getCode(),Code.SYSTEM_ERROR.getMessage());
        }
    }

    @PatchMapping("centerControl")
    @ApiOperation(value = "修改RFID集中控制器",notes = "",httpMethod = "PATCH")
    public Result modifyCenterControl(@RequestBody EquipmentCenterControllerModel param, HttpServletRequest request, HttpServletResponse response){
        try {
            equipmentService.modifyCenterControl(param,request,response);
            return new Result(Code.SUCCESS.getCode(),Code.SUCCESS.getMessage());
        }catch (BusinessException e){
            return new Result(e.getCode(),e.getErrorMessage());
        }catch (Exception e){
            return new Result(Code.SYSTEM_ERROR.getCode(),Code.SYSTEM_ERROR.getMessage());
        }
    }


    @PutMapping("handover")
    @ApiOperation(value = "添加交接作业终端",notes = "",httpMethod = "PUT")
    public Result addHandover(@RequestBody EquipmentModel param, HttpServletRequest request, HttpServletResponse response){
        try {
            equipmentService.addHandover(param,request,response);
            return new Result(Code.SUCCESS.getCode(),Code.SUCCESS.getMessage());
        }catch (BusinessException e){
            return new Result(e.getCode(), e.getErrorMessage());
        }catch (Exception e){
            return new Result(Code.SYSTEM_ERROR.getCode(),Code.SYSTEM_ERROR.getMessage());
        }
    }

    @PostMapping("handover")
    @ApiOperation(value = "查询交接作业终端",notes = "",httpMethod = "POST")
    public Result getHandover(@RequestBody EquipmentModel equipmentModel, HttpServletRequest request, HttpServletResponse response){
        try {
            PageHelper.startPage(equipmentModel.getPageNum(), equipmentModel.getPageSize()," id DESC ");
            List<Equipment> handover = equipmentService.getHandover(equipmentModel,request,response);
            Result result = new Result(Code.SUCCESS.getCode(),Code.SUCCESS.getMessage());
            result.getData().put("total",((Page<?>) handover).getTotal());
            result.getData().put("handover",handover);
            return result;
        }catch (BusinessException e){
            return new Result(e.getCode(),e.getErrorMessage());
        }catch (Exception e){
            return new Result(Code.SYSTEM_ERROR.getCode(),Code.SYSTEM_ERROR.getMessage());
        }
    }

    @DeleteMapping("handover/{id}")
    @ApiOperation(value = "删除交接作业终端",notes = "",httpMethod = "DELETE")
    public Result removeHandover(@PathVariable("id") Integer id, HttpServletRequest request, HttpServletResponse response){
        try {
            equipmentService.removeHandover(id,request,response);
            return new Result(Code.SUCCESS.getCode(),Code.SUCCESS.getMessage());
        }catch (BusinessException e){
            return new Result(e.getCode(),e.getErrorMessage());
        }catch (Exception e){
            return new Result(Code.SYSTEM_ERROR.getCode(),Code.SYSTEM_ERROR.getMessage());
        }
    }

    @PatchMapping("handover")
    @ApiOperation(value = "修改交接作业终端",notes = "",httpMethod = "PATCH")
    public Result modifyHandover(@RequestBody EquipmentModel param, HttpServletRequest request, HttpServletResponse response){
        try {
            equipmentService.modifyHandover(param,request,response);
            return new Result(Code.SUCCESS.getCode(),Code.SUCCESS.getMessage());
        }catch (BusinessException e){
            return new Result(e.getCode(),e.getErrorMessage());
        }catch (Exception e){
            return new Result(Code.SYSTEM_ERROR.getCode(),Code.SYSTEM_ERROR.getMessage());
        }
    }


    @PutMapping("boxing")
    @ApiOperation(value = "添加装箱作业终端",notes = "",httpMethod = "PUT")
    public Result addBoxing(@RequestBody EquipmentModel param, HttpServletRequest request, HttpServletResponse response){
        try {
            equipmentService.addBoxing(param,request,response);
            return new Result(Code.SUCCESS.getCode(),Code.SUCCESS.getMessage());
        }catch (BusinessException e){
            return new Result(e.getCode(), e.getErrorMessage());
        }catch (Exception e){
            return new Result(Code.SYSTEM_ERROR.getCode(),Code.SYSTEM_ERROR.getMessage());
        }
    }

    @PostMapping("boxing")
    @ApiOperation(value = "查询装箱作业终端",notes = "",httpMethod = "POST")
    public Result getBoxing(@RequestBody EquipmentModel equipmentModel, HttpServletRequest request, HttpServletResponse response){
        try {
            PageHelper.startPage(equipmentModel.getPageNum(), equipmentModel.getPageSize()," id DESC ");
            List<Equipment> boxing = equipmentService.getBoxing(equipmentModel,request,response);
            Result result = new Result(Code.SUCCESS.getCode(),Code.SUCCESS.getMessage());
            result.getData().put("total",((Page<?>) boxing).getTotal());
            result.getData().put("boxing",boxing);
            return result;
        }catch (BusinessException e){
            return new Result(e.getCode(),e.getErrorMessage());
        }catch (Exception e){
            return new Result(Code.SYSTEM_ERROR.getCode(),Code.SYSTEM_ERROR.getMessage());
        }
    }

    @DeleteMapping("boxing/{id}")
    @ApiOperation(value = "删除装箱作业终端",notes = "",httpMethod = "DELETE")
    public Result removeBoxing(@PathVariable("id") Integer id, HttpServletRequest request, HttpServletResponse response){
        try {
            equipmentService.removeBoxing(id,request,response);
            return new Result(Code.SUCCESS.getCode(),Code.SUCCESS.getMessage());
        }catch (BusinessException e){
            return new Result(e.getCode(),e.getErrorMessage());
        }catch (Exception e){
            return new Result(Code.SYSTEM_ERROR.getCode(),Code.SYSTEM_ERROR.getMessage());
        }
    }

    @PatchMapping("boxing")
    @ApiOperation(value = "修改装箱作业终端",notes = "",httpMethod = "PATCH")
    public Result modifyBoxing(@RequestBody EquipmentModel param, HttpServletRequest request, HttpServletResponse response){
        try {
            equipmentService.modifyBoxing(param,request,response);
            return new Result(Code.SUCCESS.getCode(),Code.SUCCESS.getMessage());
        }catch (BusinessException e){
            return new Result(e.getCode(),e.getErrorMessage());
        }catch (Exception e){
            return new Result(Code.SYSTEM_ERROR.getCode(),Code.SYSTEM_ERROR.getMessage());
        }
    }


    @GetMapping("count")
    @ApiOperation(value = "查询设备数量及在线离线数量",notes = "",httpMethod = "GET")
    public Result count(Integer type, HttpServletRequest request, HttpServletResponse response){
        try {
            Result result = new Result(Code.SUCCESS.getCode(),Code.SUCCESS.getMessage());
            Map<String,Object> map = equipmentService.getCount(type,request,response);
            result.getData().putAll(map);
            return result;
        }catch (Exception e){
            return new Result(Code.SYSTEM_ERROR.getCode(),Code.SYSTEM_ERROR.getMessage());
        }
    }


    @GetMapping("getDeviceId")
    @ApiOperation(value = "获取摄像头设备编号",notes = "",httpMethod = "GET")
    public Result getDeviceId(Integer type, HttpServletRequest request, HttpServletResponse response){
        try {
            Result result = new Result(Code.SUCCESS.getCode(),Code.SUCCESS.getMessage());
            Map<String,Object> map = equipmentService.getDeviceId(type,request,response);
            result.getData().putAll(map);
            return result;
        }catch (Exception e){
            return new Result(Code.SYSTEM_ERROR.getCode(),Code.SYSTEM_ERROR.getMessage());
        }
    }

}
