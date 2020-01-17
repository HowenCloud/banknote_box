package com.ixilink.banknote_box.service.service.impl;

import com.ixilink.banknote_box.common.dao.BanknoteLineAtmMapper;
import com.ixilink.banknote_box.common.dao.BanknoteLineMapper;
import com.ixilink.banknote_box.common.dao.BanknoteLineUserMapper;
import com.ixilink.banknote_box.common.dao.UserMapper;
import com.ixilink.banknote_box.common.exception.BusinessException;
import com.ixilink.banknote_box.common.model.Code;
import com.ixilink.banknote_box.common.pojo.*;
import com.ixilink.banknote_box.common.util.NumberUtil;
import com.ixilink.banknote_box.common.util.RedisUtil;
import com.ixilink.banknote_box.common.util.ValidatePermissionsUtil;
import com.ixilink.banknote_box.service.parameter_model.banknote_line.BanknoteLineModel;
import com.ixilink.banknote_box.service.service.BanknoteLineService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: 张俊
 * @date: 2019-11-21 16:48
 */
@Service
public class BanknoteLineServiceImpl implements BanknoteLineService {

    @Resource
    private BanknoteLineMapper banknoteLineMapper;
    @Resource
    private BanknoteLineUserMapper banknoteLineUserMapper;
    @Resource
    private BanknoteLineAtmMapper banknoteLineAtmMapper;
    @Resource
    private UserMapper userMapper;
    @Resource
    private RedisUtil redisUtil;

    @Override
    public List<Map<String,Object>> getUser(HttpServletRequest request, HttpServletResponse response) {
        User user = ValidatePermissionsUtil.getUser(request, redisUtil);
        return userMapper.getAddMoneyUser(user.getLibraryId());
    }

    @Override
    public String getName(HttpServletRequest request, HttpServletResponse response) {
        User user = ValidatePermissionsUtil.getUser(request, redisUtil);
        BanknoteLineExample banknoteLineExample = new BanknoteLineExample();
        banknoteLineExample.createCriteria().andLibraryIdEqualTo(user.getLibraryId());
        long l = banknoteLineMapper.countByExample(banknoteLineExample);
        return NumberUtil.numToZh_Lower(l+1)+"组";
    }

    @Override
    @Transactional
    public void addBanknoteLine(BanknoteLineModel param, HttpServletRequest request, HttpServletResponse response) {
        ValidatePermissionsUtil.validatePermission(request,redisUtil,10);
        User user = ValidatePermissionsUtil.getUser(request, redisUtil);
        BanknoteLineExample banknoteLineExample = new BanknoteLineExample();
        banknoteLineExample.createCriteria().andNameEqualTo(param.getName());
        long count = banknoteLineMapper.countByExample(banknoteLineExample);
        if (count > 0){
            throw new BusinessException(Code.MESSAGE_ERROR.getCode(),param.getName()+"已存在，请刷新页面在试");
        }

        BanknoteLine banknoteLine = new BanknoteLine(null,param.getName(),param.getRemarks(),new Date().getTime(),null,user.getLibraryId());
        int i = banknoteLineMapper.insertSelective(banknoteLine);
        if (i < 1){
            throw new BusinessException(Code.MESSAGE_ERROR.getCode(),"添加失败");
        }

        param.getUsers().forEach(userId -> {
            BanknoteLineUser banknoteLineUser = new BanknoteLineUser(banknoteLine.getId(),userId,user.getLibraryId());
            banknoteLineUserMapper.insert(banknoteLineUser);
        });

        param.getAtms().forEach(atmId -> {
            BanknoteLineAtm banknoteLineAtm = new BanknoteLineAtm(banknoteLine.getId(),atmId,user.getLibraryId());
            banknoteLineAtmMapper.insert(banknoteLineAtm);
        });

    }

    @Override
    public List<BanknoteLine> getBanknoteLine(String username, HttpServletRequest request, HttpServletResponse response) {
        if (username != null) username = "%"+username+"%";
        User user = ValidatePermissionsUtil.getUser(request, redisUtil);
        return banknoteLineMapper.find(user.getLibraryId(),username);
    }

    @Override
    public void removeBanknoteLine(Integer id, HttpServletRequest request, HttpServletResponse response) {
        ValidatePermissionsUtil.validatePermission(request,redisUtil,13);
        BanknoteLine banknoteLine = new BanknoteLine();
        banknoteLine.setId(id);
        banknoteLine.setState(2);
        int i = banknoteLineMapper.updateByPrimaryKeySelective(banknoteLine);
        if (i < 1){
            throw new BusinessException(Code.MESSAGE_ERROR.getCode(),"删除失败");
        }
    }

    @Override
    @Transactional
    public void modifyBanknoteLine(BanknoteLineModel param, HttpServletRequest request, HttpServletResponse response) {
        ValidatePermissionsUtil.validatePermission(request,redisUtil,12);
        BanknoteLine banknoteLine = new BanknoteLine(param.getId(),param.getName(),param.getRemarks(),null,null,null);
        int i = banknoteLineMapper.updateByPrimaryKeySelective(banknoteLine);
        if (i < 1){
            throw new BusinessException(Code.MESSAGE_ERROR.getCode(),"修改失败");
        }
        BanknoteLineUserExample banknoteLineUserExample = new BanknoteLineUserExample();
        banknoteLineUserExample.createCriteria().andLineIdEqualTo(param.getId());
        banknoteLineUserMapper.deleteByExample(banknoteLineUserExample);
        BanknoteLineAtmExample banknoteLineAtmExample = new BanknoteLineAtmExample();
        banknoteLineAtmExample.createCriteria().andLineIdEqualTo(param.getId());
        banknoteLineAtmMapper.deleteByExample(banknoteLineAtmExample);
        User user = ValidatePermissionsUtil.getUser(request, redisUtil);
        param.getUsers().forEach(userId -> {
            BanknoteLineUser banknoteLineUser = new BanknoteLineUser(banknoteLine.getId(),userId,user.getLibraryId());
            banknoteLineUserMapper.insert(banknoteLineUser);
        });
        param.getAtms().forEach(atmId -> {
            BanknoteLineAtm banknoteLineAtm = new BanknoteLineAtm(banknoteLine.getId(),atmId,user.getLibraryId());
            banknoteLineAtmMapper.insert(banknoteLineAtm);
        });
    }
}
