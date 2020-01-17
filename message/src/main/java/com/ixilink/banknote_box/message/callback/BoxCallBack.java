package com.ixilink.banknote_box.message.callback;

import com.ixilink.banknote_box.common.dao.*;
import com.ixilink.banknote_box.common.model.OutLineNormalResult;
import com.ixilink.banknote_box.common.pojo.*;
import com.ixilink.banknote_box.common.spring.SpringUtils;
import com.ixilink.banknote_box.common.util.JsonUtil;
import com.ixilink.banknote_box.common.util.ObjectOrMapUtil;
import com.ixilink.build_box.common.MessageCallback;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @description: udp报文接收类
 * @author: 张俊
 * @date: 2019-11-09 15:01
 */
@Slf4j
public class BoxCallBack implements MessageCallback {

    private ApplicationContext applicationContext = SpringUtils.getApplicationContext();
    private EquipmentMapper equipmentMapper = applicationContext.getBean(EquipmentMapper.class);
    private EquipmentAtmMapper equipmentAtmMapper = applicationContext.getBean(EquipmentAtmMapper.class);
    private OutLibraryLineAtmMapper outLibraryLineAtmMapper = applicationContext.getBean(OutLibraryLineAtmMapper.class);
    private OutLibraryLineMapper outLibraryLineMapper = applicationContext.getBean(OutLibraryLineMapper.class);
    private OutLibraryTaskMapper outLibraryTaskMapper = applicationContext.getBean(OutLibraryTaskMapper.class);
    private CarBoxMapper carBoxMapper = applicationContext.getBean(CarBoxMapper.class);

    private static BoxCallBack boxCallBack;
    public static BoxCallBack getInstance(){
        if (boxCallBack == null){
            synchronized (BoxCallBack.class){
                if (boxCallBack == null){
                    boxCallBack = new BoxCallBack();
                }
            }
        }
        return boxCallBack;
    }

    @Override
    public void messageReceived(String ip, String message) {
        log.debug("收到UDP报文："+message);
        //报文头
        String vcmd = message.substring(0, 2);
        //指令对象
        String obj = message.substring(6, 8);
        //响应状态
        String code = message.substring(2, 4);

        switch (obj) {
            case "22":
                Integer layer = Integer.valueOf(message.substring(12, 14));
                Integer column = Integer.valueOf(message.substring(14, 16),16);
                String RFID = message.substring(16,message.length());
                System.out.println(layer+" "+column+" "+RFID);
                //ATM机通道编号
                Double ceil = Math.ceil(column / 5.0);
                Integer passageway = ceil.intValue();
                //钞箱位置编号
                Integer position = column%5;
                if (position == 0) position = 5;

                EquipmentExample equipmentExample = new EquipmentExample();
                equipmentExample.createCriteria().andIpEqualTo(ip).andEquipmentTypeEqualTo(2).andStateEqualTo(1);
                List<Equipment> equipments = equipmentMapper.selectByExample(equipmentExample);
                if (equipments.size()==0){
                    log.error("收到未知设备："+ip+"的数据");
                    return;
                }
                //集中控制器
                Equipment equipment = equipments.get(0);

                //查询ATM机数据
                EquipmentAtmExample equipmentAtmExample = new EquipmentAtmExample();
                equipmentAtmExample.createCriteria().andControllerIdEqualTo(equipment.getId()).andPassagewayEqualTo(passageway);
                List<EquipmentAtm> equipmentAtms = equipmentAtmMapper.selectByExample(equipmentAtmExample);
                if (equipmentAtms.size()==0){
                    log.error("没有相应ATM设备信息");
                    return;
                }
                EquipmentAtm equipmentAtm = equipmentAtms.get(0);

                //查询出库ATM加钞任务信息
                OutLibraryLineAtmExample example = new OutLibraryLineAtmExample();
                example.createCriteria().andAtmIdEqualTo(equipmentAtm.getId());
                example.setOrderByClause(" id DESC ");
                List<OutLibraryLineAtm> lineAtms = outLibraryLineAtmMapper.selectByExample(example);
                if (lineAtms.size()==0){
                    log.error("没有相应ATM加钞任务信息");
                    return;
                }
                //ATM加钞任务
                OutLibraryLineAtm outLibraryLineAtm = lineAtms.get(0);
                //补录加钞钞箱
                List<String> supplementBox = JsonUtil.jackson2other(outLibraryLineAtm.getSupplementBox(),List.class,String.class);
                //实际加钞钞箱
                List<String> depositBox = JsonUtil.jackson2other(outLibraryLineAtm.getDepositBox(),List.class,String.class);
                //卸钞钞箱
                List<String> takeBox = JsonUtil.jackson2other(outLibraryLineAtm.getTakeBox(),List.class,String.class);
                //应卸钞钞箱
                List<String> originalTakeBox = JsonUtil.jackson2other(outLibraryLineAtm.getOriginalTakeBox(),List.class,String.class);

                //卸钞
                if ("00000000".equals(RFID)){
                    //更新ATM机钞箱ID
                    equipmentAtmMapper.changeAtmBox("box"+position,0,equipment.getId(),passageway);
                    //卸下钞箱信息
                    Map<String, Object> oldBoxInfo = null;
                    try {
                        Map<String, Object> map = ObjectOrMapUtil.objectToMap(equipmentAtm);
                        oldBoxInfo = equipmentAtmMapper.findInfoByKey((int)map.get("box"+position));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (oldBoxInfo == null || oldBoxInfo.isEmpty()) return;
                    if (depositBox.contains(oldBoxInfo.get("number").toString())){
                        depositBox.remove(oldBoxInfo.get("number").toString());
                        carBoxMapper.modifySite("运输中",(int)oldBoxInfo.get("id"));
                    }else {
                        takeBox.add(oldBoxInfo.get("number").toString());
                        carBoxMapper.modifySite("已卸钞 运输中",(int)oldBoxInfo.get("id"));
                    }
                    if (originalTakeBox.toString().equals(takeBox.toString())) {
                        outLibraryLineAtm.setInState(1);
                    }
                    else if (takeBox.containsAll(originalTakeBox) && originalTakeBox.containsAll(takeBox)) {
                        outLibraryLineAtm.setInState(1);
                    }
                    else {
                        outLibraryLineAtm.setInState(2);
                    }
                }
                //加钞
                else {
                    //放入的钞箱信息
                    Map<String, Object> boxInfo = equipmentAtmMapper.findInfoByPassiveRfid(RFID);
                    //更新ATM机钞箱ID
                    equipmentAtmMapper.changeAtmBox("box"+position,(int)boxInfo.get("id"),equipment.getId(),passageway);
                    if (takeBox.contains(boxInfo.get("number").toString())){
                        takeBox.remove(boxInfo.get("number").toString());
                    }else {
                        depositBox.add(boxInfo.get("number").toString());
                    }
                    carBoxMapper.modifySite(equipmentAtm.getName()+"("+equipmentAtm.getNumber()+") 第"+position+"格",(int)boxInfo.get("id"));
                }

                supplementBox.addAll(depositBox);
                //实际加钞钞箱数量
                List<Map<String, Object>> deposit = new ArrayList<>();
                if (supplementBox.size()>0){
                    deposit = outLibraryLineMapper.inventory(null, supplementBox);
                }
                int totalMoney = validateCount(deposit);
                //实际数量格式化
                OutLineNormalResult depositResult = new OutLineNormalResult(deposit);
                //应有钞箱数量
                OutLineNormalResult normalResult = validateCount(outLibraryLineAtm, equipmentAtm.getBoxType());
                //加钞状态 1正常/2异常
                log.debug(totalMoney+"==="+outLibraryLineAtm.getTotalMoney());
                log.debug(depositResult+"==="+normalResult+"==="+depositResult.toString()+"==="+normalResult.toString());
                log.debug("验证对象"+depositResult.equals(normalResult));
                log.debug("验证字符串"+depositResult.toString().equals(normalResult.toString()));
                log.debug(totalMoney+"==="+outLibraryLineAtm.getTotalMoney());

                if (outLibraryLineAtm.getTotalMoney().equals(totalMoney) && depositResult.toString().equals(normalResult.toString())){
                    //绿灯
                    outLibraryLineAtm.setSchedule(1);
                }else {
                    //红灯
                    outLibraryLineAtm.setSchedule(2);
                }

                //设置最新加钞卸钞钞箱
                outLibraryLineAtm.setDepositBox(JsonUtil.list2str(depositBox));
                outLibraryLineAtm.setTakeBox(JsonUtil.list2str(takeBox));
                //更新出库任务ATM任务进度
                outLibraryLineAtmMapper.updateByPrimaryKeySelective(outLibraryLineAtm);

                example.clear();
                example.createCriteria().andLineIdEqualTo(outLibraryLineAtm.getLineId()).andStateEqualTo(1);
                long sumCount = outLibraryLineAtmMapper.countByExample(example);
                example.getCriteria().andScheduleGreaterThan(0);
                long overCount = outLibraryLineAtmMapper.countByExample(example);
                log.debug("sumCount:"+sumCount+"   overCount:"+overCount);
                if (sumCount == overCount){
                    OutLibraryLine outLibraryLine1 = outLibraryLineMapper.selectByPrimaryKey(outLibraryLineAtm.getLineId());
                    if (outLibraryLine1.getBoxingState()==2 && outLibraryLine1.getCheckState()==1 && outLibraryLine1.getHandoverCheckState()==1 && outLibraryLine1.getAddCheckState()==1 && outLibraryLine1.getCheckSchedule()==3) {
                        OutLibraryLine outLibraryLine = new OutLibraryLine();
                        outLibraryLine.setId(outLibraryLineAtm.getLineId());
                        outLibraryLine.setTaskState(2);
                        outLibraryLineMapper.updateByPrimaryKeySelective(outLibraryLine);

                        OutLibraryLineExample outLibraryLineExample = new OutLibraryLineExample();
                        outLibraryLineExample.createCriteria().andTaskIdEqualTo(outLibraryLine1.getTaskId()).andTaskStateEqualTo(1);
                        long lineCount = outLibraryLineMapper.countByExample(outLibraryLineExample);
                        if (lineCount == 0) {
                            OutLibraryTask task = new OutLibraryTask();
                            task.setId(outLibraryLine1.getTaskId());
                            task.setTaskSchedule(4);
                            task.setTaskState(2);
                            outLibraryTaskMapper.updateByPrimaryKeySelective(task);
                        }
                    }
                }
                break;
            default:
                System.out.println("未知指令");
                break;
        }
    }
    //计算总金额
    private int validateCount(List<Map<String, Object>> deposit){
        int totalMoney = 0;
        for (Map<String,Object> map:deposit){
            log.debug(map.get("unit").toString()+"==="+map.get("totalMoney").toString());
            totalMoney += Integer.valueOf(map.get("totalMoney").toString());
        }
        return totalMoney;
    }
    //计算各类钞箱数量
    private OutLineNormalResult validateCount(OutLibraryLineAtm outLibraryLineAtm,int boxType){
        //应有数量
        OutLineNormalResult normalResult = new OutLineNormalResult();
        switch (boxType){
            case 1:
                normalResult.setDbNum(normalResult.getDbNum()+outLibraryLineAtm.getTotalBox()-1);
                normalResult.setDbScrapNum(normalResult.getDbScrapNum()+1);
                break;
            case 2:
                normalResult.setDfNum(normalResult.getDfNum()+outLibraryLineAtm.getTotalBox()-1);
                normalResult.setDfScrapNum(normalResult.getDfScrapNum()+1);
                break;
            case 3:{
                Integer atmTotalBox = outLibraryLineAtm.getTotalBox();
                int num = outLibraryLineAtm.getUnit()==0?0:(outLibraryLineAtm.getTotalMoney() / outLibraryLineAtm.getUnit());
                normalResult.setCrsNum(normalResult.getCrsNum()+num);
                normalResult.setCrsScrapNum(normalResult.getCrsScrapNum()+1);
                normalResult.setCrsEmptyNum(normalResult.getCrsEmptyNum()+(atmTotalBox-num-1));
                break;
            }
            case 4: {
                Integer atmTotalBox = outLibraryLineAtm.getTotalBox();
                int num = outLibraryLineAtm.getUnit()==0?0:(outLibraryLineAtm.getTotalMoney() / outLibraryLineAtm.getUnit());
                normalResult.setScrsNum(normalResult.getScrsNum()+num);
                normalResult.setScrsScrapNum(normalResult.getScrsScrapNum()+1);
                normalResult.setScrsEmptyNum(normalResult.getScrsEmptyNum()+(atmTotalBox-num-1));
                break;
            }
        }
        return normalResult;
    }
}
