package com.ixilink.banknote_box.common.dao;
import com.ixilink.banknote_box.common.pojo.CarBox;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface CarBoxMapper {
    @Insert("INSERT INTO `car_box` (`serial_number`,`box_type`,`active_rfid`,`passive_rfid`,create_time,`library_id`) \n"
            + "VALUES(#{serialNumber},#{boxType},#{activeRfid},#{passiveRfid},#{createTime},#{libraryId})")
    int insert(CarBox record);

    @Select({ "<script>"
            + "\n"
            + " SELECT `id`,`serial_number` serialNumber ,(SELECT NAME FROM `car_box_type` WHERE id=`box_type`) boxType, \n"
            + "             `site`,(CASE `box_use_state` WHEN 1 THEN '空闲' \n"
            + "             WHEN 2 THEN '待使用' \n"
            + "             WHEN 3 THEN '待交接' \n"
            + "             WHEN 4 THEN '待加钞' \n"
            + "             WHEN 5 THEN '已加钞' \n"
            + "             WHEN 6 THEN '待清机' END)boxUseState,IF(`box_transport_state`=1,'正常','低电量') boxTransportState, \n"
            + "             `active_rfid` activeRfid,`passive_rfid` passiveRfid,`create_time` createTime ,box_use_state boxUseStateId,box_transport_state boxTransportStateId,box_type  boxTypeId \n"
            + "             FROM `car_box`   \n"
            + "             WHERE box_use_state IN(1,2,3,4,5,6)  "
            + " AND library_id=#{libraryId}"
            + "<when test='serialNumber !=null and serialNumber != \"\"'>"
            + "  AND LOCATE(#{serialNumber},serial_number)>0"
            + "</when>"
            + "<when test='boxType !=null and  boxType != \"\"'>"
            + " AND box_type=#{boxType}"
            + "</when>"
            + "<when test='boxUseState !=null and  boxUseState != \"\"'>"
            + " AND box_use_state=#{boxUseState}"
            + "</when>"
            + "<when test='boxTransportState !=null and  boxTransportState != \"\"'>"
            + " AND box_transport_state=#{boxTransportState}"
            + "</when>"
            + " ORDER BY create_time "
            + "</script>" })
    List<Map<String,Object>> select(Map<String, Object> record);
    @Update("UPDATE  `car_box` SET `serial_number` = #{serialNumber},`box_type` = #{boxType},`active_rfid` = #{activeRfid},`passive_rfid` = #{passiveRfid}\n"
            + "WHERE `id` = #{id};")//id activeRfid passiveRfid boxType serialNumber
    int updateByPrimaryKey(CarBox record);
    @Delete("UPDATE `car_box` SET box_use_state=7 WHERE id=#{id}")
    int deleteByPrimaryKey(Integer id);
    //根据id查询钞箱的使用状态
    @Select("SELECT  box_use_state FROM `car_box` WHERE id=#{id}")
    int selectboxUseState(Integer id);
    @Select("SELECT * FROM `car_box_type`")
    List<Map<String,Object>> selectCarBoxType();
    @Select("CALL car_box_num(#{libraryId})")
    Map<String,Object> selectCaxBoxNums(Integer libraryId);
    @Update("UPDATE `car_box` set  box_use_state =2,car_money=#{carMoney} where serial_number=#{carBoxNumber} and library_id=#{libraryId};")
    int updateStateByOperator(@Param("carMoney")Integer carMoney,@Param("carBoxNumber")String carBoxNumber,@Param("libraryId")Integer libraryId);
    @Select("SELECT box_use_state FROM `car_box` WHERE serial_number=#{carBoxNumber} and library_id=#{libraryId}")
    Integer selectStateByOperator(@Param("carBoxNumber")String carBoxNumber,@Param("libraryId")Integer libraryId);

    /**修改钞箱位置*/
    @Update("UPDATE `car_box` set  site = #{site} where id=#{id};")
    int modifySite(@Param("site")String site,@Param("id")Integer id);
}