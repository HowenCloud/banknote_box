package com.ixilink.banknote_box.service.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ixilink.banknote_box.common.exception.BusinessException;
import com.ixilink.banknote_box.common.model.Code;
import com.ixilink.banknote_box.common.model.Result;
import com.ixilink.banknote_box.common.model.UserSimple;
import com.ixilink.banknote_box.common.pojo.User;
import com.ixilink.banknote_box.common.util.JsonUtil;
import com.ixilink.banknote_box.common.util.ObjectOrMapUtil;
import com.ixilink.banknote_box.common.util.RedisUtil;
import com.ixilink.banknote_box.common.util.ValidatePermissionsUtil;
import com.ixilink.banknote_box.service.parameter_model.user.AddUserModel;
import com.ixilink.banknote_box.service.parameter_model.user.FindUserModel;
import com.ixilink.banknote_box.service.service.RoleService;
import com.ixilink.banknote_box.service.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: 张俊
 * @date: 2019-11-08 22:40
 */
@Log4j2
@RestController
@RequestMapping("user")
@Api(value = "user", description = "用户管理")
public class UserController {
    //GET, POST（多参数）, PUT（添加）, PATCH(更新部分), DELETE（删除）
    @Resource
    private UserService userService;
    @Resource
    private RoleService roleService;
    @Resource
    private RedisUtil redisUtil;

    @PutMapping("add")
    @ApiOperation(value = "添加用户",notes = "",httpMethod = "PUT")
    public Result add(@RequestBody AddUserModel param, HttpServletRequest request, HttpServletResponse response){
        try {
            userService.addUser(param,request,response);
            return new Result(Code.SUCCESS.getCode(),Code.SUCCESS.getMessage());
        }catch (BusinessException e){
            return new Result(e.getCode(), e.getErrorMessage());
        }catch (Exception e){
            return new Result(Code.SYSTEM_ERROR.getCode(),Code.SYSTEM_ERROR.getMessage());
        }
    }

    @GetMapping("all")
    @ApiOperation(value = "查询所有用户",notes = "",httpMethod = "GET")
    public Result getAll(HttpServletRequest request, HttpServletResponse response){
        try {
            List<User> all = userService.getUserAll();
            Result result = new Result(Code.SUCCESS.getCode(),Code.SUCCESS.getMessage());
            result.getData().put("users",all);
            return result;
        }catch (BusinessException e){
            return new Result(e.getCode(),e.getErrorMessage());
        }catch (Exception e){
            return new Result(Code.SYSTEM_ERROR.getCode(),Code.SYSTEM_ERROR.getMessage());
        }
    }

    @PostMapping("some")
    @ApiOperation(value = "根据条件查询用户",notes = "",httpMethod = "POST")
    public Result some(@RequestBody FindUserModel param, HttpServletRequest request, HttpServletResponse response){
        try {
            List<User> users = userService.getUsers(param,request,response);
            Result result = new Result(Code.SUCCESS.getCode(),Code.SUCCESS.getMessage());
            result.getData().put("total",((Page<?>) users).getTotal());
            result.getData().put("users",users);
            return result;
        }catch (BusinessException e){
            return new Result(e.getCode(),e.getErrorMessage());
        }catch (Exception e){
            return new Result(Code.SYSTEM_ERROR.getCode(),Code.SYSTEM_ERROR.getMessage());
        }
    }

    @DeleteMapping("remove/{id}")
    @ApiOperation(value = "删除用户",notes = "",httpMethod = "DELETE")
    public Result remove(@PathVariable("id") Integer id, HttpServletRequest request, HttpServletResponse response){
        try {
            userService.removeUser(id,request,response);
            return new Result(Code.SUCCESS.getCode(),Code.SUCCESS.getMessage());
        }catch (BusinessException e){
            return new Result(e.getCode(),e.getErrorMessage());
        }catch (Exception e){
            return new Result(Code.SYSTEM_ERROR.getCode(),Code.SYSTEM_ERROR.getMessage());
        }
    }

    @PatchMapping("modify")
    @ApiOperation(value = "修改用户",notes = "",httpMethod = "PATCH")
    public Result modify(@RequestBody AddUserModel param, HttpServletRequest request, HttpServletResponse response){
        try {
            userService.modifyUser(param,request,response);
            return new Result(Code.SUCCESS.getCode(),Code.SUCCESS.getMessage());
        }catch (BusinessException e){
            return new Result(e.getCode(),e.getErrorMessage());
        }catch (Exception e){
            return new Result(Code.SYSTEM_ERROR.getCode(),Code.SYSTEM_ERROR.getMessage());
        }
    }

    @GetMapping("department")
    @ApiOperation(value = "查询部门下拉列表",notes = "",httpMethod = "GET")
    public Result department(@Param("libraryId")Integer libraryId, HttpServletRequest request, HttpServletResponse response){
        try {
            List<Map<String, Object>> department = userService.department(libraryId, request);
            Result result = new Result(Code.SUCCESS.getCode(), Code.SUCCESS.getMessage());
            result.getData().put("department",department);
            return result;
        }catch (BusinessException e){
            return new Result(e.getCode(),e.getErrorMessage());
        }catch (Exception e){
            return new Result(Code.SYSTEM_ERROR.getCode(),Code.SYSTEM_ERROR.getMessage());
        }
    }

    @GetMapping("role")
    @ApiOperation(value = "查询角色下拉列表",notes = "",httpMethod = "GET")
    @ApiImplicitParam(name = "libraryId", value = "库ID", required = true, dataType = "String", paramType="query")
    public Result role(@Param("libraryId")Integer libraryId, HttpServletRequest request, HttpServletResponse response){
        try {
            List<Map<String, Object>> role = userService.role(libraryId, request);
            Result result = new Result(Code.SUCCESS.getCode(), Code.SUCCESS.getMessage());
            result.getData().put("role",role);
            return result;
        }catch (BusinessException e){
            return new Result(e.getCode(),e.getErrorMessage());
        }catch (Exception e){
            return new Result(Code.SYSTEM_ERROR.getCode(),Code.SYSTEM_ERROR.getMessage());
        }
    }

    @GetMapping("info")
    @ApiOperation(value = "查询个人用户信息",notes = "",httpMethod = "GET")
    public Result info(HttpServletRequest request, HttpServletResponse response){
        try {
            Result result = new Result(Code.SUCCESS.getCode(),Code.SUCCESS.getMessage());
            User user = ValidatePermissionsUtil.getUser(request,redisUtil);
            List<Map<String,Object>> pageMaps = roleService.myPages(user.getRoleId());
            List<Integer> permission = null;
            if (redisUtil.getStringRedisTemplate().opsForHash().hasKey("rolePermissions","r-"+user.getRoleId())){
                permission = ValidatePermissionsUtil.getPermission(user.getRoleId(), redisUtil);
            }else {
                permission = roleService.getPermissionsByRole(user.getRoleId(),response);
            }
//            List<com.ixilink.banknote_box.common.pojo.Page> pages = roleService.getPagesByUser(user.getId(), response);
//            if (pages.size()>0){
//                com.ixilink.banknote_box.common.pojo.Page page = pages.get(0);
//                if (page.getChilds().size()>0){
//                    result.getData().put("first",page.getChilds().get(0).getPageUrl());
//                }else {
//                    result.getData().put("first",page.getPageUrl());
//                }
//            }
//            List<Map<String,Object>> pageMaps = new ArrayList<>();
//            for (com.ixilink.banknote_box.common.pojo.Page p:pages){
//                Map<String, Object> map = ObjectOrMapUtil.objectToMap(p);
//                if (p.getChilds().size()==0) {
//                    map.remove("childs");
//                }else if (p.getChilds().size()>0){
//                    List<Map<String,Object>> childs = new ArrayList<>();
//                    for (com.ixilink.banknote_box.common.pojo.Page c:p.getChilds()){
//                        Map<String, Object> child = ObjectOrMapUtil.objectToMap(c);
//                        child.remove("childs");
//                        childs.add(child);
//                        map.put("childs",childs);
//                    }
//                }
//                pageMaps.add(map);
//            }
            redisUtil.getStringRedisTemplate().opsForHash().put("users","u-"+user.getId(),JsonUtil.obj2str(user));
            result.getData().put("user",user);
            result.getData().put("pages",pageMaps);
            result.getData().put("permissions",permission);
            return result;
        }catch (BusinessException e){
            return new Result(e.getCode(),e.getErrorMessage());
        }catch (Exception e){
            return new Result(Code.SYSTEM_ERROR.getCode(),Code.SYSTEM_ERROR.getMessage());
        }
    }

    @GetMapping("infoById")
    @ApiOperation(value = "查询指定用户信息",notes = "",httpMethod = "GET")
    public Result infoById(Integer id, HttpServletRequest request, HttpServletResponse response){
        try {
            User user = userService.infoById(id, request);
            Result result = new Result(Code.SUCCESS.getCode(),Code.SUCCESS.getMessage());
            result.getData().put("user",user);
            return result;
        }catch (BusinessException e){
            return new Result(e.getCode(),e.getErrorMessage());
        }catch (Exception e){
            return new Result(Code.SYSTEM_ERROR.getCode(),Code.SYSTEM_ERROR.getMessage());
        }
    }

    @GetMapping("twoInfo")
    @ApiOperation(value = "查询双人用户信息",notes = "",httpMethod = "GET")
    public Result twoInfo(HttpServletRequest request, HttpServletResponse response){
        try {
            Result result = new Result(Code.SUCCESS.getCode(),Code.SUCCESS.getMessage());
            User user = ValidatePermissionsUtil.getUser(request,redisUtil);
            List<Map<String,Object>> pageMaps = roleService.myPages(user.getRoleId());
            List<Integer> permission = null;
            if (redisUtil.getStringRedisTemplate().opsForHash().hasKey("rolePermissions","r-"+user.getRoleId())){
                permission = ValidatePermissionsUtil.getPermission(user.getRoleId(), redisUtil);
            }else {
                permission = roleService.getPermissionsByRole(user.getRoleId(),response);
            }
            redisUtil.getStringRedisTemplate().opsForHash().put("users","u-"+user.getId(),JsonUtil.obj2str(user));
            result.getData().put("user",user);
            User partner = userService.infoById(user.getPartnerId(), request);
            result.getData().put("partner",partner);
            result.getData().put("pages",pageMaps);
            result.getData().put("permissions",permission);
            return result;
        }catch (BusinessException e){
            return new Result(e.getCode(),e.getErrorMessage());
        }catch (Exception e){
            return new Result(Code.SYSTEM_ERROR.getCode(),Code.SYSTEM_ERROR.getMessage());
        }
    }

    @GetMapping("getDepositUser")
    @ApiOperation(value = "加钞人员列表",notes = "",httpMethod = "GET")
    public Result getDepositUser(HttpServletRequest request, HttpServletResponse response){
        try {
            List<UserSimple> depositUsers = userService.getDepositUser(request);
            Result result = new Result(Code.SUCCESS.getCode(), Code.SUCCESS.getMessage());
            result.getData().put("depositUsers",depositUsers);
            return result;
        }catch (BusinessException e){
            return new Result(e.getCode(),e.getErrorMessage());
        }catch (Exception e){
            return new Result(Code.SYSTEM_ERROR.getCode(),Code.SYSTEM_ERROR.getMessage());
        }
    }


}
