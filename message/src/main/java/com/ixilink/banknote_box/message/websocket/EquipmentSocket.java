package com.ixilink.banknote_box.message.websocket;

import com.ixilink.banknote_box.common.jwt.PassToken;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @description:
 * @author: 张俊
 * @date: 2019-05-16 17:28
 */
@Slf4j
@Component
//访问服务端的url地址
@ServerEndpoint(value = "/equipmentSocket")
@PassToken
public class EquipmentSocket {
    private static CopyOnWriteArraySet<EquipmentSocket> webSocketSet = new CopyOnWriteArraySet<>();

    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;
    /**
     * 连接建立成功调用的方法*/
    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        webSocketSet.add( this);
        try {
            sendMessage("连接成功");
        } catch (IOException e) {
            log.error("websocket IO异常");
        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        webSocketSet.remove(this);
    }

    /**
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) {
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
        error.printStackTrace();
    }


    private synchronized void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }


    /**
     * 发送信息给客户端
     * @param status 状态
     * @throws IOException
     */
    public void sendMessage(Object status) throws IOException {
        for (EquipmentSocket equipmentSocket : webSocketSet) {
            equipmentSocket.sendMessage(status.toString());
        }
    }


}
