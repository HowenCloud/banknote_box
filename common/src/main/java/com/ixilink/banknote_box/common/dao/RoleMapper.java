package com.ixilink.banknote_box.common.dao;

import com.ixilink.banknote_box.common.pojo.Role;
import com.ixilink.banknote_box.common.pojo.RoleExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface RoleMapper {
    long countByExample(RoleExample example);

    int deleteByExample(RoleExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Role record);

    int insertSelective(Role record);

    List<Role> selectByExample(RoleExample example);

    Role selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Role record, @Param("example") RoleExample example);

    int updateByExample(@Param("record") Role record, @Param("example") RoleExample example);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);

    @Select("SELECT GROUP_CONCAT(id SEPARATOR \",\")ids FROM role WHERE deleting=2 ")
    String getNeedLogin();
}