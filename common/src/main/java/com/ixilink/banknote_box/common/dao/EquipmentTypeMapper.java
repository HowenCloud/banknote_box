package com.ixilink.banknote_box.common.dao;

import com.ixilink.banknote_box.common.pojo.EquipmentType;
import com.ixilink.banknote_box.common.pojo.EquipmentTypeExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EquipmentTypeMapper {
    long countByExample(EquipmentTypeExample example);

    int deleteByExample(EquipmentTypeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(EquipmentType record);

    int insertSelective(EquipmentType record);

    List<EquipmentType> selectByExample(EquipmentTypeExample example);

    EquipmentType selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") EquipmentType record, @Param("example") EquipmentTypeExample example);

    int updateByExample(@Param("record") EquipmentType record, @Param("example") EquipmentTypeExample example);

    int updateByPrimaryKeySelective(EquipmentType record);

    int updateByPrimaryKey(EquipmentType record);
}