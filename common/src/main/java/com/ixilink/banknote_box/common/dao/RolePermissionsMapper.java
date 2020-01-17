package com.ixilink.banknote_box.common.dao;

import com.ixilink.banknote_box.common.pojo.RolePermissions;
import com.ixilink.banknote_box.common.pojo.RolePermissionsExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RolePermissionsMapper {
    long countByExample(RolePermissionsExample example);

    int deleteByExample(RolePermissionsExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(RolePermissions record);

    int insertSelective(RolePermissions record);

    List<RolePermissions> selectByExample(RolePermissionsExample example);

    RolePermissions selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") RolePermissions record, @Param("example") RolePermissionsExample example);

    int updateByExample(@Param("record") RolePermissions record, @Param("example") RolePermissionsExample example);

    int updateByPrimaryKeySelective(RolePermissions record);

    int updateByPrimaryKey(RolePermissions record);

    int insertList(@Param("list") List<RolePermissions> list);
}