package com.ixilink.banknote_box.message.thread;

import com.ha.facecamera.configserver.*;
import com.ha.facecamera.configserver.pojo.Face;
import com.ha.sdk.HaCamera;
import com.ixilink.banknote_box.common.util.FileUtil;
import com.ixilink.banknote_box.message.callback.*;
import com.ixilink.banknote_box.message.common.FacePublicData;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.Objects;

/**
 * @description:
 * @author: 张皓峰
 * @date: 2019-12-11 16:40
 */
@Slf4j
public class InitFace implements Runnable {

    @Override
    public void run() {

        //配置服务器
        FacePublicData.startConfigServer();

    }

}
