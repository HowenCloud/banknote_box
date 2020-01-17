package com.ixilink.banknote_box.message.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @description: 自定义配置类
 * @author: 张皓峰
 * @date: 2019-12-09 19:04
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "custom.config")
public class CustomConfig {

    //端口
    @Value("${server.port}")
    private int port;

    //跨域安全通行验证域名
    private String origin;

    //读写器ip
    private String reader_writer_ip;

}
