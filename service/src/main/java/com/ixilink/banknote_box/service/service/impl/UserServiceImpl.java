package com.ixilink.banknote_box.service.service.impl;

import com.github.pagehelper.PageHelper;
import com.ixilink.banknote_box.common.dao.RoleMapper;
import com.ixilink.banknote_box.common.dao.UserMapper;
import com.ixilink.banknote_box.common.exception.BusinessException;
import com.ixilink.banknote_box.common.model.Code;
import com.ixilink.banknote_box.common.model.UserSimple;
import com.ixilink.banknote_box.common.pojo.Role;
import com.ixilink.banknote_box.common.pojo.RoleExample;
import com.ixilink.banknote_box.common.pojo.User;
import com.ixilink.banknote_box.common.pojo.UserExample;
import com.ixilink.banknote_box.common.util.*;
import com.ixilink.banknote_box.service.parameter_model.user.AddUserModel;
import com.ixilink.banknote_box.service.parameter_model.user.FindUserModel;
import com.ixilink.banknote_box.service.service.UserService;
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
 * @date: 2019-11-08 22:35
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;
    @Resource
    private RoleMapper roleMapper;
    @Resource
    private RedisUtil redisUtil;

    @Override
    @Transactional
    public void addUser(AddUserModel param, HttpServletRequest request, HttpServletResponse response) {
        ValidatePermissionsUtil.validatePermission(request,redisUtil,1);
        UserExample userExample = new UserExample();
        userExample.createCriteria().andLoginNameEqualTo(param.getLoginName()).andStateNotEqualTo(2);
        long count = userMapper.countByExample(userExample);
        if (count > 0){
            throw new BusinessException(Code.MESSAGE_ERROR.getCode(),"该用户名已存在");
        }
        int userId = ValidatePermissionsUtil.getUserId(request);
        if (!redisUtil.getStringRedisTemplate().hasKey("key-u-"+userId)){
            throw new BusinessException(Code.MESSAGE_ERROR.getCode(),"数据异常，请刷新页面再试");
        }
        String key = redisUtil.getStringRedisTemplate().opsForValue().get("key-u-"+userId);
        String password = null;
        try {
            password = EncryptUtil.aesDecrypt(param.getPassword().trim(), key.trim());
        } catch (Exception e) {
            throw new BusinessException(Code.VERIFY_ERROR.getCode(),Code.VERIFY_ERROR.getMessage());
        }
        User me = ValidatePermissionsUtil.getUser(request,redisUtil);
        Integer libraryId = me.getLibraryId()==0?param.getLibraryId():me.getLibraryId();
        User user = new User(null,param.getLoginName(),PasswordUtil.encodeMD5(password),param.getName(),param.getSex(),param.getDepartmentId(),param.getRoleId(),null,new Date().getTime(),libraryId);
        int i = userMapper.insertSelective(user);
        if (i <= 0){
            throw new BusinessException(Code.MESSAGE_ERROR.getCode(),"添加失败");
        }
    }

    @Override
    @Transactional
    public void removeUser(Integer id, HttpServletRequest request, HttpServletResponse response) {
        ValidatePermissionsUtil.validatePermission(request,redisUtil,3);
        User user = new User();
        user.setId(id);
        user.setState(2);
        int i = userMapper.updateByPrimaryKeySelective(user);
        if (i <= 0){
            throw new BusinessException(Code.MESSAGE_ERROR.getCode(),"删除失败，请刷新页面再试！");
        }
    }

    @Override
    public List<User> getUserAll() {
        return userMapper.userInfo(new UserExample());
    }

    @Override
    public List<User> getUsers(FindUserModel param, HttpServletRequest request, HttpServletResponse response) {
        User me = ValidatePermissionsUtil.getUser(request,redisUtil);
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andStateEqualTo(1);
        if (param.getName() != null && !"".equals(param.getName())){
            criteria.andNameLike("%"+param.getName()+"%");
        }
        if (param.getDepartmentId() != null){
            criteria.andDepartmentIdEqualTo(param.getDepartmentId());
        }
        //传中心库id查询
//        criteria.andLibraryIdEqualTo(me.getLibraryId()==0?param.getLibraryId():me.getLibraryId());
        //不传中心库查询
        if (me.getLibraryId()==0){
            RoleExample roleExample = new RoleExample();
            roleExample.createCriteria().andModifiableEqualTo(1).andDeletingEqualTo(2).andNeedLoginEqualTo(2);
            List<Role> roles = roleMapper.selectByExample(roleExample);
            List<Integer> roleIds = new ArrayList<>();
            roleIds.add(0);
            roles.forEach(role -> roleIds.add(role.getId()));
            criteria.andRoleIdIn(roleIds);
        }else {
            criteria.andLibraryIdEqualTo(me.getLibraryId());
        }
        PageHelper.startPage(param.getPageNum(), param.getPageSize());
        return userMapper.userInfo(userExample);
    }

    @Override
    @Transactional
    public void modifyUser(AddUserModel param, HttpServletRequest request, HttpServletResponse response) {
        ValidatePermissionsUtil.validatePermission(request,redisUtil,2);
        User user = new User(param.getId(),param.getLoginName(),null,param.getName(),param.getSex(),param.getDepartmentId(),param.getRoleId(),null,null,null);
        int i = userMapper.updateByPrimaryKeySelective(user);
        if (i < 0){
            throw new BusinessException(Code.MESSAGE_ERROR.getCode(),"修改失败，请刷新页面再试！");
        }
        if (redisUtil.getStringRedisTemplate().opsForHash().hasKey("users","u-"+param.getId())){
            String userStr = (String) redisUtil.getStringRedisTemplate().opsForHash().get("users","u-"+param.getId());
            User user1 = (User) JsonUtil.str2Obj(userStr, User.class);
            user1.setLoginName(param.getLoginName());
            user1.setName(param.getName());
            user1.setSex(param.getSex());
            user1.setDepartmentId(param.getDepartmentId());
            user1.setRoleId(param.getRoleId());
            redisUtil.getStringRedisTemplate().opsForHash().put("users","u-"+param.getId(),JsonUtil.obj2str(user1));
        }
    }

    @Override
    public List<Map<String, Object>> department(Integer libraryId, HttpServletRequest request) {
        User user = ValidatePermissionsUtil.getUser(request, redisUtil);
        if (user.getLibraryId()!=0){
            libraryId = user.getLibraryId();
        }
        return userMapper.departments(libraryId);
    }

    @Override
    public User infoById(Integer id, HttpServletRequest request) {
        return userMapper.userInfoById(id);
    }

    @Override
    public List<Map<String, Object>> role(Integer libraryId, HttpServletRequest request) {
        User user = ValidatePermissionsUtil.getUser(request, redisUtil);
        if (user.getLibraryId()!=0){
            libraryId = user.getLibraryId();
        }
        Integer isSystemer = null;
        if (user.getRoleId()==1){
            isSystemer = 1;
        }
        return userMapper.roles(libraryId,isSystemer);
    }

    @Override
    public List<UserSimple> getDepositUser(HttpServletRequest request) {
        User me = ValidatePermissionsUtil.getUser(request,redisUtil);
        RoleExample roleExample = new RoleExample();
        roleExample.createCriteria().andLibraryIdEqualTo(me.getLibraryId()).andRoleNameEqualTo("加钞岗");
        List<Role> roles = roleMapper.selectByExample(roleExample);
        return userMapper.getSimpleUser(me.getLibraryId(), roles.get(0).getId());
    }

}
