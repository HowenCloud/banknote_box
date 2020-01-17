package com.ixilink.banknote_box.message.callback;

import com.ha.facecamera.configserver.events.CameraDisconnectedEventHandler;

/**
 * @description:
 * @author: 张皓峰
 * @date: 2019-12-11 15:11
 */
public class CameraDisconnected implements CameraDisconnectedEventHandler {

    private static CameraDisconnected cameraDisconnected;
    private CameraDisconnected(){}
    public static CameraDisconnected getInstance(){
        if (cameraDisconnected == null ){
            synchronized (CameraDisconnected.class){
                if (cameraDisconnected == null){
                    cameraDisconnected = new CameraDisconnected();
                }
            }
        }
        return cameraDisconnected;
    }

    @Override
    public void onCameraDisconnected(String s) {
//        System.out.println("下线："+s);
    }
}
