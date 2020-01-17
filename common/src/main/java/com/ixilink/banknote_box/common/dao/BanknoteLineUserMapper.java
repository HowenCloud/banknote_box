package com.ixilink.banknote_box.common.dao;

import com.ixilink.banknote_box.common.pojo.BanknoteLineUser;
import com.ixilink.banknote_box.common.pojo.BanknoteLineUserExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BanknoteLineUserMapper {
    long countByExample(BanknoteLineUserExample example);

    int deleteByExample(BanknoteLineUserExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BanknoteLineUser record);

    int insertSelective(BanknoteLineUser record);

    List<BanknoteLineUser> selectByExample(BanknoteLineUserExample example);

    BanknoteLineUser selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BanknoteLineUser record, @Param("example") BanknoteLineUserExample example);

    int updateByExample(@Param("record") BanknoteLineUser record, @Param("example") BanknoteLineUserExample example);

    int updateByPrimaryKeySelective(BanknoteLineUser record);

    int updateByPrimaryKey(BanknoteLineUser record);
}