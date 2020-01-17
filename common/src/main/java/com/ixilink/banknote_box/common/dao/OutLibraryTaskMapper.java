package com.ixilink.banknote_box.common.dao;

import com.ixilink.banknote_box.common.pojo.OutLibraryTask;
import com.ixilink.banknote_box.common.pojo.OutLibraryTaskExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OutLibraryTaskMapper {
    long countByExample(OutLibraryTaskExample example);

    int deleteByExample(OutLibraryTaskExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(OutLibraryTask record);

    int insertSelective(OutLibraryTask record);

    List<OutLibraryTask> selectByExample(OutLibraryTaskExample example);

    OutLibraryTask selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") OutLibraryTask record, @Param("example") OutLibraryTaskExample example);

    int updateByExample(@Param("record") OutLibraryTask record, @Param("example") OutLibraryTaskExample example);

    int updateByPrimaryKeySelective(OutLibraryTask record);

    int updateByPrimaryKey(OutLibraryTask record);
}