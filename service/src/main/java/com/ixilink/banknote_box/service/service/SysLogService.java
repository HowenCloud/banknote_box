package com.ixilink.banknote_box.service.service;

import com.ixilink.banknote_box.common.pojo.SysLog;
import com.ixilink.banknote_box.common.pojo.SysLogExcel;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface SysLogService {
    void Insert(SysLog param);

    List<Map<String,Object>> select(Map<String,Object> param,HttpServletRequest request);

    List<SysLogExcel> selectExcel(Map<String,Object> param,HttpServletRequest request);

    List<Map<String,Object>> selectLogOperationObject();
}
