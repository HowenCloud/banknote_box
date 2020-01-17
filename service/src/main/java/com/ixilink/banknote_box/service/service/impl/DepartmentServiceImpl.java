package com.ixilink.banknote_box.service.service.impl;

import com.ixilink.banknote_box.common.dao.DepartmentMapper;
import com.ixilink.banknote_box.common.exception.BusinessException;
import com.ixilink.banknote_box.common.exception.ServiceException;
import com.ixilink.banknote_box.common.model.Code;
import com.ixilink.banknote_box.common.pojo.Department;
import com.ixilink.banknote_box.common.util.RedisUtil;
import com.ixilink.banknote_box.common.util.ValidatePermissionsUtil;
import com.ixilink.banknote_box.service.service.DepartmentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;


@Service
public class DepartmentServiceImpl implements DepartmentService {
    @Resource
    private DepartmentMapper departmentMapper;
    @Resource
    private RedisUtil redisUtil;

    @Override
    public void deleteByPrimary(Integer id,HttpServletRequest request) {
        ValidatePermissionsUtil.validatePermission(request,redisUtil,9);
        departmentMapper.deleteByPrimary(id);
    }

    @Override
    public void insert(Department record, HttpServletRequest request) {
        ValidatePermissionsUtil.validatePermission(request,redisUtil,7);
        try {
            record.setLibraryId(ValidatePermissionsUtil.getUser(request,redisUtil).getLibraryId());
            Integer i  = departmentMapper.insert(record);
        } catch (Exception e) {
            throw new BusinessException(Code.MESSAGE_ERROR.getCode(),"部门名称存在，请换部门名称重新创建");
        }
    }

    @Override
    public List<Map<String,Object>> selectByDepartmentName(String departmentName,HttpServletRequest request) {
        Integer libraryId = ValidatePermissionsUtil.getUser(request, redisUtil).getLibraryId();
        return departmentMapper.selectByDepartmentName(departmentName,libraryId);
    }

    @Override
    public void updateByPrimaryKey(Map<String,Object> record,HttpServletRequest request) {
        ValidatePermissionsUtil.validatePermission(request,redisUtil,8);
        int i = departmentMapper.updateByPrimaryKey(record);
        if(i<1) {
            throw new ServiceException("修改失败");
        }
    }
}
