package com.ixilink.banknote_box.service.service.impl;

import com.github.pagehelper.PageHelper;
import com.ixilink.banknote_box.common.dao.*;
import com.ixilink.banknote_box.common.exception.BusinessException;
import com.ixilink.banknote_box.common.model.Code;
import com.ixilink.banknote_box.common.model.ExcelDataModel;
import com.ixilink.banknote_box.common.model.OutLineNormalResult;
import com.ixilink.banknote_box.common.model.UserSimple;
import com.ixilink.banknote_box.common.pojo.*;
import com.ixilink.banknote_box.common.util.*;
import com.ixilink.banknote_box.service.parameter_model.banknote_line.FindLineModel;
import com.ixilink.banknote_box.service.parameter_model.out_library.*;
import com.ixilink.banknote_box.service.service.OutLibraryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.*;

/**
 * @description:
 * @author: 张皓峰
 * @date: 2019-12-04 15:08
 */
@Slf4j
@Service
public class OutLibraryServiceImpl implements OutLibraryService {

    @Resource
    private OutLibraryTaskMapper outLibraryTaskMapper;
    @Resource
    private OutLibraryLineMapper outLibraryLineMapper;
    @Resource
    private InLibraryTaskMapper inLibraryTaskMapper;
    @Resource
    private InLibraryLineMapper inLibraryLineMapper;
    @Resource
    private OutLibraryLineUserMapper outLibraryLineUserMapper;
    @Resource
    private OutLibraryLineAtmMapper outLibraryLineAtmMapper;
    @Resource
    private OutLibraryLineBoxingUserMapper outLibraryLineBoxingUserMapper;
    @Resource
    private BanknoteLineMapper banknoteLineMapper;
    @Resource
    private EquipmentAtmMapper equipmentAtmMapper;
    @Resource
    private RedisUtil redisUtil;

    @Override
    @Transactional
    public List<LibraryLineModel> importExcel(MultipartFile file, HttpServletRequest request, HttpServletResponse response) {
        // 保存临时存放路径
        String filePath = System.getProperty("user.dir");
        File packages = new File(filePath,"temp");

        if (file == null) return new ArrayList<>();
        String fileName = FileUtil.createName()+FileUtil.getSuffix(file.getOriginalFilename());
        if (!packages.exists()){
            if (!packages.mkdirs()) log.info("创建文件夹失败");
        }
        try {
            //写入临时文件
            file.transferTo(new File(packages.getPath(), fileName));
        } catch (Exception e) {
            throw new BusinessException(Code.UP_DEFEATED.getCode(), Code.UP_DEFEATED.getMessage());
        }
        //临时文件
        File excel = new File(packages.getPath(), fileName);

        String[] titles = {"number","name","channel","totalMoney","unit","totalBox","remarks"};
        int[] otherIndex = {2};
        try {
            ExcelUtil excelUtil = new ExcelUtil(excel,titles,1,3,otherIndex,4,true,"加钞");
            List<ExcelDataModel> allData = excelUtil.getAllData();

            User user = ValidatePermissionsUtil.getUser(request,redisUtil);
            List<LibraryLineModel> list = new ArrayList<>();
            for (ExcelDataModel excelData:allData){
                int totalBox = 0;
                int totalMoney = 0;
                String lineName = excelData.getOther().get("other-2-6").toString();
                Integer batch = Integer.valueOf(excelData.getOther().get("other-2-4").toString());
                List<UserSimple> lineUser = banknoteLineMapper.getUserByLineName(user.getLibraryId(),lineName);

                List<LineAtmModel> atms = new ArrayList<>();
                for (Map<String,Object> map:excelData.getData()){
                    String number = map.get("number").toString();
                    String name = map.get("name").toString();
                    String channel = map.get("channel").toString();
                    Integer totalBox1 = Integer.valueOf(map.get("totalBox").toString());
                    totalBox += totalBox1;
                    Integer unit1 = Integer.valueOf(map.get("unit").toString());
                    Integer totalMoney1 = Integer.valueOf(map.get("totalMoney").toString());
                    totalMoney += totalMoney1;
                    String remarks = map.get("remarks").toString();
                    Map<String, Object> bankBranchAndAtmId = equipmentAtmMapper.getBankBranchAndAtmId(number, channel);
                    if (bankBranchAndAtmId == null || bankBranchAndAtmId.isEmpty()) continue;
                    LineAtmModel lineAtmModel = new LineAtmModel(Integer.valueOf(bankBranchAndAtmId.get("id").toString()),bankBranchAndAtmId.get("bankBranch").toString(),number,name,channel,totalBox1,unit1,totalMoney1,remarks);
                    atms.add(lineAtmModel);
                }
                LibraryLineModel libraryLineModel = new LibraryLineModel(lineName,totalBox,totalMoney,lineUser,batch,"",atms);
                list.add(libraryLineModel);
            }

            FileUtil.del(excel);
            return list;
        } catch (Exception e){
            FileUtil.del(excel);
            throw e;
        }
    }

    @Override
    @Transactional
    public void createTask(Map<String,List<LibraryLineModel>> param, HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<LibraryLineModel> list = param.get("list");
        int totalBox = 0;
        int totalMoney = 0;
        User user = ValidatePermissionsUtil.getUser(request,redisUtil);
        OutLibraryTask task = new OutLibraryTask(null,list.size(),totalBox,totalMoney,null,null,user.getId(),new Date().getTime(),null,null,null,user.getLibraryId());
        int i = outLibraryTaskMapper.insertSelective(task);
        if (i<1) throw new BusinessException(Code.MESSAGE_ERROR.getCode(), "添加任务失败");

        List<OutLibraryLineUser> lineUsers = new ArrayList<>();
        List<OutLibraryLineAtm> lineAtms = new ArrayList<>();

        for (LibraryLineModel lineModel:list){
            totalBox += lineModel.getTotalBox();
            totalMoney += lineModel.getTotalMoney();
            OutLineNormalResult normalResult = new OutLineNormalResult();
            List<Integer> inventoryResult = new ArrayList<>();
            List<Integer> supplementResult = new ArrayList<>();
            for (LineAtmModel atmModel:lineModel.getAtms()){
                String name = atmModel.getName();
                int startIndex = (!name.contains("("))?name.indexOf("（"):name.indexOf("(");
                int endIndex = (!name.contains(")"))?name.indexOf("）"):name.indexOf(")");
                String boxType = name.substring(startIndex+1,endIndex);
                log.debug("钞箱类型："+boxType);
                switch (boxType.trim()){
                    case "黑":
                        normalResult.setDbNum(normalResult.getDbNum()+atmModel.getTotalBox()-1);
                        normalResult.setDbScrapNum(normalResult.getDbScrapNum()+1);
                        break;
                    case "DB":
                        normalResult.setDbNum(normalResult.getDbNum()+atmModel.getTotalBox()-1);
                        normalResult.setDbScrapNum(normalResult.getDbScrapNum()+1);
                        break;
                    case "DF":
                        normalResult.setDfNum(normalResult.getDfNum()+atmModel.getTotalBox()-1);
                        normalResult.setDfScrapNum(normalResult.getDfScrapNum()+1);
                        break;
                    case "CRS":{
                        Integer atmTotalBox = atmModel.getTotalBox();
                        int num = atmModel.getUnit()==0?0:(atmModel.getTotalMoney() / atmModel.getUnit() / 100);
                        normalResult.setCrsNum(normalResult.getCrsNum()+num);
                        normalResult.setCrsScrapNum(normalResult.getCrsScrapNum()+1);
                        normalResult.setCrsEmptyNum(normalResult.getCrsEmptyNum()+(atmTotalBox-num-1));
                        break;}
                    case "SCRS":{
                        Integer atmTotalBox = atmModel.getTotalBox();
                        int num = atmModel.getUnit()==0?0:(atmModel.getTotalMoney() / atmModel.getUnit() / 100);
                        normalResult.setScrsNum(normalResult.getScrsNum()+num);
                        normalResult.setScrsScrapNum(normalResult.getScrsScrapNum()+1);
                        normalResult.setScrsEmptyNum(normalResult.getScrsEmptyNum()+(atmTotalBox-num-1));
                        break;}
                    default:{
                        log.error("未知钞箱种类："+name);
                        throw new BusinessException(Code.MESSAGE_ERROR.getCode(), "未知钞箱种类"+name);
                    }
                }
            }
            OutLibraryLine line = new OutLibraryLine(null,task.getId(),lineModel.getLineName(),lineModel.getTotalBox(),lineModel.getTotalMoney()/10000,lineModel.getBatch(),lineModel.getRemarks(),
                    null,null,null,null,user.getLibraryId(),JsonUtil.obj2str(normalResult),JsonUtil.list2str(inventoryResult),JsonUtil.list2str(supplementResult));
            i = outLibraryLineMapper.insertSelective(line);
            if (i<1) throw new BusinessException(Code.MESSAGE_ERROR.getCode(), "添加任务失败");
            for (UserSimple userSimple:lineModel.getLineUser()){
                OutLibraryLineUser outLibraryLineUser = new OutLibraryLineUser(line.getId(),userSimple.getId(),user.getLibraryId());
                lineUsers.add(outLibraryLineUser);
            }
            for (LineAtmModel atmModel:lineModel.getAtms()){
                /*计算应下架钞箱 start*/
                EquipmentAtm equipmentAtm = equipmentAtmMapper.selectByPrimaryKey(atmModel.getAtmId());
                //应下架钞箱编号集合
                List<String> boxNumbers = new ArrayList<>();
                //应下架钞箱ID集合
                List<Integer> bosIds = new ArrayList<>();
                for (int index=1; index<=5; index++){
                    Integer id = (Integer) ReflexUtil.get(equipmentAtm, "getBox" + index);
                    if (id > 0) bosIds.add(id);
                }
                if (bosIds.size() > 0) {
                    List<Map<String, Object>> boxs = equipmentAtmMapper.findInfoInKeys(bosIds);
                    for (Map<String, Object> box:boxs) boxNumbers.add(box.get("number").toString());
                }
                /*计算应下架钞箱 end*/
                OutLibraryLineAtm atm = new OutLibraryLineAtm(null,line.getId(),atmModel.getAtmId(),atmModel.getTotalBox(),atmModel.getUnit()/100,atmModel.getTotalMoney()/10000,
                        null,atmModel.getRemarks(),null,user.getLibraryId(),JsonUtil.list2str(boxNumbers));
                if (boxNumbers.size()==0) atm.setInState(1);
                lineAtms.add(atm);
            }
        }
        OutLibraryTask task1 = new OutLibraryTask(task.getId(),null,totalBox,totalMoney/10000,null,null,
                null,null,null,null,null,list.get(0).getBatch(),null);
        i = outLibraryTaskMapper.updateByPrimaryKeySelective(task1);
        if (i<1) throw new BusinessException(Code.MESSAGE_ERROR.getCode(), "添加任务失败");

        i = outLibraryLineUserMapper.insertList(lineUsers);
        if (i<1) throw new BusinessException(Code.MESSAGE_ERROR.getCode(), "添加任务失败");

        i = outLibraryLineAtmMapper.insertList(lineAtms);
        if (i<1) throw new BusinessException(Code.MESSAGE_ERROR.getCode(), "添加任务失败");
    }

    @Override
    public List<OutLibraryTask> findTask(FindTaskModel param, HttpServletRequest request, HttpServletResponse response) {
        User user = ValidatePermissionsUtil.getUser(request,redisUtil);
        OutLibraryTaskExample taskExample = new OutLibraryTaskExample();
        OutLibraryTaskExample.Criteria criteria = taskExample.createCriteria();
        criteria.andLibraryIdEqualTo(user.getLibraryId());
        /*if (param.getState()==0){
            criteria.andApplyStateEqualTo(0);
        }else {
            criteria.andTaskStateEqualTo(1);
        }*/
        if (param.getApplyState() != null) criteria.andApplyStateEqualTo(param.getApplyState());
        if (param.getCheckState() != null) criteria.andCheckStateEqualTo(param.getCheckState());
        if (param.getBoxingState() != null) criteria.andBoxingStateEqualTo(param.getBoxingState());
        if (param.getTaskState() != null) criteria.andTaskStateEqualTo(param.getTaskState());

        PageHelper.startPage(param.getPageNum(), param.getPageSize());
        return outLibraryTaskMapper.selectByExample(taskExample);
    }

    @Override
    public List<OutLibraryTask> findHandoverTask(Integer checkSchedule, HttpServletRequest request, HttpServletResponse response) {
        User user = ValidatePermissionsUtil.getUser(request,redisUtil);
        String idStr = outLibraryLineMapper.getTaskIdsByCheckSchedule(user.getLibraryId(), checkSchedule);
        List<Integer> ids = JsonUtil.jackson2other(idStr,List.class,Integer.class);
        if (ids.size() == 0) return new ArrayList<>();

        OutLibraryTaskExample outLibraryTaskExample = new OutLibraryTaskExample();
        outLibraryTaskExample.createCriteria().andTaskStateEqualTo(1)
                .andTaskScheduleEqualTo(2)
                .andApplyStateEqualTo(1).andIdIn(ids)
                .andCreateTimeGreaterThan(DateFormatUtil.strToDate(DateFormatUtil.getStringDateShort()).getTime());
        return outLibraryTaskMapper.selectByExample(outLibraryTaskExample);
    }


    @Override
    public Map<String, Object> findTaskDetails(Integer id, HttpServletRequest request, HttpServletResponse response) throws Exception {
        OutLibraryTask task = outLibraryTaskMapper.selectByPrimaryKey(id);
        return ObjectOrMapUtil.objectToMap(task);
    }

    @Override
    public List<Map<String,Object>> findLine(FindLineModel param, HttpServletRequest request, HttpServletResponse response) {
        User user = ValidatePermissionsUtil.getUser(request,redisUtil);
//        String username = "";
//        if (param.getUsername() != null) username = "%"+param.getUsername()+"%";
        if (param.getUsers()!=null && param.getUsers().size()==0) {
            param.setUsers(null);
        }
        PageHelper.startPage(param.getPageNum(), param.getPageSize());
        return outLibraryLineMapper.findLine(user.getLibraryId(), param.getTaskId(), param.getUsers());
    }

    @Override
    public List<Map<String, Object>> findBoxingLine(FindLineModel param, HttpServletRequest request, HttpServletResponse response) throws Exception {
        PageHelper.startPage(param.getPageNum(), param.getPageSize());
        List<Map<String, Object>> line = outLibraryLineMapper.findBoxingLine(param.getTaskId());
        for (Map<String,Object> l: line){
            @SuppressWarnings("all")
            Map<String,Object> normalResult = (HashMap<String,Object>)JsonUtil.str2Obj(l.get("normalResult").toString(), HashMap.class);
            normalResult.put("dbTotalMoney",(int)normalResult.get("dbNum")*20/*+(int)normalResult.get("dbScrapNum")*/);
            normalResult.put("dfTotalMoney",(int)normalResult.get("dfNum")*20/*+(int)normalResult.get("dfScrapNum")*/);
            normalResult.put("crsTotalMoney",(int)normalResult.get("crsNum")*20/*+((int)normalResult.get("crsEmptyNum")+(int)normalResult.get("crsScrapNum"))*/);
            normalResult.put("scrsTotalMoney",(int)normalResult.get("scrsNum")*20/*+((int)normalResult.get("scrsEmptyNum")+(int)normalResult.get("scrsScrapNum"))*/);
            l.putAll(normalResult);
            l.remove("normalResult");
        }
        return line;
    }

    @Override
    public List<OutLibraryLine> findHandoverLine(FindHandoverLineModel param, HttpServletRequest request, HttpServletResponse response) {
        OutLibraryLineExample outLibraryLineExample = new OutLibraryLineExample();
        outLibraryLineExample.createCriteria().andTaskIdEqualTo(param.getTaskId()).andCheckScheduleEqualTo(param.getCheckSchedule());
        /*if (param.getCheckSchedule().equals(0)){
            outLibraryLineExample.getCriteria().andCheckStateNotEqualTo(1);
        }
        else if (param.getCheckSchedule().equals(1)){
            outLibraryLineExample.getCriteria().andHandoverCheckStateNotEqualTo(1);
        }
        else if (param.getCheckSchedule().equals(2)){
            outLibraryLineExample.getCriteria().andAddCheckStateNotEqualTo(1);
        }*/
        /*if (param.getLineName() !=null && !"".equals(param.getLineName())){
            outLibraryLineExample.getCriteria().andLineNameLike("%"+param.getLineName()+"");
        }*/
        return outLibraryLineMapper.selectByExample(outLibraryLineExample);
    }

    @Override
    public Map<String, Object> findLineDetails(Integer id, HttpServletRequest request, HttpServletResponse response) throws Exception {
        OutLibraryLine outLibraryLine = outLibraryLineMapper.selectByPrimaryKey(id);
        String username = outLibraryLineMapper.getAddMoneyUser(id);
        OutLineNormalResult normalResult = (OutLineNormalResult)JsonUtil.str2Obj(outLibraryLine.getNormalResult(), OutLineNormalResult.class);
//        Map<String, Object> normalMap = ObjectOrMapUtil.objectToMap(normalResult);
        Map<String, Object> line = ObjectOrMapUtil.objectToMap(outLibraryLine);
        line.put("dbCount",normalResult.getDbNum()+normalResult.getDbScrapNum());
        line.put("dbMoney",normalResult.getDbNum()*20);
        line.put("dfCount",normalResult.getDfNum()+normalResult.getDfScrapNum());
        line.put("dfMoney",normalResult.getDfNum()*20);
        line.put("crsCount",normalResult.getCrsNum()+normalResult.getCrsEmptyNum()+normalResult.getCrsScrapNum());
        line.put("crsMoney",normalResult.getCrsNum()*20);
        line.put("scrsCount",normalResult.getScrsNum()+normalResult.getScrsEmptyNum()+normalResult.getScrsScrapNum());
        line.put("scrsMoney",normalResult.getScrsNum()*20);

        line.put("totalBox",outLibraryLine.getTotalBox());
        line.put("totalMoney",outLibraryLine.getTotalMoney());

        line.put("username",username);
        return line;
    }

    @Override
    public List<Map<String, Object>> findLineAtm(FindLineAtmModel param, HttpServletRequest request, HttpServletResponse response) {
        if (!param.getBankBranch().equals(""))param.setBankBranch("%"+param.getBankBranch()+"%");
        if (!param.getNumber().equals(""))param.setNumber("%"+param.getNumber()+"%");
        PageHelper.startPage(param.getPageNum(), param.getPageSize());
        return outLibraryLineAtmMapper.findLineAtm(param.getOutLineId(),param.getBankBranch(),param.getNumber());
    }

    @Override
    @Transactional
    public void approvalTask(Map<String, Object> param, HttpServletRequest request, HttpServletResponse response) {
        User user = ValidatePermissionsUtil.getUser(request,redisUtil);
        OutLibraryTask task = new OutLibraryTask();
        task.setId(Integer.valueOf(param.get("id").toString()));
        task.setTaskSchedule(2);
        task.setApplyState(Integer.valueOf(param.get("approvalState").toString()));
        task.setApprovalReason(param.get("approvalReason")==null?"":param.get("approvalReason").toString());
        task.setApplyUser(user.getId());
        int i = outLibraryTaskMapper.updateByPrimaryKeySelective(task);
        if (i<1) throw new BusinessException(Code.MESSAGE_ERROR.getCode(), "审批任务失败");

        //审批通过则创建入库任务
        if (Integer.valueOf(param.get("approvalState").toString())==1){
            task = outLibraryTaskMapper.selectByPrimaryKey(task.getId());
            InLibraryTask inLibraryTask = new InLibraryTask(task.getId(),task.getPublisher(),new Date().getTime(),null,null,null,null,null,task.getLibraryId());
            i = inLibraryTaskMapper.insertSelective(inLibraryTask);
            if (i<1) throw new BusinessException(Code.MESSAGE_ERROR.getCode(), "创建入库任务失败");

            OutLibraryLineExample outLibraryLineExample = new OutLibraryLineExample();
            outLibraryLineExample.createCriteria().andTaskIdEqualTo(task.getId());
            List<OutLibraryLine> outLibraryLines = outLibraryLineMapper.selectByExample(outLibraryLineExample);

            int overCount = 0;
            for (OutLibraryLine outLibraryLine : outLibraryLines){
                String atmIds = outLibraryLineMapper.getAtmIdsByLine(outLibraryLine.getId());
                List<Integer> ids = JsonUtil.jackson2other(atmIds,List.class,Integer.class);
                //如果没有则跳过
                if (ids.size()==0) continue;
                EquipmentAtmExample equipmentAtmExample = new EquipmentAtmExample();
                equipmentAtmExample.createCriteria().andIdIn(ids);
                List<EquipmentAtm> equipmentAtms = equipmentAtmMapper.selectByExample(equipmentAtmExample);
                int totalBox = 0;
                List<Integer> boxIds = new ArrayList<>();
                for (EquipmentAtm atm:equipmentAtms){
                    if (atm.getBox1() !=0) {
                        totalBox++;
                        boxIds.add(atm.getBox1());
                    }
                    if (atm.getBox2() !=0) {
                        totalBox++;
                        boxIds.add(atm.getBox2());
                    }
                    if (atm.getBox3() !=0) {
                        totalBox++;
                        boxIds.add(atm.getBox3());
                    }
                    if (atm.getBox4() !=0) {
                        totalBox++;
                        boxIds.add(atm.getBox4());
                    }
                    if (atm.getBox5() !=0) {
                        totalBox++;
                        boxIds.add(atm.getBox5());
                    }
                }
                List<Map<String, Object>> inventory = new ArrayList<>();
                if (boxIds.size()>0){
                    inventory = outLibraryLineMapper.inventoryByIds(boxIds);
                }
                //各类钞箱应有数量
                OutLineNormalResult normalResult = new OutLineNormalResult(inventory);
                //入库线路详情
                InLibraryLine inLibraryLine = new InLibraryLine(outLibraryLine.getId(),task.getId(),outLibraryLine.getLineName(),totalBox,outLibraryLine.getLibraryId(),JsonUtil.obj2str(normalResult),"[]","[]");

                OutLibraryLineAtmExample example = new OutLibraryLineAtmExample();
                example.createCriteria().andLineIdEqualTo(outLibraryLine.getId()).andStateEqualTo(1);
                //总条数
                long sumCount = outLibraryLineAtmMapper.countByExample(example);
                example.getCriteria().andInStateEqualTo(0);
                //未完成条数
                long noOverAtmCount = outLibraryLineAtmMapper.countByExample(example);
                if (sumCount > 0 && noOverAtmCount == 0) {
                    inLibraryLine.setCheckState(1);
                    inLibraryLine.setFaceState(1);
                    inLibraryLine.setLineState(1);
                    overCount++;
                }
                i = inLibraryLineMapper.insertSelective(inLibraryLine);
                if (i<1) throw new BusinessException(Code.MESSAGE_ERROR.getCode(), "创建入库任务失败");
            }
            if (overCount == outLibraryLines.size()){
                inLibraryTask.setTaskSchedule(4);
                inLibraryTask.setTaskState(1);
                inLibraryTask.setDownTime(new Date().getTime());
                inLibraryTask.setHandoverTime(new Date().getTime());
                inLibraryTask.setReceiveTime(new Date().getTime());
                i = inLibraryTaskMapper.updateByPrimaryKeySelective(inLibraryTask);
                if (i<1) throw new BusinessException(Code.MESSAGE_ERROR.getCode(), "创建入库任务失败");
            }
        }
    }

    @Override
    public void boxing(Map<String, Integer> param, HttpServletRequest request, HttpServletResponse response) {
        User user = ValidatePermissionsUtil.getUser(request,redisUtil);
        OutLibraryLine line = outLibraryLineMapper.selectByPrimaryKey(param.get("id"));
        if (param.get("boxingState") == 0){
            if (line == null )throw new BusinessException(Code.MESSAGE_ERROR.getCode(), "该出库线路不存在");
            if (line.getBoxingState() == 2) throw new BusinessException(Code.MESSAGE_ERROR.getCode(), "该出库线路已完成配箱");
            OutLibraryLineBoxingUserExample outLibraryLineBoxingUserExample = new OutLibraryLineBoxingUserExample();
            outLibraryLineBoxingUserExample.createCriteria().andLineIdEqualTo(param.get("id"));
            outLibraryLineBoxingUserMapper.deleteByExample(outLibraryLineBoxingUserExample);
        }
        else if (param.get("boxingState")==1){
            if (line == null )throw new BusinessException(Code.MESSAGE_ERROR.getCode(), "该出库线路不存在");
            if (line.getBoxingState() == 1) throw new BusinessException(Code.MESSAGE_ERROR.getCode(), "该出库线路已在其他配箱组进行");
            if (line.getBoxingState() == 2) throw new BusinessException(Code.MESSAGE_ERROR.getCode(), "该出库线路已完成配箱");
            OutLibraryLineBoxingUser boxingUser = new OutLibraryLineBoxingUser(param.get("id"),user.getId());
            outLibraryLineBoxingUserMapper.insert(boxingUser);
            boxingUser = new OutLibraryLineBoxingUser(param.get("id"),user.getPartnerId());
            outLibraryLineBoxingUserMapper.insert(boxingUser);
        }
        else if (param.get("boxingState")==2){
            if (line == null )throw new BusinessException(Code.MESSAGE_ERROR.getCode(), "该出库线路不存在");
//            if (line.getBoxingState() == 0) throw new BusinessException(Code.MESSAGE_ERROR.getCode(), "错误操作，请先确认配箱任务");
            if (line.getBoxingState() == 2) throw new BusinessException(Code.MESSAGE_ERROR.getCode(), "请勿重复提交");
        }
        OutLibraryLine outLibraryLine = new OutLibraryLine();
        outLibraryLine.setId(param.get("id"));
        outLibraryLine.setBoxingState(param.get("boxingState"));
        int i = outLibraryLineMapper.updateByPrimaryKeySelective(outLibraryLine);
        if (i<1) throw new BusinessException(Code.MESSAGE_ERROR.getCode(), "提交失败");

        OutLibraryLineExample outLibraryLineExample = new OutLibraryLineExample();
        outLibraryLineExample.createCriteria().andTaskIdEqualTo(line.getTaskId()).andBoxingStateLessThanOrEqualTo(1);
        long count = outLibraryLineMapper.countByExample(outLibraryLineExample);
        if (count == 0){
            OutLibraryTask task = new OutLibraryTask();
            task.setId(line.getTaskId());
            task.setBoxingState(1);
            i = outLibraryTaskMapper.updateByPrimaryKeySelective(task);
            if (i<1) throw new BusinessException(Code.MESSAGE_ERROR.getCode(), "提交失败");
        }
    }

    @Override
    public Map<String, Object> details(Integer id, HttpServletRequest request, HttpServletResponse response) {
        User user = ValidatePermissionsUtil.getUser(request,redisUtil);
        //出库线路详情
        OutLibraryLine line = outLibraryLineMapper.selectByPrimaryKey(id);
        List<String> inventoryBoxNumbers = JsonUtil.jackson2other(line.getInventoryResult(), List.class, String.class);
        List<String> supplementBoxNumbers = JsonUtil.jackson2other(line.getSupplementResult(), List.class, String.class);
        inventoryBoxNumbers.addAll(supplementBoxNumbers);

        List<Map<String, Object>> details = new ArrayList<>();
        if (inventoryBoxNumbers.size()>0){
            details = outLibraryLineMapper.details(user.getLibraryId(),inventoryBoxNumbers);
        }
        Map<String, Object> map = new HashMap<>();
        List<String> db = new ArrayList<>();
        List<String> dbScrap = new ArrayList<>();
        List<String> df = new ArrayList<>();
        List<String> dfScrap = new ArrayList<>();
        List<String> crs = new ArrayList<>();
        List<String> crsScrap = new ArrayList<>();
        List<String> scrs = new ArrayList<>();
        List<String> scrsScrap = new ArrayList<>();
        map.put("db",db);
        map.put("dbScrap",dbScrap);
        map.put("df",df);
        map.put("dfScrap",dfScrap);
        map.put("crs",crs);
        map.put("crsScrap",crsScrap);
        map.put("scrs",scrs);
        map.put("scrsScrap",scrsScrap);
        for (Map<String, Object> d:details){
            switch (d.get("type").toString()){
                case "1":
                    db.add(d.get("number").toString());
                    break;
                case "2":
                    dbScrap.add(d.get("number").toString());
                    break;
                case "3":
                    df.add(d.get("number").toString());
                    break;
                case "4":
                    dfScrap.add(d.get("number").toString());
                    break;
                case "5":
                    crs.add(d.get("number").toString());
                    break;
                case "6":
                    crsScrap.add(d.get("number").toString());
                    break;
                case "7":
                    scrs.add(d.get("number").toString());
                    break;
                case "8":
                    scrsScrap.add(d.get("number").toString());
                    break;
            }
        }
        return map;
    }

    @Override
    public Map<String, Object> supplementBox(String number, HttpServletRequest request, HttpServletResponse response) {
        User user = ValidatePermissionsUtil.getUser(request,redisUtil);
        int i = outLibraryLineMapper.hasBoxByNumber(user.getLibraryId(), number);
        if (i < 1) throw new BusinessException(Code.MESSAGE_ERROR.getCode(), "钞箱不存在");
        Map<String, Object> result = outLibraryLineMapper.supplementBox(user.getLibraryId(), number);
        int state = (int) result.get("state");
        result.remove("state");
        if (2 < state && state < 6) throw new BusinessException(Code.MESSAGE_ERROR.getCode(),"该钞箱已被使用");
        if (state == 7) throw new BusinessException(Code.MESSAGE_ERROR.getCode(),"该钞箱还未清机入库，不能使用");
        return result;
    }

    @Override
    @Transactional
    public void supplement(Map<String, Object> param, HttpServletRequest request, HttpServletResponse response) {
        User user = ValidatePermissionsUtil.getUser(request,redisUtil);
        String number = param.get("number").toString();
        int i = outLibraryLineMapper.hasBoxByNumber(user.getLibraryId(), number);
        if (i < 1) throw new BusinessException(Code.MESSAGE_ERROR.getCode(), "钞箱不存在");
        Map<String, Object> result = outLibraryLineMapper.supplementBox(user.getLibraryId(), number);
        int state = (int) result.get("state");
        if (2 < state && state < 6) throw new BusinessException(Code.MESSAGE_ERROR.getCode(),"该钞箱已被使用");
        if (state == 7) throw new BusinessException(Code.MESSAGE_ERROR.getCode(),"该钞箱还未清机入库，不能使用");

        //出库线路详情
        OutLibraryLine line = outLibraryLineMapper.selectByPrimaryKey((Integer) param.get("id"));
        List<String> inventoryBoxNumbers = JsonUtil.jackson2other(line.getInventoryResult(), List.class, String.class);
        List<String> supplementBoxNumbers = JsonUtil.jackson2other(line.getSupplementResult(), List.class, String.class);
        if (!supplementBoxNumbers.contains(number)){
            supplementBoxNumbers.add(number);
        }

        //计算
        inventoryBoxNumbers.addAll(supplementBoxNumbers);
        //盘点数量
        List<Map<String, Object>> inventory = new ArrayList<>();
        if (inventoryBoxNumbers.size()>0){
            inventory = outLibraryLineMapper.inventory(user.getLibraryId(), inventoryBoxNumbers);
        }
        //实际数量格式化
        OutLineNormalResult inventoryResult = new OutLineNormalResult(inventory);
        //应有数量
        OutLineNormalResult normalResult = (OutLineNormalResult)JsonUtil.str2Obj(line.getNormalResult(), OutLineNormalResult.class);

        //盘点状态
        int inventoryState = 1;
        if (!inventoryResult.toString().equals(normalResult.toString())){
            inventoryState = 2;
        }

        //更新
        OutLibraryLine outLibraryLine = new OutLibraryLine();
        outLibraryLine.setId(line.getId());
        outLibraryLine.setSupplementResult(JsonUtil.list2str(supplementBoxNumbers));
        if (inventoryState == 1){
            if (line.getCheckSchedule()==0) outLibraryLine.setCheckState(inventoryState);
            else if (line.getCheckSchedule()==1) outLibraryLine.setHandoverCheckState(inventoryState);
            else if (line.getCheckSchedule()==2) outLibraryLine.setAddCheckState(inventoryState);
        }
        i = outLibraryLineMapper.updateByPrimaryKeySelective(outLibraryLine);
        if (i<1) throw new BusinessException(Code.MESSAGE_ERROR.getCode(), "补录数据出错");
    }

    @Override
    public void next(Map<String, Integer> param, HttpServletRequest request, HttpServletResponse response) {
        User user = ValidatePermissionsUtil.getUser(request,redisUtil);
        OutLibraryLineExample outLibraryLineExample = new OutLibraryLineExample();
        outLibraryLineExample.createCriteria().andLibraryIdEqualTo(user.getLibraryId()).andTaskIdEqualTo(param.get("id"));
        outLibraryLineExample.getCriteria().andCheckScheduleEqualTo(param.get("type"));
        if (param.get("type") == 1){
            outLibraryLineExample.getCriteria().andHandoverCheckStateEqualTo(1);
        }
        else if (param.get("type") == 2){
            outLibraryLineExample.getCriteria().andAddCheckStateEqualTo(1);
        }
        long count = outLibraryLineMapper.countByExample(outLibraryLineExample);
        if (count==0) throw new BusinessException(Code.MESSAGE_ERROR.getCode(), "没有可交接的线路，请先验证线路钞箱");
    }

    @Override
    public Map<String, Object> findTaskDetailsAll(HttpServletRequest request, HttpServletResponse response) {
        int totalBox = 0;
        int totalMoney = 0;
        int totalLine = 0;

        int dbCount = 0;
        int dbMoney = 0;
        int dfCount = 0;
        int dfMoney = 0;
        int crsCount = 0;
        int crsMoney = 0;
        int scrsCount = 0;
        int scrsMoney = 0;

        OutLibraryTaskExample outLibraryTaskExample = new OutLibraryTaskExample();
        outLibraryTaskExample.createCriteria().andCheckStateEqualTo(0);
        List<OutLibraryTask> tasks = outLibraryTaskMapper.selectByExample(outLibraryTaskExample);
        List<Integer> taskIds = new ArrayList<>();
        tasks.forEach(task ->{
            taskIds.add(task.getId());
        });
        if (taskIds.size()>0){
            OutLibraryLineExample outLibraryLineExample = new OutLibraryLineExample();
            List<OutLibraryLine> outLibraryLines = outLibraryLineMapper.selectByExampleWithBLOBs(outLibraryLineExample);
            for (OutLibraryLine outLibraryLine:outLibraryLines) {
                OutLineNormalResult normalResult = (OutLineNormalResult) JsonUtil.str2Obj(outLibraryLine.getNormalResult(), OutLineNormalResult.class);
                dbCount += normalResult.getDbNum() + normalResult.getDbScrapNum();
                dbMoney += normalResult.getDbNum()*20;
                dfCount += normalResult.getDfNum()+normalResult.getDfScrapNum();
                dfMoney += normalResult.getDfNum()*20;
                crsCount += normalResult.getCrsNum()+normalResult.getCrsEmptyNum()+normalResult.getCrsScrapNum();
                crsMoney += normalResult.getCrsNum()*20;
                scrsCount += normalResult.getScrsNum()+normalResult.getScrsEmptyNum()+normalResult.getScrsScrapNum();
                scrsMoney += normalResult.getScrsNum()*20;
                totalBox += outLibraryLine.getTotalBox();
                totalMoney += outLibraryLine.getTotalMoney();
            }
            totalLine = outLibraryLines.size();
        }
        Map<String, Object> result = new HashMap<>();
        result.put("dbCount",dbCount);
        result.put("dbMoney",dbMoney);
        result.put("dfCount",dfCount);
        result.put("dfMoney",dfMoney);
        result.put("crsCount",crsCount);
        result.put("crsMoney",crsMoney);
        result.put("scrsCount",scrsCount);
        result.put("scrsMoney",scrsMoney);

        result.put("totalBox",totalBox);
        result.put("totalMoney",totalMoney);
        result.put("totalLine",totalLine);

        return result;
    }


}
