package com.ixilink.banknote_box.common.util;

import lombok.extern.slf4j.Slf4j;

import javax.imageio.stream.FileImageInputStream;
import javax.imageio.stream.FileImageOutputStream;
import java.io.*;
import java.util.UUID;

/**
 * @description:
 * @author: 张皓峰
 * @date: 2019-12-04 15:22
 */
@Slf4j
public class FileUtil {

    /**
     * 功能描述：
     *〈获取存放根路径〉
     * @author 张皓峰
     * @date 2019-12-04 15:36
     * @return java.lang.String 路径
     */
    public static String getPath(){
        return System.getProperty("user.dir");
    }
    /**
     * 功能描述：
     *〈生成UUID文件名〉
     * @author 张皓峰
     * @date 2019-12-04 15:36
     * @return java.lang.String 文件名
     */
    public static String createName(){
        return UUID.randomUUID().toString();
    }

    /**
     * 功能描述：
     *〈获取文件后缀〉
     * @author 张皓峰
     * @date 2019-12-04 15:35
     * @param fileName 文件名
     * @return java.lang.String 文件后缀
     */
    public static String getSuffix(String fileName){
        if (fileName == null) return "";
        return fileName.substring(fileName.lastIndexOf("."), fileName.length());
    }

    /**
     * 删除文件
     * @param file 文件
     */
    public static void del(File file){
        if (!file.exists()) {
            return;
        }
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File f : (files != null ? files : new File[0])) {
                del(f);
            }
        }
        if (!file.delete()){
            log.error("删除文件失败");
        }
    }

    /**
     * 将文件转换成Byte数组
     * @param pathStr 图片路径
     * @return byte数组
     */
    public static byte[] getBytesByFile(String pathStr) {
        File file = new File(pathStr);
        try {
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1024);
            byte[] b = new byte[1024];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            byte[] data = bos.toByteArray();
            bos.close();
            return data;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 图片到byte数组
     * @param path 图片路径
     * @return byte数组
     */
    public byte[] image2byte(String path){
        byte[] data = null;
        FileImageInputStream input = null;
        try {
            input = new FileImageInputStream(new File(path));
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            int numBytesRead = 0;
            while ((numBytesRead = input.read(buf)) != -1) {
                output.write(buf, 0, numBytesRead);
            }
            data = output.toByteArray();
            output.close();
            input.close();
        } catch (IOException ex1) {
            ex1.printStackTrace();
        }
        return data;
    }

    /**
     * byte数组转存到图片
     * @param data byte数据
     * @param path 图片路径
     */
    public static void byte2image(byte[] data,String path){
        if(data.length<3||path.equals("")) return;
        try{
            FileImageOutputStream imageOutput = new FileImageOutputStream(new File(path));
            imageOutput.write(data, 0, data.length);
            imageOutput.close();
            System.out.println("Make Picture success,Please find image in " + path);
        } catch(Exception ex) {
            System.out.println("Exception: " + ex);
            ex.printStackTrace();
        }
    }

}
