package com.ixilink.banknote_box.service.service;

import com.ixilink.banknote_box.common.pojo.CarBox;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface CarBoxService {


    void insert(CarBox record, HttpServletRequest request);

    List<Map<String,Object>> select(Map<String, Object> record,HttpServletRequest request);

    void updateByPrimaryKey(CarBox record);

    void deleteByPrimaryKey(Integer id);

    List<Map<String,Object>> selectCarBoxType();

    Map<String,Object> selectCaxBoxNums(HttpServletRequest request);
}
