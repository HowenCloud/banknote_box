package com.ixilink.banknote_box.common.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.ixilink.banknote_box.common.exception.BusinessException;
import com.ixilink.banknote_box.common.model.Code;
import com.ixilink.banknote_box.common.pojo.User;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @description: 验证权限工具类
 * @author: 张俊
 * @date: 2019-11-13 19:37
 */
public class ValidatePermissionsUtil {

    public static void validatePermission(HttpServletRequest request, RedisUtil redisUtil, Integer ... permissions){
        User me = getUser(request, redisUtil);
        String rolePermissionsStr = (String) redisUtil.getStringRedisTemplate().opsForHash().get("rolePermissions","r-"+me.getRoleId());
        List<Integer> myPermissions = JsonUtil.jackson2other(rolePermissionsStr, List.class, Integer.class);
        for (Integer p:permissions){
            if (!myPermissions.contains(p)){
                throw new BusinessException(Code.NO_SECURITY.getCode(),"你没有权限做此操作！");
            }
        }
    }


    public static int getUserId(HttpServletRequest request){
        String token = request.getHeader("token");
        String userId = null;
        try {
            userId = JWT.decode(token).getAudience().get(0);
        } catch (JWTDecodeException j) {
            throw new BusinessException(Code.TOKEN_FIND_ERROR.getCode(),"token失效，请重新登录");
        }
        return Integer.valueOf(userId);
    }


    public static User getUser(HttpServletRequest request, RedisUtil redisUtil){
        int userId = getUserId(request);
        String userStr = (String) redisUtil.getStringRedisTemplate().opsForHash().get("users","u-"+userId);
        return (User) JsonUtil.str2Obj(userStr, User.class);
    }


    public static List<Integer> getPermission(Integer roleId, RedisUtil redisUtil){
        String rolePermissionsStr = (String) redisUtil.getStringRedisTemplate().opsForHash().get("rolePermissions","r-"+roleId);
        return JsonUtil.jackson2other(rolePermissionsStr, List.class, Integer.class);
    }
}
