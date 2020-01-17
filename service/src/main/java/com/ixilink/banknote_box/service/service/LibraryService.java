package com.ixilink.banknote_box.service.service;

import com.ixilink.banknote_box.common.pojo.BackLibrary;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: 张俊
 * @date: 2019-11-25 19:14
 */
public interface LibraryService {

    List<Map<String,Object>> get(HttpServletRequest request, HttpServletResponse response);

    void add(BackLibrary param, HttpServletRequest request, HttpServletResponse response);
}
