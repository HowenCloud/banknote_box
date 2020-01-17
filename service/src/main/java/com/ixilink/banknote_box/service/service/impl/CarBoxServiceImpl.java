package com.ixilink.banknote_box.service.service.impl;

import com.ixilink.banknote_box.common.dao.CarBoxMapper;
import com.ixilink.banknote_box.common.exception.BusinessException;
import com.ixilink.banknote_box.common.model.Code;
import com.ixilink.banknote_box.common.pojo.CarBox;
import com.ixilink.banknote_box.common.pojo.User;
import com.ixilink.banknote_box.common.util.RedisUtil;
import com.ixilink.banknote_box.common.util.ValidatePermissionsUtil;
import com.ixilink.banknote_box.service.service.CarBoxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Service
public class CarBoxServiceImpl implements CarBoxService {
    @Resource
    private CarBoxMapper carBoxMapper;
    @Autowired
    private RedisUtil redisUtil;

    @Override
    public void insert(CarBox record, HttpServletRequest request) {
        User user = ValidatePermissionsUtil.getUser(request,redisUtil);
        Integer libraryId = user.getLibraryId();
        record.setLibraryId(libraryId);
        carBoxMapper.insert(record);
    }

    @Override
    public List<Map<String, Object>> select(Map<String, Object> record, HttpServletRequest request) {
        record.put("libraryId",ValidatePermissionsUtil.getUser(request,redisUtil).getLibraryId());
        return carBoxMapper.select(record);
    }

    @Override
    public void updateByPrimaryKey(CarBox record) {
        carBoxMapper.updateByPrimaryKey(record);
    }

    @Override
    public void deleteByPrimaryKey(Integer id) {
        int state = carBoxMapper.selectboxUseState(id);
        if(state==1) {
            carBoxMapper.deleteByPrimaryKey(id);
        }else{
           throw  new BusinessException(Code.MESSAGE_ERROR.getCode(),"钞箱正在使用不能删除！");
        }
    }

    @Override
    public List<Map<String,Object>> selectCarBoxType(){
       return carBoxMapper.selectCarBoxType();
    }

    @Override
    public Map<String,Object> selectCaxBoxNums(HttpServletRequest request){
        return carBoxMapper.selectCaxBoxNums(ValidatePermissionsUtil.getUser(request,redisUtil).getLibraryId());
    }
}
