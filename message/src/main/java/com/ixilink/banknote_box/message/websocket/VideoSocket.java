package com.ixilink.banknote_box.message.websocket;

import com.ixilink.banknote_box.common.jwt.PassToken;
import com.ixilink.banknote_box.message.common.FacePublicData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @description:
 * @author: 张皓峰
 * @date: 2019-12-12 15:07
 */
@Slf4j
@Component
@ServerEndpoint(value = "/videoSocket/{deviceID}")
@PassToken
public class VideoSocket {
    private static ConcurrentHashMap<String, VideoSocket> webSocketSet = new ConcurrentHashMap<>();

    private Session session;
    private String deviceID;

    public Session getSession() {
        return session;
    }

    /**
     * 连接建立成功调用的方法*/
    @OnOpen
    public synchronized void onOpen(@PathParam(value = "deviceID") String deviceID, Session session) {
        this.session = session;
        this.deviceID = deviceID;
        try {
            if (webSocketSet.get(deviceID) != null) {
                webSocketSet.get(deviceID).session.close();
                log.error("多人使用，已断开原有连接");
            }
        } catch (IOException e) {
            log.error("关闭原有连接异常");
        }
        webSocketSet.put(deviceID,this);
        FacePublicData.startStream(deviceID);
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public synchronized void onClose() {
        FacePublicData.stopStream(this.deviceID);
        FacePublicData.stopRecognitionFace(this.deviceID);
        webSocketSet.remove(this.deviceID);
    }

    /**
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        if ("exit".equals(message)) {
            try {
                session.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        log.info("来自客户端的消息:" + message);
    }

    /**
     *
     * @param session 用户会话
     * @param error 错误信息
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("发生错误");
    }


    private synchronized void sendMessage(String message)  {
        try {
            this.session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            log.error("发送数据异常");
        }
    }
    private synchronized void sendMessage(ByteBuffer message)  {
        try {
            this.session.getBasicRemote().sendBinary(message);
        } catch (IOException e) {
            log.error("发送数据异常");
        }
    }


    /**
     * 发送信息给客户端
     * @param message 数据
     */
    public synchronized void sendMessage(String deviceID, String message, ByteBuffer data)  {
        if (webSocketSet.get(deviceID) != null) {
            if (message == null){
                webSocketSet.get(deviceID).sendMessage(data);
            }else {
                webSocketSet.get(deviceID).sendMessage(message);
            }
        }else {
            if (webSocketSet.size()==0){
//                log.error("无人使用设备，即将自动关闭...");
//                FacePublicData.stopStream(deviceID);
            }else {
                log.error("该客户端不在线");
            }
        }
    }


}
