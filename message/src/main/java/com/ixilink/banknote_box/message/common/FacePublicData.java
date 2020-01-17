package com.ixilink.banknote_box.message.common;

import com.ha.facecamera.configserver.*;
import com.ha.facecamera.configserver.pojo.Face;
import com.ixilink.banknote_box.common.exception.BusinessException;
import com.ixilink.banknote_box.common.model.Code;
import com.ixilink.banknote_box.common.util.FileUtil;
import com.ixilink.banknote_box.message.callback.*;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: 张皓峰
 * @date: 2019-12-11 16:44
 */
@Slf4j
public class FacePublicData {
    /**配置服务器*/
    private static ConfigServer configServer;
    /**配置服务器启动状态*/
    private static Boolean configServerState = false;
    /**数据服务器*/
    private static DataServer dataServer;
    /**数据服务器启动状态*/
    private static Boolean dataServerState = false;
    /**识别到的用户ID*/
    public static Map<String,List<Integer>> userId = new HashMap<>();

    public static ConfigServer getConfigServer() {
        return configServer;
    }

    public static DataServer getDataServer() {
        return dataServer;
    }

    public static Boolean getConfigServerState() {
        return configServerState;
    }

    public static Boolean getDataServerState() {
        return dataServerState;
    }

    private FacePublicData(){}

    /**
     * 启动配置服务器
     */
    public static void startConfigServer(){
        if (!configServerState) {
            synchronized (FacePublicData.class) {
                if (!configServerState) {
                    if (configServer == null) configServer = new ConfigServer();
                    boolean start = configServer.start(10001, new ConfigServerConfig());
                    log.info("配置服务器启动状态："+(start?"成功":"失败"));
                    if (start) {
                        //注册上线通知回调
                        configServer.onCameraConnected(CameraConnected.getInstance());
                        //注册下线通知回调
                        configServer.onCameraDisconnected(CameraDisconnected.getInstance());
                        //注册鉴权消息回调
                        configServer.onAuthing(CameraAuthing.getInstance());
                        //注册视频流回调
                        configServer.onStreamDataReceived(StreamDataReceived.getInstance());
                    }
                    configServerState = start;
                }
            }
        }
    }
    /**
     * 启动数据服务器
     */
    public static void startDataServer(){
        if (!dataServerState) {
            synchronized (FacePublicData.class) {
                if (!dataServerState) {
                    if (dataServer == null) dataServer = new DataServer();
                    DataServerConfig dsc = new DataServerConfig();
                    dsc.connectStateInvokeCondition = ConnectStateInvokeCondition.DeviceNoKnown;
                    boolean start = dataServer.start(10002, dsc);
                    log.info("数据服务器启动状态："+(start?"成功":"失败"));
                    if (start) {
                        //注册上线通知回调
                        dataServer.onCameraConnected(CameraConnected.getInstance());
                        //注册下线通知回调
                        dataServer.onCameraDisconnected(CameraDisconnected.getInstance());
                        //注册鉴权消息回调
                        dataServer.onAuthing(CameraAuthing.getInstance());
                        //注册视频识别处理回调
                        dataServer.onCaptureCompareDataReceived(CaptureCompareDataReceived.getInstance());
                    }
                    dataServerState = start;
                }
            }
        }
    }
    /**
     * 关闭配置服务器
     */
    public static void stopConfigServer(){
        if (configServerState) {
            synchronized (FacePublicData.class) {
                if (configServerState && configServer!=null) {
                    configServer.stop();
                    configServerState = false;
                }
            }
        }
    }
    /**
     * 关闭数据服务器
     */
    public static void stopDataServer(){
        if (!dataServerState) {
            synchronized (FacePublicData.class) {
                if (!dataServerState && dataServer!=null) {
                    dataServer.stop();
                    dataServerState = false;
                }
            }
        }
    }
    /**
     * 获取最近错误码
     * @return 错误码
     */
    public static int getLastErrorCode(){
        return configServer.getLastErrorCode();
    }
    /**
     * 获取最近错误信息
     * @return 错误信息
     */
    public static String getLastErrorMsg(){
        return configServer.getLastErrorMsg();
    }

    /**
     * 验证设备连接状态
     * @param deviceID 设备编号
     */
    private static void ValidateOnlineState(String deviceID){
        while (!FacePublicData.configServer.getCameraOnlineState(deviceID)){
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 添加人脸
     * @param deviceID 设备编号
     * @param id 用户ID
     * @param name 用户姓名
     * @param path 人脸图片路径
     * @return 成功状态
     */
    public static boolean addFace(String deviceID, String id, String name, String path){
        ValidateOnlineState(deviceID);
        Face f = new Face();
        f.setId(id);
        f.setName(name);
        f.setRole(1);
        f.setJpgFilePath(new String[]{path});
        return FacePublicData.configServer.addFace(deviceID, f, 5000);
    }

    /**
     * 开始识别人脸
     */
    public static void startRecognitionFace(String deviceID){
        if (userId.containsKey(deviceID)){
            throw new BusinessException(Code.HOLD_UP.getCode(),Code.HOLD_UP.getMessage());
        }
        userId.put(deviceID,new ArrayList<>());
    }
    /**
     * 关闭识别人脸
     */
    public static void stopRecognitionFace(String deviceID){
        userId.remove(deviceID);
        //删除临时图片
        FileUtil.del(new File(FileUtil.getPath(),"\\temp\\face\\"+deviceID));
    }

    /**
     * 开始订阅视频流数据
     */
    public static void startStream(String deviceID){
        ValidateOnlineState(deviceID);
        if (!configServer.getIsStreamStart(deviceID)){
            configServer.startStream(deviceID);
        }
    }
    /**
     * 取消订阅视频流数据
     */
    public static void stopStream(String deviceID){
        ValidateOnlineState(deviceID);
        configServer.stopStream(deviceID);
    }


}
