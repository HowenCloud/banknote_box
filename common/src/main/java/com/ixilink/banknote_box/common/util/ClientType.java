package com.ixilink.banknote_box.common.util;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @description:
 * @author: 张皓峰
 * @date: 2019-12-16 9:30
 */
public class ClientType {

    /**
     * 获取客户端平台类型
     * @param request 请求头
     * @return
     */
    public static int getType(HttpServletRequest request){
        String userAgent = request.getHeader("user-agent").toLowerCase();
        int type = 0;
        if(userAgent.contains("micromessenger")){
            //微信
            type = 4;
        }else if(userAgent.contains("android")){
            //安卓
            type = 2;
        }else if(userAgent.contains("iphone") || userAgent.contains("ipad") || userAgent.contains("ipod")){
            //苹果
            type = 3;
        }else{
            //电脑
            type = 1;
        }
        return type;
    }

    public static String getTypeName(HttpServletRequest request){
        String userAgent = request.getHeader("user-agent").toLowerCase();
        String type = "";
        if(userAgent.contains("micromessenger")){
            type = "微信";
        }else if(userAgent.contains("android")){
            type = "安卓";
        }else if(userAgent.contains("iphone") || userAgent.contains("ipad") || userAgent.contains("ipod")){
            type = "苹果";
        }else{
            type = "电脑";
        }
        return type;
    }

    /**
     * 校验是否移动端
     * @param request
     * @return
     */
    public static boolean isFromMobile(HttpServletRequest request) {
        //1. 获得请求UA
        String userAgent = request.getHeader("USER-AGENT").toLowerCase();

        //2.声明手机和平板的UA的正则表达式
        // \b 是单词边界(连着的两个(字母字符 与 非字母字符) 之间的逻辑上的间隔),
        // 字符串在编译时会被转码一次,所以是 "\\b"
        // \B 是单词内部逻辑间隔(连着的两个字母字符之间的逻辑上的间隔)
        String phoneReg = "\\b(ip(hone|od)|android|opera m(ob|in)i" + "|windows (phone|ce)|blackberry"
                + "|s(ymbian|eries60|amsung)|p(laybook|alm|rofile/midp" + "|laystation portable)|nokia|fennec|htc[-_]"
                + "|mobile|up.browser|[1-4][0-9]{2}x[1-4][0-9]{2})\\b";
        String tableReg = "\\b(ipad|tablet|(Nexus 7)|up.browser" + "|[1-4][0-9]{2}x[1-4][0-9]{2})\\b";

        // 3.移动设备正则匹配：手机端、平板
        Pattern phonePat = Pattern.compile(phoneReg, Pattern.CASE_INSENSITIVE);
        Pattern tablePat = Pattern.compile(tableReg, Pattern.CASE_INSENSITIVE);
        if (null == userAgent) {
            userAgent = "";
        }
        // 4.匹配
        Matcher matcherPhone = phonePat.matcher(userAgent);
        Matcher matcherTable = tablePat.matcher(userAgent);
        if (matcherPhone.find() || matcherTable.find()) {
            return true; //来自手机或者平板
        } else {
            return false; //来自PC
        }
    }

}
