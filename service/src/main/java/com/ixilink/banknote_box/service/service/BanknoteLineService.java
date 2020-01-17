package com.ixilink.banknote_box.service.service;

import com.ixilink.banknote_box.common.pojo.BanknoteLine;
import com.ixilink.banknote_box.service.parameter_model.banknote_line.BanknoteLineModel;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: 张俊
 * @date: 2019-11-21 16:47
 */
public interface BanknoteLineService {

    List<Map<String,Object>> getUser(HttpServletRequest request, HttpServletResponse response);

    String getName(HttpServletRequest request, HttpServletResponse response);

    void addBanknoteLine(BanknoteLineModel param, HttpServletRequest request, HttpServletResponse response);

    List<BanknoteLine> getBanknoteLine(String username, HttpServletRequest request, HttpServletResponse response);

    void removeBanknoteLine(Integer id, HttpServletRequest request, HttpServletResponse response);

    void modifyBanknoteLine(BanknoteLineModel param, HttpServletRequest request, HttpServletResponse response);
}
