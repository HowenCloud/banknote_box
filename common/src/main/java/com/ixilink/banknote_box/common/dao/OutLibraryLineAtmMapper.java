package com.ixilink.banknote_box.common.dao;

import com.ixilink.banknote_box.common.pojo.OutLibraryLineAtm;
import com.ixilink.banknote_box.common.pojo.OutLibraryLineAtmExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface OutLibraryLineAtmMapper {
    long countByExample(OutLibraryLineAtmExample example);

    int deleteByExample(OutLibraryLineAtmExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(OutLibraryLineAtm record);

    int insertSelective(OutLibraryLineAtm record);

    List<OutLibraryLineAtm> selectByExample(OutLibraryLineAtmExample example);

    OutLibraryLineAtm selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") OutLibraryLineAtm record, @Param("example") OutLibraryLineAtmExample example);

    int updateByExample(@Param("record") OutLibraryLineAtm record, @Param("example") OutLibraryLineAtmExample example);

    int updateByPrimaryKeySelective(OutLibraryLineAtm record);

    int updateByPrimaryKey(OutLibraryLineAtm record);

    int insertList(@Param("list")List<OutLibraryLineAtm> list);

    List<Map<String,Object>> findLineAtm(@Param("lineId")Integer lineId, @Param("bankBranch")String bankBranch, @Param("number")String number);
}