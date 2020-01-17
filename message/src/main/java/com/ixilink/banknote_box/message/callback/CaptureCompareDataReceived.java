package com.ixilink.banknote_box.message.callback;

import com.ha.facecamera.configserver.events.CaptureCompareDataReceivedEventHandler;
import com.ha.facecamera.configserver.pojo.CaptureCompareData;
import com.ixilink.banknote_box.common.dao.EquipmentMapper;
import com.ixilink.banknote_box.common.dao.SystemSettingMapper;
import com.ixilink.banknote_box.common.dao.UserMapper;
import com.ixilink.banknote_box.common.pojo.*;
import com.ixilink.banknote_box.common.spring.SpringUtils;
import com.ixilink.banknote_box.common.util.FileUtil;
import com.ixilink.banknote_box.common.util.IpAddressUtils;
import com.ixilink.banknote_box.common.util.JsonUtil;
import com.ixilink.banknote_box.common.util.ObjectOrMapUtil;
import com.ixilink.banknote_box.message.common.FacePublicData;
import com.ixilink.banknote_box.message.config.CustomConfig;
import com.ixilink.banknote_box.message.websocket.FaceSocket;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description: 视频流数据处理
 * @author: 张皓峰
 * @date: 2019-12-11 15:28
 */
@Slf4j
public class CaptureCompareDataReceived implements CaptureCompareDataReceivedEventHandler {

    @Value("${server.port}")
    private String port;

    private ApplicationContext applicationContext = SpringUtils.getApplicationContext();
    private UserMapper userMapper = applicationContext.getBean(UserMapper.class);
    private EquipmentMapper equipmentMapper = applicationContext.getBean(EquipmentMapper.class);
    private SystemSettingMapper systemSettingMapper = applicationContext.getBean(SystemSettingMapper.class);
    private FaceSocket faceSocket = applicationContext.getBean(FaceSocket.class);
    private CustomConfig customConfig = applicationContext.getBean(CustomConfig.class);

    private static CaptureCompareDataReceived captureCompareDataReceived;
    private CaptureCompareDataReceived(){}
    public static CaptureCompareDataReceived getInstance(){
        if (captureCompareDataReceived == null ){
            synchronized (CaptureCompareDataReceived.class){
                if (captureCompareDataReceived == null){
                    captureCompareDataReceived = new CaptureCompareDataReceived();
                }
            }
        }
        return captureCompareDataReceived;
    }

    @Override
    public void onCaptureCompareDataReceived(CaptureCompareData captureCompareData) {
        if (captureCompareData.isPersonMatched()) {
            if (FacePublicData.userId.get(captureCompareData.getCameraID()) != null) {
                //发送识别到人脸
                if (!FacePublicData.userId.get(captureCompareData.getCameraID()).contains(Integer.valueOf(captureCompareData.getPersonID()))) {
                    //将图片数据转存到本地
                    String path = FileUtil.getPath();
                    File packages = new File(path, "\\temp\\face\\" + captureCompareData.getCameraID());
                    if (!packages.exists()) {
                        boolean mkdirs = packages.mkdirs();
                    }
                    String name = FileUtil.createName() + ".jpg";
                    String filePath = packages.getPath() + File.separator + name;
                    FileUtil.byte2image(captureCompareData.getFeatureImageData(), filePath);
                    FacePublicData.userId.get(captureCompareData.getCameraID()).add(Integer.valueOf(captureCompareData.getPersonID()));
                    //被识别用户
                    User user = userMapper.userInfoById(Integer.valueOf(captureCompareData.getPersonID()));
                    Map<String, Object> data = new HashMap<>();
                    try {
                        data = ObjectOrMapUtil.objectToMap(user);
                    } catch (Exception e) {
                        log.error("数据转换出错");
                    }
                    data.put("face", "http://"+IpAddressUtils.getIp() + ":"+customConfig.getPort()+"/message/temp/face/" + captureCompareData.getCameraID() + "/" + name);
//                    data.put("face", "/message/temp/face/" + captureCompareData.getCameraID() + "/" + name);
                    //查询设备
                    EquipmentExample equipmentExample = new EquipmentExample();
                    equipmentExample.createCriteria().andStateEqualTo(1).andNumberEqualTo(captureCompareData.getCameraID());
                    List<Equipment> equipment = equipmentMapper.selectByExample(equipmentExample);
                    if (equipment.size() > 0) {
                        SystemSettingExample systemSettingExample = new SystemSettingExample();
                        systemSettingExample.createCriteria().andHandoverAmeraIpEqualTo(equipment.get(0).getIp());
                        systemSettingExample.or().andAssignmentsAmeraIpEqualTo(equipment.get(0).getIp());
                        //查询摄像头所属区域
                        List<SystemSetting> systemSettings = systemSettingMapper.selectByExample(systemSettingExample);
                        if (systemSettings.size() > 0) {
                            //发送数据
                            if (systemSettings.get(0).getAssignmentsAmeraIp().equals(equipment.get(0).getIp())) {
                                faceSocket.sendMessage(user.getLibraryId().toString(), "1", JsonUtil.obj2str(data));
                            }
                            if (systemSettings.get(0).getHandoverAmeraIp().equals(equipment.get(0).getIp())) {
                                faceSocket.sendMessage(user.getLibraryId().toString(), "2", JsonUtil.obj2str(data));
                            }
                        }
                    }
                }
            }
        }
    }



}
