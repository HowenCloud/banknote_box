package com.ixilink.banknote_box.common.dao;

import com.ixilink.banknote_box.common.pojo.PermissionsType;
import com.ixilink.banknote_box.common.pojo.PermissionsTypeExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PermissionsTypeMapper {
    long countByExample(PermissionsTypeExample example);

    int deleteByExample(PermissionsTypeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PermissionsType record);

    int insertSelective(PermissionsType record);

    List<PermissionsType> selectByExample(PermissionsTypeExample example);

    PermissionsType selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PermissionsType record, @Param("example") PermissionsTypeExample example);

    int updateByExample(@Param("record") PermissionsType record, @Param("example") PermissionsTypeExample example);

    int updateByPrimaryKeySelective(PermissionsType record);

    int updateByPrimaryKey(PermissionsType record);
}