package com.ixilink.banknote_box.service.service.impl;

import com.ixilink.banknote_box.common.dao.*;
import com.ixilink.banknote_box.common.exception.BusinessException;
import com.ixilink.banknote_box.common.model.Code;
import com.ixilink.banknote_box.common.pojo.*;
import com.ixilink.banknote_box.common.util.JsonUtil;
import com.ixilink.banknote_box.common.util.ObjectOrMapUtil;
import com.ixilink.banknote_box.common.util.RedisUtil;
import com.ixilink.banknote_box.common.util.ValidatePermissionsUtil;
import com.ixilink.banknote_box.service.parameter_model.role.AddRoleModel;
import com.ixilink.banknote_box.service.service.RoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * @description:
 * @author: 张俊
 * @date: 2019-11-13 20:24
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Resource
    private RoleMapper roleMapper;
    @Resource
    private PageMapper pageMapper;
    @Resource
    private PermissionsMapper permissionsMapper;
    @Resource
    private RolePermissionsMapper rolePermissionsMapper;
    @Resource
    private RolePageMapper rolePageMapper;
    @Resource
    private PermissionsTypeMapper permissionsTypeMapper;
    @Resource
    private UserMapper userMapper;
    @Resource
    private RedisUtil redisUtil;

    @Override
    @Transactional
    public void addRole(AddRoleModel param, HttpServletRequest request, HttpServletResponse response) {
        ValidatePermissionsUtil.validatePermission(request,redisUtil,4);
        RoleExample roleExample = new RoleExample();
        roleExample.createCriteria().andRoleNameEqualTo(param.getRoleName());
        long count = roleMapper.countByExample(roleExample);
        if (count > 0){
            throw new BusinessException(Code.MESSAGE_ERROR.getCode(),"该角色已存在");
        }
        User me = ValidatePermissionsUtil.getUser(request,redisUtil);
        //插入角色
        Role role = new Role(null,param.getRoleName(),param.getRemarks(),new Date().getTime(),null,null,null,null,null,me.getLibraryId());
        int addCount = roleMapper.insertSelective(role);
        if (addCount < 1){
            throw new BusinessException(Code.MESSAGE_ERROR.getCode(),"添加失败");
        }
        //插入角色的页面权限
        if (param.getPages().size()>0) {
            List<RolePage> rolePages = new ArrayList<>();
            if (param.getPages().contains(0)) param.getPages().remove(0);
            param.getPages().forEach(page -> {
                RolePage rolePage = new RolePage(role.getId(), page, me.getLibraryId());
                rolePages.add(rolePage);
            });
            int rolePageCount = rolePageMapper.insertList(rolePages);
            if (rolePageCount < 1) {
                throw new BusinessException(Code.MESSAGE_ERROR.getCode(), "添加失败");
            }
        }
        //插入角色的功能权限
        if (param.getPermissions().size()>0) {
            List<RolePermissions> rolePermissions = new ArrayList<>();
            List<Integer> permissions = new ArrayList<>();
            param.getPermissions().forEach(perm -> {if (perm > 0) permissions.add(perm);});
            redisUtil.getStringRedisTemplate().opsForHash().put("rolePermissions","r-"+param.getId(),JsonUtil.list2str(permissions));
            permissions.forEach(permission -> {
                RolePermissions rolePermission = new RolePermissions(role.getId(), permission, me.getLibraryId());
                rolePermissions.add(rolePermission);
            });
            int rolePermissionCount = rolePermissionsMapper.insertList(rolePermissions);
            if (rolePermissionCount < 1) {
                throw new BusinessException(Code.MESSAGE_ERROR.getCode(), "添加失败");
            }
        }

    }

    @Override
    @Transactional
    public void removeRole(Integer id, HttpServletRequest request, HttpServletResponse response) {
        ValidatePermissionsUtil.validatePermission(request,redisUtil,6);
        Role role1 = roleMapper.selectByPrimaryKey(id);
        if (role1.getDeleting().equals(2)){
            throw new BusinessException(Code.MESSAGE_ERROR.getCode(),"系统角色不能被删除！");
        }
        UserExample userExample = new UserExample();
        userExample.createCriteria().andRoleIdEqualTo(id).andStateNotEqualTo(1);
        long count = userMapper.countByExample(userExample);
        if (count>0){
            throw new BusinessException(Code.MESSAGE_ERROR.getCode(),"该角色下有用户，不能删除！");
        }
        Role role = new Role();
        role.setId(id);
        role.setState(2);
        int i = roleMapper.updateByPrimaryKeySelective(role);
        if (i <= 0){
            throw new BusinessException(Code.MESSAGE_ERROR.getCode(),"删除失败，请刷新页面再试！");
        }
        redisUtil.getStringRedisTemplate().opsForHash().delete("rolePermissions","r-"+id);
    }

    @Override
    public List<Role> getRoles(String roleName, HttpServletRequest request, HttpServletResponse response) {
        RoleExample roleExample = new RoleExample();
        RoleExample.Criteria criteria = roleExample.createCriteria();
        criteria.andStateEqualTo(1).andIsSelectEqualTo(1);
        if (roleName != null && !"".equals(roleName)) criteria.andRoleNameLike("%"+roleName+"%");
        User user = ValidatePermissionsUtil.getUser(request, redisUtil);
        if (user.getLibraryId()!=0){
            criteria.andLibraryIdEqualTo(user.getLibraryId());
        }
        return roleMapper.selectByExample(roleExample);
    }

    @Override
    @Transactional
    public void modifyRole(AddRoleModel param, HttpServletRequest request, HttpServletResponse response) {
        ValidatePermissionsUtil.validatePermission(request,redisUtil,5);
        Role role1 = roleMapper.selectByPrimaryKey(param.getId());
        if (role1.getModifiable().equals(2)){
            throw new BusinessException(Code.MESSAGE_ERROR.getCode(),"该角色不能修改！");
        }
        User me = ValidatePermissionsUtil.getUser(request,redisUtil);
        Role role = new Role(param.getId(),param.getRoleName(),param.getRemarks(),null,null,null,null,null,null,null);
        String needLogin = roleMapper.getNeedLogin();
        String[] split = needLogin.split(",");
        List<String> ids = Arrays.asList(split);
        if (ids.contains(param.getId().toString())){
            role.setRoleName(null);
        }
        int i = roleMapper.updateByPrimaryKeySelective(role);
        if (i < 1){
            throw new BusinessException(Code.MESSAGE_ERROR.getCode(),"修改失败");
        }
        //删除原有页面
        RolePageExample rolePageExample = new RolePageExample();
        rolePageExample.createCriteria().andRoleIdEqualTo(param.getId());
        rolePageMapper.deleteByExample(rolePageExample);
        //插入角色的页面权限
        if (param.getPages().size()>0) {
            List<RolePage> rolePages = new ArrayList<>();
            if (param.getPages().contains(0)) param.getPages().remove(0);
            param.getPages().forEach(page -> {
                RolePage rolePage = new RolePage(role.getId(), page, me.getLibraryId());
                rolePages.add(rolePage);
            });
            int rolePageCount = rolePageMapper.insertList(rolePages);
            if (rolePageCount < 1) {
                throw new BusinessException(Code.MESSAGE_ERROR.getCode(), "修改失败");
            }
        }
        //删除原有权限
        RolePermissionsExample rolePermissionsExample = new RolePermissionsExample();
        rolePermissionsExample.createCriteria().andRoleIdEqualTo(param.getId());
        rolePermissionsMapper.deleteByExample(rolePermissionsExample);
        //插入角色的功能权限
        if (param.getPermissions().size()>0){
            List<RolePermissions> rolePermissions = new ArrayList<>();
            List<Integer> permissions = new ArrayList<>();
            param.getPermissions().forEach(perm ->{if (perm >0) permissions.add(perm);});
            redisUtil.getStringRedisTemplate().opsForHash().put("rolePermissions","r-"+param.getId(),JsonUtil.list2str(permissions));
            permissions.forEach(permission -> {
                RolePermissions rolePermission = new RolePermissions(role.getId(),permission, me.getLibraryId());
                rolePermissions.add(rolePermission);
            });
            int rolePermissionCount = rolePermissionsMapper.insertList(rolePermissions);
            if (rolePermissionCount < 1){
                throw new BusinessException(Code.MESSAGE_ERROR.getCode(),"修改失败");
            }
        }
    }

    @Override
    public List<Page> getPages(HttpServletRequest request, HttpServletResponse response) {
        PageExample parent = new PageExample();
        parent.createCriteria().andPageLevelEqualTo(0).andIsSelectEqualTo(1).andStateEqualTo(1);
        List<Page> parentPages = pageMapper.selectByExample(parent);

        PageExample child = new PageExample();
        child.createCriteria().andPageLevelEqualTo(1).andIsSelectEqualTo(1).andStateEqualTo(1);
        List<Page> childPages = pageMapper.selectByExample(child);

        childPages.forEach(c -> {
            parentPages.forEach(p -> {
                if (p.getId().equals(c.getParentId())){
                    p.getChilds().add(c);
                }
            });
        });
        return parentPages;
    }

    @Override
    public List<PermissionsType> getPermissions(HttpServletRequest request, HttpServletResponse response) {
        PermissionsTypeExample permissionsTypeExample = new PermissionsTypeExample();
        permissionsTypeExample.createCriteria().andIsSelectEqualTo(1);
        List<PermissionsType> permissionsTypes = permissionsTypeMapper.selectByExample(permissionsTypeExample);

        List<Permissions> permissions = permissionsMapper.selectByExample(new PermissionsExample());
        permissionsTypes.forEach(permissionsType -> {
            permissionsType.setId(-permissionsType.getId());
        });
        permissions.forEach(p -> {
            permissionsTypes.forEach(permissionsType -> {
                if (permissionsType.getId().equals(-p.getPermType())){
                    permissionsType.getChilds().add(p);
                }
            });
        });
        return permissionsTypes;
    }

    @Override
    public List<Map<String,Object>> myPages(Integer roleId) throws Exception {
//        return getPagesByRoleId(roleId);
        return getMyPages(roleId);
    }

    @Override
    public List<Integer> myPermissions(HttpServletRequest request, HttpServletResponse response) {
        User user = (User)request.getSession().getAttribute("user");
        return getPermissionIdsByRoleId(user.getRoleId());
    }

    @Override
    public List<Page> getPagesByUser(Integer userId, HttpServletResponse response) {
        User user = userMapper.selectByPrimaryKey(userId);
        return getPagesByRoleId(user.getRoleId());
    }

    @Override
    public List<Integer> getPermissionsByUser(Integer userId, HttpServletResponse response) {
        User user = userMapper.selectByPrimaryKey(userId);
        return getPermissionIdsByRoleId(user.getRoleId());
    }

    @Override
    public List<Integer> getPagesByRole(Integer roleId, HttpServletResponse response) {
        return getPageIdsByRoleId(roleId);
    }

    @Override
    public List<Integer> getPermissionsByRole(Integer roleId, HttpServletResponse response) {
        return getPermissionIdsByRoleId(roleId);
    }

    /**根据角色id查询页面id集合*/
    private List<Integer> getPageIdsByRoleId(Integer roleId) {
        RolePageExample rolePageExample = new RolePageExample();
        rolePageExample.createCriteria().andRoleIdEqualTo(roleId);
        List<RolePage> rolePages = rolePageMapper.selectByExample(rolePageExample);

        List<Integer> pageIds = new ArrayList<>();
        rolePages.forEach(rolePage -> pageIds.add(rolePage.getPageId()));

        return pageIds;
    }
    /**根据角色id查询页面集合(分级分层包装)*/
    private List<Page> getPagesByRoleId(Integer roleId) {
        RolePageExample rolePageExample = new RolePageExample();
        rolePageExample.createCriteria().andRoleIdEqualTo(roleId);
        List<RolePage> rolePages = rolePageMapper.selectByExample(rolePageExample);

        List<Integer> pageIds = new ArrayList<>();
        rolePages.forEach(rolePage -> pageIds.add(rolePage.getPageId()));

        PageExample parent = new PageExample();
        parent.createCriteria().andPageLevelEqualTo(0).andStateEqualTo(1);
        if(pageIds.size()>0) parent.getCriteria().andIdIn(pageIds);
        List<Page> parentPages = pageMapper.selectByExample(parent);

        PageExample child = new PageExample();
        child.createCriteria().andPageLevelEqualTo(1).andStateEqualTo(1);
        if(pageIds.size()>0) parent.getCriteria().andIdIn(pageIds);
        List<Page> childPages = pageMapper.selectByExample(child);

        childPages.forEach(c -> {
            parentPages.forEach(p -> {
                if (p.getId().equals(c.getParentId())){
                    p.getChilds().add(c);
                }
            });
        });
        return parentPages;
    }
    /**根据角色id查询权限id集合*/
    private List<Integer> getPermissionIdsByRoleId(Integer roleId) {
        RolePermissionsExample rolePermissionsExample = new RolePermissionsExample();
        rolePermissionsExample.createCriteria().andRoleIdEqualTo(roleId);
        List<RolePermissions> rolePermissions = rolePermissionsMapper.selectByExample(rolePermissionsExample);
        List<Integer> permissionIds = new ArrayList<>();
        rolePermissions.forEach(rolePermission -> permissionIds.add(rolePermission.getPermissionsId()));
        redisUtil.getStringRedisTemplate().opsForHash().put("rolePermissions","r-"+roleId,JsonUtil.list2str(permissionIds));
        return permissionIds;
    }
    /**根据角色id查询权限集合*/
    private List<Permissions> getPermissionsByRoleId(Integer roleId) {
        RolePermissionsExample rolePermissionsExample = new RolePermissionsExample();
        rolePermissionsExample.createCriteria().andRoleIdEqualTo(roleId);
        List<RolePermissions> rolePermissions = rolePermissionsMapper.selectByExample(rolePermissionsExample);
        List<Integer> permissionIds = new ArrayList<>();
        rolePermissions.forEach(rolePermission -> permissionIds.add(rolePermission.getPermissionsId()));

        PermissionsExample permissionsExample = new PermissionsExample();
        permissionsExample.createCriteria().andIdIn(permissionIds);
        return permissionsMapper.selectByExample(permissionsExample);
    }
    /**根据角色id查询页面集合*/
    private List<Map<String,Object>> getMyPages(Integer roleId) throws Exception {
        RolePageExample rolePageExample = new RolePageExample();
        rolePageExample.createCriteria().andRoleIdEqualTo(roleId);
        List<RolePage> rolePages = rolePageMapper.selectByExample(rolePageExample);

        List<Integer> pageIds = new ArrayList<>();
        rolePages.forEach(rolePage -> pageIds.add(rolePage.getPageId()));

        PageExample parent = new PageExample();
        parent.createCriteria().andStateEqualTo(1).andRemarksNotEqualTo("菜单夹");
        if(pageIds.size()<1) pageIds.add(0);
        parent.getCriteria().andIdIn(pageIds);
        List<Map<String,Object>> pageMaps = new ArrayList<>();
        List<Page> pages = pageMapper.selectByExample(parent);
        for (Page p:pages){
            Map<String, Object> map = ObjectOrMapUtil.objectToMap(p);
            map.remove("childs");
            pageMaps.add(map);
        }
        return pageMaps;
    }


}
