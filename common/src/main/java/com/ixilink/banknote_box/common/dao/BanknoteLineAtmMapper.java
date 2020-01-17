package com.ixilink.banknote_box.common.dao;

import com.ixilink.banknote_box.common.pojo.BanknoteLineAtm;
import com.ixilink.banknote_box.common.pojo.BanknoteLineAtmExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BanknoteLineAtmMapper {
    long countByExample(BanknoteLineAtmExample example);

    int deleteByExample(BanknoteLineAtmExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BanknoteLineAtm record);

    int insertSelective(BanknoteLineAtm record);

    List<BanknoteLineAtm> selectByExample(BanknoteLineAtmExample example);

    BanknoteLineAtm selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BanknoteLineAtm record, @Param("example") BanknoteLineAtmExample example);

    int updateByExample(@Param("record") BanknoteLineAtm record, @Param("example") BanknoteLineAtmExample example);

    int updateByPrimaryKeySelective(BanknoteLineAtm record);

    int updateByPrimaryKey(BanknoteLineAtm record);
}