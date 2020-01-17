package com.ixilink.banknote_box.common.dao;

import com.ixilink.banknote_box.common.pojo.FundBagManagement;
import com.ixilink.banknote_box.common.pojo.FundBagManagementExcel;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

public interface FundBagManagementMapper {

    @Insert("INSERT INTO `bnoteboxmonitoe_development`.`fund_bag_management` (`installation_time`,`bagging_amount`,`bagging_personnel`,`state`,`bagging_lock_bar_number`,`fund_bag_name`,`bagging_amount_all`,`library_id`,`bagging_personnel_id`) \n"
            + "VALUES(#{installationTime},#{baggingAmount},#{baggingPersonnel},#{state},#{baggingLockBarNumber},#{fundBagName},#{baggingAmountAll},#{libraryId},#{baggingPersonnelId});")
    int insert(FundBagManagement record);

    @Select("SELECT IFNULL(MAX(number),0) FROM fund_bag_management WHERE FROM_UNIXTIME(installation_time/1000,\"%Y-%m-%d\")=FROM_UNIXTIME(#{date}/1000,\"%Y-%m-%d\") AND bagging_personnel=#{baggingPersonnel} AND  library_id =#{libraryId}")
    int selectNumber(Map<String,Object> map);

    @Select("SELECT \n"
            + "`id`,CONCAT(number,'-',FROM_UNIXTIME(installation_time/1000,\"%Y%m%d\"),'-',CONCAT(`bagging_amount`,'w'),'-',`bagging_personnel`),\n"
            + "`installation_time` installationTime,CONCAT(`bagging_amount`,\"万\") baggingAmount,`bagging_personnel` baggingPersonnel,`state` \n"
            + "FROM  `fund_bag_management` ;")
    List<FundBagManagement> selectByMap(Map map);

    @Select({ "<script>"
            + "SELECT id,installation_time installationTime,bagging_amount baggingAmount,bagging_personnel baggingPersonnel,\n"
            + "state state,fund_bag_name fundBagName,number number,bagging_amount_all baggingAmountAll,bagging_lock_bar_number baggingLockBarNumber,\n"
            + "library_id libraryId,bagging_personnel_id baggingPersonnelId\n"
            + "FROM `fund_bag_management` "
            + "WHERE 1=1 AND state &lt;&gt; 4  AND library_id=#{libraryId} "
            + "<when test='fundBagName!=null and fundBagName != \"\"'>"
            + "AND LOCATE(#{fundBagName},fund_bag_name)>0"
            + "</when>"
            + "<when test='baggingPersonnel!=null and  baggingPersonnel != \"\"'>"
            + "AND LOCATE(#{baggingPersonnel},bagging_personnel)>0"
            + "</when>"
            + "<when test='state!=null and  state != \"\"'>"
            + "AND state=#{state}"
            + "</when>"
            + "  ORDER BY installation_time"
            + "</script>" })
    List<Map<String,Object>> selectByParam(Map map);

    /*    +"<when test='baggingAmount !=null and  baggingAmount  != \"\"'>"
            + "  AND bagging_amount =#{baggingAmount}"
            + "</when>"*/

    @Update("UPDATE `fund_bag_management` SET "
            + "installation_time=#{installationTime},"
            + "bagging_amount=#{baggingAmount},"
            + "bagging_personnel=#{baggingPersonnel},"
            + "fund_bag_name=#{fundBagName},"
            + "bagging_amount_all=#{baggingAmount},"
            + "bagging_personnel_Id=#{baggingPersonnelId}, "
            + "bagging_lock_bar_number=#{baggingLockBarNumber} "
            + "WHERE id=#{id}")
    int updateFundBagById(Map<String,Object> map);

    @Update("UPDATE fund_bag_management SET state =4 WHERE id=#{id}")
    int deleteById(Integer id);

    @Select("SELECT state FROM `fund_bag_management` WHERE id=#{id}")
    int selectStateById(Integer id);

    @Select({ "<script>"
            + "SELECT  FROM_UNIXTIME(installation_time/1000,'%Y-%m-%d %T') installationTime,CONCAT(bagging_amount,'万') baggingAmount,bagging_personnel baggingPersonnel,\n"
            + "(CASE state \n"
            + "WHEN 1 THEN '未使用'\n"
            + "WHEN 2 THEN '已使用'\n"
            + "WHEN 3 THEN '使用中'\n"
            + "WHEN 4 THEN '删除'\n"
            + "END)state\n"
            + ",fund_bag_name fundBagName FROM `fund_bag_management`"
            + "WHERE 1=1 and state &lt;&gt; 4 and library_id=#{libraryId}"
            + "<when test='fundBagName!=null and fundBagName != \"\"'>"
            + " AND LOCATE(#{fundBagName},fund_bag_name)>0"
            + "</when>"
            + "<when test='baggingPersonnel!=null and  baggingPersonnel != \"\"'>"
            + "AND LOCATE(#{baggingPersonnel},bagging_personnel)>0"
            + "</when>"
            + "<when test='state!=null and  state != \"\"'>"
            + "AND state=#{state}"
            + "</when>"
            + "</script>" })
    List<FundBagManagementExcel> selectToExcel(Map map);
    /*装袋人员查询*/
    @Select("SELECT id,`name` FROM `user` WHERE role_id=5 AND state=1 AND  library_id=#{libraryId}")
    List<Map<String,Object>> selectBaggingPersonnel(@Param("libraryId") Integer libraryId);
    @Select("CALL fund_bag_management(#{libraryId});")
    Map<String,Object>   selectNumberAndAmount(@Param("libraryId") Integer libraryId);
    @Select("SELECT COUNT(1) FROM `fund_bag_management` WHERE library_id =#{libraryId} AND bagging_lock_bar_number=#{baggingLockBarNumber} AND state IN (1,3);")
    Integer selectBaggingLockBarNumber(@Param("baggingLockBarNumber") String baggingLockBarNumber,@Param("libraryId") Integer libraryId);
    @Select("SELECT bagging_lock_bar_number FROM`fund_bag_management` WHERE state IN(1,3) AND id=#{id}")
    String selectBaggingLockBarNumberById(@Param("id") Integer id);
    @Select("<script>"
            + "select `name` from `user`  "
            + "where id in "
            + " <foreach item='item' index='index' collection='baggingPersonnelId' open='(' separator=',' close=')'>"
            + "#{item}"
            + "</foreach>"
            + "</script>")
    List<String> selectNameById(@Param("baggingPersonnelId") List baggingPersonnelId);

    @Update("UPDATE `fund_bag_management` set  state =#{state},bagging_amount=bagging_amount-#{carMoney} where id=#{fundId};")
    int updateMoneyByOperator(@Param("carMoney")Integer carMoney,@Param("fundId")Integer fundId,@Param("state")Integer state );

    @Select("SELECT bagging_amount FROM `fund_bag_management` WHERE id=#{id}")
    int selecLeftMoneyById(Integer id);

    @Select("SELECT id,fund_bag_name fundBagName1,CONCAT(bagging_lock_bar_number,'-',SUBSTRING_INDEX(SUBSTRING_INDEX(fund_bag_name,'-',2),'-',-1),'-',bagging_amount,'w','-',bagging_personnel)fundBagName2 FROM `fund_bag_management`  WHERE state IN(1,2)  AND library_id=#{libraryId}")
    List<Map<String,Object>> selectFundNameByLibraryId(@Param("libraryId")Integer libraryId);

}