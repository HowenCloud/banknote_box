package com.ixilink.banknote_box.common.dao;

import com.ixilink.banknote_box.common.pojo.InLibraryTask;
import com.ixilink.banknote_box.common.pojo.InLibraryTaskExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface InLibraryTaskMapper {
    long countByExample(InLibraryTaskExample example);

    int deleteByExample(InLibraryTaskExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(InLibraryTask record);

    int insertSelective(InLibraryTask record);

    List<InLibraryTask> selectByExample(InLibraryTaskExample example);

    InLibraryTask selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") InLibraryTask record, @Param("example") InLibraryTaskExample example);

    int updateByExample(@Param("record") InLibraryTask record, @Param("example") InLibraryTaskExample example);

    int updateByPrimaryKeySelective(InLibraryTask record);

    int updateByPrimaryKey(InLibraryTask record);
}