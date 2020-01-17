package com.ixilink.banknote_box.common.dao;

import com.ixilink.banknote_box.common.pojo.RolePage;
import com.ixilink.banknote_box.common.pojo.RolePageExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RolePageMapper {
    long countByExample(RolePageExample example);

    int deleteByExample(RolePageExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(RolePage record);

    int insertSelective(RolePage record);

    List<RolePage> selectByExample(RolePageExample example);

    RolePage selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") RolePage record, @Param("example") RolePageExample example);

    int updateByExample(@Param("record") RolePage record, @Param("example") RolePageExample example);

    int updateByPrimaryKeySelective(RolePage record);

    int updateByPrimaryKey(RolePage record);

    int insertList(@Param("list") List<RolePage> list);
}