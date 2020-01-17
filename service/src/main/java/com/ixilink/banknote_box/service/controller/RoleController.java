package com.ixilink.banknote_box.service.controller;

import com.ixilink.banknote_box.common.exception.BusinessException;
import com.ixilink.banknote_box.common.model.Code;
import com.ixilink.banknote_box.common.model.Result;
import com.ixilink.banknote_box.common.pojo.*;
import com.ixilink.banknote_box.common.util.RedisUtil;
import com.ixilink.banknote_box.common.util.ValidatePermissionsUtil;
import com.ixilink.banknote_box.service.parameter_model.role.AddRoleModel;
import com.ixilink.banknote_box.service.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
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
 * @date: 2019-11-13 20:16
 */
@Log4j2
@RestController
@RequestMapping("role")
@Api(value = "role", description = "角色管理")
public class RoleController {
    //GET, POST（多参数）, PUT（添加）, PATCH(更新部分), DELETE（删除）
    @Resource
    private RoleService roleService;
    @Resource
    private RedisUtil redisUtil;

    @PutMapping("add")
    @ApiOperation(value = "添加角色",notes = "",httpMethod = "PUT")
    public Result add(@RequestBody AddRoleModel param, HttpServletRequest request, HttpServletResponse response){
        try {
            roleService.addRole(param,request,response);
            return new Result(Code.SUCCESS.getCode(),Code.SUCCESS.getMessage());
        }catch (BusinessException e){
            return new Result(e.getCode(), e.getErrorMessage());
        }catch (Exception e){
            return new Result(Code.SYSTEM_ERROR.getCode(),Code.SYSTEM_ERROR.getMessage());
        }
    }

    @GetMapping("some")
    @ApiOperation(value = "根据条件查询角色",notes = "",httpMethod = "GET")
    @ApiImplicitParam(name = "roleName", value = "角色名", dataType = "String", paramType = "query")
    public Result some(String roleName, HttpServletRequest request, HttpServletResponse response){
        try {
            List<Role> roles = roleService.getRoles(roleName,request,response);
            Result result = new Result(Code.SUCCESS.getCode(),Code.SUCCESS.getMessage());
            result.getData().put("roles",roles);
            return result;
        }catch (BusinessException e){
            return new Result(e.getCode(),e.getErrorMessage());
        }catch (Exception e){
            return new Result(Code.SYSTEM_ERROR.getCode(),Code.SYSTEM_ERROR.getMessage());
        }
    }

    @DeleteMapping("remove/{id}")
    @ApiOperation(value = "删除角色",notes = "",httpMethod = "DELETE")
    public Result remove(@PathVariable("id") Integer id, HttpServletRequest request, HttpServletResponse response){
        try {
            roleService.removeRole(id,request,response);
            return new Result(Code.SUCCESS.getCode(),Code.SUCCESS.getMessage());
        }catch (BusinessException e){
            return new Result(e.getCode(),e.getErrorMessage());
        }catch (Exception e){
            return new Result(Code.SYSTEM_ERROR.getCode(),Code.SYSTEM_ERROR.getMessage());
        }
    }

    @PatchMapping("modify")
    @ApiOperation(value = "修改角色",notes = "",httpMethod = "PATCH")
    public Result modify(@RequestBody AddRoleModel param, HttpServletRequest request, HttpServletResponse response){
        try {
            roleService.modifyRole(param,request,response);
            return new Result(Code.SUCCESS.getCode(),Code.SUCCESS.getMessage());
        }catch (BusinessException e){
            return new Result(e.getCode(),e.getErrorMessage());
        }catch (Exception e){
            return new Result(Code.SYSTEM_ERROR.getCode(),Code.SYSTEM_ERROR.getMessage());
        }
    }

    @GetMapping("pages")
    @ApiOperation(value = "查询导航页面",notes = "",httpMethod = "GET")
    public Result pages(HttpServletRequest request, HttpServletResponse response){
        try {
            List<Page> pages = roleService.getPages(request, response);
            Result result = new Result(Code.SUCCESS.getCode(),Code.SUCCESS.getMessage());
            result.getData().put("pages",pages);
            return result;
        }catch (BusinessException e){
            return new Result(e.getCode(),e.getErrorMessage());
        }catch (Exception e){
            return new Result(Code.SYSTEM_ERROR.getCode(),Code.SYSTEM_ERROR.getMessage());
        }
    }

    @GetMapping("permissions")
    @ApiOperation(value = "查询功能权限",notes = "",httpMethod = "GET")
    public Result permissions(HttpServletRequest request, HttpServletResponse response){
        try {
            List<PermissionsType> permissions = roleService.getPermissions(request, response);
            Result result = new Result(Code.SUCCESS.getCode(),Code.SUCCESS.getMessage());
            result.getData().put("permissions",permissions);
            return result;
        }catch (BusinessException e){
            return new Result(e.getCode(),e.getErrorMessage());
        }catch (Exception e){
            return new Result(Code.SYSTEM_ERROR.getCode(),Code.SYSTEM_ERROR.getMessage());
        }
    }

    @GetMapping("myPages")
    @ApiOperation(value = "查询我的可访问页面",notes = "",httpMethod = "GET")
    public Result myPages(HttpServletRequest request, HttpServletResponse response){
        try {
            User user = ValidatePermissionsUtil.getUser(request,redisUtil);
            List<Map<String,Object>> pages = roleService.myPages(user.getRoleId());
            Result result = new Result(Code.SUCCESS.getCode(),Code.SUCCESS.getMessage());
            result.getData().put("pages",pages);
            return result;
        }catch (BusinessException e){
            return new Result(e.getCode(),e.getErrorMessage());
        }catch (Exception e){
            return new Result(Code.SYSTEM_ERROR.getCode(),Code.SYSTEM_ERROR.getMessage());
        }
    }

    @GetMapping("myPermissions")
    @ApiOperation(value = "查询我的功能权限",notes = "",httpMethod = "GET")
    public Result myPermissions(HttpServletRequest request, HttpServletResponse response){
        try {
            List<Integer> permissions = roleService.myPermissions(request, response);
            Result result = new Result(Code.SUCCESS.getCode(),Code.SUCCESS.getMessage());
            result.getData().put("permissions",permissions);
            return result;
        }catch (BusinessException e){
            return new Result(e.getCode(),e.getErrorMessage());
        }catch (Exception e){
            return new Result(Code.SYSTEM_ERROR.getCode(),Code.SYSTEM_ERROR.getMessage());
        }
    }

    @GetMapping("getPagesByUser")
    @ApiOperation(value = "查询用户可访问页面",notes = "",httpMethod = "GET")
    public Result getPagesByUser(@Param("userId") Integer userId, HttpServletRequest request, HttpServletResponse response){
        try {
            List<Page> pages = roleService.getPagesByUser(userId, response);
            Result result = new Result(Code.SUCCESS.getCode(),Code.SUCCESS.getMessage());
            result.getData().put("pages",pages);
            return result;
        }catch (BusinessException e){
            return new Result(e.getCode(),e.getErrorMessage());
        }catch (Exception e){
            return new Result(Code.SYSTEM_ERROR.getCode(),Code.SYSTEM_ERROR.getMessage());
        }
    }

    @GetMapping("getPermissionsByUser")
    @ApiOperation(value = "查询用户功能权限",notes = "",httpMethod = "GET")
    public Result getPermissionsByUser(@Param("userId") Integer userId, HttpServletRequest request, HttpServletResponse response){
        try {
            List<Integer> permissions = roleService.getPermissionsByUser(userId, response);
            Result result = new Result(Code.SUCCESS.getCode(),Code.SUCCESS.getMessage());
            result.getData().put("permissions",permissions);
            return result;
        }catch (BusinessException e){
            return new Result(e.getCode(),e.getErrorMessage());
        }catch (Exception e){
            return new Result(Code.SYSTEM_ERROR.getCode(),Code.SYSTEM_ERROR.getMessage());
        }
    }

    @GetMapping("getPagesByRole")
    @ApiOperation(value = "查询角色可访问页面",notes = "",httpMethod = "GET")
    public Result getPagesByRole(@Param("roleId") Integer roleId, HttpServletRequest request, HttpServletResponse response){
        try {
            List<Integer> pages = roleService.getPagesByRole(roleId, response);
            Result result = new Result(Code.SUCCESS.getCode(),Code.SUCCESS.getMessage());
            result.getData().put("pages",pages);
            return result;
        }catch (BusinessException e){
            return new Result(e.getCode(),e.getErrorMessage());
        }catch (Exception e){
            return new Result(Code.SYSTEM_ERROR.getCode(),Code.SYSTEM_ERROR.getMessage());
        }
    }

    @GetMapping("getPermissionsByRole")
    @ApiOperation(value = "查询角色功能权限",notes = "",httpMethod = "GET")
    public Result getPermissionsByRole(@Param("roleId") Integer roleId, HttpServletRequest request, HttpServletResponse response){
        try {
            List<Integer> permissions = roleService.getPermissionsByRole(roleId, response);
            Result result = new Result(Code.SUCCESS.getCode(),Code.SUCCESS.getMessage());
            result.getData().put("permissions",permissions);
            return result;
        }catch (BusinessException e){
            return new Result(e.getCode(),e.getErrorMessage());
        }catch (Exception e){
            return new Result(Code.SYSTEM_ERROR.getCode(),Code.SYSTEM_ERROR.getMessage());
        }
    }

}
