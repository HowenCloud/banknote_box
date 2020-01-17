package com.ixilink.banknote_box.common.dao;

import com.ixilink.banknote_box.common.pojo.OutLibraryLineBoxingUser;
import com.ixilink.banknote_box.common.pojo.OutLibraryLineBoxingUserExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OutLibraryLineBoxingUserMapper {
    long countByExample(OutLibraryLineBoxingUserExample example);

    int deleteByExample(OutLibraryLineBoxingUserExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(OutLibraryLineBoxingUser record);

    int insertSelective(OutLibraryLineBoxingUser record);

    List<OutLibraryLineBoxingUser> selectByExample(OutLibraryLineBoxingUserExample example);

    OutLibraryLineBoxingUser selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") OutLibraryLineBoxingUser record, @Param("example") OutLibraryLineBoxingUserExample example);

    int updateByExample(@Param("record") OutLibraryLineBoxingUser record, @Param("example") OutLibraryLineBoxingUserExample example);

    int updateByPrimaryKeySelective(OutLibraryLineBoxingUser record);

    int updateByPrimaryKey(OutLibraryLineBoxingUser record);
}