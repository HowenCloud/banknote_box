package com.ixilink.banknote_box.common.dao;

import com.ixilink.banknote_box.common.model.UserSimple;
import com.ixilink.banknote_box.common.pojo.BanknoteLine;
import com.ixilink.banknote_box.common.pojo.BanknoteLineExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BanknoteLineMapper {
    long countByExample(BanknoteLineExample example);

    int deleteByExample(BanknoteLineExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BanknoteLine record);

    int insertSelective(BanknoteLine record);

    List<BanknoteLine> selectByExample(BanknoteLineExample example);

    BanknoteLine selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BanknoteLine record, @Param("example") BanknoteLineExample example);

    int updateByExample(@Param("record") BanknoteLine record, @Param("example") BanknoteLineExample example);

    int updateByPrimaryKeySelective(BanknoteLine record);

    int updateByPrimaryKey(BanknoteLine record);

    List<BanknoteLine> find(@Param("libraryId")Integer libraryId,@Param("username")String username);

    List<UserSimple> getUserByLineName(@Param("libraryId")Integer libraryId, @Param("lineName")String lineName);
}