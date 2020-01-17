package com.ixilink.banknote_box.service.controller;


import com.ixilink.banknote_box.common.exception.BusinessException;
import com.ixilink.banknote_box.common.model.Code;
import com.ixilink.banknote_box.common.model.Result;
import com.ixilink.banknote_box.common.pojo.ReadyMoneyApplyTarget;
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
@RequestMapping("ReadyMoneyIn")
@Api(value = "/ReadyMoneyIn", description = "预钞入库")
public class ReadyMoneyInController {
    @Resource
    private ReadyMoneyTaskService readyMoneyTaskService;
    @ApiOperation(value = "预装钞箱入库任务查询（组长）", notes = "传入参数：无"
            + "返回参数：grouper: 组长\n"
            + "totalNumber: 总数\n"
            + "createTime: 时间\n"
            + "id:任务id\n"
            + "state: 1待入库 2已入库")
    @GetMapping(value = "selectApplyByGrouper")
    public Result selectApplyByGrouper(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        List<Map<String,Object>> map = readyMoneyTaskService.selectApplyByGrouper(request);
        Result result = new Result(Code.SUCCESS.getCode(), Code.SUCCESS.getMessage());
        result.getData().put("task", map);
        return result;
    }
    @PostMapping("selectFaceGrouperLibraryer/{id}")
    @ApiOperation(value = "根据任务id查询需要的组长，管库员的id",notes = "")
    public Result selectFaceGrouperLibraryer(@PathVariable("id") Integer id,HttpServletRequest request, HttpServletResponse response){
        try {
            Map<String, Object> map = readyMoneyTaskService.selectFaceGrouperLibraryer(id, request);
            Result result = new Result(Code.SUCCESS.getCode(), Code.SUCCESS.getMessage());
            result.getData().put("grouperLibraryer",map);
            return result;
        }catch (BusinessException e){
            return new Result(e.getCode(), e.getErrorMessage());
        }catch (Exception e){
            return new Result(Code.SYSTEM_ERROR.getCode(),Code.SYSTEM_ERROR.getMessage());
        }
    }
    @ApiOperation(value = "预装钞箱入库任务-识别目标", notes = "传入参数：任务id"
            + "返回参数：")
    @GetMapping(value = "selectApplyTarget/{id}")
    public Result selectApplyTarget( @PathVariable("id") Integer id, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        ReadyMoneyApplyTarget map = readyMoneyTaskService.selectApplyTarget(id);
        Result result = new Result(Code.SUCCESS.getCode(), Code.SUCCESS.getMessage());
        result.getData().put("task", map);
        return result;
    }
    @ApiOperation(value = "钞箱补录查询", notes = "传入参数：钞箱编号\n"
            + "返回参数：id(id) carBoxNumber(钞箱编号)    fundBagName(基金袋名字)  carTypeName(类型名)  carType:1 DB,3 DF,5 CRS,7 SCRS ")
    @GetMapping(value = "selectApplyCarBox/{carBoxNumber}")
    public Result selectApplyCarBox( @PathVariable("carBoxNumber") String carBoxNumber, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        try {
        Map<String,Object> map = readyMoneyTaskService.selectApplyCarBox(carBoxNumber,request);
        Result result = new Result(Code.SUCCESS.getCode(), Code.SUCCESS.getMessage());
        result.getData().put("carBox", map);
        return result;
        }catch (BusinessException e){
            return new Result(e.getCode(), e.getErrorMessage());
        }catch (Exception e){
            return new Result(Code.SYSTEM_ERROR.getCode(),Code.SYSTEM_ERROR.getMessage());
        }
    }
    @PostMapping("supplement")
    @ApiOperation(value = "补录",notes = "number(钞箱编号) id(任务id) detailsId(钞箱补录查询补录查询的id) count(操作步骤)")
    public Result supplement(@RequestBody Map<String,Object> param, HttpServletRequest request, HttpServletResponse response){
        try {
            param.put("storageTask","inStorageTask");
            param.put("storageTaskSupplement","inStorageTaskSupplement");
            param.put("storageTaskNum","inStorageTaskNum");
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
    @PostMapping("clearLastStep")
    @ApiOperation(value = "清除上一步",notes = "number（第几步) id(任务id)")
    public Result clearLastStep(@RequestBody Map<String,Object> param, HttpServletRequest request, HttpServletResponse response){
        try {
            param.put("storageTask","inStorageTask");
            param.put("storageTaskSupplement","inStorageTaskSupplement");
            param.put("storageTaskNum","inStorageTaskNum");
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