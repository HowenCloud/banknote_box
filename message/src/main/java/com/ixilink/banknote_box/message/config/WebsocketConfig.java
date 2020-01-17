package com.ixilink.banknote_box.message.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @description: websocket配置
 * @author: 张俊
 * @date: 2019-05-16 14:53
 */

@Configuration
public class WebsocketConfig {

    @Bean
    public ServerEndpointExporter createServerEndExporter(){
        return new ServerEndpointExporter();
    }
}
