package com.ixilink.banknote_box.service.controller;

import com.alibaba.fastjson.JSON;
import com.ixilink.banknote_box.common.dao.UserMapper;
import com.ixilink.banknote_box.common.model.Code;
import com.ixilink.banknote_box.common.model.Result;
import com.ixilink.banknote_box.common.pojo.User;
import com.ixilink.banknote_box.common.pojo.UserExample;
import com.ixilink.banknote_box.common.util.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

//import com.ixilink.microsvc.common.repository.UserRepository;

/**
 * description: 测试
 * author: 张俊
 * date: 2019-11-06 17:23
 */
@Log4j2
@RestController
@RequestMapping("/")
@Api(value = "/", description = "测试模块")
public class TestController {

    /** 端口号 */
    @Value("${server.port}")
    private int port;
    @Resource
    private UserMapper userMapper;

    @RequestMapping("/")
    public String hello(){
        return "hello service";
    }

    /**
     * 查
     * @return
     */
    @ApiIgnore
    @GetMapping(value = "/list")
    @ApiOperation(value = "查询用户集合",notes = "",httpMethod = "GET")
    public List<User> getUserList(){
        return userMapper.userInfo(new UserExample());
    }


    @ApiIgnore
    @RequestMapping("/base64test")
    @ResponseBody
    public String base64test(HttpServletRequest request){
        String address=request.getParameter("amp;address");
        String name=request.getParameter("name");
        log.debug("base64传输前");
        log.debug("name:"+name+"   address:"+address);
        log.debug("base64取值后");
        byte[] result1 = Base64.decodeBase64(name);
        byte[] result2 = Base64.decodeBase64(address);
        String str1=new String(result1);
        String str2=new String(result2);
        log.debug("name:"+str1+"   address:"+str2);
        return "name:"+str1+"   address:"+str2;
    }

    @ApiIgnore
    @GetMapping("/images")
    @ResponseBody
    public void find(@RequestParam(value="id") String id, HttpServletRequest request, HttpServletResponse response){
        HttpSession session = request.getSession();
        Object key = session.getAttribute(Helper.SESSION_LOGIN_TOKEN);//原始令牌
        InputStream fis = null;
        OutputStream os = null;
        URL url = null;
        HttpURLConnection httpUrl = null;
        try {
//            log.debug(id+">>>>>>>>"+ key.toString());
            String img = EncryptUtil.aesDecrypt(id, key.toString());
            String path = "http://localhost:"+port+"/images/"+img;
            url = new URL(path);//远程图片地址
            httpUrl = (HttpURLConnection) url.openConnection();
            httpUrl.setRequestProperty(Helper.SESSION_LOGIN_TOKEN, key.toString());
            DisableSSLCertificateCheckUtil.disableChecks();
            httpUrl.connect();
            //fis = new FileInputStream("d:/img/test.jpg");//本地图片地址
            fis = httpUrl.getInputStream();
            os = response.getOutputStream();
            int count = 0;
            byte[] buffer = new byte[1024 * 8];
            while ((count = fis.read(buffer)) != -1) {
                os.write(buffer, 0, count);
                os.flush();
            }
        } catch (Exception e) {
            if (e.getMessage().contains("你的主机中的软件中止了一个已建立的连接") || e.getMessage().contains("apache.catalina.connector.ClientAbortException") || e.getMessage().contains("远程主机强迫关闭了一个现有的连接")){
                log.debug("重复请求");
            }else if (e.getMessage().contains("No name matching localhost found")){
                log.debug("https证书无效");
            }else if (e.getMessage().contains("Such issues can arise if a bad key is used during decryption")){
                log.debug("token无效，参数解析失败");
            }
            else {
                e.printStackTrace();
            }
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
                if (os != null) {
                    os.close();
                }
                if (httpUrl != null) {
                    httpUrl.disconnect();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
