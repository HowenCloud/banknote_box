package com.ixilink.banknote_box.message.service.impl;

import com.ixilink.banknote_box.common.dao.*;
import com.ixilink.banknote_box.common.exception.BusinessException;
import com.ixilink.banknote_box.common.model.Code;
import com.ixilink.banknote_box.common.model.OutLineNormalResult;
import com.ixilink.banknote_box.common.pojo.*;
import com.ixilink.banknote_box.common.util.JsonUtil;
import com.ixilink.banknote_box.common.util.RedisUtil;
import com.ixilink.banknote_box.common.util.ValidatePermissionsUtil;
import com.ixilink.banknote_box.message.command.BoxManager;
import com.ixilink.banknote_box.message.common.FacePublicData;
import com.ixilink.banknote_box.message.service.InLineInventoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * @description:
 * @author: 张皓峰
 * @date: 2020-01-11 14:09
 */
@Service
public class InLineInventoryServiceImpl implements InLineInventoryService {

    @Resource
    private RedisUtil redisUtil;
    @Resource
    private SystemSettingMapper systemSettingMapper;
    @Resource
    private OutLibraryLineMapper outLibraryLineMapper;
    @Resource
    private OutLibraryLineAtmMapper outLibraryLineAtmMapper;
    @Resource
    private OutLibraryTaskMapper outLibraryTaskMapper;
    @Resource
    private InLibraryLineMapper inLibraryLineMapper;
    @Resource
    private InLibraryTaskMapper inLibraryTaskMapper;
    @Resource
    private EquipmentMapper equipmentMapper;

    @Override
    public Map<String, Object> inventoryLine(Integer id, HttpServletRequest request, HttpServletResponse response) {
        User user = ValidatePermissionsUtil.getUser(request,redisUtil);
        SystemSetting systemSetting = systemSettingMapper.selectByPrimaryKey(user.getLibraryId());
        BoxManager boxManager = new BoxManager(systemSetting.getHandoverReaderWriterIp());
        boxManager.connection();
        boxManager.clean();
        boxManager.scan();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //盘点集合
        List<String> rfids = boxManager.read();
        boxManager.close();

        //入库线路详情
        InLibraryLine line = inLibraryLineMapper.selectByPrimaryKey(id);
        //线路补录集合
        List<String> supplementResult = JsonUtil.jackson2other(line.getSupplementResult(), List.class, String.class);
        rfids.addAll(supplementResult);

        //钞箱编号
        String boxNumber = "[]";
        if (rfids.size()>0) {
            boxNumber = outLibraryLineMapper.getBoxNumber(user.getLibraryId(), rfids);
        }
        //线路盘点钞箱
        List<String> boxNumbers = JsonUtil.jackson2other(boxNumber,List.class,String.class);
        //线路补录钞箱
        List<String> supplementBoxNumbers = JsonUtil.jackson2other(line.getSupplementResult(),List.class,String.class);
        //线路钞箱总和
        List<String> sumBoxNumbers = new ArrayList<>();
        sumBoxNumbers.addAll(boxNumbers);
        sumBoxNumbers.addAll(supplementBoxNumbers);

        //实际数量
        List<Map<String, Object>> inventoryResultMap = new ArrayList<>();
        if (boxNumbers.size()>0){
            inventoryResultMap = outLibraryLineMapper.inventory(user.getLibraryId(), sumBoxNumbers);
        }
        //线路各类钞箱实际数量格式化
        OutLineNormalResult inventoryResult = new OutLineNormalResult(inventoryResultMap);
        //线路各类钞箱应有数量
        OutLineNormalResult normalResult = (OutLineNormalResult)JsonUtil.str2Obj(line.getNormalResult(), OutLineNormalResult.class);

        //盘点状态
        int inventoryState = 1;
        if (!inventoryResult.toString().equals(normalResult.toString())){
            inventoryState = 2;
        }


        //查询该线路下的ATM任务集合
        OutLibraryLineAtmExample example = new OutLibraryLineAtmExample();
        example.createCriteria().andLineIdEqualTo(id).andStateEqualTo(1);
        List<OutLibraryLineAtm> lineAtms = outLibraryLineAtmMapper.selectByExample(example);
        for (OutLibraryLineAtm outLibraryLineAtm:lineAtms){
            //盘点卸钞钞箱
            List<String> oldTakeBox = JsonUtil.jackson2other(outLibraryLineAtm.getTakeBox(),List.class,String.class);
            List<String> newTakeBox = new ArrayList<>();
            //补录卸钞钞箱
            List<String> oldSupplementTakeBox = JsonUtil.jackson2other(outLibraryLineAtm.getSupplementTakeBox(),List.class,String.class);
            List<String> newSupplementTakeBox = new ArrayList<>(oldSupplementTakeBox);
            //应有卸钞钞箱
            List<String> originalTakeBox = JsonUtil.jackson2other(outLibraryLineAtm.getOriginalTakeBox(),List.class,String.class);
            for (String str:oldTakeBox){
                if (boxNumbers.contains(str)) newTakeBox.add(str);
            }
            for (String str:oldSupplementTakeBox){
                if (boxNumbers.contains(str)) {
                    newTakeBox.add(str);
                    newSupplementTakeBox.remove(str);
                }
            }
            for (String str:originalTakeBox){
                if (boxNumbers.contains(str) && !newTakeBox.contains(str)) newTakeBox.add(str);
            }
            outLibraryLineAtm.setTakeBox(JsonUtil.list2str(newTakeBox));
            outLibraryLineAtm.setSupplementBox(JsonUtil.list2str(newSupplementTakeBox));
            outLibraryLineAtmMapper.updateByPrimaryKeySelective(outLibraryLineAtm);

            //如果盘点状态正常则继续判断ATM对应钞箱是否正确
            if (inventoryState == 1) {
                Set<String> takeBox = new HashSet<>();
                takeBox.addAll(newTakeBox);
                takeBox.addAll(newSupplementTakeBox);
                if (originalTakeBox.size() > 0 || originalTakeBox.size() != takeBox.size()) {
                    inventoryState = 2;
                }
            }
        }

        //更新线路数据及状态
        InLibraryLine inLibraryLine = new InLibraryLine();
        inLibraryLine.setId(id);
        inLibraryLine.setCheckState(inventoryState);
        inLibraryLine.setInventoryResult(JsonUtil.list2str(boxNumbers));
        int i = inLibraryLineMapper.updateByPrimaryKeySelective(inLibraryLine);
        if (i<1) throw new BusinessException(Code.MESSAGE_ERROR.getCode(), "识别数据出错");

        Map<String,Object> map = new HashMap<>();
        map.put("inventoryState",inventoryState);
        map.put("inventoryResult",inventoryResult);
        map.put("normalResult",normalResult);
        return map;
    }

    @Override
    @Transactional
    public void handoverInLine(Integer taskId, HttpServletRequest request, HttpServletResponse response) {
        User user = ValidatePermissionsUtil.getUser(request,redisUtil);

        InLibraryLineExample inLibraryLineExample = new InLibraryLineExample();
        inLibraryLineExample.createCriteria().andTaskIdEqualTo(taskId);

        //任务的线路总条数
        long sumCount = inLibraryLineMapper.countByExample(inLibraryLineExample);

        //已完成盘点  待人脸交接的线路
        inLibraryLineExample.getCriteria().andCheckStateEqualTo(1).andFaceStateEqualTo(0).andLineStateEqualTo(0);
        long count = inLibraryLineMapper.countByExample(inLibraryLineExample);
        if (count==0) throw new BusinessException(Code.MESSAGE_ERROR.getCode(), "没有可交接的线路，请刷新页面再试");

        //更新线路
        InLibraryLine inLibraryLine = new InLibraryLine();
        inLibraryLine.setFaceState(1);
        //执行更新进度
        int i = inLibraryLineMapper.updateByExampleSelective(inLibraryLine, inLibraryLineExample);
        if (i < 1) throw new BusinessException(Code.MESSAGE_ERROR.getCode(), "交接失败");

        //已交接条数
        long handoverCount = inLibraryLineMapper.countByExample(inLibraryLineExample);

        //更新任务具体进度
        InLibraryTask inLibraryTask = inLibraryTaskMapper.selectByPrimaryKey(taskId);
        if (inLibraryTask.getTaskSchedule()==1){
            InLibraryTask task = new InLibraryTask();
            task.setId(taskId);
            task.setTaskSchedule(2);
            inLibraryTaskMapper.updateByPrimaryKeySelective(task);
        }
        else if (sumCount == (handoverCount+i)){
            InLibraryTask task = new InLibraryTask();
            task.setId(taskId);
            task.setTaskSchedule(3);
            inLibraryTaskMapper.updateByPrimaryKeySelective(task);
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

}
