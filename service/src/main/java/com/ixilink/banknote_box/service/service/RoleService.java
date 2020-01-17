package com.ixilink.banknote_box.service.service;

import com.ixilink.banknote_box.common.pojo.Page;
import com.ixilink.banknote_box.common.pojo.PermissionsType;
import com.ixilink.banknote_box.common.pojo.Role;
import com.ixilink.banknote_box.service.parameter_model.role.AddRoleModel;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: 张俊
 * @date: 2019-11-13 20:24
 */
public interface RoleService {

    void addRole(AddRoleModel param, HttpServletRequest request, HttpServletResponse response);

    void removeRole(Integer id, HttpServletRequest request, HttpServletResponse response);

    List<Role> getRoles(String roleName, HttpServletRequest request, HttpServletResponse response);

    void modifyRole(AddRoleModel param, HttpServletRequest request, HttpServletResponse response);

    List<Page> getPages(HttpServletRequest request, HttpServletResponse response);

    List<PermissionsType> getPermissions(HttpServletRequest request, HttpServletResponse response);

    List<Map<String,Object>> myPages(Integer roleId) throws Exception;

    List<Integer> myPermissions(HttpServletRequest request, HttpServletResponse response);

    List<Page> getPagesByUser(Integer userId, HttpServletResponse response);

    List<Integer> getPermissionsByUser(Integer userId, HttpServletResponse response);

    List<Integer> getPagesByRole(Integer roleId, HttpServletResponse response);

    List<Integer> getPermissionsByRole(Integer roleId, HttpServletResponse response);
}
