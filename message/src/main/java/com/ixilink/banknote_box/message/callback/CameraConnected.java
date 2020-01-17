package com.ixilink.banknote_box.message.callback;

import com.ha.facecamera.configserver.events.CameraConnectedEventHandler;

/**
 * @description:
 * @author: 张皓峰
 * @date: 2019-12-11 15:10
 */
public class CameraConnected implements CameraConnectedEventHandler {

    private static CameraConnected cameraConnected;
    private CameraConnected(){}
    public static CameraConnected getInstance(){
        if (cameraConnected == null ){
            synchronized (CameraConnected.class){
                if (cameraConnected == null){
                    cameraConnected = new CameraConnected();
                }
            }
        }
        return cameraConnected;
    }

    @Override
    public void onCameraConnected(String s) {
//        System.out.println("上线："+s);
    }
}
