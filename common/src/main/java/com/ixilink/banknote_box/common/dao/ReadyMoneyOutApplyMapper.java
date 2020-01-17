package com.ixilink.banknote_box.common.dao;

import com.ixilink.banknote_box.common.pojo.ReadyMoneyOutApply;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

public interface ReadyMoneyOutApplyMapper {

    int insertSelective(ReadyMoneyOutApply record);

    @Select("SELECT id,(SELECT `name` FROM USER WHERE id=grouper_id)grouper,state,total_number totalNumber,create_time createTime FROM `ready_money_out_apply` where library_id =#{libraryId}")
    List<Map<String,Object>> selectApplyOut(@Param("libraryId") Integer libraryId);

    @Select("SELECT  normal_result normalResult,total_number totalNumber FROM `ready_money_out_apply` WHERE id =#{id}")
    Map<String,Object> selectApplyTarget(@Param("id") Integer id);

    @Update(" update `ready_money_out_apply` set inventory_result=#{inventoryResult},supplement_result=null where id=#{id};")
    int updateTaskInventory(@Param("inventoryResult")String inventoryResult,@Param("id")Integer id);

    @Update("update `ready_money_out_apply` set supplement_result=#{supplementResult} where id=#{id};")
    int updateTaskSupplement(@Param("supplementResult")String supplementResult,@Param("id")Integer id);

    @Select("select grouper_id grouperId,libraryer_id libraryerId from `ready_money_out_apply` where id =#{id} and  library_id =#{libraryId}")
    Map<String,Object> selectFaceOutGrouperLibraryer(@Param("libraryId") Integer libraryId,@Param("id") Integer id);
    @Update("UPDATE `ready_money_out_apply` SET approval_people=#{approvalPeople},state=2 where id=#{id};")
    int outHandover(@Param("approvalPeople")String approvalPeople,@Param("id")Integer id);

    @Select("SELECT grouper_id grouperId,inventory_result inventoryResult,supplement_result supplementResult FROM `ready_money_out_apply`  WHERE id=#{id}")
    Map<String,Object> selectOutInventorySupplement(Integer id);
    @Update({"<script> "
            + "UPDATE `ready_money_libraryer` SET state=2,grouper_id=#{grouperId_},in_library_time=#{inLibraryTime} where details_id in  "
            + "<foreach collection='list' item='id' index='index' open='(' separator=',' close=')'>"
            + " #{id}"
            + "</foreach>"
            + "</script>"})
    int updateOutLibrary(@Param("grouperId_")Integer grouperId_,@Param("inLibraryTime")long inLibraryTime,@Param("list")List list);

}