package com.ixilink.banknote_box.common.dao;

import com.ixilink.banknote_box.common.pojo.Department;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface DepartmentMapper {

    Integer insertSelective(Department record);

    Department selectByPrimaryKey(Integer id);

    @Select("UPDATE  `department` SET state=2 WHERE id=#{id}")
    Integer deleteByPrimary(Integer id);

    @Insert("INSERT INTO `department`(department_name,remarks,create_time,library_id)\n"
            + "VALUES(#{departmentName},#{remarks},#{createTime},#{libraryId});")
    Integer insert(Department record);
    @Select("<script>"
            + "SELECT id,department_name departmentName,remarks,create_time createTime,state,\n"
            + "(SELECT GROUP_CONCAT(NAME) FROM USER WHERE department_id=d.id AND state=1 GROUP BY department_id)details FROM department d \n"
            + " WHERE 1=1 AND state=1 AND library_id=#{libraryId}"
            + "<when test='departmentName !=null and departmentName != \"\" '>"
            + "  and LOCATE(#{departmentName},department_name)>0 "
            + "</when>"
            + "</script>")
    List<Map<String,Object>> selectByDepartmentName(@Param("departmentName") String departmentName,@Param("libraryId") Integer libraryId);


    @Update("UPDATE `department` SET department_name=#{departmentName},remarks=#{remarks} where id=#{id}")
    Integer updateByPrimaryKey(Map<String,Object> record);

}