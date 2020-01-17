package com.ixilink.banknote_box.service.service.impl;

import com.ixilink.banknote_box.common.dao.FundBagManagementMapper;
import com.ixilink.banknote_box.common.exception.BusinessException;
import com.ixilink.banknote_box.common.exception.ServiceException;
import com.ixilink.banknote_box.common.model.Code;
import com.ixilink.banknote_box.common.pojo.FundBagManagement;
import com.ixilink.banknote_box.common.pojo.FundBagManagementExcel;
import com.ixilink.banknote_box.common.util.DateFormatUtil;
import com.ixilink.banknote_box.common.util.JsonUtil;
import com.ixilink.banknote_box.common.util.RedisUtil;
import com.ixilink.banknote_box.common.util.ValidatePermissionsUtil;
import com.ixilink.banknote_box.common.util.annotation.SysLogAnnotation;
import com.ixilink.banknote_box.service.service.FundBagManagementService;
import org.apache.tomcat.util.buf.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Service
public class FundBagManagementServiceImpl implements FundBagManagementService {
//FundBagManagementController
    @Resource
    private FundBagManagementMapper fundBagManagementMapper;
    @Autowired
    private RedisUtil redisUtil;
    @Override
    @Transactional
    @SysLogAnnotation("7:添加基金袋")
    public void insert(Map record, HttpServletRequest request) {
        FundBagManagement fundBagManagement = new FundBagManagement();
        //设置是哪个区的基金袋
        Integer libraryId = ValidatePermissionsUtil.getUser(request, redisUtil).getLibraryId();
        fundBagManagement.setLibraryId(libraryId);
        String baggingLockBarNumber=(String) record.get("baggingLockBarNumber");
        fundBagManagement.setBaggingLockBarNumber(baggingLockBarNumber);
        //查询基金袋锁条号 是不是未使用和使用中
        if(fundBagManagementMapper.selectBaggingLockBarNumber(baggingLockBarNumber,libraryId)>0){
            throw  new BusinessException(Code.MESSAGE_ERROR.getCode(),"基金袋锁条码在使用中！");
        }else {
            fundBagManagement.setInstallationTime((Long) record.get("installationTime"));
            fundBagManagement.setBaggingAmount((Integer) record.get("baggingAmount"));
            fundBagManagement.setBaggingAmountAll((Integer) record.get("baggingAmount"));
            fundBagManagement.setBaggingPersonnel((String) record.get("baggingPersonnel"));
            List baggingPersonnels= fundBagManagementMapper.selectNameById((List) record.get("baggingPersonnelId"));
            String baggingPersonnel = StringUtils.join(baggingPersonnels, ',');
            fundBagManagement.setBaggingPersonnel(baggingPersonnel);
            fundBagManagement.setBaggingPersonnelId((JsonUtil.list2str((List) record.get("baggingPersonnelId"))));
            fundBagManagement.setState(1);
            //根据baggingPersonnel和installationTime来定义序号
            //根据时间和人来查询序号
            //Map<String,Object> map =new HashMap<>();
            //map.put("date",(Long)record.get("installationTime"));
            //map.put("baggingPersonnel",(String) record.get("baggingPersonnel"));
            //map.put("libraryId",ValidatePermissionsUtil.getUser(request,redisUtil).getLibraryId());
            //int number = fundBagManagementMapper.selectNumber(map);
            //fundBagManagement.setNumber(number+1);
            //1-20191111-111w-23,323
            //String s = DateFormatUtil.transferLongToDate("yyyyMMdd", (Long) record.get("installationTime"));
            //fundBagManagement.setFundBagName(number+1+"-"+s+"-"+record.get("baggingAmount")+"w"+"-"+record.get("baggingPersonnel"));
            String s = DateFormatUtil.transferLongToDate("yyyyMMdd", (Long) record.get("installationTime"));
            fundBagManagement.setFundBagName((String) record.get("baggingLockBarNumber") + "-" + s + "-" + record.get("baggingAmount") + "w" + "-" + baggingPersonnel);
            int i = fundBagManagementMapper.insert(fundBagManagement);
            if (i < 0) {
                throw new ServiceException("添加基金袋失败！");
            }
        }
    }
    @Override
    public List<FundBagManagement> selectByMap(Map map) {
        return fundBagManagementMapper.selectByMap(map);
    }

    @Override
    public List<Map<String,Object>> selectByParam(Map map,HttpServletRequest request){
        map.put("libraryId",ValidatePermissionsUtil.getUser(request,redisUtil).getLibraryId());
        List<Map<String,Object>> fundBagManagements = fundBagManagementMapper.selectByParam(map);
        for(Map<String,Object> fundBagManagement:fundBagManagements){
            String baggingPersonnelId = (String)fundBagManagement.get("baggingPersonnelId");
            List o = (List)JsonUtil.str2Obj(baggingPersonnelId, List.class);
            fundBagManagement.put("baggingPersonnelId",o);
        }

        return fundBagManagements;
    }

    @Override
    @Transactional
    @SysLogAnnotation("7:基金袋修改")
    public void updateFundBagById(Map<String, Object> map,HttpServletRequest request) {
            //number installationTime(装袋时间)   baggingAmount（装袋金额） baggingPersonnel（装袋人员） number(序号))
            int state = fundBagManagementMapper.selectStateById((Integer) map.get("id"));
            if(state==1){
                String OldBaggingLockBarNumber = fundBagManagementMapper.selectBaggingLockBarNumberById((Integer) map.get("id"));
                String baggingLockBarNumber=(String) map.get("baggingLockBarNumber");
                if(!OldBaggingLockBarNumber.equals(baggingLockBarNumber)){
                Integer libraryId = ValidatePermissionsUtil.getUser(request, redisUtil).getLibraryId();
                if(fundBagManagementMapper.selectBaggingLockBarNumber(baggingLockBarNumber,libraryId)>0){
                    throw  new BusinessException(Code.MESSAGE_ERROR.getCode(),"基金袋锁条码在使用中！");
                }
                }
                    String s = DateFormatUtil.transferLongToDate("yyyyMMdd", (Long) map.get("installationTime"));
                List baggingPersonnels= fundBagManagementMapper.selectNameById((List) map.get("baggingPersonnelId"));
                String baggingPersonnel = StringUtils.join(baggingPersonnels, ',');
                map.put("baggingPersonnel",baggingPersonnel);
                String baggingPersonnelId = JsonUtil.list2str((List) map.get("baggingPersonnelId"));
                map.put("baggingPersonnelId",baggingPersonnelId);

                map.put("fundBagName", baggingLockBarNumber + "-" + s + "-" + map.get("baggingAmount") + "w" + "-" + baggingPersonnel);
                    int i = fundBagManagementMapper.updateFundBagById(map);

            }else if(state==2) {
                throw new BusinessException(Code.MESSAGE_ERROR.getCode(), "基金袋已使用，不能修改！");
            }else{
                throw new BusinessException(Code.MESSAGE_ERROR.getCode(), "基金袋使用中，不能修改！");
            }
    }
    @Override
    @Transactional
    @SysLogAnnotation("7:基金袋删除")
    public void deleteById(Integer id){
            int state = fundBagManagementMapper.selectStateById(id);
            if(state==1){
            fundBagManagementMapper.deleteById(id);
            }else if(state==2) {
                throw new BusinessException(Code.MESSAGE_ERROR.getCode(),"基金袋已使用，不能修改！");
            }else{
                throw new BusinessException(Code.MESSAGE_ERROR.getCode(),"基金袋使用中，不能修改！");
            }

    }
    @Override
    public List<FundBagManagementExcel> selectToExcel(Map map,HttpServletRequest request) {
        map.put("libraryId",ValidatePermissionsUtil.getUser(request,redisUtil));
        return fundBagManagementMapper.selectToExcel(map);
    }
    @Override
    public List<Map<String,Object>> selectBaggingPersonnel(HttpServletRequest request){
        Integer libraryId = ValidatePermissionsUtil.getUser(request,redisUtil).getLibraryId();
        return fundBagManagementMapper.selectBaggingPersonnel(libraryId);
    }
    @Override
    public Map<String,Object> selectNumberAndAmount(HttpServletRequest request){
        Integer libraryId = ValidatePermissionsUtil.getUser(request,redisUtil).getLibraryId();
        return fundBagManagementMapper.selectNumberAndAmount(libraryId);
    }

    @Override
    public List<Map<String,Object>> selectFundNameByLibraryId(HttpServletRequest request){
        Integer libraryId = ValidatePermissionsUtil.getUser(request,redisUtil).getLibraryId();
        return fundBagManagementMapper.selectFundNameByLibraryId(libraryId);
    }
}
