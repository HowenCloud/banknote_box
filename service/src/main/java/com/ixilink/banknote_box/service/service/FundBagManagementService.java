package com.ixilink.banknote_box.service.service;


import com.ixilink.banknote_box.common.pojo.FundBagManagement;
import com.ixilink.banknote_box.common.pojo.FundBagManagementExcel;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface FundBagManagementService {
     void insert(Map record, HttpServletRequest request);

     List<FundBagManagement> selectByMap(Map map);

     List<Map<String,Object>> selectByParam(Map map,HttpServletRequest request);

     List<FundBagManagementExcel> selectToExcel(Map map,HttpServletRequest request);

     void updateFundBagById(Map<String,Object> map,HttpServletRequest request);

     void deleteById(Integer id);

     List<Map<String,Object>> selectBaggingPersonnel(HttpServletRequest request);

     Map<String,Object> selectNumberAndAmount(HttpServletRequest request);

     List<Map<String,Object>> selectFundNameByLibraryId(HttpServletRequest request);
}
