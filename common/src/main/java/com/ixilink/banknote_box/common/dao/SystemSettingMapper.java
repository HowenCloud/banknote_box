package com.ixilink.banknote_box.common.dao;

import com.ixilink.banknote_box.common.pojo.SystemSetting;
import com.ixilink.banknote_box.common.pojo.SystemSettingExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SystemSettingMapper {
    long countByExample(SystemSettingExample example);

    int deleteByExample(SystemSettingExample example);

    int deleteByPrimaryKey(Integer libraryId);

    int insert(SystemSetting record);

    int insertSelective(SystemSetting record);

    List<SystemSetting> selectByExample(SystemSettingExample example);

    SystemSetting selectByPrimaryKey(Integer libraryId);

    int updateByExampleSelective(@Param("record") SystemSetting record, @Param("example") SystemSettingExample example);

    int updateByExample(@Param("record") SystemSetting record, @Param("example") SystemSettingExample example);

    int updateByPrimaryKeySelective(SystemSetting record);

    int updateByPrimaryKey(SystemSetting record);
}