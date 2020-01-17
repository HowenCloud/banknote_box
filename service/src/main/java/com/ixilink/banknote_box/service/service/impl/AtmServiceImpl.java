package com.ixilink.banknote_box.service.service.impl;

import com.ixilink.banknote_box.common.dao.EquipmentAtmMapper;
import com.ixilink.banknote_box.common.dao.EquipmentMapper;
import com.ixilink.banknote_box.common.pojo.EquipmentAtm;
import com.ixilink.banknote_box.common.pojo.EquipmentAtmExample;
import com.ixilink.banknote_box.service.parameter_model.equipment.EquipmentModel;
import com.ixilink.banknote_box.service.parameter_model.equipment.FindATM;
import com.ixilink.banknote_box.service.service.AtmService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @description:
 * @author: 张俊
 * @date: 2019-11-22 16:43
 */
@Service
public class AtmServiceImpl implements AtmService {

    @Resource
    private EquipmentMapper equipmentMapper;
    @Resource
    private EquipmentAtmMapper equipmentAtmMapper;

    @Override
    public List<EquipmentAtm> getAtm(FindATM param, HttpServletRequest request, HttpServletResponse response) {
        return equipmentAtmMapper.getAtm(param.getBankBranch(),param.getNumber());
    }

    @Override
    public List<EquipmentAtm> getAtmByEquipment(Integer controllerId, HttpServletRequest request, HttpServletResponse response) {
        EquipmentAtmExample equipmentAtmExample = new EquipmentAtmExample();
        equipmentAtmExample.createCriteria().andControllerIdEqualTo(controllerId).andStateEqualTo(1);
        return equipmentAtmMapper.selectByExample(equipmentAtmExample);
    }

    @Override
    public List<EquipmentAtm> getAtmByLine(Integer controllerId, HttpServletRequest request, HttpServletResponse response) {

        return null;
    }

}
