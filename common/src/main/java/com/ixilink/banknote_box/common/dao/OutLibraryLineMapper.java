package com.ixilink.banknote_box.common.dao;

import com.ixilink.banknote_box.common.pojo.OutLibraryLine;
import com.ixilink.banknote_box.common.pojo.OutLibraryLineExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface OutLibraryLineMapper {
    long countByExample(OutLibraryLineExample example);

    int deleteByExample(OutLibraryLineExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(OutLibraryLine record);

    int insertSelective(OutLibraryLine record);

    List<OutLibraryLine> selectByExampleWithBLOBs(OutLibraryLineExample example);

    List<OutLibraryLine> selectByExample(OutLibraryLineExample example);

    OutLibraryLine selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") OutLibraryLine record, @Param("example") OutLibraryLineExample example);

    int updateByExampleWithBLOBs(@Param("record") OutLibraryLine record, @Param("example") OutLibraryLineExample example);

    int updateByExample(@Param("record") OutLibraryLine record, @Param("example") OutLibraryLineExample example);

    int updateByPrimaryKeySelective(OutLibraryLine record);

    int updateByPrimaryKeyWithBLOBs(OutLibraryLine record);

    int updateByPrimaryKey(OutLibraryLine record);

    /**查询线路*/
    /*List<Map<String,Object>> findLine(@Param("libraryId")Integer libraryId, @Param("taskId")Integer taskId, @Param("username")String username);*/
    List<Map<String,Object>> findLine(@Param("libraryId")Integer libraryId, @Param("taskId")Integer taskId, @Param("users")List<Integer> users);

    /**获取钞箱编号集合 json字符串*/
    String getBoxNumber(@Param("libraryId")Integer libraryId, @Param("rfids")List<String> rfids);

    /**读写器盘点钞箱*/
    List<Map<String,Object>> inventory(@Param("libraryId")Integer libraryId, @Param("boxNumbers")List<String> boxNumbers);
    List<Map<String,Object>> inventoryByIds( @Param("ids")List<Integer> ids);

    /**是否存在钞箱*/
    @Select("SELECT COUNT(*) FROM car_box WHERE box_use_state < 7 AND library_id=#{libraryId} AND serial_number = #{number};")
    int hasBoxByNumber(@Param("libraryId")Integer libraryId, @Param("number")String number);

    /**钞箱查询*/
    @Select("SELECT car_box.`id`,car_box.`serial_number` number,car_box.`box_use_state` state,car_box.`box_type` boxType,car_box_type.`name` FROM car_box LEFT JOIN car_box_type ON car_box_type.`id`=car_box.`box_type` WHERE box_use_state <7 AND library_id=#{libraryId} AND serial_number = #{number} LIMIT 1;")
    Map<String,Object> supplementBox(@Param("libraryId")Integer libraryId, @Param("number")String number);

    /**线路钞箱详情*/
    List<Map<String,Object>> details(@Param("libraryId")Integer libraryId, @Param("boxNumbers")List<String> boxNumbers);

    /**根据加钞员id 查询今日最新待完成总任务id，加钞线路任务id*/
    Map<String,Integer> getNewTaskIdAndLineIdByUserId(@Param("libraryId")Integer libraryId, @Param("userId")Integer userId, @Param("startTime")Long startTime, @Param("endTime")Long endTime );

    /**获取加钞网点列表*/
    List<Map<String,Object>> lineDetails(@Param("lineId")Integer lineId);

    /**根据钞箱编号集合查询钞箱类型及金额（加钞钞箱）*/
    List<Map<String,Object>> depositBox(@Param("libraryId")Integer libraryId, @Param("boxNumbers")List<String> boxNumbers);

    /**查询线路下的加钞人姓名*/
    @Select("SELECT IFNULL(GROUP_CONCAT(USER.`name` SEPARATOR \",\"),'无') FROM out_library_line_user ollu LEFT JOIN USER ON USER.`id` = ollu.`user_id` WHERE line_id = #{lineId}")
    String getAddMoneyUser(@Param("lineId")Integer lineId);

    /**操作岗查询配箱线路列表*/
    List<Map<String,Object>> findBoxingLine(@Param("taskId")Integer taskId);

    /**根据库id和线路识别状态查询任务id集合*/
    @Select("SELECT CONCAT('[',IFNULL(GROUP_CONCAT(DISTINCT task_id SEPARATOR \",\"),''),']') FROM out_library_line WHERE check_schedule<=#{checkSchedule} AND library_id = #{libraryId};")
    String getTaskIdsByCheckSchedule(@Param("libraryId")Integer libraryId, @Param("checkSchedule")Integer checkSchedule);

    /**根据库id和线路识别状态查询任务id集合*/
    @Select("SELECT CONCAT('[',IFNULL(GROUP_CONCAT(atm_id SEPARATOR ','),''),']') FROM out_library_line_atm WHERE line_id=#{lineId};")
    String getAtmIdsByLine(@Param("lineId")Integer lineId);
}