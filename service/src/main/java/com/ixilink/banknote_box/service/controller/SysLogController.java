package com.ixilink.banknote_box.service.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ixilink.banknote_box.common.exception.BusinessException;
import com.ixilink.banknote_box.common.model.Code;
import com.ixilink.banknote_box.common.model.Result;
import com.ixilink.banknote_box.common.pojo.SysLog;
import com.ixilink.banknote_box.common.pojo.SysLogExcel;
import com.ixilink.banknote_box.common.util.ExportExcel;
import com.ixilink.banknote_box.service.service.SysLogService;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
@RestController
@RequestMapping("sysLog")
@Api(value = "/sysLog", description = "系统日志")
public class SysLogController {

    @Resource
    private SysLogService sysLogService;
    private static final String[] HEADERS = {"序号","操作时间","操作人","操作对象","操作类容"};


    @ApiOperation(value = "插入日志", notes = "operator(操作人string) operationObject（操作对象id int） operationContent（操作内容 string）", httpMethod = "POST")//createTime operator operationObject operationContent
    @RequestMapping(value = "insert", method = RequestMethod.POST,  produces = "application/json;charset=UTF-8")
    public Result insert(@RequestBody SysLog param, HttpServletRequest request, HttpServletResponse response){
        try {
            sysLogService.Insert(param);
            Result result = new Result(Code.SUCCESS.getCode(), Code.SUCCESS.getMessage());
            return result;
        }catch (BusinessException e){
            return new Result(e.getCode(),e.getMessage());
        }catch (Exception e){
            return new Result(Code.SYSTEM_ERROR.getCode(), Code.SYSTEM_ERROR.getMessage());
        }
    }
    @ApiOperation(value = "日志查询", notes = "传入参数：pageNumber  pageSize"
            + "beginTime(开始时间 long) endTime（结束时间 long） operationObject（操作对象 int）  operator（操作人）", httpMethod = "POST")
    @RequestMapping(value = "select", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public Result select(@RequestBody Map param, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        PageHelper.startPage((Integer) param.get("pageNumber"),(Integer) param.get("pageSize"));
        List<Map<String,Object>> returnObject = sysLogService.select(param,request);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("total", ((Page<?>) returnObject).getTotal());
        map.put("returnObject", returnObject);
        return new Result(Code.SUCCESS.getCode(), Code.SUCCESS.getMessage(), map);
    }
    @ApiOperation(value = "操作对象查询")
    @GetMapping(value = "selectLogOperationObject")
    public Result selectLogOperationObject( HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        List<Map<String,Object>> returnObject = sysLogService.selectLogOperationObject();
        Result result = new Result(Code.SUCCESS.getCode(), Code.SUCCESS.getMessage());
        result.getData().put("returnObject",returnObject);
        return result;
    }

    /**
     * 导出excel:日志下载,下载到浏览器
     */
    @ApiOperation(value = "日志下载数据导出",notes = "beginTime(开始时间 long) endTime（结束时间 long）operationObject（操作对象 int）  operator（操作人）",
            httpMethod = "POST",produces="application/octet-stream")
    @RequestMapping("/exportExcel")
    public Result exportExcel(@RequestBody Map<String, Object> param,HttpServletResponse response,HttpServletRequest request){

        // 数据集合
        List<SysLogExcel> r = sysLogService.selectExcel(param,request);

        // 选择下载
        ExportExcel<SysLogExcel> exportExcel = new ExportExcel<SysLogExcel>();

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

}
