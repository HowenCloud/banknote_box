package com.ixilink.banknote_box.message.controller;

import com.ixilink.banknote_box.common.exception.BusinessException;
import com.ixilink.banknote_box.common.model.Code;
import com.ixilink.banknote_box.common.model.Result;
import com.ixilink.banknote_box.message.service.InLineInventoryService;
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
 * @date: 2020-01-11 11:42
 */
@Log4j2
@RestController
@RequestMapping("inLineInventory")
@Api(value = "平板钞箱入库", description = "平板钞箱入库模块")
public class InLineInventoryController {

    @Resource
    private InLineInventoryService inLineInventoryService;

    @GetMapping("inventory")
    @ApiOperation(value = "线路盘点",notes = "id:出库线路ID；  \n返回结果：inventoryState 状态，inventoryResult 盘点结果， normalResult 应有结果",httpMethod = "GET")
    public Result inventoryLine(Integer id, HttpServletRequest request, HttpServletResponse response){
        try {
            Map<String, Object> map = inLineInventoryService.inventoryLine(id, request, response);
            return new Result(Code.SUCCESS.getCode(), Code.SUCCESS.getMessage(),map);
        }catch (BusinessException e){
            return new Result(e.getCode(), e.getErrorMessage());
        }catch (Exception e){
            return new Result(Code.SYSTEM_ERROR.getCode(),Code.SYSTEM_ERROR.getMessage());
        }
    }


    @GetMapping("handoverLine")
    @ApiOperation(value = "交接线路",notes = "id:出库任务ID",httpMethod = "GET")
    public Result handoverInLine(Integer id, HttpServletRequest request, HttpServletResponse response){
        try {
            inLineInventoryService.handoverInLine(id, request, response);
            return new Result(Code.SUCCESS.getCode(), Code.SUCCESS.getMessage());
        }catch (BusinessException e){
            return new Result(e.getCode(), e.getErrorMessage());
        }catch (Exception e){
            return new Result(Code.SYSTEM_ERROR.getCode(),Code.SYSTEM_ERROR.getMessage());
        }
    }
}
