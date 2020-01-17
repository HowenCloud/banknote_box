package com.ixilink.banknote_box.common.util;

import java.io.File;
import java.io.InputStreamReader;
import java.io.LineNumberReader;

/**
 * @description 控制台命令工具类
 * @author 张俊
 * @date 2019-12-02 15:48
 */
public class CmdUtil {

    /**
     * Windows执行本地命令行
     * 测试ok
     * @param cmd 命令
     * @param workpath  在此目录下执行
     * @return 打印
     */
    public static String executeLocalCmd(String cmd, File workpath) {
        try {
            String[] cmdA = { "cmd.exe", "/c", cmd };
            Process process = null;
            if(workpath==null){
                process = Runtime.getRuntime().exec(cmdA);
            }else{
                process = Runtime.getRuntime().exec(cmdA, null, workpath);
            }
//             LineNumberReader br = new LineNumberReader(new InputStreamReader(process.getInputStream()));
            LineNumberReader br = new LineNumberReader(new InputStreamReader(process.getInputStream(),"GBK"));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
