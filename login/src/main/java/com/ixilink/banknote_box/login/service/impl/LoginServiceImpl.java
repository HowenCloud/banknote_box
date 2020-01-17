package com.ixilink.banknote_box.login.service.impl;

import com.ixilink.banknote_box.common.dao.RoleMapper;
import com.ixilink.banknote_box.common.dao.UserMapper;
import com.ixilink.banknote_box.common.exception.BusinessException;
import com.ixilink.banknote_box.common.jwt.JwtToken;
import com.ixilink.banknote_box.common.model.Code;
import com.ixilink.banknote_box.common.pojo.*;
import com.ixilink.banknote_box.common.util.*;
import com.ixilink.banknote_box.login.parameter_model.LoginModel;
import com.ixilink.banknote_box.login.service.LoginService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @description:
 * @author: 张俊
 * @date: 2019-11-11 16:27
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Resource
    private UserMapper userMapper;
    @Resource
    private RoleMapper roleMapper;
    @Resource
    private RedisUtil redisUtil;

    @Override
    public String login(LoginModel loginModel, HttpServletRequest request) {
        User user = getLoginUser(loginModel, request);
        int needLogin = 2;
        if (user.getLibraryId() != 0){
            Role role = roleMapper.selectByPrimaryKey(user.getRoleId());
            needLogin = role.getNeedLogin();
            String platformType = request.getHeader("platformType");
            if (ClientType.getType(request) != 1 || !"1".equals(platformType)){
                if (role.getNeedLogin() != 2 && !"整点组长".equals(role.getRoleName())) throw new BusinessException(Code.LONGIN_FAIL.getCode(),"不具备登录权限");
            }
        }
        request.getSession().setAttribute("needLogin",needLogin);
        return getToken(request,user);
    }

    @Override
    public String partnerLogin(LoginModel loginModel, HttpServletRequest request) {
        User one = ValidatePermissionsUtil.getUser(request,redisUtil);
        User two = getLoginUser(loginModel, request);
        if (!two.getRoleId().equals(one.getRoleId())){
            throw new BusinessException(Code.LONGIN_FAIL.getCode(),"请登录相同岗位账号");
        }else if (one.getId().equals(two.getId())){
            throw new BusinessException(Code.LONGIN_FAIL.getCode(),"请不要重复登录同一账号");
        }
        two.setPartnerId(one.getId());
        return getToken(request,two);
    }

    @Override
    public void logout(HttpServletRequest request) {
        try {
            User user = ValidatePermissionsUtil.getUser(request, redisUtil);
            String platformType = request.getHeader("platformType");
            //自己退出登录
            redisUtil.getStringRedisTemplate().opsForHash().delete("users", "u-" + user.getId());
            redisUtil.getStringRedisTemplate().opsForHash().delete("tokens",platformType+"-"+user.getId());
            //队友退出登录
            if (user.getPartnerId() != null){
                redisUtil.getStringRedisTemplate().opsForHash().delete("users", "u-" + user.getPartnerId());
                redisUtil.getStringRedisTemplate().opsForHash().delete("tokens",platformType+"-"+user.getPartnerId());
            }
        }catch (Exception e){
            throw new BusinessException(Code.LONGIN_TIMEOUT.getCode(),"用户登录信息失效，请登录再试！");
        }
    }

    private User getLoginUser(LoginModel loginModel, HttpServletRequest request){
//        //验证码
//        Object checkCode = request.getSession().getAttribute(Helper.SESSION_LOGIN_FAILURE_COUNT);
        UserExample userExample = new UserExample();
        userExample.createCriteria().andLoginNameEqualTo(loginModel.getLoginName()).andStateNotEqualTo(2);

        List<User> users = userMapper.userInfo(userExample);

        User user = null;
        if (users.size() == 0) {
            throw new BusinessException(Code.NO_LOGIN_USER.getCode(),Code.NO_LOGIN_USER.getMessage());
        }
        else if (users.size() == 1){
            user = users.get(0);
        }
        else {
            List<User> collect = users.stream().filter(u -> u.getState().equals(1)).collect(Collectors.toList());
            if (collect.size() == 0){
                throw new BusinessException(Code.LOGIN_USER_STATE.getCode(),Code.LOGIN_USER_STATE.getMessage());
            }
            user = collect.get(0);
        }
        verificationUser(loginModel, user, request);
        return user;
    }

    private void verificationUser(LoginModel loginModel, User user, HttpServletRequest request){
        Object key = request.getSession().getAttribute("key");
        request.getSession().removeAttribute("key");
        if (ClientType.isFromMobile(request)){
            key = EncryptUtil.KEY;
        }
        else if (key == null || "".equals(key)){
            throw new BusinessException(Code.MESSAGE_ERROR.getCode(),"非法登录");
        }

        String password = null;
        try {
            password = EncryptUtil.aesDecrypt(loginModel.getPassword(), key.toString());
        } catch (Exception e) {
            throw new BusinessException(Code.LONGIN_FAIL.getCode(),Code.LONGIN_FAIL.getMessage());
        }

        if (!user.getPassword().equals(PasswordUtil.encodeMD5(password))){
            throw new BusinessException(Code.LONGIN_FAIL.getCode(),Code.LONGIN_FAIL.getMessage());
        }
        if (user.getState().equals(3)){
            throw new BusinessException(Code.LOGIN_USER_STATE.getCode(),Code.LOGIN_USER_STATE.getMessage());
        }
    }

    private String getToken(HttpServletRequest request, User user){
        String token = null;
        try {
            token = JwtToken.creatToken(user);
        } catch (Exception e) {
            throw new BusinessException(Code.NO_LOGIN_USER.getCode(),Code.NO_LOGIN_USER.getMessage());
        }
        redisUtil.getStringRedisTemplate().opsForHash().put("users","u-"+user.getId(),JsonUtil.obj2str(user));
        RolePermissionsExample rolePermissionsExample = new RolePermissionsExample();
        rolePermissionsExample.createCriteria().andRoleIdEqualTo(user.getRoleId());
        request.getSession().setAttribute("user",user);
        String platformType = request.getHeader("platformType");
        redisUtil.getStringRedisTemplate().opsForHash().put("tokens",platformType+"-"+user.getId(),token);
        return token;
    }
}
