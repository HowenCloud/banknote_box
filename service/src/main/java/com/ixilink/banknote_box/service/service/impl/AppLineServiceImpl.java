package com.ixilink.banknote_box.service.service.impl;

import com.ixilink.banknote_box.common.dao.*;
import com.ixilink.banknote_box.common.exception.BusinessException;
import com.ixilink.banknote_box.common.model.Code;
import com.ixilink.banknote_box.common.pojo.*;
import com.ixilink.banknote_box.common.util.*;
import com.ixilink.banknote_box.service.parameter_model.app.SubmitBoxs;
import com.ixilink.banknote_box.service.parameter_model.app.SubmitDepositBox;
import com.ixilink.banknote_box.service.service.AppLineService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * @description:
 * @author: 张皓峰
 * @date: 2019-12-20 10:21
 */
@Service
public class AppLineServiceImpl implements AppLineService {

    @Resource
    private OutLibraryTaskMapper outLibraryTaskMapper;
    @Resource
    private OutLibraryLineMapper outLibraryLineMapper;
    @Resource
    private OutLibraryLineAtmMapper outLibraryLineAtmMapper;
    @Resource
    private EquipmentAtmMapper equipmentAtmMapper;
    @Resource
    private RedisUtil redisUtil;


    @Override
    public Map<String, Object> lineDetails(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> result = new HashMap<>();
        User user = ValidatePermissionsUtil.getUser(request,redisUtil);
        String dateStr = DateFormatUtil.dateToStr(new Date());
        long startTime = DateFormatUtil.strToDateLong(dateStr + " 00:00:00").getTime();
        long endTime = DateFormatUtil.strToDateLong(dateStr + " 23:59:59").getTime();
        Map<String, Integer> newTaskIdAndLineId = outLibraryLineMapper.getNewTaskIdAndLineIdByUserId(user.getLibraryId(), user.getId(), startTime, endTime);
        if (newTaskIdAndLineId == null) {
            throw new BusinessException(Code.MESSAGE_ERROR.getCode(),"没有待加钞任务");
        }
        List<Map<String, Object>> lineDetails = outLibraryLineMapper.lineDetails(newTaskIdAndLineId.get("lineId"));
        List<Map<String,Object>> doing = new ArrayList<>();
        List<Map<String,Object>> completed = new ArrayList<>();
        lineDetails.forEach(l -> {
            if (l.get("schedule").equals(0)) doing.add(l);
            else completed.add(l);
        });
        result.put("doing",doing);
        result.put("completed",completed);
        return result;
    }

    @Override
    public Map<String, Object> depositBox(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> result = new HashMap<>();
        User user = ValidatePermissionsUtil.getUser(request,redisUtil);
        String dateStr = DateFormatUtil.dateToStr(new Date());
        long startTime = DateFormatUtil.strToDateLong(dateStr + " 00:00:00").getTime();
        long endTime = DateFormatUtil.strToDateLong(dateStr + " 23:59:59").getTime();
        Map<String, Integer> newTaskIdAndLineId = outLibraryLineMapper.getNewTaskIdAndLineIdByUserId(user.getLibraryId(), user.getId(), startTime, endTime);
        if (newTaskIdAndLineId == null) {
            throw new BusinessException(Code.MESSAGE_ERROR.getCode(),"没有待加钞任务");
        }
        OutLibraryLine line = outLibraryLineMapper.selectByPrimaryKey(newTaskIdAndLineId.get("lineId"));
        List<String> inventoryResult = JsonUtil.jackson2other(line.getInventoryResult(), List.class, String.class);
        List<String> supplementResult = JsonUtil.jackson2other(line.getSupplementResult(), List.class, String.class);
        inventoryResult.addAll(supplementResult);
        List<Map<String, Object>> maps = outLibraryLineMapper.depositBox(user.getLibraryId(), inventoryResult);
        maps.forEach(map -> {
            if (map.get("typeName").toString().contains("Scrap")){
                result.computeIfAbsent(map.get("typeName").toString(), k -> new ArrayList<>());
                @SuppressWarnings("all")
                List<Map<String, Object>> list = (List<Map<String, Object>>)result.get(map.get("typeName").toString());
                list.add(map);
                result.put(map.get("typeName").toString(),list);
            }else {
                result.computeIfAbsent(map.get("typeName")+"-"+map.get("money"), k -> new ArrayList<>());
                @SuppressWarnings("all")
                List<Map<String, Object>> list = (List<Map<String, Object>>)result.get(map.get("typeName")+"-"+map.get("money"));
                list.add(map);
                result.put(map.get("typeName")+"-"+map.get("money"),list);
            }
        });
        return result;
    }

    @Override
    @Transactional
    public void submitDepositBox(SubmitDepositBox param, HttpServletRequest request, HttpServletResponse response) {
        //原ATM加钞任务进度
        OutLibraryLineAtm atm = outLibraryLineAtmMapper.selectByPrimaryKey(param.getId());
        final int[] totalMoney = {0};
        //加钞钞箱
        List<SubmitBoxs> depositBox = new ArrayList<>();
        //补录钞箱
        List<SubmitBoxs> supplementBox = new ArrayList<>();
        //卸钞钞箱
        List<String> takeBox = new ArrayList<>();
        param.getBox().forEach(box ->{
            totalMoney[0] += box.getMoney();
            if (box.getCheckType()==1) {
                depositBox.add(box);
            }else if (box.getCheckType()==2){
                supplementBox.add(box);
            }
        });
        int schedule = 1;
        if (atm.getTotalBox() != param.getBox().size() || !atm.getTotalMoney().equals(totalMoney[0])){
            schedule = 2;
        }
        //原ATM设备信息
        EquipmentAtm oldAtm = equipmentAtmMapper.selectByPrimaryKey(atm.getAtmId());
        //加钞后ATM设备信息
        EquipmentAtm newAtm = new EquipmentAtm();
        newAtm.setId(oldAtm.getId());
        try {
            for (int i=1; i<=5; i++){
                Object o = ReflexUtil.get(oldAtm, "getBox" + i);
                if (o!=null && !o.equals("")){
                    takeBox.add(o.toString());
                }
            }
            for (SubmitBoxs box:param.getBox()){
                ReflexUtil.set(newAtm,"setBox"+box.getPosition(),box.getNumber());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //加钞后ATM加钞任务进度
        OutLibraryLineAtm newAtmTask = new OutLibraryLineAtm();
        newAtmTask.setId(param.getId());
        newAtmTask.setDepositBox(JsonUtil.list2str(depositBox));
        newAtmTask.setSupplementBox(JsonUtil.list2str(supplementBox));
        newAtmTask.setTakeBox(JsonUtil.list2str(takeBox));
        newAtmTask.setSchedule(schedule);
        int i = outLibraryLineAtmMapper.updateByPrimaryKeySelective(newAtmTask);
        if (i <1) {
            throw new BusinessException(Code.MESSAGE_ERROR.getCode(),"提交失败");
        }
        i = equipmentAtmMapper.updateByPrimaryKeySelective(newAtm);
        if (i <1) {
            throw new BusinessException(Code.MESSAGE_ERROR.getCode(),"提交失败");
        }

        OutLibraryLineAtmExample outLibraryLineAtmExample = new OutLibraryLineAtmExample();
        outLibraryLineAtmExample.createCriteria().andLineIdEqualTo(atm.getLineId()).andStateEqualTo(1).andScheduleEqualTo(0);
        long atmCount = outLibraryLineAtmMapper.countByExample(outLibraryLineAtmExample);

        if (atmCount == 0){
            OutLibraryLine outLibraryLine = new OutLibraryLine();
            outLibraryLine.setId(atm.getLineId());
            outLibraryLine.setTaskState(2);
            i = outLibraryLineMapper.updateByPrimaryKeySelective(outLibraryLine);
            if (i <1) {
                throw new BusinessException(Code.MESSAGE_ERROR.getCode(),"提交失败");
            }

            OutLibraryLine line = outLibraryLineMapper.selectByPrimaryKey(atm.getLineId());
            OutLibraryLineExample outLibraryLineExample = new OutLibraryLineExample();
            outLibraryLineExample.createCriteria().andTaskIdEqualTo(line.getTaskId()).andTaskStateEqualTo(1);
            long lineCount = outLibraryLineMapper.countByExample(outLibraryLineExample);
            if (lineCount == 0){
                OutLibraryTask task = new OutLibraryTask();
                task.setId(line.getTaskId());
                task.setTaskState(2);
                i = outLibraryTaskMapper.updateByPrimaryKeySelective(task);
                if (i <1) {
                    throw new BusinessException(Code.MESSAGE_ERROR.getCode(),"提交失败");
                }
            }
        }


//        List<String> box = new ArrayList<>();
//        if (redisUtil.getStringRedisTemplate().opsForHash().hasKey("atmBox","atm-"+param.getId())){
//            String boxStr = (String) redisUtil.getStringRedisTemplate().opsForHash().get("atmBox", "atm-" + param.getId());
//            box = JsonUtil.jackson2other(boxStr,List.class,String.class);
//        }
//        redisUtil.getStringRedisTemplate().opsForHash().put("atmBox","atm-"+param.getId(),JsonUtil.list2str(box));

    }

    @Override
    public Map<String, Object> atmDetails(Integer id, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> result = new HashMap<>();
        OutLibraryLineAtm atm = outLibraryLineAtmMapper.selectByPrimaryKey(id);
        List<SubmitBoxs> depositBoxDetails = JsonUtil.jackson2other(atm.getDepositBox(), List.class, SubmitBoxs.class);
        List<SubmitBoxs> supplementBoxDetails = JsonUtil.jackson2other(atm.getSupplementBox(), List.class, SubmitBoxs.class);
        depositBoxDetails.addAll(supplementBoxDetails);
        List<String> takeBox = JsonUtil.jackson2other(atm.getTakeBox(), List.class, String.class);
        List<String> deposit = new ArrayList<>();
        depositBoxDetails.forEach(box ->{
            deposit.add(box.getNumber());
        });
        result.put("depositBoxDetails",depositBoxDetails);
        result.put("deposit",deposit);
        result.put("takeBox",takeBox);
        return result;
    }
}
