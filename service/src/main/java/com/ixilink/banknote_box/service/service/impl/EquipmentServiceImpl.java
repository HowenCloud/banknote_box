package com.ixilink.banknote_box.service.service.impl;

import com.ixilink.banknote_box.common.dao.EquipmentAtmMapper;
import com.ixilink.banknote_box.common.dao.EquipmentMapper;
import com.ixilink.banknote_box.common.dao.EquipmentTypeMapper;
import com.ixilink.banknote_box.common.dao.SystemSettingMapper;
import com.ixilink.banknote_box.common.exception.BusinessException;
import com.ixilink.banknote_box.common.model.Code;
import com.ixilink.banknote_box.common.model.EquipmentStatus;
import com.ixilink.banknote_box.common.pojo.*;
import com.ixilink.banknote_box.common.util.EquipmentUtil;
import com.ixilink.banknote_box.common.util.OnlineTest;
import com.ixilink.banknote_box.common.util.RedisUtil;
import com.ixilink.banknote_box.common.util.ValidatePermissionsUtil;
import com.ixilink.banknote_box.service.parameter_model.equipment.AtmModel;
import com.ixilink.banknote_box.service.parameter_model.equipment.EquipmentCenterControllerModel;
import com.ixilink.banknote_box.service.parameter_model.equipment.EquipmentModel;
import com.ixilink.banknote_box.service.service.EquipmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * @description:
 * @author: 张俊
 * @date: 2019-11-18 20:27
 */

@Service
@Slf4j
public class EquipmentServiceImpl implements EquipmentService {

    @Resource
    private EquipmentMapper equipmentMapper;
    @Resource
    private EquipmentAtmMapper equipmentAtmMapper;
    @Resource
    private SystemSettingMapper systemSettingMapper;
    @Resource
    private EquipmentTypeMapper equipmentTypeMapper;
    @Resource
    private RedisUtil redisUtil;

    @Override
    @Transactional
    public void addReaderWriter(EquipmentModel param, HttpServletRequest request, HttpServletResponse response) {
        ValidatePermissionsUtil.validatePermission(request,redisUtil,14);
        User user = ValidatePermissionsUtil.getUser(request,redisUtil);
        addEquipment(param,1, user.getLibraryId());
    }

    @Override
    @Transactional
    public void removeReaderWriter(Integer id, HttpServletRequest request, HttpServletResponse response) {
        ValidatePermissionsUtil.validatePermission(request,redisUtil,16);
        User user = ValidatePermissionsUtil.getUser(request,redisUtil);
        removeEquipment(id,user.getLibraryId());
    }

    @Override
    public List<Equipment> getReaderWriter(EquipmentModel equipmentModel, HttpServletRequest request, HttpServletResponse response) {
        return findEquipment(equipmentModel,1,request);
    }

    @Override
    @Transactional
    public void modifyReaderWriter(EquipmentModel param, HttpServletRequest request, HttpServletResponse response) {
        ValidatePermissionsUtil.validatePermission(request,redisUtil,15);
        modifyEquipment(param,1);
    }

    @Override
    @Transactional
    public void addCenterControl(EquipmentCenterControllerModel param, HttpServletRequest request, HttpServletResponse response) {
        ValidatePermissionsUtil.validatePermission(request,redisUtil,14);
        EquipmentModel model = new EquipmentModel(param.getId(),param.getNumber(),param.getName(),param.getIp(),param.getStatus());
        User user = ValidatePermissionsUtil.getUser(request,redisUtil);
        addEquipment(model,2,user.getLibraryId());
        addAtm(param.getAtms(),model.getId(),user.getLibraryId());
    }

    @Override
    public List<Equipment> getCenterControl(EquipmentModel equipmentModel, HttpServletRequest request, HttpServletResponse response) {
        List<Equipment> equipments = findEquipment(equipmentModel, 2,request);
        List<Map> atmCount = equipmentAtmMapper.getAtmCount();
        equipments.forEach(equipment -> {
            atmCount.forEach(atmCountMap ->{
                if (atmCountMap.get("controllerId").equals(equipment.getId())){
                    equipment.setAtmCount(Integer.valueOf(atmCountMap.get("num").toString()));
                }
            });
        });
        return equipments;
    }

    @Override
    public void removeCenterControl(Integer id, HttpServletRequest request, HttpServletResponse response) {
        ValidatePermissionsUtil.validatePermission(request,redisUtil,16);
        User user = ValidatePermissionsUtil.getUser(request,redisUtil);
        removeEquipment(id,user.getLibraryId());
        int i = equipmentAtmMapper.removeAtmByEquipmentId(id);
        if (i < 1){
            throw new BusinessException(Code.MESSAGE_ERROR.getCode(),"删除失败");
        }
    }

    @Override
    public void modifyCenterControl(EquipmentCenterControllerModel param, HttpServletRequest request, HttpServletResponse response) {
        ValidatePermissionsUtil.validatePermission(request,redisUtil,15);
        EquipmentModel model = new EquipmentModel(param.getId(),param.getNumber(),param.getName(),param.getIp(),param.getStatus());
        modifyEquipment(model,2);
        EquipmentAtmExample equipmentAtmExample = new EquipmentAtmExample();
        equipmentAtmExample.createCriteria().andControllerIdEqualTo(param.getId()).andStateEqualTo(1);
        List<EquipmentAtm> equipmentAtms = equipmentAtmMapper.selectByExample(equipmentAtmExample);

        List<AtmModel> addAtms = new ArrayList<>();
        List<Integer> modifyAtmIds = new ArrayList<>();
        param.getAtms().forEach(a -> {
            if (a.getId() != null){
                modifyAtmIds.add(a.getId());
                equipmentAtms.forEach(ea -> {
                    if (ea.getId().equals(a.getId()))
                        if (!(ea.getChannel().equals(a.getChannel()) && ea.getNumber().equals(a.getNumber()) && ea.getName().equals(a.getName()) && ea.getPassageway().equals(a.getPassageway()))){
                            modifyAtm(a);
                        }
                });
            }else {
                addAtms.add(a);
            }
        });
        equipmentAtms.forEach(ea -> {if (!modifyAtmIds.contains(ea.getId())) equipmentAtmMapper.removeAtmById(ea.getId());});
        User user = ValidatePermissionsUtil.getUser(request,redisUtil);
        addAtm(addAtms,model.getId(),user.getLibraryId());
    }

    @Override
    public void addHandover(EquipmentModel param, HttpServletRequest request, HttpServletResponse response) {
        ValidatePermissionsUtil.validatePermission(request,redisUtil,14);
        User user = ValidatePermissionsUtil.getUser(request,redisUtil);
        addEquipment(param,3,user.getLibraryId());
    }

    @Override
    public List<Equipment> getHandover(EquipmentModel equipmentModel, HttpServletRequest request, HttpServletResponse response) {
        return findEquipment(equipmentModel,3,request);
    }

    @Override
    public void removeHandover(Integer id, HttpServletRequest request, HttpServletResponse response) {
        ValidatePermissionsUtil.validatePermission(request,redisUtil,16);
        User user = ValidatePermissionsUtil.getUser(request,redisUtil);
        removeEquipment(id,user.getLibraryId());
    }

    @Override
    public void modifyHandover(EquipmentModel param, HttpServletRequest request, HttpServletResponse response) {
        ValidatePermissionsUtil.validatePermission(request,redisUtil,15);
        modifyEquipment(param,3);
    }

    @Override
    public void addBoxing(EquipmentModel param, HttpServletRequest request, HttpServletResponse response) {
        ValidatePermissionsUtil.validatePermission(request,redisUtil,14);
        User user = ValidatePermissionsUtil.getUser(request,redisUtil);
        addEquipment(param,4,user.getLibraryId());
    }

    @Override
    public List<Equipment> getBoxing(EquipmentModel equipmentModel, HttpServletRequest request, HttpServletResponse response) {
        return findEquipment(equipmentModel,4,request);
    }

    @Override
    public void removeBoxing(Integer id, HttpServletRequest request, HttpServletResponse response) {
        ValidatePermissionsUtil.validatePermission(request,redisUtil,16);
        User user = ValidatePermissionsUtil.getUser(request,redisUtil);
        removeEquipment(id,user.getLibraryId());
    }

    @Override
    public void modifyBoxing(EquipmentModel param, HttpServletRequest request, HttpServletResponse response) {
        ValidatePermissionsUtil.validatePermission(request,redisUtil,15);
        modifyEquipment(param,4);
    }

    @Override
    public Map<String, Object> getCount(Integer type, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<>();
        String key = EquipmentUtil.getKey(type);
        User user = ValidatePermissionsUtil.getUser(request,redisUtil);
        Object sumCount1 = redisUtil.hget("sumCount", key + "-" + user.getLibraryId());
        Object onlineCount1 = redisUtil.hget("onlineCount", key+"-"+user.getLibraryId());
        Integer sumCount = sumCount1==null?0:(Integer)sumCount1;
        Integer onlineCount = onlineCount1==null?0:(Integer)onlineCount1;
        map.put("sumCount",sumCount);
        map.put("onlineCount",onlineCount);
        map.put("downCount",sumCount-onlineCount);
        return map;
    }

    @Override
    public Equipment getCenterControl(Integer id, HttpServletRequest request, HttpServletResponse response) {
        return equipmentMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<EquipmentAtm> getCenterControlAtms(Integer id, HttpServletRequest request, HttpServletResponse response) {
        User user = ValidatePermissionsUtil.getUser(request, redisUtil);
        EquipmentAtmExample equipmentAtmExample = new EquipmentAtmExample();
        equipmentAtmExample.createCriteria().andStateEqualTo(1).andControllerIdEqualTo(id).andLibraryIdEqualTo(user.getLibraryId());
        return equipmentAtmMapper.selectByExample(equipmentAtmExample);
    }

    @Override
    public Map<String, Object> getDeviceId(Integer type, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> result = new HashMap<>();
        User user = ValidatePermissionsUtil.getUser(request, redisUtil);
        SystemSetting systemSetting = systemSettingMapper.selectByPrimaryKey(user.getLibraryId());
        if (systemSetting == null) throw new BusinessException(Code.MESSAGE_ERROR.getCode(), "缺少系统配置");
        EquipmentExample equipmentExample = new EquipmentExample();
        equipmentExample.createCriteria().andStateEqualTo(1).andLibraryIdEqualTo(user.getLibraryId()).andIpEqualTo(type == 1 ? systemSetting.getAssignmentsAmeraIp() : systemSetting.getHandoverAmeraIp());
        List<Equipment> equipment = equipmentMapper.selectByExample(equipmentExample);
        if (equipment.size()==0) {
            throw new BusinessException(Code.MESSAGE_ERROR.getCode(), "没有设备，请先录入");
        }
        result.put("DeviceId",equipment.get(0).getNumber());
        return result;
    }


    /**添加设备*/
    private void addEquipment(EquipmentModel param,Integer type, Integer libraryId) {
        validateHas(type,param);
        Equipment equipment = new Equipment(null,type,param.getNumber(),param.getName(),param.getIp(),null, libraryId);
        int i = equipmentMapper.insertSelective(equipment);
        if (i < 1){
            throw new BusinessException(Code.MESSAGE_ERROR.getCode(),"添加失败");
        }
        param.setId(equipment.getId());
        Integer sumCount = 0;
        String key = EquipmentUtil.getKey(equipment.getEquipmentType());
        if (redisUtil.hHasKey("sumCount", key+"-"+libraryId)){
            sumCount = (Integer)redisUtil.hget("sumCount", key+"-"+libraryId);
        }
        sumCount++;
        redisUtil.hset("sumCount", key+"-"+libraryId,sumCount);
        EquipmentStatus equipmentStatus = new EquipmentStatus(param.getIp(),type,libraryId,1);
        redisUtil.hset("equipmentStatus", equipment.getId().toString(),equipmentStatus);
    }
    /**删除设备*/
    @SuppressWarnings("unchecked")
    private void removeEquipment(Integer id, Integer libraryId) {
        Equipment equipment = new Equipment(id,null,null,null,null,2,null);
        int i = equipmentMapper.updateByPrimaryKeySelective(equipment);
        if (i < 1){
            throw new BusinessException(Code.MESSAGE_ERROR.getCode(),"删除失败");
        }
        Integer type = 0;
        if (redisUtil.hHasKey("equipmentStatus", id.toString())){
            EquipmentStatus equipmentStatus = (EquipmentStatus)redisUtil.hget("equipmentStatus", id.toString());
            type = equipmentStatus.getEquipmentType();
        }
        Integer sumCount = 0;
        String key = EquipmentUtil.getKey(type);
        if (redisUtil.hHasKey("sumCount", key+"-"+libraryId)){
            sumCount = (Integer)redisUtil.hget("sumCount", key+"-"+libraryId);
        }
        if (sumCount>0)sumCount--;
        redisUtil.hset("sumCount", key+"-"+libraryId,sumCount);

        Integer onlineCount = 0;
        if (redisUtil.hHasKey("onlineCount", key+"-"+libraryId)){
            onlineCount = (Integer)redisUtil.hget("onlineCount", key+"-"+libraryId);
            List<Integer> onlineList = (ArrayList)redisUtil.hget("onlineList", key + "-" + libraryId);
            if (onlineList.contains(id)){
                onlineList.remove(id);
                onlineCount--;
                redisUtil.hset("onlineList", key+"-"+libraryId,onlineList);
            }else {
                List<Integer> downList = (ArrayList)redisUtil.hget("onlineList", key + "-" + libraryId);
                downList.remove(id);
                redisUtil.hset("downList", key+"-"+libraryId,downList);
            }
        }
        redisUtil.hset("onlineCount", key+"-"+libraryId,onlineCount);
    }
    /**查询设备*/
    @SuppressWarnings("unchecked")
    private List<Equipment> findEquipment(EquipmentModel equipmentModel, Integer type, HttpServletRequest request) {
        EquipmentExample equipmentExample = new EquipmentExample();
        EquipmentExample.Criteria criteria = equipmentExample.createCriteria();
        criteria.andEquipmentTypeEqualTo(type).andStateEqualTo(1);
        if (equipmentModel.getNumber() != null && !"".equals(equipmentModel.getNumber())) criteria.andNumberLike("%"+equipmentModel.getNumber()+"%");
        if (equipmentModel.getName() != null && !"".equals(equipmentModel.getName())) criteria.andNumberLike("%"+equipmentModel.getName()+"%");
        if (equipmentModel.getIp() != null && !"".equals(equipmentModel.getIp())) criteria.andNumberLike("%"+equipmentModel.getIp()+"%");
        User user = ValidatePermissionsUtil.getUser(request,redisUtil);
        if (user.getLibraryId() != null) criteria.andLibraryIdEqualTo(user.getLibraryId());
        if (equipmentModel.getStatus() != null ){
            List<Integer> ids = new ArrayList<>();
            String key = EquipmentUtil.getKey(type);
            if (user.getLibraryId() != null){
                if (equipmentModel.getStatus()==1){
                    Map<Object, Object> onlineList = redisUtil.hmget("onlineList");
                    Collection<Object> values = onlineList.values();
                    for (Object o:values){
                        ArrayList<Integer> o1 = (ArrayList<Integer>) o;
                        ids.addAll(o1);
                    }
                }else if (equipmentModel.getStatus()==2) {
                    Map<Object, Object> downList = redisUtil.hmget("downList");
                    Collection<Object> values = downList.values();
                    for (Object o:values){
                        ArrayList<Integer> o1 = (ArrayList<Integer>) o;
                        ids.addAll(o1);
                    }
                }
            }else {
                if (equipmentModel.getStatus()==1){
                    ids = (ArrayList<Integer>)redisUtil.hget("onlineList", key+"-"+user.getLibraryId());
                }else if (equipmentModel.getStatus()==2) {
                    ids = (ArrayList<Integer>) redisUtil.hget("downList", key+"-"+user.getLibraryId());
                }
            }
            ids.add(0);
            criteria.andIdIn(ids);
        }
        equipmentExample.setOrderByClause("id DESC");
        List<Equipment> equipments = equipmentMapper.selectByExample(equipmentExample);
        if (equipmentModel.getStatus() == null ){
            equipments.forEach(e -> {
                EquipmentStatus equipmentStatus = (EquipmentStatus)redisUtil.hget("equipmentStatus", e.getId().toString());
                e.setStatus(equipmentStatus==null?2:equipmentStatus.getStatus());
            });
        }else {
            equipments.forEach(e -> {
                e.setStatus(equipmentModel.getStatus());
            });
        }
        return equipments;
    }
    /**修改设备*/
    private void modifyEquipment(EquipmentModel equipmentModel, Integer type) {
        validateHas(type,equipmentModel);
        Equipment equipment = new Equipment(equipmentModel.getId(),null,equipmentModel.getNumber(),equipmentModel.getName(),equipmentModel.getIp(),null,null);
        int i = equipmentMapper.updateByPrimaryKeySelective(equipment);
        if (i < 1){
            throw new BusinessException(Code.MESSAGE_ERROR.getCode(),"修改失败");
        }
        EquipmentStatus equipmentStatus = (EquipmentStatus)redisUtil.hget("equipmentStatus", equipmentModel.getId().toString());
        if (equipmentStatus!=null) {
            equipmentStatus.setIp(equipmentModel.getIp());
            redisUtil.hset("equipmentStatus", equipmentModel.getId().toString(), equipmentStatus);
        }
    }
    /**添加ATM机*/
    private void addAtm(List<AtmModel> atms, Integer equipmentId, Integer libraryId) {
        if (atms.size() > 7){
            throw new BusinessException(Code.MESSAGE_ERROR.getCode(),"超出数量");
        }
        if (atms.size()<1) return;
        List<EquipmentAtm> list = new ArrayList<>();
        atms.forEach(atm -> {
            EquipmentAtm equipmentAtm = new EquipmentAtm(null,equipmentId,atm.getNumber(),atm.getName(),atm.getChannel(),atm.getPassageway(),null,libraryId);
            //计算钞箱类型
            String name = atm.getName();
            int startIndex = (!name.contains("("))?name.indexOf("（"):name.indexOf("(");
            int endIndex = (!name.contains(")"))?name.indexOf("）"):name.indexOf(")");
            String boxType = name.substring(startIndex+1,endIndex);
            switch (boxType.trim()){
                case "黑":
                    equipmentAtm.setBoxType(1);
                    break;
                case "DB":
                    equipmentAtm.setBoxType(1);
                    break;
                case "DF":
                    equipmentAtm.setBoxType(2);
                    break;
                case "CRS":
                    equipmentAtm.setBoxType(3);
                    break;
                case "SCRS":
                    equipmentAtm.setBoxType(4);
                    break;
                default:{
                    log.error("未知钞箱种类："+name);
                    throw new BusinessException(Code.MESSAGE_ERROR.getCode(), "未知ATM设备钞箱种类"+name);
                }
            }
            list.add(equipmentAtm);
        });
        int count = equipmentAtmMapper.insertList(list);
        if (count < atms.size()){
            throw new BusinessException(Code.MESSAGE_ERROR.getCode(),"添加失败");
        }
    }
    /**修改ATM机*/
    private void modifyAtm(AtmModel atm) {
        EquipmentAtm equipmentAtm = new EquipmentAtm(atm.getId(),null,atm.getNumber(),atm.getName(),atm.getChannel(),atm.getPassageway(),null,null);
        equipmentAtmMapper.updateByPrimaryKeySelective(equipmentAtm);
    }
    /**验证设备是否存在*/
    private void validateHas(Integer type,EquipmentModel param){
        boolean pass = OnlineTest.isIp(param.getIp());
        if (!pass) {
            throw new BusinessException(Code.MESSAGE_ERROR.getCode(),"请输入规范的ip地址");
        }
        EquipmentExample equipmentExample = new EquipmentExample();
        equipmentExample.createCriteria().andEquipmentTypeEqualTo(type).andNumberEqualTo(param.getNumber()).andStateEqualTo(1).andIdNotEqualTo(param.getId());
        long count = equipmentMapper.countByExample(equipmentExample);
        if (count > 0){
            throw new BusinessException(Code.MESSAGE_ERROR.getCode(),"该设备编号已存在");
        }
        equipmentExample.clear();
        equipmentExample.createCriteria().andEquipmentTypeEqualTo(type).andNameEqualTo(param.getName()).andStateEqualTo(1).andIdNotEqualTo(param.getId());
        count = equipmentMapper.countByExample(equipmentExample);
        if (count > 0){
            throw new BusinessException(Code.MESSAGE_ERROR.getCode(),"该设备名称已存在");
        }
        equipmentExample.clear();
        equipmentExample.createCriteria().andIpEqualTo(param.getIp()).andStateEqualTo(1).andIdNotEqualTo(param.getId());
        count = equipmentMapper.countByExample(equipmentExample);
        if (count > 0){
            throw new BusinessException(Code.MESSAGE_ERROR.getCode(),"该设备IP已存在");
        }
    }

}
