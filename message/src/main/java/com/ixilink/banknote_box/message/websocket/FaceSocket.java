package com.ixilink.banknote_box.message.websocket;

import com.ixilink.banknote_box.common.jwt.PassToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @description:
 * @author: 张皓峰
 * @date: 2019-12-12 15:07
 */
@Slf4j
@Component
@ServerEndpoint(value = "/faceSocket/{libraryId}/{type}")
@PassToken
public class FaceSocket {
    private static ConcurrentHashMap<String, FaceSocket> webSocketSet = new ConcurrentHashMap<>();

    private String key;
    private Session session;

    /**
     * 连接建立成功调用的方法*/
    @OnOpen
    public void onOpen(@PathParam(value = "libraryId") String libraryId, @PathParam("type") String type, Session session) {
        this.session = session;
        try {
            if (webSocketSet.containsKey(libraryId+"-"+type)) {
                webSocketSet.get(libraryId + "-" + type).session.close();
                log.info("关闭已有连接");
            }
        } catch (IOException e) {
            log.error("关闭连接异常");
        }
        this.key = libraryId + "-" + type;
        webSocketSet.put(libraryId+"-"+type,this);
//        sendMessage("连接成功");
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        webSocketSet.remove(this.key);
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
        error.printStackTrace();
    }


    private synchronized void sendMessage(String message)  {
        try {
            this.session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            log.error("发送数据异常");
        }
    }


    /**
     * 发送信息给客户端
     * @param message 数据
     */
    public void sendMessage(String libraryId, String type, String message)  {
        if (webSocketSet.get(libraryId+"-"+type) != null) {
            webSocketSet.get(libraryId+"-"+type).sendMessage(message);
        }else {
            log.error("该客户端不在线");
        }
    }


}
