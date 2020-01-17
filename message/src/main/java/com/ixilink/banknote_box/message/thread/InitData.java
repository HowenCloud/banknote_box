package com.ixilink.banknote_box.message.thread;

import com.ixilink.banknote_box.message.callback.*;
import com.ixilink.build_box.config.CallbackConfig;
import com.ixilink.build_box.udp.UdpServer;
import lombok.extern.slf4j.Slf4j;

/**
 * @description: 初始化数据
 * @author: 张俊
 * @date: 2019-05-05 9:25
 */
@Slf4j
public class InitData implements Runnable {

    @Override
    public void run() {
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>初始化udp报文接收类>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        CallbackConfig.setMessageCallback(BoxCallBack.getInstance());
        UdpServer.getInstance().Start();
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>初始化系统配置完毕>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    }

}
