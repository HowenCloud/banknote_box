package com.ixilink.banknote_box.service.service;

import com.ixilink.banknote_box.common.pojo.Department;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface DepartmentService {


    void deleteByPrimary(Integer id,HttpServletRequest request);
    void insert(Department record, HttpServletRequest request);
    List<Map<String,Object>> selectByDepartmentName(String param,HttpServletRequest request);
    void updateByPrimaryKey(Map<String,Object> record,HttpServletRequest request);
}
