package com.ixilink.banknote_box.common.dao;

import com.ixilink.banknote_box.common.pojo.EquipmentAtm;
import com.ixilink.banknote_box.common.pojo.EquipmentAtmExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

public interface EquipmentAtmMapper {
    long countByExample(EquipmentAtmExample example);

    int deleteByExample(EquipmentAtmExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(EquipmentAtm record);

    int insertSelective(EquipmentAtm record);

    List<EquipmentAtm> selectByExample(EquipmentAtmExample example);

    EquipmentAtm selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") EquipmentAtm record, @Param("example") EquipmentAtmExample example);

    int updateByExample(@Param("record") EquipmentAtm record, @Param("example") EquipmentAtmExample example);

    int updateByPrimaryKeySelective(EquipmentAtm record);

    int updateByPrimaryKey(EquipmentAtm record);

    int insertList(@Param("list") List<EquipmentAtm> list);

    @Select("SELECT controller_id controllerId,COUNT(*)num FROM equipment_atm WHERE state=1 GROUP BY controller_id ;")
    List<Map> getAtmCount();

    @Update("UPDATE equipment_atm SET state = 2 WHERE controller_id = #{equipmentId};")
    int removeAtmByEquipmentId(Integer equipmentId);

    @Update("UPDATE equipment_atm SET state = 2 WHERE id = #{id};")
    void removeAtmById(Integer id);

    List<EquipmentAtm> getAtm(@Param("bankBranch") String bankBranch, @Param("number") String number );

    Map<String,Object> getBankBranchAndAtmId(@Param("number") String number, @Param("channel") String channel);

    /**更新ATM钞箱*/
    @Update("UPDATE equipment_atm SET ${boxName} = #{boxId} WHERE controller_id = #{controllerId} and passageway = #{passageway} and state=1;")
    void changeAtmBox(@Param("boxName")String boxName,@Param("boxId")Integer boxId,@Param("controllerId")Integer controllerId,@Param("passageway")Integer passageway);

    /**根据钞箱无缘rfid查询钞箱信息*/
    @Select("SELECT id,serial_number number,box_type boxType,site,box_use_state boxUseState,box_transport_state boxTransportState,active_rfid activeRfid,passive_rfid passiveRfid,create_time createTime,state," +
            "library_id libraryId,car_money money FROM `car_box` WHERE passive_rfid=#{passiveRfid} and box_use_state<7")
    Map<String,Object> findInfoByPassiveRfid(@Param("passiveRfid")String passiveRfid);

    /**根据钞箱ID主键查询钞箱信息*/
    @Select("SELECT id,serial_number number,box_type boxType,site,box_use_state boxUseState,box_transport_state boxTransportState,active_rfid activeRfid,passive_rfid passiveRfid,create_time createTime,state," +
            "library_id libraryId,car_money money FROM `car_box` WHERE id=#{id}")
    Map<String,Object> findInfoByKey(@Param("id")Integer id);

    /**根据钞箱ID主键查询钞箱信息*/
    @Select({ "<script>"
            +"SELECT id,serial_number number,box_type boxType,site,box_use_state boxUseState,box_transport_state boxTransportState,active_rfid activeRfid,passive_rfid passiveRfid,create_time createTime,state,"
            +"library_id libraryId,car_money money FROM `car_box` WHERE id IN "
            + "<foreach item='item' index='index' collection='ids' open='(' separator=',' close=')'> "
            + "#{item}"
            + "</foreach> "
            + "</script> " })
    List<Map<String,Object>> findInfoInKeys(@Param("ids")List<Integer> ids);
}