package com.ixilink.banknote_box.common.dao;

import com.ixilink.banknote_box.common.pojo.InLibraryLine;
import com.ixilink.banknote_box.common.pojo.InLibraryLineExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface InLibraryLineMapper {
    long countByExample(InLibraryLineExample example);

    int deleteByExample(InLibraryLineExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(InLibraryLine record);

    int insertSelective(InLibraryLine record);

    List<InLibraryLine> selectByExampleWithBLOBs(InLibraryLineExample example);

    List<InLibraryLine> selectByExample(InLibraryLineExample example);

    InLibraryLine selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") InLibraryLine record, @Param("example") InLibraryLineExample example);

    int updateByExampleWithBLOBs(@Param("record") InLibraryLine record, @Param("example") InLibraryLineExample example);

    int updateByExample(@Param("record") InLibraryLine record, @Param("example") InLibraryLineExample example);

    int updateByPrimaryKeySelective(InLibraryLine record);

    int updateByPrimaryKeyWithBLOBs(InLibraryLine record);

    int updateByPrimaryKey(InLibraryLine record);

    @Select("SELECT IFNULL(SUM(total_box),0) FROM in_library_line WHERE task_id = #{taskId}")
    int getBoxCount(@Param("taskId")Integer taskId);

    /**查询线路*/
    List<Map<String,Object>> findLine(@Param("libraryId")Integer libraryId, @Param("taskId")Integer taskId, @Param("users")List<Integer> users);

    /**查询入库线路的ATM详情*/
    List<Map<String,Object>> findAtm(@Param("lineId")Integer lineId,@Param("number")String number);

    /**查询入库线路未加钞ATM详情*/
    List<Map<String,Object>> findNoDepositAtm(@Param("lineId")Integer lineId,@Param("number")String number);

    /**查询入库线路未加钞钞箱详情*/
    List<Map<String,Object>> findNoDepositBox(@Param("type")Integer type,@Param("number")String number,@Param("numbers")List<String> numbers);
}