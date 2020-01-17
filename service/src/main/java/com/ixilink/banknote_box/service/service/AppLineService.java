package com.ixilink.banknote_box.service.service;

import com.ixilink.banknote_box.service.parameter_model.app.SubmitDepositBox;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @description:
 * @author: 张皓峰
 * @date: 2019-12-20 10:20
 */
public interface AppLineService {
    Map<String,Object> lineDetails(HttpServletRequest request, HttpServletResponse response);

    Map<String,Object> depositBox(HttpServletRequest request, HttpServletResponse response);

    void submitDepositBox(SubmitDepositBox param, HttpServletRequest request, HttpServletResponse response);

    Map<String,Object> atmDetails(Integer id, HttpServletRequest request, HttpServletResponse response);
}
