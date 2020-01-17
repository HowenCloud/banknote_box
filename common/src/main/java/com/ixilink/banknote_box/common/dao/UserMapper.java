package com.ixilink.banknote_box.common.dao;

import com.ixilink.banknote_box.common.model.UserSimple;
import com.ixilink.banknote_box.common.pojo.BackLibrary;
import com.ixilink.banknote_box.common.pojo.User;
import com.ixilink.banknote_box.common.pojo.UserExample;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserMapper {
    long countByExample(UserExample example);

    int deleteByExample(UserExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    List<User> selectByExample(UserExample example);

    User selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") User record, @Param("example") UserExample example);

    int updateByExample(@Param("record") User record, @Param("example") UserExample example);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    List<User> userInfo(UserExample example);

    User userInfoById(Integer id);

    List<Map<String,Object>> departments(@Param("libraryId") Integer libraryId);

    List<Map<String,Object>> roles(@Param("libraryId") Integer libraryId,@Param("isSystemer") Integer isSystemer);

    @Select("SELECT id,name FROM back_library ;")
    List<Map<String,Object>> library();

    @Insert("INSERT INTO back_library(name) values (#{name});")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int addLibrary(BackLibrary param);

    @Select("SELECT id,NAME FROM USER WHERE role_id = (SELECT id FROM role WHERE role_name='加钞岗' AND library_id=#{libraryId}) AND state=1;")
    List<Map<String,Object>> getAddMoneyUser(@Param("libraryId")Integer libraryId);

    List<UserSimple> getSimpleUser(@Param("libraryId")Integer libraryId, @Param("roleId")Integer roleId);
}