package com.ixilink.banknote_box.service.service;


import com.ixilink.banknote_box.common.pojo.HandOverModel;
import com.ixilink.banknote_box.common.pojo.ReadyMoneyApplyTarget;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public interface ReadyMoneyOutService {



  List<Map<String,Object>> selectApplyOut(HttpServletRequest request);

  ReadyMoneyApplyTarget selectApplyOutTarget(Integer id);


  Map<String,Object> selectFaceOutGrouperLibraryer(Integer id, HttpServletRequest request);

  ReadyMoneyApplyTarget outSupplement(Map<String, Object> param, HttpServletRequest request, HttpServletResponse response);

  void outHandover(HandOverModel param, HttpServletRequest request);



}
