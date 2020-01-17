package com.ixilink.banknote_box.message.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @description:
 * @author: 张皓峰
 * @date: 2020-01-11 13:59
 */
public interface InLineInventoryService {

    Map<String,Object> inventoryLine(Integer id, HttpServletRequest request, HttpServletResponse response);

    void handoverInLine(Integer id, HttpServletRequest request, HttpServletResponse response);

}
