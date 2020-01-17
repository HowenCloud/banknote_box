package com.ixilink.banknote_box.service.controller;

import com.ixilink.banknote_box.common.exception.BusinessException;
import com.ixilink.banknote_box.common.model.Code;
import com.ixilink.banknote_box.common.model.Result;
import com.ixilink.banknote_box.common.pojo.BackLibrary;
import com.ixilink.banknote_box.service.service.LibraryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: 张俊
 * @date: 2019-11-25 19:13
 */
@Log4j2
@RestController
@RequestMapping("library")
@Api(value = "library", description = "机构管理")
public class LibraryController {

    @Resource
    private LibraryService libraryService;


    @GetMapping("")
    @ApiOperation(value = "查询中心库列表",notes = "",httpMethod = "GET")
    public Result library(HttpServletRequest request, HttpServletResponse response){
        try {
            List<Map<String,Object>> library = libraryService.get(request,response);
            Result result = new Result(Code.SUCCESS.getCode(),Code.SUCCESS.getMessage());
            result.getData().put("library",library);
            return result;
        }catch (BusinessException e){
            return new Result(e.getCode(),e.getErrorMessage());
        }catch (Exception e){
            return new Result(Code.SYSTEM_ERROR.getCode(),Code.SYSTEM_ERROR.getMessage());
        }
    }

    @PutMapping("")
    @ApiOperation(value = "添加中心库",notes = "",httpMethod = "PUT")
    public Result add(@RequestBody BackLibrary param, HttpServletRequest request, HttpServletResponse response){
        try {
            libraryService.add(param,request,response);
            return new Result(Code.SUCCESS.getCode(),Code.SUCCESS.getMessage());
        }catch (BusinessException e){
            return new Result(e.getCode(), e.getErrorMessage());
        }catch (Exception e){
            return new Result(Code.SYSTEM_ERROR.getCode(),Code.SYSTEM_ERROR.getMessage());
        }
    }
}
