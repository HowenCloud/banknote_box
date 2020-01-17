package com.ixilink.banknote_box.message.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @description:
 * @author: 张皓峰
 * @date: 2019-12-09 10:34
 */
public interface OutLineInventoryService {

    Map<String,Object> inventoryLine(Integer id, Integer type, HttpServletRequest request, HttpServletResponse response);

    void startRecognitionFace(Map<String, Integer> param, HttpServletRequest request, HttpServletResponse response);

    void stopRecognitionFace(Map<String, Integer> param, HttpServletRequest request, HttpServletResponse response);

    void handoverLine(Map<String, Integer> param, HttpServletRequest request, HttpServletResponse response);

    void reloadRecognitionFace(Integer type, HttpServletRequest request, HttpServletResponse response);
}
