package com.ixilink.banknote_box.message;

import com.ixilink.banknote_box.message.job.EquipmentStatusJob;
import com.ixilink.banknote_box.message.thread.InitData;
import com.ixilink.banknote_box.message.thread.InitFace;
import com.ixilink.build_box.udp.UdpServer;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan("com.ixilink.banknote_box.common.dao")
@ComponentScan(basePackages = {"com.ixilink.banknote_box.common.config","com.ixilink.banknote_box.common.dao","com.ixilink.banknote_box.common.util","com.ixilink.banknote_box.common.spring","com.ixilink.banknote_box.message"})
//@EnableConfigurationProperties(CustomConfig.class)
public class MessageApplication {

    public static void main(String[] args) {
        SpringApplication.run(MessageApplication.class, args);
        new Thread(new InitFace()).start();
        new Thread(new EquipmentStatusJob()).start();
        new Thread(new InitData()).start();
    }

}
