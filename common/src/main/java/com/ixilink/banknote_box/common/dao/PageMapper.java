package com.ixilink.banknote_box.common.dao;

import com.ixilink.banknote_box.common.pojo.Page;
import com.ixilink.banknote_box.common.pojo.PageExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PageMapper {
    long countByExample(PageExample example);

    int deleteByExample(PageExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Page record);

    int insertSelective(Page record);

    List<Page> selectByExample(PageExample example);

    Page selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Page record, @Param("example") PageExample example);

    int updateByExample(@Param("record") Page record, @Param("example") PageExample example);

    int updateByPrimaryKeySelective(Page record);

    int updateByPrimaryKey(Page record);
}