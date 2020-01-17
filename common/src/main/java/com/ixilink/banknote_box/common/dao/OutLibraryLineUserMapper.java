package com.ixilink.banknote_box.common.dao;

import com.ixilink.banknote_box.common.model.UserSimple;
import com.ixilink.banknote_box.common.pojo.OutLibraryLineUser;
import com.ixilink.banknote_box.common.pojo.OutLibraryLineUserExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface OutLibraryLineUserMapper {
    long countByExample(OutLibraryLineUserExample example);

    int deleteByExample(OutLibraryLineUserExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(OutLibraryLineUser record);

    int insertSelective(OutLibraryLineUser record);

    List<OutLibraryLineUser> selectByExample(OutLibraryLineUserExample example);

    OutLibraryLineUser selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") OutLibraryLineUser record, @Param("example") OutLibraryLineUserExample example);

    int updateByExample(@Param("record") OutLibraryLineUser record, @Param("example") OutLibraryLineUserExample example);

    int updateByPrimaryKeySelective(OutLibraryLineUser record);

    int updateByPrimaryKey(OutLibraryLineUser record);

    int insertList(@Param("list")List<OutLibraryLineUser> list);

    @Select("SELECT DISTINCT user.`id`,user.`name` FROM out_library_line_user " +
            "LEFT JOIN USER ON out_library_line_user.`user_id` = user.`id` " +
            "WHERE out_library_line_user.`line_id` IN(SELECT id FROM out_library_line WHERE task_id = #{taskId}) ")
    List<UserSimple> getLineUser(@Param("taskId")Integer taskId);
}