package com.ixilink.banknote_box.service.controller;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ixilink.banknote_box.common.exception.BusinessException;
import com.ixilink.banknote_box.common.model.Code;
import com.ixilink.banknote_box.common.model.Result;
import com.ixilink.banknote_box.common.pojo.ReadyMoneyExecutorTaskModel;
import com.ixilink.banknote_box.common.pojo.ReadyMoneyReturnDetails;
import com.ixilink.banknote_box.common.pojo.ReadyMoneyTaskReturn;
import com.ixilink.banknote_box.service.parameter_model.readyMoney.*;
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
@RequestMapping("ReadyMoneyTask")
@Api(value = "/ReadyMoneyTask", description = "预钞箱寄存")
public class ReadyMoneyTaskController {
    @Resource
    private ReadyMoneyTaskService readyMoneyTaskService;
    @ApiOperation(value = "主管新建预装钞箱任务", notes = "")
    @PutMapping(value = "insert")
    public Result insert(@RequestBody InsertReadyMoneyTaskModel param, HttpServletRequest request, HttpServletResponse response){
        try {
            readyMoneyTaskService.insert(param,request);
            Result result = new Result(Code.SUCCESS.getCode(), Code.SUCCESS.getMessage());
            return result;
        }catch (BusinessException e){
            return new Result(e.getCode(),e.getErrorMessage());
        }catch (Exception e){
            return new Result(Code.SYSTEM_ERROR.getCode(), Code.SYSTEM_ERROR.getMessage());
        }
    }
    @ApiOperation(value = "主管预装箱任务审核状态查询", notes = "传入参数：page pageSize\n"
            + "返回参数：initiator(发布人) createTime(发布时间) receivePeople（整点组长岗任务接收人）state(-1拒绝 1待审核 2已审核 )")
    @PostMapping(value = "selectByInitiator")
    public Result selectByInitiator(@RequestBody Map<String,Object> param,HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        PageHelper.startPage((Integer) param.get("page"),(Integer) param.get("pageSize"));
        List<ReadyMoneyTaskReturn> readyMoneyTask = readyMoneyTaskService.selectByInitiator(request);
        Result result = new Result(Code.SUCCESS.getCode(), Code.SUCCESS.getMessage());
        result.getData().put("total", ((Page<?>) readyMoneyTask).getTotal());
        result.getData().put("readyMoneyTask", readyMoneyTask);
        return result;
    }
    @ApiOperation(value = "组长查询待审批审批通过拒绝的任务", notes = "传入参数：page pageSize\n"
            + "返回参数：initiator(发布人) createTime(发布时间) receivePeople（整点组长岗任务接收人）")
    @PostMapping(value = "selectApproving")
    public Result selectApproving(@RequestBody Map<String,Object> param, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        PageHelper.startPage((Integer) param.get("page"),(Integer) param.get("pageSize"));
      List<ReadyMoneyTaskReturn> readyMoneyTask = readyMoneyTaskService.selectApproving(request);
      Result result = new Result(Code.SUCCESS.getCode(), Code.SUCCESS.getMessage());
        result.getData().put("total", ((Page<?>) readyMoneyTask).getTotal());
      result.getData().put("approving", readyMoneyTask);
      return result;
    }
    @ApiOperation(value = "操作岗审批通过的任务查询", notes = "传入参数：page pageSize\n"
            + "返回参数：initiator(发布人) createTime(发布时间) receivePeople（整点组长岗任务接收人）state(2审核)")
    @PostMapping(value = "selectApprovedAndPerforming")
    public Result selectApprovedAndPerforming(@RequestBody Map<String,Object> param, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        PageHelper.startPage((Integer) param.get("page"),(Integer) param.get("pageSize"));
        List<ReadyMoneyTaskReturn> readyMoneyTask = readyMoneyTaskService.selectApprovedAndPerforming(request);
        Result result = new Result(Code.SUCCESS.getCode(), Code.SUCCESS.getMessage());
        result.getData().put("total", ((Page<?>) readyMoneyTask).getTotal());
        result.getData().put("approved", readyMoneyTask);
        return result;
    }
    @ApiOperation(value = "审核", notes = "传入参数：id(任务id) state(-1 不通过 2通过)")
    @PostMapping(value = "approve")
    public Result approve(@RequestBody Map<String,Object> param, HttpServletRequest request, HttpServletResponse response){
        try {
            readyMoneyTaskService.approve(param,request);
            return new Result(Code.SUCCESS.getCode(),Code.SUCCESS.getMessage());
        }catch (BusinessException e){
            return new Result(e.getCode(),e.getErrorMessage());
        }catch (Exception e){
            return new Result(Code.SYSTEM_ERROR.getCode(),Code.SYSTEM_ERROR.getMessage());
        }
    }
    @ApiOperation(value = "整点操作执行head", notes = "传入参数：id(任务id)\n"
            + "返回参数：\ttype:1 DB,3 DF,5 CRS,7 SCRS            \n"
            + "    totalMoney:总金额        \t\t\n"
            + "    number:总数量             \t\n"
            + "    performedNumber:已装数量      \n"
            + "    performedMoney:已装金额\n"
            + "performingNumber：未装数量 \n"
            + "performingMoney：未装金额 \n"
            + "    unfinishNumberAndMoney:待装数量和金额详情")
    @GetMapping(value = "selectExecutorTaskDetails/{id}")
    public Result selectExecutorTaskDetails(@PathVariable("id")Integer id, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        List<ReadyMoneyExecutorTaskModel> map = readyMoneyTaskService.selectExecutorTaskDetails(id);
        Result result = new Result(Code.SUCCESS.getCode(), Code.SUCCESS.getMessage());
        result.getData().put("executorTaskDetails", map);
        return result;
    }
    @ApiOperation(value = "整点操作加钞", notes = "")
    @PutMapping(value = "insertOperation")
    public Result insertOperation(@RequestBody ReadyMoneyDetailsModel param, HttpServletRequest request, HttpServletResponse response){
        try {
            Integer integer = readyMoneyTaskService.insertOperation(param, request);
            Result result = new Result(Code.SUCCESS.getCode(), Code.SUCCESS.getMessage());
            result.getData().put("id",integer);
            return result;
        }catch (BusinessException e){
            return new Result(e.getCode(),e.getErrorMessage());
        }catch (Exception e){
            return new Result(Code.SYSTEM_ERROR.getCode(), Code.SYSTEM_ERROR.getMessage());
        }
    }
    @ApiOperation(value = "整点操作移交", notes = "传入参数：handoverPeople(接受人的id integer) ids(整点操作岗返回的id集合)")
    @PostMapping(value = "handOver")
    public Result handOver(@RequestBody Map<String,Object> param, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        readyMoneyTaskService.handOver(param,request);
        Result result = new Result(Code.SUCCESS.getCode(), Code.SUCCESS.getMessage());
        return result;
    }
    @ApiOperation(value = "组长查询已完成的任务", notes = "传入参数：page pageSize\n"
            + "返回参数：initiator(发布人) createTime(发布时间) receivePeople（整点组长岗任务接收人） executor(整点操作岗执行人) handoverPeople（交接人）")
    @PostMapping(value = "selectPerformed")
    public Result selectPerformed(@RequestBody Map<String,Object> param, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        PageHelper.startPage((Integer) param.get("page"),(Integer) param.get("pageSize"));
        List<ReadyMoneyTaskReturn> readyMoneyTask = readyMoneyTaskService.selectPerformed(request);
        Result result = new Result(Code.SUCCESS.getCode(), Code.SUCCESS.getMessage());
        result.getData().put("total", ((Page<?>) readyMoneyTask).getTotal());
        result.getData().put("approved", readyMoneyTask);
        return result;
    }
    @ApiOperation(value = "组长已完成任务钞箱详情head", notes = "传入参数：id(任务id)\n"
            + "返回值：receivePeople: 接受人\n"
            + "performedTime: 完成时间\n"
            + "createTime:  发布时间  \n"
            + "executor:     执行人 \n"
            + "initiator:  发布人  ")
    @GetMapping(value = "performedHead/{id}")
    public Result performedHead(@PathVariable("id")Integer id, HttpServletRequest request, HttpServletResponse response, HttpSession session) {

        Map<String,Object> map = readyMoneyTaskService.performedHead(id);
        Result result = new Result(Code.SUCCESS.getCode(), Code.SUCCESS.getMessage());
        result.getData().put("head", map);
        return result;
    }
    @ApiOperation(value = "已完成任务钞箱详情", notes = "传入参数：id(任务id) carBoxNumber(钞箱编号) carType(钞箱类型 1db 3df 5crs 7scrs) page pageSize\n")
    @PostMapping(value = "performedDetails")
    public Result performedDetails(@RequestBody Map<String,Object> param, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        PageHelper.startPage((Integer) param.get("page"),(Integer) param.get("pageSize"));
        List<ReadyMoneyReturnDetails> map = readyMoneyTaskService.performedDetails(param);
        Result result = new Result(Code.SUCCESS.getCode(), Code.SUCCESS.getMessage());
        result.getData().put("total", ((Page<?>) map).getTotal());
        result.getData().put("returnDetails", map);
        return result;
    }
    @ApiOperation(value = "组长名下钞箱详情", notes = "传入参数： state(1在持 2入库 3出库必须选一个) grouperId(组长id) beginTime(开始时间) endTime(结束时间) page() pageSize()\n"
            + "返回参数  dbNum: db钞箱总数,\n"
            + "dbTotalMoney: db钞箱总金额,\n"
            + "dfNum: df钞箱总数,\n"
            + "dfTotalMoney: df钞箱总金额,\n"
            + "crsNum: crs钞箱总数,\n"
            + "crsTotalMoney: crs钞箱总金额,\n"
            + "scrsNum: scrs钞箱总数,\n"
            + "totalNumber: 钞箱总数,\n"
            + "totalMoney: 钞箱总金额,\n"
            + "scrsTotalMoney: scrs钞箱总金额\n"
            + "total: 总数,\n"
            + "fundBagName: 基金袋名字,\n"
            + "gid: id,\n"
            + "carType: 钞箱类型,\n"
            + "executor: 操作岗人员,\n"
            + "carMoney: 装箱金额,\n"
            + "time: 时间,\n"
            + "carBoxNumber: 钞箱编号,\n"
            + "state: 钞箱状态 1在持 2入库 3出库 ,\n"
            + "grouper：持有人\n"
            + "handOverPeople：移交人")
    @PostMapping(value = "selectUnderGroupLeaderDetails")
    public Result selectUnderGroupLeaderDetails(@RequestBody Map<String,Object> param, HttpServletRequest request, HttpServletResponse response, HttpSession session) {//libraryId state grouperId beginTime endTime
        PageHelper.startPage((Integer) param.get("page"),(Integer) param.get("pageSize"));
        Map<String, Object> map = readyMoneyTaskService.selectUnderGroupLeaderDetails(param, request);
        Result result = new Result(Code.SUCCESS.getCode(), Code.SUCCESS.getMessage());
        result.getData().put("total", ((Page<?>) map.get("underGroupLeaderDetails")).getTotal());
        result.getData().put("underGroupLeaderDetails", map.get("underGroupLeaderDetails"));
        result.getData().put("underGroupLeaderHeadDetails", map.get("underGroupLeaderHeadDetails"));
        return result;
    }
    @ApiOperation(value = "任务接受人查询", notes = "传入参数：无"
            + "返回参数：")
    @GetMapping(value = "selectHandoverPerson")
    public Result selectHandoverPerson( HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        List<Map<String, Object>> maps = readyMoneyTaskService.selectHandoverPerson(request);
        Result result = new Result(Code.SUCCESS.getCode(), Code.SUCCESS.getMessage());
        result.getData().put("users", maps);
        return result;
    }
    @ApiOperation(value = "钞箱编号和类型查询", notes = "传入参数：activeRfid(钞箱有源编号)\n"
            + "返回参数：serialNumber: 钞箱序号,carType(钞箱类型 1db 3df 5crs 7scrs)")
    @GetMapping(value = "selectNumberAndType/{activeRfid}")
    public Result selectNumberAndType(@PathVariable("activeRfid")String activeRfid, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        Map<String,Object> map = readyMoneyTaskService.selectNumberAndType(activeRfid);
        Result result = new Result(Code.SUCCESS.getCode(), Code.SUCCESS.getMessage());
        result.getData().put("head", map);
        return result;
    }
    @ApiOperation(value = "钞箱持有人查询", notes = "传入参数：无"
            + "返回参数：")
    @GetMapping(value = "selectNameAndId")
    public Result selectNameAndId( HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        List<Map<String,Object>> map = readyMoneyTaskService.selectGrouperNameAndId(request);
        Result result = new Result(Code.SUCCESS.getCode(), Code.SUCCESS.getMessage());
        result.getData().put("users", map);
        return result;
    }
    @ApiOperation(value = "库管员查询", notes = "传入参数：无"
            + "返回参数：")
    @GetMapping(value = "selectLibraryerNameAndId")
    public Result selectLibraryNameAndId( HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        List<Map<String,Object>> map = readyMoneyTaskService.selectLibraryNameAndId(request);
        Result result = new Result(Code.SUCCESS.getCode(), Code.SUCCESS.getMessage());
        result.getData().put("users", map);
        return result;
    }
    @ApiOperation(value = "组长入库申请", notes = "")
    @PutMapping(value = "insertApply")
    public Result insertApply(@RequestBody ReadyMoneyApplyModel param, HttpServletRequest request, HttpServletResponse response){
        try {
            readyMoneyTaskService.insertApply(param,request);
            Result result = new Result(Code.SUCCESS.getCode(), Code.SUCCESS.getMessage());
            return result;
        }catch (BusinessException e){
            return new Result(e.getCode(),e.getErrorMessage());
        }catch (Exception e){
            return new Result(Code.SYSTEM_ERROR.getCode(), Code.SYSTEM_ERROR.getMessage());
        }
    }
    @ApiOperation(value = "管库员名下钞箱详情", notes = "传入参数： state(1在持 2出库) libraryerId(管库员id) beginTime(开始时间) endTime(结束时间) page() pageSize()\n"
            + "返回参数  dbNum: db钞箱总数,\n"
            + "dbTotalMoney: db钞箱总金额,\n"
            + "dfNum: df钞箱总数,\n"
            + "dfTotalMoney: df钞箱总金额,\n"
            + "crsNum: crs钞箱总数,\n"
            + "crsTotalMoney: crs钞箱总金额,\n"
            + "scrsNum: scrs钞箱总数,\n"
            + "totalNumber: 钞箱总数,\n"
            + "totalMoney: 钞箱总金额,\n"
            + "scrsTotalMoney: scrs钞箱总金额\n"
            + "total: 总数,\n"
            + "fundBagName: 基金袋名字,\n"
            + "lid: id,\n"
            + "carType: 钞箱类型,\n"
            + "executor: 操作岗人员,\n"
            + "carMoney: 装箱金额,\n"
            + "time: 时间,\n"
            + "carBoxNumber: 钞箱编号,\n"
            + "state: 钞箱状态 1在持 2入库 3出库 ,\n"
            + "libraryer：持有人\n"
            + "handOverPeople移交人")
    @PostMapping(value = "selectUnderLibraryDetails")
    public Result selectUnderLibraryDetails(@RequestBody LibraryerParamModel param, HttpServletRequest request, HttpServletResponse response, HttpSession session) {//libraryId state grouperId beginTime endTime
        PageHelper.startPage( param.getPage(), param.getPageSize());
        Map<String, Object> map = readyMoneyTaskService.selectUnderLibraryDetails(param, request);
        Result result = new Result(Code.SUCCESS.getCode(), Code.SUCCESS.getMessage());
        result.getData().put("total", ((Page<?>) map.get("underLibraryerDetails")).getTotal());
        result.getData().put("underLibraryerDetails", map.get("underLibraryerDetails"));
        result.getData().put("underLibraryerHeadDetails", map.get("underLibraryerHeadDetails"));
        return result;
    }
    @ApiOperation(value = "退账", notes = "fundId 基金袋id")
    @PatchMapping(value = "refund")
    public Result refund(Integer fundId, HttpServletRequest request, HttpServletResponse response){
        try {
            readyMoneyTaskService.refund(fundId,request);
            return new Result(Code.SUCCESS.getCode(), Code.SUCCESS.getMessage());
        }catch (BusinessException e){
            return new Result(e.getCode(),e.getMessage());
        }catch (Exception e){
            return new Result(Code.SYSTEM_ERROR.getCode(), Code.SYSTEM_ERROR.getMessage());
        }
    }
    @ApiOperation(value = "操作岗修改基金袋", notes = "fundId 基金袋id")
    @PatchMapping(value = "updateFund")
    public Result updateFund(Integer fundId, HttpServletRequest request, HttpServletResponse response){
        try {
            readyMoneyTaskService.updateFund(fundId,request);
            return new Result(Code.SUCCESS.getCode(), Code.SUCCESS.getMessage());
        }catch (BusinessException e){
            return new Result(e.getCode(),e.getMessage());
        }catch (Exception e){
            return new Result(Code.SYSTEM_ERROR.getCode(), Code.SYSTEM_ERROR.getMessage());
        }
    }
    @ApiOperation(value = "管库员出库申请", notes = "")
    @PutMapping(value = "outLibraryApply")
    public Result outLibraryApply(@RequestBody ReadyMoneyApplyModel param, HttpServletRequest request, HttpServletResponse response){
        try {
            readyMoneyTaskService.outLibraryApply(param,request);
            Result result = new Result(Code.SUCCESS.getCode(), Code.SUCCESS.getMessage());
            return result;
        }catch (BusinessException e){
            return new Result(e.getCode(),e.getErrorMessage());
        }catch (Exception e){
            return new Result(Code.SYSTEM_ERROR.getCode(), Code.SYSTEM_ERROR.getMessage());
        }
    }
    @ApiOperation(value = "库管员钞箱移交", notes = "List<Integer> 库管员的集合")
    @PatchMapping(value = "boxHandOver")
    public Result boxHandOver(@RequestBody List<Integer> param, HttpServletRequest request, HttpServletResponse response){
        try {
            readyMoneyTaskService.boxHandOver(param,request);
            return new Result(Code.SUCCESS.getCode(), Code.SUCCESS.getMessage());
        }catch (BusinessException e){
            return new Result(e.getCode(),e.getMessage());
        }catch (Exception e){
            return new Result(Code.SYSTEM_ERROR.getCode(), Code.SYSTEM_ERROR.getMessage());
        }
    }
}