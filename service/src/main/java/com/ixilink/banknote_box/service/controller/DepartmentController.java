package com.ixilink.banknote_box.service.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ixilink.banknote_box.common.exception.BusinessException;
import com.ixilink.banknote_box.common.model.Code;
import com.ixilink.banknote_box.common.model.Result;
import com.ixilink.banknote_box.common.pojo.Department;
import com.ixilink.banknote_box.service.service.DepartmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Log4j2
@RestController
@RequestMapping("Department")
@Api(value = "/Department", description = "部门管理")
public class DepartmentController {

    @Resource
    private DepartmentService departmentService;


    @ApiOperation(value = "添加部门", notes = "departmentName(部门名字) remarks（备注描述")//departmentName remarks createTime
    @PutMapping(value = "insert")
    public Result insert(@RequestBody Department param, HttpServletRequest request, HttpServletResponse response){
        try {
            param.setCreateTime(new Date().getTime());
            departmentService.insert(param,request);
            return new Result(Code.SUCCESS.getCode(), Code.SUCCESS.getMessage());
        }catch (BusinessException e){
            return new Result(e.getCode(),e.getErrorMessage());
        }catch (Exception e){
            return new Result(Code.SYSTEM_ERROR.getCode(), Code.SYSTEM_ERROR.getMessage());
        }
    }

    @ApiOperation(value = "查询部门", notes = "参数：departmentName(部门名字)  pageNumber  pageSize"
            + "\n返回值 id(id),departmentName(部门名称),remarks（备注描述）,createTime（创建时间）,details(详情),state（状态：1在用 2已删除）")//id,department_name departmentName,remarks,create_time createTime,state
    @PostMapping(value = "select")
    public Result select(@RequestBody Map<String,Object> param, HttpServletRequest request, HttpServletResponse response){
        try {
            PageHelper.startPage((Integer) param.get("pageNumber"),(Integer) param.get("pageSize"));
            List<Map<String,Object>> department = departmentService.selectByDepartmentName((String) param.get("departmentName"),request);
            Result result = new Result(Code.SUCCESS.getCode(), Code.SUCCESS.getMessage());
            result.getData().put("total",((Page<?>) department).getTotal());
            result.getData().put("department", department);
            return result;
        }catch (BusinessException e){
            return new Result(e.getCode(),e.getMessage());
        }catch (Exception e){
            return new Result(Code.SYSTEM_ERROR.getCode(), Code.SYSTEM_ERROR.getMessage());
        }
    }
    @ApiOperation(value = "修改部门", notes = "departmentName(部门名称) remarks（部门描述） id")
    @PatchMapping(value = "update")
    public Result update(@RequestBody Map<String,Object> param, HttpServletRequest request, HttpServletResponse response){
        try {
           departmentService.updateByPrimaryKey(param,request);
            return new Result(Code.SUCCESS.getCode(), Code.SUCCESS.getMessage());
        }catch (BusinessException e){
            return new Result(e.getCode(),e.getMessage());
        }catch (Exception e){
            return new Result(Code.SYSTEM_ERROR.getCode(), Code.SYSTEM_ERROR.getMessage());
        }
    }

    @ApiOperation(value = "删除部门", notes = "id")//id,department_name departmentName,remarks,create_time createTime,state
    @DeleteMapping(value = "delete/{id}")
    public Result delete(@PathVariable("id")Integer id, HttpServletRequest request, HttpServletResponse response){
        try {
          departmentService.deleteByPrimary(id,request);
            Result result = new Result(Code.SUCCESS.getCode(), Code.SUCCESS.getMessage());
            return result;
        }catch (BusinessException e){
            return new Result(e.getCode(),e.getMessage());
        }catch (Exception e){
            return new Result(Code.SYSTEM_ERROR.getCode(), Code.SYSTEM_ERROR.getMessage());
        }
    }
}
