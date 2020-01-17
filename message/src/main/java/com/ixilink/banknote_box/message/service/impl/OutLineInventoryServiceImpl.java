package com.ixilink.banknote_box.message.service.impl;

import com.ixilink.banknote_box.common.dao.EquipmentMapper;
import com.ixilink.banknote_box.common.dao.OutLibraryLineMapper;
import com.ixilink.banknote_box.common.dao.OutLibraryTaskMapper;
import com.ixilink.banknote_box.common.dao.SystemSettingMapper;
import com.ixilink.banknote_box.common.exception.BusinessException;
import com.ixilink.banknote_box.common.model.Code;
import com.ixilink.banknote_box.common.model.OutLineNormalResult;
import com.ixilink.banknote_box.common.pojo.*;
import com.ixilink.banknote_box.common.util.*;
import com.ixilink.banknote_box.message.command.BoxManager;
import com.ixilink.banknote_box.message.common.FacePublicData;
import com.ixilink.banknote_box.message.service.OutLineInventoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: 张皓峰
 * @date: 2019-12-09 10:35
 */
@Service
public class OutLineInventoryServiceImpl implements OutLineInventoryService {

    @Resource
    private RedisUtil redisUtil;
    @Resource
    private OutLibraryLineMapper outLibraryLineMapper;
    @Resource
    private SystemSettingMapper systemSettingMapper;
    @Resource
    private OutLibraryTaskMapper outLibraryTaskMapper;
    @Resource
    private EquipmentMapper equipmentMapper;

    @Override
    @Transactional
    public Map<String,Object> inventoryLine(Integer id, Integer type, HttpServletRequest request, HttpServletResponse response) {
        User user = ValidatePermissionsUtil.getUser(request,redisUtil);
        SystemSetting systemSetting = systemSettingMapper.selectByPrimaryKey(user.getLibraryId());
        BoxManager boxManager = new BoxManager(type==0?systemSetting.getAssignmentsReaderWriterIp():systemSetting.getHandoverReaderWriterIp());
        boxManager.connection();
        boxManager.clean();
        boxManager.scan();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        List<String> rfids = boxManager.read();
        boxManager.close();

        //出库线路详情
        OutLibraryLine line = outLibraryLineMapper.selectByPrimaryKey(id);
        List<String> supplementResult = JsonUtil.jackson2other(line.getSupplementResult(), List.class, String.class);
        rfids.addAll(supplementResult);

        //钞箱编号
        String boxNumber = "[]";
        if (rfids.size()>0) {
            boxNumber = outLibraryLineMapper.getBoxNumber(user.getLibraryId(), rfids);
        }
        //盘点钞箱
        List<String> boxNumbers = JsonUtil.jackson2other(boxNumber,List.class,String.class);
        //补录钞箱
        List<String> SumboxNumbers = JsonUtil.jackson2other(line.getSupplementResult(),List.class,String.class);
        //总和
        SumboxNumbers.addAll(boxNumbers);

        //实际数量
        List<Map<String, Object>> inventory = new ArrayList<>();
        if (boxNumbers.size()>0){
            inventory = outLibraryLineMapper.inventory(user.getLibraryId(), SumboxNumbers);
        }
        //实际数量格式化
        OutLineNormalResult inventoryResult = new OutLineNormalResult(inventory);
        //应有数量
        OutLineNormalResult normalResult = (OutLineNormalResult)JsonUtil.str2Obj(line.getNormalResult(), OutLineNormalResult.class);

        //盘点状态
        int inventoryState = 1;
        if (!inventoryResult.toString().equals(normalResult.toString())){
            inventoryState = 2;
        }

        //更新
        OutLibraryLine outLibraryLine = new OutLibraryLine();
        outLibraryLine.setId(id);
        if (type==0) outLibraryLine.setCheckState(inventoryState);
        else if (type==1) outLibraryLine.setHandoverCheckState(inventoryState);
        else if (type==2) outLibraryLine.setAddCheckState(inventoryState);
        outLibraryLine.setInventoryResult(JsonUtil.list2str(boxNumbers));
        int i = outLibraryLineMapper.updateByPrimaryKeySelective(outLibraryLine);
        if (i<1) throw new BusinessException(Code.MESSAGE_ERROR.getCode(), "识别数据出错");
        Map<String,Object> map = new HashMap<>();
        map.put("inventoryState",inventoryState);
        map.put("inventoryResult",inventoryResult);
        map.put("normalResult",normalResult);
        return map;
    }

    @Override
    @Transactional
    public void startRecognitionFace(Map<String, Integer> param, HttpServletRequest request, HttpServletResponse response) {
        User user = ValidatePermissionsUtil.getUser(request,redisUtil);
        SystemSetting systemSetting = systemSettingMapper.selectByPrimaryKey(user.getLibraryId());
        if (systemSetting == null) throw new BusinessException(Code.MESSAGE_ERROR.getCode(), "缺少系统配置");
        if (param.get("type") == 1){
            if (systemSetting.getAssignmentsAmeraIp().equals("")) throw new BusinessException(Code.MESSAGE_ERROR.getCode(), "请先配置作业区摄像头IP");
            if (!OnlineTest.ping(systemSetting.getAssignmentsAmeraIp())) throw new BusinessException(Code.MESSAGE_ERROR.getCode(), "作业区摄像头不在线");
        }
        else if (param.get("type") == 2){
            if (systemSetting.getHandoverAmeraIp().equals("")) throw new BusinessException(Code.MESSAGE_ERROR.getCode(), "请先配置交接区摄像头IP");
            if (!OnlineTest.ping(systemSetting.getHandoverAmeraIp())) throw new BusinessException(Code.MESSAGE_ERROR.getCode(), "交接区摄像头不在线");
        }
        EquipmentExample equipmentExample = new EquipmentExample();
        equipmentExample.createCriteria().andStateEqualTo(1).andLibraryIdEqualTo(user.getLibraryId()).andIpEqualTo(param.get("type") == 1 ? systemSetting.getAssignmentsAmeraIp() : systemSetting.getHandoverAmeraIp());
        List<Equipment> equipment = equipmentMapper.selectByExample(equipmentExample);
        if (equipment.size()==0) {
            throw new BusinessException(Code.MESSAGE_ERROR.getCode(), "没有设备，请先录入");
        }
        if (!FacePublicData.getDataServerState()) FacePublicData.startDataServer();
        FacePublicData.startRecognitionFace(equipment.get(0).getNumber());
    }

    @Override
    public void stopRecognitionFace(Map<String, Integer> param, HttpServletRequest request, HttpServletResponse response) {
        User user = ValidatePermissionsUtil.getUser(request,redisUtil);
        //查询系统配置 获取ip 获取设备编号
        SystemSetting systemSetting = systemSettingMapper.selectByPrimaryKey(user.getLibraryId());
        EquipmentExample equipmentExample = new EquipmentExample();
        equipmentExample.createCriteria().andStateEqualTo(1).andLibraryIdEqualTo(user.getLibraryId()).andIpEqualTo(systemSetting.getHandoverAmeraIp());
        List<Equipment> equipment = equipmentMapper.selectByExample(equipmentExample);
        if (equipment.size()>0){
            FacePublicData.stopRecognitionFace(equipment.get(0).getNumber());
            //如果没有设备使用   则关闭人脸识别服务器
            if (FacePublicData.userId.isEmpty()) {
                FacePublicData.stopDataServer();
            }
        }
    }

    @Override
    @Transactional
    public void handoverLine(Map<String, Integer> param, HttpServletRequest request, HttpServletResponse response) {
        User user = ValidatePermissionsUtil.getUser(request,redisUtil);
        OutLibraryLineExample outLibraryLineExample = new OutLibraryLineExample();
        outLibraryLineExample.createCriteria().andLibraryIdEqualTo(user.getLibraryId()).andTaskIdEqualTo(param.get("id"));

        //任务的线路总条数
        long sumCount = outLibraryLineMapper.countByExample(outLibraryLineExample);

        //已完成（校验/交接/加钞）交接的条数
        checkLineCount(outLibraryLineExample,param.get("type"),1);
        long count = outLibraryLineMapper.countByExample(outLibraryLineExample);
        if (count==0) throw new BusinessException(Code.MESSAGE_ERROR.getCode(), "没有可交接的线路，请刷新页面再试");

        //更新线路
        OutLibraryLine outLibraryLine = new OutLibraryLine();
        outLibraryLine.setCheckSchedule(param.get("type")+1);
        //执行更新进度
        int i = outLibraryLineMapper.updateByExampleSelective(outLibraryLine, outLibraryLineExample);
        if (i < 1) throw new BusinessException(Code.MESSAGE_ERROR.getCode(), "交接失败");

        //已交接条数
        long checkCount = outLibraryLineMapper.countByExample(outLibraryLineExample);

        //更新任务校验状态
        //更新任务具体进度
        if (sumCount == (checkCount+i) && param.get("type")!=1){
            OutLibraryTask task = new OutLibraryTask();
            task.setId(param.get("id"));
            if (param.get("type") == 0) task.setCheckState(1);
            if (param.get("type") == 2) task.setTaskSchedule(3);
            outLibraryTaskMapper.updateByPrimaryKeySelective(task);
        }
        //查询系统配置 获取ip 获取设备编号
        SystemSetting systemSetting = systemSettingMapper.selectByPrimaryKey(user.getLibraryId());
        EquipmentExample equipmentExample = new EquipmentExample();
        equipmentExample.createCriteria().andStateEqualTo(1).andLibraryIdEqualTo(user.getLibraryId()).andIpEqualTo(systemSetting.getHandoverAmeraIp());
        List<Equipment> equipment = equipmentMapper.selectByExample(equipmentExample);
        if (equipment.size()>0){
            FacePublicData.stopRecognitionFace(equipment.get(0).getNumber());
            //如果没有一台设备被使用   则关闭人脸识别服务器
            if (FacePublicData.userId.isEmpty()) {
                FacePublicData.stopDataServer();
            }
        }
    }

    @Override
    public void reloadRecognitionFace(Integer type, HttpServletRequest request, HttpServletResponse response) {
        User user = ValidatePermissionsUtil.getUser(request,redisUtil);
        SystemSetting systemSetting = systemSettingMapper.selectByPrimaryKey(user.getLibraryId());
        EquipmentExample equipmentExample = new EquipmentExample();
        equipmentExample.createCriteria().andStateEqualTo(1).andLibraryIdEqualTo(user.getLibraryId()).andIpEqualTo(type == 1 ? systemSetting.getAssignmentsAmeraIp() : systemSetting.getHandoverAmeraIp());
        List<Equipment> equipment = equipmentMapper.selectByExample(equipmentExample);
//        FacePublicData.userId.remove(equipment.get(0).getNumber());
        FacePublicData.userId.get(equipment.get(0).getNumber()).clear();
    }

    /**
     * 功能描述：
     *〈获取 未/已识别 线路数量〉
     * @author 张皓峰
     * @date 2020-01-09 15:55
     * @param outLibraryLineExample 过滤对象
     * @param type 识别进度
     * @param state 识别状态
     */
    private void checkLineCount(OutLibraryLineExample outLibraryLineExample, int type, int state){
        outLibraryLineExample.getCriteria().andCheckScheduleEqualTo(type);
        if (type == 0){
            outLibraryLineExample.getCriteria().andCheckStateEqualTo(state);
        }
        else if (type == 1){
            outLibraryLineExample.getCriteria().andHandoverCheckStateEqualTo(state);
        }
        else if (type == 2){
            outLibraryLineExample.getCriteria().andAddCheckStateEqualTo(state);
        }
    }

}
