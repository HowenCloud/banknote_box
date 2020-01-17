
//package com.ixilink.banknote_box.login.controller;
//
//import com.ixilink.banknote_box.common.dao.UserMapper;
//import com.ixilink.banknote_box.common.pojo.User;
//import com.ixilink.banknote_box.common.pojo.UserExample;
//import com.ixilink.banknote_box.common.util.JsonUtil;
//import com.ixilink.banknote_box.common.util.RedisUtil;
//import io.swagger.annotations.Api;
//import lombok.extern.log4j.Log4j2;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.web.bind.annotation.*;
//
//import javax.annotation.Resource;
//import java.util.List;
//
///**
// * description: 测试
// * author: 张俊
// * date: 2019-11-06 17:23
// */
//@Log4j2
//@RestController
//@RequestMapping("/")
//@Api(value = "/", description = "测试模块")
//public class TestController {
//
//    /** 端口号 */
//    @Value("${server.port}")
//    private int port;
//    @Resource
//    private UserMapper userMapper;
//    @Resource
//    private RedisUtil redisUtil;
//
//    @RequestMapping("/")
//    public String hello(){
//        //存字符串
//        /*redisUtil.getStringRedisTemplate().opsForValue().set("user2",JsonUtil.obj2str(new User(1,"张三","","")));
//        String user2 = redisUtil.getStringRedisTemplate().opsForValue().get("user2");
//        User user3 = (User) JsonUtil.str2Obj(user2, User.class);
//        System.out.println(user3);*/
//        //存对象
//        /*redisUtil.set("user1",new User(1,"张三","",""));
//        User  user = (User) redisUtil.get("user1");
//        System.out.println(user);*/
//        return "hello login";
//    }
//
//    /**
//     * 查
//     * @return
//     */
//    @GetMapping(value = "/list")
//    public List<User> getUserList(){
//        return userMapper.selectByExample(new UserExample());
//    }
//
//}

