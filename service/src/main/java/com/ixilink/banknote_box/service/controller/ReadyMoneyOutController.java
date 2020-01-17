package com.ixilink.banknote_box.service.controller;
import com.ixilink.banknote_box.common.exception.BusinessException;
import com.ixilink.banknote_box.common.model.Code;
import com.ixilink.banknote_box.common.model.Result;
import com.ixilink.banknote_box.common.pojo.ReadyMoneyApplyTarget;

import com.ixilink.banknote_box.service.service.ReadyMoneyOutService;
import com.ixilink.banknote_box.service.service.ReadyMoneyTaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Log4j2
@RestController
@RequestMapping("ReadyMoneyOut")
@Api(value = "/ReadyMoneyOut", description = "预钞箱出库")
public class ReadyMoneyOutController {
    @Resource
    private ReadyMoneyOutService readyMoneyOutService;
    @Resource
    private ReadyMoneyTaskService readyMoneyTaskService;
    @ApiOperation(value = "出库任务查询", notes = "传入参数：无"
            + "返回参数：")
    @GetMapping(value = "selectApplyOut")
    public Result selectApplyOut( HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        List<Map<String,Object>> map = readyMoneyOutService.selectApplyOut(request);
        Result result = new Result(Code.SUCCESS.getCode(), Code.SUCCESS.getMessage());
        result.getData().put("task", map);
        return result;
    }
    @ApiOperation(value = "预装钞箱出库任务-识别目标", notes = "传入参数：任务id"
            + "返回参数：")
    @GetMapping(value = "selectApplyOutTarget/{id}")
    public Result selectApplyOutTarget( @PathVariable("id") Integer id, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        ReadyMoneyApplyTarget map = readyMoneyOutService.selectApplyOutTarget(id);
        Result result = new Result(Code.SUCCESS.getCode(), Code.SUCCESS.getMessage());
        result.getData().put("task", map);
        return result;
    }
    @PostMapping("selectFaceOutGrouperLibraryer/{id}")
    @ApiOperation(value = "根据任务id查询需要的组长，管库员的id",notes = "")
    public Result selectFaceOutGrouperLibraryer(@PathVariable("id") Integer id,HttpServletRequest request, HttpServletResponse response){
        try {
            Map<String, Object> map = readyMoneyOutService.selectFaceOutGrouperLibraryer(id, request);
            Result result = new Result(Code.SUCCESS.getCode(), Code.SUCCESS.getMessage());
            result.getData().put("grouperLibraryer",map);
            return result;
        }catch (BusinessException e){
            return new Result(e.getCode(), e.getErrorMessage());
        }catch (Exception e){
            return new Result(Code.SYSTEM_ERROR.getCode(),Code.SYSTEM_ERROR.getMessage());
        }
    }
    @PostMapping("outSupplement")
    @ApiOperation(value = "出库补录",notes = "number(钞箱编号) id(任务id) detailsId(钞箱补录查询补录查询的id) count(操作步骤)")
    public Result outSupplement(@RequestBody Map<String,Object> param, HttpServletRequest request, HttpServletResponse response){
        try {
            param.put("storageTask","outStorageTask");
            param.put("storageTaskSupplement","outStorageTaskSupplement");
            param.put("storageTaskNum","outStorageTaskNum");
            ReadyMoneyApplyTarget supplement = readyMoneyTaskService.supplement(param, request, response);
            Result result = new Result(Code.SUCCESS.getCode(), Code.SUCCESS.getMessage());
            result.getData().put("supplement",supplement);
            return result;
        }catch (BusinessException e){
            return new Result(e.getCode(), e.getErrorMessage());
        }catch (Exception e){
            return new Result(Code.SYSTEM_ERROR.getCode(),Code.SYSTEM_ERROR.getMessage());
        }
    }
    @PostMapping("outClearLastStep")
    @ApiOperation(value = "出库清除上一步",notes = "number（第几步) id(任务id)")
    public Result outClearLastStep(@RequestBody Map<String,Object> param, HttpServletRequest request, HttpServletResponse response){
        try {
            param.put("storageTask","outStorageTask");
            param.put("storageTaskSupplement","outStorageTaskSupplement");
            param.put("storageTaskNum","outStorageTaskNum");
            ReadyMoneyApplyTarget num =readyMoneyTaskService.clearLastStep(param, request, response);
            Result result = new Result(Code.SUCCESS.getCode(), Code.SUCCESS.getMessage());
            result.getData().put("num",num);
            return result;
        }catch (BusinessException e){
            return new Result(e.getCode(), e.getErrorMessage());
        }catch (Exception e){
            return new Result(Code.SYSTEM_ERROR.getCode(),Code.SYSTEM_ERROR.getMessage());
        }
    }
}