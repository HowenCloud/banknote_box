package com.ixilink.banknote_box.common.util;

import java.io.IOException;
import java.net.InetAddress;

public class OnlineTest {

    /**
     * 判断IP地址是否在线
     */
    public static boolean ping(String ipAddress)  {
        int  timeOut =  1000 ;  //超时应该在3钞以上
        if (ipAddress ==null || ipAddress.equals("") || ipAddress.equals("127.0.0.1") || ipAddress.equals("localhost")) {
            return false;
        }
        try {
            return InetAddress.getByName(ipAddress).isReachable(timeOut);
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * 判断IP地址是否合法
     */
    public static boolean isIp(String text) {
        if (text != null && !text.isEmpty()) {
            // 定义正则表达式
            String regex = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\."+
            "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."+
            "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."+
            "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";
            // 判断ip地址是否与正则表达式匹配
            if (text.matches(regex)) {
                // 返回判断信息
                return true;
            } else {
                // 返回判断信息
                return false;
            }
        }
        return false;
    }

}