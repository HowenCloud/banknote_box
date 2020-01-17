package com.ixilink.banknote_box.service.service;

import com.ixilink.banknote_box.common.pojo.EquipmentAtm;
import com.ixilink.banknote_box.service.parameter_model.equipment.FindATM;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @description:
 * @author: 张俊
 * @date: 2019-11-22 16:43
 */
public interface AtmService {

    List<EquipmentAtm> getAtm(FindATM param, HttpServletRequest request, HttpServletResponse response);

    List<EquipmentAtm> getAtmByEquipment(Integer controllerId, HttpServletRequest request, HttpServletResponse response);

    List<EquipmentAtm> getAtmByLine(Integer controllerId, HttpServletRequest request, HttpServletResponse response);
}
