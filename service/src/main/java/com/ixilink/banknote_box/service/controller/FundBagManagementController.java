package com.ixilink.banknote_box.service.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ixilink.banknote_box.common.exception.BusinessException;
import com.ixilink.banknote_box.common.model.Code;
import com.ixilink.banknote_box.common.model.Result;
import com.ixilink.banknote_box.common.pojo.FundBagManagementExcel;
import com.ixilink.banknote_box.common.util.ExportExcel;
import com.ixilink.banknote_box.service.service.FundBagManagementService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

@Log4j2
@RestController
@RequestMapping("fundBagManagement")
@Api(value = "/fundBagManagement", description = "基金袋管理")
public class FundBagManagementController {

    @Resource
    private FundBagManagementService fundBagManagementService;
    private static final String[] HEADERS = {"序号","基金袋名称","装袋时间","装袋金额","装袋人员","基金袋状态"};

    @ApiOperation(value = "查询基金袋数量和金额", notes = "传入参数：无 \n"
            + "返回数据：allNumber（基金袋总数）  allAmount（基金袋总金额）  unuseNumber（未使用基金袋数量）  unuseAmount（未使用基金袋金额）  usedNumber（已使用基金袋数量）  usedAmount（已使用基金袋金额）  usingNumber（使用中基金袋数量）  usingAmount（使用中基金袋金额）  ")
    @GetMapping(value = "selectNumberAndAmount")
    public Result selectNumberAndAmount( HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        Map<String, Object> numberAndAmount = fundBagManagementService.selectNumberAndAmount(request);
        Result result = new Result(Code.SUCCESS.getCode(), Code.SUCCESS.getMessage());
        result.getData().put("numberAndAmount", numberAndAmount);
        return result;
    }
    @ApiOperation(value = "装袋人员查询", notes = "传入参数：无")
    @GetMapping(value = "selectBaggingPersonnel")
    public Result selectBaggingPersonnel( HttpServletRequest request, HttpServletResponse response, HttpSession session) {

        List<Map<String, Object>> baggingPersonnel = fundBagManagementService.selectBaggingPersonnel(request);
        Result result = new Result(Code.SUCCESS.getCode(), Code.SUCCESS.getMessage());
        result.getData().put("baggingPersonnel", baggingPersonnel);
        return result;
    }

    @ApiOperation(value = "添加基金袋", notes = "installationTime（装袋时间，时间戳）  baggingAmount（装袋金额 int）  baggingPersonnel（装袋人员，多个用逗号隔开，是人名）baggingLockBarNumber(基金袋锁条码) baggingPersonnelId(装袋人员的id,用逗号隔开)")
    @PutMapping(value = "insert")
    public Result insert(@RequestBody Map param, HttpServletRequest request, HttpServletResponse response){
        try {
            //param.get("installationTime");
            fundBagManagementService.insert(param,request);
            Result result = new Result(Code.SUCCESS.getCode(), Code.SUCCESS.getMessage());
            return result;
        }catch (BusinessException e){
            return new Result(e.getCode(),e.getErrorMessage().toString());
        }catch (Exception e){
            return new Result(Code.SYSTEM_ERROR.getCode(), Code.SYSTEM_ERROR.getMessage());
        }
    }

    @ApiOperation(value = "基金袋查询", notes = "传入参数：pageNumber  pageSize fundBagName(基金袋名称)baggingPersonnel(装袋人员) state(基金袋状态：1未使用 2已使用 3使用中)\n"
            + "返回参数installationTime装袋时间\n"
            + "baggingAmount剩余金额\n"
            + "baggingPersonnel装袋人员\n"
            + "state基金袋状态：1未使用 2已使用 3使用中 4删除\n"
            + "fundBagName基金袋名字\n"
            + "\n"
            + "baggingLockBarNumber基金袋锁条号\n"
            + "libraryId中心库id\n"
            + "baggingPersonnelId装袋人员id")
    @PostMapping(value = "serach")
    public Result serach(@RequestBody Map param, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        PageHelper.startPage((Integer) param.get("pageNumber"),(Integer) param.get("pageSize"));

        List<Map<String,Object>> returnObject = fundBagManagementService.selectByParam(param,request);
        Result result = new Result(Code.SUCCESS.getCode(), Code.SUCCESS.getMessage());
        result.getData().put("total",((Page<?>) returnObject).getTotal());
        result.getData().put("returnObject", returnObject);
        return result;
    }


    @ApiOperation(value = "基金袋修改", notes = "installationTime(装袋时间)   baggingAmount（装袋金额） baggingPersonnelId（装袋人员id集合） baggingLockBarNumber(基金袋锁条码))  id")
    @PutMapping(value = "updateFundBag")
    public Result updateFundBagById(@RequestBody Map param, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        try {
            fundBagManagementService.updateFundBagById(param,request);
            Result result = new Result(Code.SUCCESS.getCode(), Code.SUCCESS.getMessage());
            return result;
        }catch (BusinessException e){
            return new Result(e.getCode(),e.getErrorMessage());
        }catch (Exception e){
            return new Result(Code.SYSTEM_ERROR.getCode(), Code.SYSTEM_ERROR.getMessage());
        }
    }

    @ApiOperation(value = "基金袋删除", notes = "id")
    @DeleteMapping(value = "delete/{id}")
    public Result deleteById(@PathVariable("id") Integer id, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        try {
            fundBagManagementService.deleteById(id);
            Result result = new Result(Code.SUCCESS.getCode(), Code.SUCCESS.getMessage());
            return result;
        }catch (BusinessException e){
            return new Result(e.getCode(),e.getErrorMessage());
        }catch (Exception e){
            return new Result(Code.SYSTEM_ERROR.getCode(), Code.SYSTEM_ERROR.getMessage());
        }
    }

    @ApiOperation(value = "基金袋信息导出",notes = "fundBagName(基金袋名称)baggingPersonnel(装袋人员) state(基金袋状态：1未使用 2已使用 3使用中)",
            httpMethod = "POST",produces="application/octet-stream")
    @RequestMapping("/exportExcel")
    public Result exportExcel(@RequestBody Map<String, Object> param,HttpServletRequest request,HttpServletResponse response){

        // 数据集合
        List<FundBagManagementExcel> r = fundBagManagementService.selectToExcel(param,request);

        // 选择下载
        ExportExcel<FundBagManagementExcel> exportExcel = new ExportExcel<FundBagManagementExcel>();

        String filename = "操作日志.xlsx";
        OutputStream out = null;
        try {
            //下载到浏览器默认地址
            out = response.getOutputStream();
//			response.setContentType("application/octet-stream;charset=UTF-8");
            response.setHeader("Content-disposition", "attachment;filename="+ URLEncoder.encode(filename, "UTF-8"));

            // 进行导出
            exportExcel.exportExcel("Sheet1", HEADERS, r, out);

        } catch (Exception e1) {
            e1.printStackTrace();
            return null;
        }

        return null;
    }
    @ApiOperation(value = "所有基金袋名字查询", notes = "传入参数：无"
            + "返回参数：")
    @GetMapping(value = "selectFundNameByLibraryId")
    public Result selectFundNameByLibraryId( HttpServletRequest request, HttpServletResponse response, HttpSession session) {

        List<Map<String,Object>> map = fundBagManagementService.selectFundNameByLibraryId(request);
        Result result = new Result(Code.SUCCESS.getCode(), Code.SUCCESS.getMessage());
        result.getData().put("fundName", map);
        return result;
    }

}
