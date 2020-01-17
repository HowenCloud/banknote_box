package com.ixilink.banknote_box.common.dao;

import com.ixilink.banknote_box.common.pojo.SysLog;
import com.ixilink.banknote_box.common.pojo.SysLogExcel;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;
@Mapper
public interface SysLogMapper {
    @Insert("INSERT INTO sys_log(`create_time`,`operator`,`operation_object`,`operation_content`,`library_id`) \n"
            + "VALUES(#{createTime},#{operator},#{operationObject},#{operationContent},#{libraryId} );")
    int Insert(SysLog param);

    @Select({ "<script>"
            + "SELECT id,create_time createTime,s.operator operator,\n"
            + "(SELECT operation_object  FROM `sys_log_object_type` l WHERE l.id=s.operation_object)operationObject, operation_content operationContent FROM  `sys_log` s"
            + " WHERE 1=1 "
            + " AND library_id= #{libraryId}"
            + "<when test='beginTime!=null and begintime != \"\"'>"
            + "  AND create_time  &gt;= #{beginTime}"
            + "</when>"
            + "<when test='endTime!=null and  endTime != \"\"'>"
            + "  AND create_time &lt;= #{endTime}"
            + "</when>"
            + "<when test='operator !=null and  operator  != \"\"'>"
            + "  AND operator =#{operator}"
            + "</when>"
            + "<when test='operationObject !=null and  operationObject  != \"\"'>"
            + "  AND operation_object =#{operationObject}"
            + "</when>"
            + "</script>" })
    List<Map<String,Object>> select(Map<String,Object> param);

    @Select({ "<script>"
            + "SELECT FROM_UNIXTIME(create_time/1000,'%Y-%m-%d %T') createTime,s.operator operator,\n"
            + "(SELECT operation_object  FROM `sys_log_object_type` l WHERE l.id=s.operation_object)operationObject, operation_content operationContent FROM  `sys_log` s"
            + " WHERE 1=1 "
            + "  AND library_id= #{libraryId}"
            + "<when test='beginTime!=null and begintime != \"\"'>"
            + "  AND create_time  &gt;= #{beginTime}"
            + "</when>"
            + "<when test='endTime!=null and  endTime != \"\"'>"
            + "  AND create_time &lt;= #{endTime}"
            + "</when>"
            + "<when test='operator !=null and  operator  != \"\"'>"
            + "  AND operator =#{operator}"
            + "</when>"
            + "<when test='operationObject !=null and  operationObject  != \"\"'>"
            + "  AND operation_object =#{operationObject}"
            + "</when>"
            + "</script>" })
    List<SysLogExcel> selectExcel(Map<String,Object> param);

    @Select("SELECT id,operation_object operationObject FROM `sys_log_object_type`")
    List<Map<String,Object>> selectLogoperationObject();
}