package com.ixilink.banknote_box.message.websocket;

import com.alibaba.fastjson.JSONObject;
import com.ixilink.banknote_box.common.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;


@Slf4j
@Component
@ServerEndpoint(value = "/boxSocket/{id}")
public class BoxSocket {
    private static ConcurrentHashMap<String, BoxSocket> webSocketSet = new ConcurrentHashMap<>();

    private Session session;

    /**
     * 连接建立成功调用的方法*/
    @OnOpen
    public void onOpen(@PathParam(value = "id") String id ,Session session) {
        this.session = session;
        try {
            if (webSocketSet.get(id) != null) {
                webSocketSet.get(id ).session.close();
                log.info("关闭已有连接");
            }
        } catch (IOException e) {
            log.error("关闭连接异常");
        }
        webSocketSet.put(id,this);
        HashMap<String,Object> map = new HashMap<>();
        map.put("code",201);
        map.put("message","连接成功");
        sendMessage(JSONObject.toJSONString(map));
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        webSocketSet.remove(this);
        log.info("关闭连接");
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
    public void sendMessage(String id, String message)  {
        if (webSocketSet.get(id) != null) {
            webSocketSet.get(id).sendMessage(message);
        }else {
            log.error("该客户端不在线");
        }
    }


}
