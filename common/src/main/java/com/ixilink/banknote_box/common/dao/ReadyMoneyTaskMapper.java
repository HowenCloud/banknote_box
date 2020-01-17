package com.ixilink.banknote_box.common.dao;

import com.ixilink.banknote_box.common.pojo.*;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;
@Mapper
public interface ReadyMoneyTaskMapper {

    @Insert("INSERT INTO `ready_money_task` (`initiator`,`receive_people`,`library_id`,`create_time`) \n"
            + "VALUES (#{initiator},#{receivePeople},#{libraryId},#{createTime}) \n")
    @Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
    int insert(ReadyMoneyTask record);

    @Select("SELECT `id`,(SELECT NAME FROM `user` WHERE id=initiator)initiator,create_time createTime,\n"
            + "(SELECT GROUP_CONCAT(`name`) FROM `user` WHERE FIND_IN_SET(id,receive_people))`receivePeople`,\n"
            + "`state`,library_id `libraryId`\n"
            + " FROM `ready_money_task` WHERE library_id =#{libraryId}  AND state IN(-1,1,2) ORDER BY createTime  DESC")//AND initiator=#{userId}
    List<ReadyMoneyTaskReturn> selectByInitiator(@Param("libraryId")Integer libraryId);//, @Param("userId")Integer userId

    @Select("SELECT `id`,(SELECT NAME FROM `user` WHERE id=initiator)initiator,create_time createTime,\n"
            + "(SELECT GROUP_CONCAT(`name`) FROM `user` WHERE FIND_IN_SET(id,receive_people))`receivePeople`,\n"
            + "`state`,library_id `libraryId`\n"
            + " FROM `ready_money_task` WHERE library_id =#{libraryId} and state IN(-1,1,2) ")//and FIND_IN_SET(#{userId},receive_people)
    List<ReadyMoneyTaskReturn> selectApproving(@Param("libraryId")Integer libraryId);//, @Param("userId")Integer userId

    @Select("SELECT `id`,(SELECT NAME FROM `user` WHERE id=initiator)initiator,create_time createTime,\n"
            + "(SELECT GROUP_CONCAT(`name`) FROM `user` WHERE FIND_IN_SET(id,receive_people))`receivePeople`,\n"
            + "`state`,library_id `libraryId`\n"
            + " FROM `ready_money_task`  WHERE state IN(2) AND library_id=#{libraryId}")
    List<ReadyMoneyTaskReturn> selectApprovedAndPerforming(@Param("libraryId") Integer libraryId);

    @Select("select sum(number) totalNumber,SUM(number*box_money) totalMoney from `ready_money_box_num_money` where task_id=#{taskId}")
    Map<String,Object> selectApprovedAndPerformingNumberAndMoney(@Param("taskId") Integer taskId);
    @Select(" SELECT type,SUM(box_money*number) totalMoney,SUM(number) number  FROM `ready_money_box_num_money` WHERE task_id=#{taskId} GROUP BY TYPE;")
    List<Map<String,Object>>  selectBoxNumMoneyByTaskId(Integer taskId);

    @Insert("<script> "
            + " insert into `ready_money_box_num_money` (`task_id`,`type`,`box_money`,`number`,`variable_number`) \n"
            + " values "
            + " <foreach item='list' index='key' collection='lists' separator=','> "
            + " (#{list.taskId},#{list.type},#{list.boxMoney},#{list.number},#{list.variableNumber})"
            + " </foreach>"
            + " </script> ")
    int insertBoxNumMoney(@Param("lists") List<ReadyMoneyBoxNumMoney> lists);


    @Update("UPDATE ready_money_task SET state=#{state},receive_p =#{userId} ,approval_time=#{approvalTime} where id=#{id};")
    Integer approve(@Param("userId")Integer userId,@Param("approvalTime")Long approvalTime,@Param("id")Integer id,@Param("state")Integer state);

    /*,\n"
            + "(SELECT GROUP_CONCAT(`name`) FROM `user` WHERE  FIND_IN_SET(id,(SELECT GROUP_CONCAT(DISTINCT(handover_people)) FROM `ready_money_details` WHERE ready_money_details.task_id=ready_money_task.`id`)))`handoverPeople`*/
    @Select("SELECT `id`,(SELECT NAME FROM `user` WHERE id=initiator)initiator,create_time createTime,\n"
            + "(SELECT GROUP_CONCAT(`name`) FROM `user` WHERE FIND_IN_SET(`user`.id,receive_people))`receivePeople`,\n"
            + "(SELECT GROUP_CONCAT(`name`) FROM `user` WHERE  FIND_IN_SET(id,(SELECT GROUP_CONCAT(DISTINCT(executor)) FROM `ready_money_details` WHERE ready_money_details.task_id=ready_money_task.`id`)))executor,`state`,library_id `libraryId`\n"
            + " FROM `ready_money_task`  WHERE state =3 AND library_id=#{libraryId} ")
    List<ReadyMoneyTaskReturn> selectPerformed(@Param("libraryId") Integer libraryId);

    @Select("SELECT type,SUM(box_money*number) totalMoney,SUM(number) number ,SUM((number-variable_number))performedNumber,\n"
            + "SUM((number-variable_number)*box_money )performedMoney,SUM(variable_number)performingNumber,SUM(variable_number*box_money )performingMoney,\n"
            + "GROUP_CONCAT(variable_number,'-',box_money)unfinishNumberMoney\n"
            + "FROM `ready_money_box_num_money` WHERE task_id=#{taskId} GROUP BY TYPE;")
    List<ReadyMoneyExecutorTaskModel> selectByIdCarType(@Param("taskId") Integer taskId);
    @Select("SELECT SUM(variable_number) FROM `ready_money_box_num_money` WHERE task_id=#{taskId}")
    int selectAllNum(@Param("taskId") Integer taskId);

    @Select("SELECT variable_number FROM `ready_money_box_num_money` WHERE task_id=#{taskId} AND type=#{type} AND box_money=#{boxMoney}")
   Integer selectLeftBox(@Param("taskId") Integer taskId,@Param("type") Integer type,@Param("boxMoney") Integer boxMoney);
    @Insert("INSERT INTO `ready_money_details` (`task_id`,`car_box_number`,`car_money`,`fund_id`,`executor`,`car_type`,`create_time`,`library_id`,`fund_bag_name`) \n"
            + "VALUES(#{taskId},#{carBoxNumber},#{carMoney},#{fundId},#{usersId},#{carType},#{createTime},#{libraryId},#{fundBagName})")
    @Options(useGeneratedKeys = true,keyColumn = "id",keyProperty = "id")
    int insertOperation(ReadyMoneyDetails parm);

    @Update("UPDATE`ready_money_box_num_money` SET variable_number=variable_number-1 WHERE task_id=#{taskId} AND type=#{type} AND box_money=#{boxMoney}")
    int reduceBoxNum(@Param("taskId") Integer taskId,@Param("type") Integer type,@Param("boxMoney") Integer boxMoney);

    @Update("UPDATE `ready_money_task` SET state =3,performed_time=#{performedTime} WHERE id=#{taskId}")
    int updateTaskToPerformed(@Param("taskId") Integer taskId,@Param("performedTime") Long performedTime);

    @Select({ "<script>"
            + "select id,car_box_number carBoxNumber,(case car_type \n"
            + "WHEN 1 THEN 'DB钞箱'\n"
            + "WHEN 3 THEN 'DF钞箱'\n"
            + "WHEN 5 THEN 'CRS钞箱'\n"
            + "WHEN 7 THEN 'SCRS钞箱'\n"
            + "end)carType,car_money carMoney,\n"
            + "(select fund_bag_name from `fund_bag_management` where id=fund_id)fundBagName,fund_id fundId,\n"
            + "(select group_concat(name) from `user` where find_in_set(id,executor))executor from `ready_money_details` where task_id=#{taskId} "
            +"<when test='carBoxNumber != null and carBoxNumber != \"\"'>"
            + " AND car_box_number like #{carBoxNumber} "
            + "</when>"
            +"<when test='carType != null and carType != \"\"'>"
            + " AND car_type = #{carType} "
            + "</when>"
            +" ORDER BY  create_time"
            + "</script>" })
    List<ReadyMoneyReturnDetails> performedDetails(@Param("taskId") Integer taskId,@Param("carBoxNumber")String carBoxNumber,@Param("carType") Integer carType);
    /*,\n"
            + "(SELECT GROUP_CONCAT(`name`) FROM `user` WHERE  FIND_IN_SET(id,(SELECT GROUP_CONCAT(DISTINCT(handover_people)) FROM `ready_money_details` WHERE ready_money_details.task_id=#{taskId})))`handoverPeople`*/
    @Select("SELECT (SELECT `name` FROM `user` WHERE user.id=initiator)initiator,create_time createTime,performed_time performedTime,\n"
            + "(SELECT GROUP_CONCAT(`name`) FROM `user` WHERE FIND_IN_SET(user.id,receive_people))receivePeople,\n"
            + "(SELECT GROUP_CONCAT(`name`) FROM `user` WHERE  FIND_IN_SET(id,(SELECT GROUP_CONCAT(DISTINCT(executor)) FROM `ready_money_details` WHERE ready_money_details.task_id=#{taskId})))executor \n"
            + "FROM `ready_money_task` WHERE id=#{taskId}")
    Map<String,Object> performedHead(@Param("taskId") Integer taskId);

  @Insert({
            "<script>",
            "INSERT INTO `ready_money_gouper` (`hand_over_people_id`,`hold_time`,`state`,`details_id`,`library_id`)  VALUES ",
            "<foreach collection='list' item='item' index='index' separator=','>",
            "(#{item.handOverPeopleId},#{item.holdTime},#{item.state},#{item.detailsId},#{item.libraryId})",
            "</foreach>",
            "</script>"
    })
    int handOver(@Param("list") List<Map<String,Object>> list);
    @Insert("INSERT INTO `ready_money_gouper` (`hand_over_people_id`,`hold_time`,`state`,`details_id`,`library_id`)  VALUES (#{handOverPeopleId},#{holdTime},#{state},#{detailsId},#{libraryId})")
    int handOverOne(Map<String,Object> map);
  @Insert("INSERT INTO `ready_money_gouper` (`grouper_id`,`hand_over_people_id`,`hold_time`,`state`,`details_id`,`library_id`)  VALUES \n"
          + "(#{grouperId},#{handOverPeopleId},#{holdTime},1,#{detailsId},#{libraryId})")
    int outHandOver(@Param("grouperId")Integer grouperId,@Param("handOverPeopleId")String handOverPeopleId,@Param("holdTime")Long holdTime,@Param("detailsId")Integer detailsId,@Param("libraryId")Integer libraryId);

    @Select("SELECT car_type carType,COUNT(1)number,SUM(car_money)money FROM `ready_money_details` WHERE FIND_IN_SET(id,#{ids})   GROUP BY car_type")
    List<Map<String,Object>> selectUnderGroupLeaderHead(@Param("ids") String ids);

    @Select({ "<script>"
            + "SELECT d.id did,g.`id` gid, \n"
            + "(CASE\n"
            + "WHEN g.state =1 THEN g.hold_time\n"
            + "WHEN g.state =2 THEN g.in_library_time\n"
            + "WHEN g.state =3 THEN g.out_library_time\n"
            + "END)`time`,(CASE d.car_type \n"
            + "WHEN 1 THEN 'DB钞箱'\n"
            + "WHEN 3 THEN 'DF钞箱'\n"
            + "WHEN 5 THEN 'CRS钞箱'\n"
            + "WHEN 7 THEN 'SCRS钞箱'\n"
            + "END)carType,d.`car_box_number`carBoxNumber ,g.`state`,d.`car_money` carMoney,\n"
            + "(SELECT fund_bag_name FROM `fund_bag_management` WHERE id=d.`fund_id`)fundBagName,\n"
            + "(SELECT GROUP_CONCAT(NAME) FROM `user` WHERE FIND_IN_SET(id,d.`executor`))executor,\n"
            + "(SELECT GROUP_CONCAT(NAME) FROM `user` WHERE FIND_IN_SET(id,g.`grouper_id`))grouper,\n"
            + "(SELECT GROUP_CONCAT(NAME) FROM `user` WHERE FIND_IN_SET(id,g.`hand_over_people_id`))handOverPeople\n"
            + "FROM `ready_money_gouper` g LEFT JOIN \n"
            + "ready_money_details d ON g.`details_id`=d.`id`\n"
            + "WHERE g.`library_id` =#{libraryId} "
            +"<when test='state != null and state != \"\"'>"
            + " AND g.state =#{state} "
            + "</when>"
            +"<when test='state != 1 and grouperId != null and grouperId != \"\"'>"
            + " AND grouper_id = #{grouperId} "
            + "</when>"
            +"<when test='beginTime != null and beginTime != \"\"'>"
            + " AND time &gt; #{beginTime} "
            + "</when>"
            +"<when test='endTime != null and endTime != \"\"'>"
            + " AND time &lt; #{endTime} "
            + "</when>"
            +" ORDER BY  time"
            + "</script>" })
    List<Map<String,Object>> selectUnderGroupLeaderDetails(Map<String,Object> param);
    @Select({ "<script>"
            + "SELECT d.id did,l.`id` lid, \n"
            + "(CASE\n"
            + "WHEN l.state =1 THEN l.`in_library_time`\n"
            + "WHEN l.state =2 THEN l.`out_library_time`\n"
            + "END)`time`,(CASE d.car_type \n"
            + "WHEN 1 THEN 'DB钞箱'\n"
            + "WHEN 3 THEN 'DF钞箱'\n"
            + "WHEN 5 THEN 'CRS钞箱'\n"
            + "WHEN 7 THEN 'SCRS钞箱'\n"
            + "END)carType,d.`car_box_number`carBoxNumber ,l.`state`,d.`car_money` carMoney,\n"
            + "(SELECT fund_bag_name FROM `fund_bag_management` WHERE id=d.`fund_id`)fundBagName,\n"
            + "(SELECT GROUP_CONCAT(NAME) FROM `user` WHERE FIND_IN_SET(id,d.`executor`))executor,\n"
            + "(SELECT GROUP_CONCAT(NAME) FROM `user` WHERE FIND_IN_SET(id,l.`libraryer_id`))library,\n"
            + "(SELECT GROUP_CONCAT(NAME) FROM `user` WHERE FIND_IN_SET(id,l.`hand_over_people_id`))handOverPeople\n"
            + "FROM `ready_money_libraryer` l LEFT JOIN \n"
            + "ready_money_details d ON l.`details_id`=d.`id`\n"
            + "WHERE l.`library_id` =#{libraryId} "
            +"<when test='state != null and state != \"\"'>"
            + " AND l.state =#{state} "
            + "</when>"
            +"<when test='libraryerId != null and libraryerId != \"\"'>"
            + " AND FIND_IN_SET(#{libraryerId},libraryer_id) "
            + "</when>"
            +"<when test='beginTime != null and beginTime != \"\"'>"
            + " AND time &gt; #{beginTime} "
            + "</when>"
            +"<when test='endTime != null and endTime != \"\"'>"
            + " AND time &lt; #{endTime} "
            + "</when>"
            +" ORDER BY  time"
            + "</script>" })
    List<Map<String,Object>> selectUnderLibraryDetails(Map<String,Object> param);

    int insertApply(ReadyMoneyApply readyMoneyApply);

    @Select("SELECT id,`name` FROM `user` WHERE  role_id=18 AND library_id=#{libraryId};")
     List<Map<String,Object>>  selectHandoverPerson(@Param("libraryId") Integer libraryId);
    @Select("SELECT serial_number serialNumber,box_type `carType`,\n"
            + "(CASE box_type \n"
            + "WHEN 1 THEN 'DB钞箱'\n"
            + "WHEN 3 THEN 'DF钞箱'\n"
            + "WHEN 5 THEN 'CRS钞箱'\n"
            + "WHEN 7 THEN 'SCRS钞箱'\n"
            + "END)carTypeName\n"
            + " FROM `car_box` WHERE passive_rfid=#{activeRfid}")
    Map<String,Object> selectNumberAndType(@Param("activeRfid") String activeRfid);
    @Select("SELECT `name`,id FROM `user`  WHERE  role_id =(SELECT id FROM `role` WHERE role_name=\"管库员\" AND library_id=#{libraryId}) AND library_id= #{libraryId}")
    List<Map<String,Object>> selectGrouperNameAndId(@Param("libraryId")Integer libraryId);
    @Select("select `name`,id from `user` where library_id=#{libraryId}")
    List<Map<String,Object>> selectLibraryNameAndId(@Param("libraryId")Integer libraryId);
    @Select("SELECT id,(SELECT `name` FROM USER WHERE id=grouper_id)grouper,state,total_number totalNumber,create_time createTime FROM `ready_money_in_apply` where library_id =#{libraryId}")
    List<Map<String,Object>> selectApplyByGrouper(@Param("libraryId") Integer libraryId);
    @Select("SELECT  normal_result normalResult,total_number totalNumber FROM `ready_money_in_apply` WHERE id =#{id}")
    Map<String,Object> selectApplyTarget(@Param("id") Integer id);
    @Select("SELECT  normal_result normalResult,total_number totalNumber FROM `ready_money_out_apply` WHERE id =#{id}")
    Map<String,Object> selectApplyOutTarget(@Param("id") Integer id);
    @Select("SELECT id,car_box_number carBoxNumber,car_type carType,\n"
            + "(CASE car_type \n"
            + "WHEN 1 THEN 'DB钞箱'\n"
            + "WHEN 3 THEN 'DF钞箱'\n"
            + "WHEN 5 THEN 'CRS钞箱'\n"
            + "WHEN 7 THEN 'SCRS钞箱'\n"
            + "END)carTypeName,\n"
            + "(SELECT fund_bag_name FROM fund_bag_management WHERE id=fund_id)fundBagName FROM `ready_money_details` WHERE car_box_number=#{carBoxNumber} AND library_id=#{libraryId}")
    Map<String,Object> selectApplyCarBox(@Param("carBoxNumber") String carBoxNumber,@Param("libraryId") Integer libraryId);
    @Insert("INSERT INTO ready_money_out_apply (`db`,`df`,`crs`,`scrs`,`create_time`,`grouper_id`,`libraryer_id`,`library_id`) \n"
            + "VALUES(#{db},#{df},#{crs},#{scrs},#{createTime},#{grouperId},#{libraryerIds},#{libraryId})")
    Integer outLibraryApply(ReadyMoneyApply readyMoneyApply);

    @Update("UPDATE `ready_money_libraryer` SET libraryer_id=#{libraryerId} where library_id =#{libraryId}")
    int boxHandOver(@Param("libraryerId")String libraryerId,@Param("libraryId")Integer libraryId);
    /*修改基金袋金额*/
    @Update("update `fund_bag_management` set bagging_amount=bagging_amount+(SELECT SUM(car_money) FROM `ready_money_details` WHERE library_id=#{libraryId} AND  fund_id=#{fundId} and executor=#{executor1} or executor=#{executor2})\n"
            + "where library_id=#{libraryId} and id=#{fundId}")
    int refund(@Param("libraryId")Integer libraryId,@Param("fundId")Integer fundId,@Param("executor1")String executor1,@Param("executor2")String executor2);

    @Update("UPDATE `ready_money_details` SET fund_id = NULL WHERE library_id=#{libraryId} AND  fund_id=#{fundId} AND executor=#{executor1} or executor=#{executor2}")
    int updateFundToNull(@Param("libraryId")Integer libraryId,@Param("fundId")Integer fundId,@Param("executor1")String executor1,@Param("executor2")String executor2);

    @Update("UPDATE `ready_money_details` SET fund_id =#{fundId} WHERE library_id=#{libraryId} AND  fund_id IS NULL and executor=#{executor1} or executor=#{executor2}")
    int updateFund(@Param("libraryId")Integer libraryId,@Param("fundId")Integer fundId,@Param("executor1")String executor1,@Param("executor2")String executor2);

    @Update("UPDATE `fund_bag_management` SET bagging_amount=bagging_amount-(SELECT SUM(car_money) FROM `ready_money_details` WHERE library_id=#{libraryId} AND  fund_id IS NULL AND executor=#{executor1} OR executor=#{executor2}) WHERE library_id=#{libraryId} and id=#{fundId}")
    int updateFundReduce(@Param("libraryId")Integer libraryId,@Param("fundId")Integer fundId,@Param("executor1")String executor1,@Param("executor2")String executor2);

    @Select("SELECT id  FROM `ready_money_details` WHERE library_id=#{libraryId} AND task_id=#{taskId} ")
    List<Integer> selectDetailsIdByTaskedId(@Param("libraryId")Integer libraryId,@Param("taskId")Integer taskId);
    List<Map<String,Object>> inventory(@Param("libraryId")Integer libraryId, @Param("boxNumbers")List<String> boxNumbers);

    @Select("SELECT d.id,d.car_box_number carBoxNumber,(CASE d.car_type \n"
            + "WHEN 1 THEN 'DB钞箱'\n"
            + "WHEN 3 THEN 'DF钞箱'\n"
            + "WHEN 5 THEN 'CRS钞箱'\n"
            + "WHEN 7 THEN 'SCRS钞箱'\n"
            + "END)carTypeName,d.car_type carType,(SELECT fund_bag_name FROM `fund_bag_management` m WHERE m.id=d.fund_id)fundBagName\n"
            + "\n"
            + "FROM `ready_money_details` d  WHERE d.library_id=#{libraryId} AND d.car_box_number = #{number}")
    Map<String,Object> selectBoxBySerialType(@Param("number") String number,@Param("libraryId") Integer libraryId);

    @Select("select grouper_id grouperId,libraryer_id libraryerId from `ready_money_in_apply` where id =#{id} and  library_id =#{libraryId}")
    Map<String,Object> selectFaceGrouperLibraryer(@Param("libraryId") Integer libraryId,@Param("id") Integer id);
    @Select("select grouper_id grouperId,libraryer_id libraryerId from `ready_money_out_apply` where id =#{id} and  library_id =#{libraryId}")
    Map<String,Object> selectFaceGrouperOutLibraryer(@Param("libraryId") Integer libraryId,@Param("id") Integer id);
    @Update(" update `ready_money_in_apply` set inventory_result=#{inventoryResult},supplement_result=null where id=#{id};")
    int updateTaskInventory(@Param("inventoryResult")String inventoryResult,@Param("id")Integer id);

    @Update("update `ready_money_in_apply` set supplement_result=#{supplementResult} where id=#{id};")
    int updateTaskSupplement(@Param("supplementResult")String supplementResult,@Param("id")Integer id);

    @Update("UPDATE `ready_money_in_apply` SET approval_people=#{approvalPeople},inventory_result=#{inventoryResult},state=2 where id=#{id};")
    int inHandover(@Param("approvalPeople")String approvalPeople,@Param("id")Integer id,@Param("inventoryResult")String inventoryResult);
    @Update("UPDATE `ready_money_out_apply` SET approval_people=#{approvalPeople},inventory_result=#{inventoryResult},state=2 where id=#{id};")
    int outHandover(@Param("approvalPeople")String approvalPeople,@Param("id")Integer id,@Param("inventoryResult")String inventoryResult);


    @Select("SELECT grouper_id grouperId,inventory_result inventoryResult,supplement_result supplementResult FROM `ready_money_in_apply`  WHERE id=#{id}")
    Map<String,Object> selectInventorySupplement(Integer id);
    @Select("SELECT grouper_id grouperId,inventory_result inventoryResult,supplement_result supplementResult FROM `ready_money_out_apply`  WHERE id=#{id}")
    Map<String,Object> selectOutInventorySupplement(Integer id);
    @Update({"<script> "
            + "UPDATE `ready_money_gouper` SET state=2,grouper_id=#{grouperId_},hand_over_people_id=#{libraryersId},in_library_time=#{inLibraryTime} where details_id in  "
            + "<foreach collection='list' item='id' index='index' open='(' separator=',' close=')'>"
            + " #{id}"
            + "</foreach>"
            + "</script>"})
    int updateOverHandGrouper(@Param("libraryersId")String libraryersId,@Param("grouperId_")Integer grouperId_,@Param("inLibraryTime")long inLibraryTime,@Param("list")List list);
    @Update({"<script> "
            + "UPDATE `ready_money_libraryer` SET state=2,out_library_time=#{inLibraryTime} where details_id in  "
            + "<foreach collection='list' item='id' index='index' open='(' separator=',' close=')'>"
            + " #{id}"
            + "</foreach>"
            + "</script>"})
    int updateOutOverHandGrouper(@Param("inLibraryTime")long inLibraryTime,@Param("list")List list);
    @Select({"<script> "
            + "  SELECT \n"
            + "  `id`,`serial_number` serialNumber,`box_type` boxType,`site`,`box_use_state` boxUseState,\n"
            + "  `box_transport_state` boxTransportState,`active_rfid` activeRfid,`passive_rfid` passiveRfid,`create_time` createTime,`state`,\n"
            + "  `library_id` libraryId,`car_money`  carMoney\n"
            + "FROM\n"
            + " `car_box` \n"
            + "    WHERE library_id=1 AND active_rfid IN\n"
            + "    <foreach collection=\"rfids\" item=\"rfid\" open=\"(\" separator=\",\" close=\")\">\n"
            + "      #{rfid}\n"
            + "    </foreach>"
            + "</script>"})
    List<CarBox> getCarBoxList(@Param("libraryId")Integer libraryId, @Param("rfids")List<String> rfids);

    @Select("SELECT (CASE car_type\n"
            + "WHEN 1 THEN 'db'\n"
            + "WHEN 3 THEN 'df'\n"
            + "WHEN 5 THEN 'crs'\n"
            + "WHEN 7 THEN 'scrs'\n"
            + "END )carType\n"
            + "FROM  `ready_money_details` WHERE id =#{detailsId}")
    String getCarType(@Param("detailsId") Integer detailsId);

}