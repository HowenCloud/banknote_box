package com.ixilink.banknote_box.service.service.impl;
import com.ixilink.banknote_box.common.dao.SysLogMapper;
import com.ixilink.banknote_box.common.pojo.SysLog;
import com.ixilink.banknote_box.common.pojo.SysLogExcel;
import com.ixilink.banknote_box.common.pojo.User;
import com.ixilink.banknote_box.common.util.RedisUtil;
import com.ixilink.banknote_box.common.util.ValidatePermissionsUtil;
import com.ixilink.banknote_box.service.service.SysLogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Service
public class SysLogServiceImpl implements SysLogService {


    @Resource
    private SysLogMapper sysLogMapper;
    @Resource
    private RedisUtil redisUtil;
    @Override
    public void Insert(SysLog param) {

        sysLogMapper.Insert(param);
    }

    @Override
    public List<Map<String,Object>> select(Map<String, Object> param,HttpServletRequest request) {
        User user = ValidatePermissionsUtil.getUser(request,redisUtil);
        Integer libraryId = user.getLibraryId();
        param.put("libraryId",libraryId);
        return sysLogMapper.select(param);
    }

    @Override
    public List<SysLogExcel> selectExcel(Map<String, Object> param,HttpServletRequest request) {
        User user = ValidatePermissionsUtil.getUser(request,redisUtil);
        Integer libraryId = user.getLibraryId();
        param.put("libraryId",libraryId);
        return sysLogMapper.selectExcel(param);
    }

    @Override
    public List<Map<String, Object>> selectLogOperationObject() {
        return sysLogMapper.selectLogoperationObject();
    }
}
