package com.ixilink.banknote_box.message.service;

import com.ixilink.banknote_box.common.pojo.HandOverModel;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public interface ReadyMoneyService {

    void selectNumberAndType(String activeRfid, HttpServletRequest request);


    Map<String,Object> scanInBox(Map<String,Object> param, HttpServletRequest request, HttpServletResponse response);


    void startReadyMoneyFace(HttpServletRequest request, HttpServletResponse response);

    void inHandover(HandOverModel param, HttpServletRequest request);

}

