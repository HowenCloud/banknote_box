package com.ixilink.banknote_box.service.service.impl;

import com.ixilink.banknote_box.common.dao.RoleMapper;
import com.ixilink.banknote_box.common.dao.RolePageMapper;
import com.ixilink.banknote_box.common.dao.RolePermissionsMapper;
import com.ixilink.banknote_box.common.dao.UserMapper;
import com.ixilink.banknote_box.common.exception.BusinessException;
import com.ixilink.banknote_box.common.model.Code;
import com.ixilink.banknote_box.common.pojo.BackLibrary;
import com.ixilink.banknote_box.common.pojo.Role;
import com.ixilink.banknote_box.common.pojo.RolePage;
import com.ixilink.banknote_box.common.pojo.RolePermissions;
import com.ixilink.banknote_box.service.service.LibraryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: 张俊
 * @date: 2019-11-25 19:16
 */
@Service
public class LibraryServiceImpl implements LibraryService {

    @Resource
    private UserMapper userMapper;
    @Resource
    private RoleMapper roleMapper;
    @Resource
    private RolePageMapper rolePageMapper;
    @Resource
    private RolePermissionsMapper rolePermissionsMapper;

    @Override
    public List<Map<String, Object>> get(HttpServletRequest request, HttpServletResponse response) {
        return userMapper.library();
    }

    @Override
    @Transactional
    public void add(BackLibrary param, HttpServletRequest request, HttpServletResponse response) {
        int i0 = userMapper.addLibrary(param);
        if (i0 < 1){
            throw new BusinessException(Code.MESSAGE_ERROR.getCode(),"添加失败");
        }
        Role role1 = new Role(null,"用户信息操作岗","负责管理用户人员信息",new Date().getTime(),1,2,2,1,1,param.getId());
        int i1 = roleMapper.insertSelective(role1);
        if (i1 < 1){
            throw new BusinessException(Code.MESSAGE_ERROR.getCode(),"添加失败");
        }
        List<RolePage> rolePages = new ArrayList<>();
        for (int i=33; i<=36; i++){
            RolePage rolePage = new RolePage(role1.getId(),i,param.getId());
            rolePages.add(rolePage);
        }
        rolePageMapper.insertList(rolePages);
        List<RolePermissions> rolePermissions = new ArrayList<>();
        for (int i=1; i<=9; i++){
            RolePermissions rolePermission = new RolePermissions(role1.getId(),i,param.getId());
            rolePermissions.add(rolePermission);
        }
        rolePermissionsMapper.insertList(rolePermissions);

        Role role2 = new Role(null,"用户信息审批岗","负责审批用户管理的操作",new Date().getTime(),1,2,2,1,1,param.getId());
        int i2 = roleMapper.insertSelective(role2);
        if (i2 < 1){
            throw new BusinessException(Code.MESSAGE_ERROR.getCode(),"添加失败");
        }
        List<RolePage> rolePages2 = new ArrayList<>();
        RolePage rolePage = new RolePage(role1.getId(),1,param.getId());
        rolePages2.add(rolePage);
        rolePageMapper.insertList(rolePages2);
        List<RolePermissions> rolePermissions2 = new ArrayList<>();
        for (int i=41; i<=43; i++){
            RolePermissions rolePermission = new RolePermissions(role1.getId(),i,param.getId());
            rolePermissions2.add(rolePermission);
        }
        rolePermissionsMapper.insertList(rolePermissions2);
    }
}
