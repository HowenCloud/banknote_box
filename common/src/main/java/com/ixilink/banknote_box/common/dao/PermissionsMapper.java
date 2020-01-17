package com.ixilink.banknote_box.common.dao;

import com.ixilink.banknote_box.common.pojo.Permissions;
import com.ixilink.banknote_box.common.pojo.PermissionsExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PermissionsMapper {
    long countByExample(PermissionsExample example);

    int deleteByExample(PermissionsExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Permissions record);

    int insertSelective(Permissions record);

    List<Permissions> selectByExample(PermissionsExample example);

    Permissions selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Permissions record, @Param("example") PermissionsExample example);

    int updateByExample(@Param("record") Permissions record, @Param("example") PermissionsExample example);

    int updateByPrimaryKeySelective(Permissions record);

    int updateByPrimaryKey(Permissions record);
}