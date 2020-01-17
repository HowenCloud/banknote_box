package com.ixilink.banknote_box.message.callback;

import com.ha.facecamera.configserver.events.CameraAuthingEventHandler;

import java.io.UnsupportedEncodingException;
import java.util.Date;

/**
 * @description: 鉴权消息
 * @author: 张皓峰
 * @date: 2019-12-11 18:14
 */
public class CameraAuthing implements CameraAuthingEventHandler {

    private static CameraAuthing cameraAuthing;
    private CameraAuthing(){}
    public static CameraAuthing getInstance(){
        if (cameraAuthing == null ){
            synchronized (CameraAuthing.class){
                if (cameraAuthing == null){
                    cameraAuthing = new CameraAuthing();
                }
            }
        }
        return cameraAuthing;
    }

    @Override
    public boolean onCameraAuthing(Date time, String username, byte[] password) {
//        try {
//            System.out.println(String.format("configServer 收到鉴权消息username=>%s password=>%s", username, new String(password,"GBK")));
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
        return true;
    }
}
