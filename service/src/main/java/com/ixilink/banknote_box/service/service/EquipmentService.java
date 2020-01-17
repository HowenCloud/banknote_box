package com.ixilink.banknote_box.service.service;

import com.ixilink.banknote_box.common.pojo.Equipment;
import com.ixilink.banknote_box.common.pojo.EquipmentAtm;
import com.ixilink.banknote_box.service.parameter_model.equipment.EquipmentCenterControllerModel;
import com.ixilink.banknote_box.service.parameter_model.equipment.EquipmentModel;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: 张俊
 * @date: 2019-11-18 20:27
 */
public interface EquipmentService {

    void addReaderWriter(EquipmentModel param, HttpServletRequest request, HttpServletResponse response);

    void removeReaderWriter(Integer id, HttpServletRequest request, HttpServletResponse response);

    List<Equipment> getReaderWriter(EquipmentModel equipmentModel, HttpServletRequest request, HttpServletResponse response);

    void modifyReaderWriter(EquipmentModel param, HttpServletRequest request, HttpServletResponse response);

    void addCenterControl(EquipmentCenterControllerModel param, HttpServletRequest request, HttpServletResponse response);

    List<Equipment> getCenterControl(EquipmentModel equipmentModel, HttpServletRequest request, HttpServletResponse response);

    void removeCenterControl(Integer id, HttpServletRequest request, HttpServletResponse response);

    void modifyCenterControl(EquipmentCenterControllerModel param, HttpServletRequest request, HttpServletResponse response);

    void addHandover(EquipmentModel param, HttpServletRequest request, HttpServletResponse response);

    List<Equipment> getHandover(EquipmentModel equipmentModel, HttpServletRequest request, HttpServletResponse response);

    void removeHandover(Integer id, HttpServletRequest request, HttpServletResponse response);

    void modifyHandover(EquipmentModel param, HttpServletRequest request, HttpServletResponse response);

    void addBoxing(EquipmentModel param, HttpServletRequest request, HttpServletResponse response);

    List<Equipment> getBoxing(EquipmentModel equipmentModel, HttpServletRequest request, HttpServletResponse response);

    void removeBoxing(Integer id, HttpServletRequest request, HttpServletResponse response);

    void modifyBoxing(EquipmentModel param, HttpServletRequest request, HttpServletResponse response);

    Map<String,Object> getCount(Integer type, HttpServletRequest request, HttpServletResponse response);

    Equipment getCenterControl(Integer id, HttpServletRequest request, HttpServletResponse response);

    List<EquipmentAtm> getCenterControlAtms(Integer id, HttpServletRequest request, HttpServletResponse response);

    Map<String,Object> getDeviceId(Integer type, HttpServletRequest request, HttpServletResponse response);
}
