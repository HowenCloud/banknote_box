package com.ixilink.banknote_box.service.service.impl;

import com.github.pagehelper.PageHelper;
import com.ixilink.banknote_box.common.dao.*;
import com.ixilink.banknote_box.common.exception.BusinessException;
import com.ixilink.banknote_box.common.model.Code;
import com.ixilink.banknote_box.common.model.OutLineNormalResult;
import com.ixilink.banknote_box.common.model.UserSimple;
import com.ixilink.banknote_box.common.pojo.*;
import com.ixilink.banknote_box.common.util.*;
import com.ixilink.banknote_box.service.parameter_model.banknote_line.FindLineModel;
import com.ixilink.banknote_box.service.parameter_model.in_library.FindAtmModel;
import com.ixilink.banknote_box.service.parameter_model.in_library.FindBoxModel;
import com.ixilink.banknote_box.service.parameter_model.in_library.FindTaskModel;
import com.ixilink.banknote_box.service.parameter_model.in_library.SupplementBox;
import com.ixilink.banknote_box.service.service.InLibraryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * @description:
 * @author: 张皓峰
 * @date: 2020-01-10 14:42
 */
@Service
public class InLibraryServiceImpl implements InLibraryService {

    @Resource
    private OutLibraryTaskMapper outLibraryTaskMapper;
    @Resource
    private OutLibraryLineMapper outLibraryLineMapper;
    @Resource
    private OutLibraryLineUserMapper outLibraryLineUserMapper;
    @Resource
    private OutLibraryLineAtmMapper outLibraryLineAtmMapper;
    @Resource
    private InLibraryTaskMapper inLibraryTaskMapper;
    @Resource
    private InLibraryLineMapper inLibraryLineMapper;
    @Resource
    private UserMapper userMapper;
    @Resource
    private RedisUtil redisUtil;

    @Override
    public List<InLibraryTask> findTask(FindTaskModel param, HttpServletRequest request, HttpServletResponse response) {
        User user = ValidatePermissionsUtil.getUser(request,redisUtil);
        InLibraryTaskExample inLibraryTaskExample = new InLibraryTaskExample();
        inLibraryTaskExample.createCriteria().andLibraryIdEqualTo(user.getLibraryId());
        inLibraryTaskExample.setOrderByClause("task_state");
        PageHelper.startPage(param.getPageNum(), param.getPageSize());
        List<InLibraryTask> inLibraryTasks = inLibraryTaskMapper.selectByExample(inLibraryTaskExample);
        setOtherValue(inLibraryTasks);
        return inLibraryTasks;
    }

    @Override
    public Map<String, Object> findInTaskDetails(Integer id, HttpServletRequest request, HttpServletResponse response) throws Exception {
        InLibraryTask inLibraryTask = inLibraryTaskMapper.selectByPrimaryKey(id);

        User user = userMapper.selectByPrimaryKey(inLibraryTask.getPublisher());
        inLibraryTask.setPublisherName(user.getName());

        OutLibraryTask outLibraryTask = outLibraryTaskMapper.selectByPrimaryKey(id);
        inLibraryTask.setBatch(outLibraryTask.getBatch());

        InLibraryLineExample inLibraryLineExample = new InLibraryLineExample();
        long totalLine = inLibraryLineMapper.countByExample(inLibraryLineExample);
        int totalBox = inLibraryLineMapper.getBoxCount(id);

        Map<String, Object> map = ObjectOrMapUtil.objectToMap(inLibraryTask);
        map.put("totalLine",totalLine);
        map.put("totalBox",totalBox);
        return map;
    }

    @Override
    public List<UserSimple> getLineUser(Integer id, HttpServletRequest request, HttpServletResponse response) {
        return outLibraryLineUserMapper.getLineUser(id);
    }

    @Override
    public List<Map<String, Object>> findInLine(FindLineModel param, HttpServletRequest request, HttpServletResponse response) {
        User user = ValidatePermissionsUtil.getUser(request,redisUtil);
        PageHelper.startPage(param.getPageNum(), param.getPageSize());
        List<Map<String, Object>> lines = inLibraryLineMapper.findLine(user.getLibraryId(), param.getTaskId(), param.getUsers());
        for (Map<String, Object> map:lines){
            OutLibraryLine line = outLibraryLineMapper.selectByPrimaryKey((int) map.get("id"));
            map.put("batch",line.getBatch());
        }
        return lines;
    }

    @Override
    public Map<String, Object> findInLineDetails(Integer id, HttpServletRequest request, HttpServletResponse response) {
        InLibraryLine inLibraryLine = inLibraryLineMapper.selectByPrimaryKey(id);
        OutLibraryLine outLibraryLine = outLibraryLineMapper.selectByPrimaryKey(id);
        OutLineNormalResult normalResult = (OutLineNormalResult)JsonUtil.str2Obj(inLibraryLine.getNormalResult(), OutLineNormalResult.class);
        Map<String,Object> result = new HashMap<>();
        result.put("db",normalResult.getDbNum()+normalResult.getDbScrapNum());
        result.put("df",normalResult.getDfNum()+normalResult.getDfScrapNum());
        result.put("crs",normalResult.getCrsNum()+normalResult.getCrsEmptyNum()+normalResult.getCrsScrapNum());
        result.put("scrs",normalResult.getScrsNum()+normalResult.getScrsEmptyNum()+normalResult.getScrsScrapNum());
        result.put("totalBox",inLibraryLine.getTotalBox());
        result.put("batch",outLibraryLine.getBatch());
        return result;
    }

    @Override
    public List<Map<String, Object>> findAtm(FindAtmModel param, HttpServletRequest request, HttpServletResponse response) {
        if (param.getNumber() != null) param.setNumber("%"+param.getNumber()+"%");
        PageHelper.startPage(param.getPageNum(), param.getPageSize());
        List<Map<String, Object>> atms = inLibraryLineMapper.findAtm(param.getLineId(), param.getNumber());
        for (Map<String, Object> map:atms){
            List<String> list = JsonUtil.jackson2other(map.get("takeBox").toString(),List.class,String.class);
            map.put("takeBox",list);
        }
        return atms;
    }

    @Override
    public List<Map<String, Object>> findNoDepositAtm(FindAtmModel param, HttpServletRequest request, HttpServletResponse response) {
        if (param.getNumber() != null) param.setNumber("%"+param.getNumber()+"%");
        PageHelper.startPage(param.getPageNum(), param.getPageSize());
        return inLibraryLineMapper.findNoDepositAtm(param.getLineId(), param.getNumber());
    }

    @Override
    public List<Map<String, Object>> findNoDepositBox(FindBoxModel param, HttpServletRequest request, HttpServletResponse response) throws Exception {
        OutLibraryLine outLibraryLine = outLibraryLineMapper.selectByPrimaryKey(param.getLineId());
        String addMoneyUser = outLibraryLineMapper.getAddMoneyUser(param.getLineId());
        //该线路所有钞箱
        List<String> all = JsonUtil.jackson2other(outLibraryLine.getInventoryResult(),List.class,String.class);
        List<String> supplementResult = JsonUtil.jackson2other(outLibraryLine.getSupplementResult(),List.class,String.class);
        all.addAll(supplementResult);

        OutLibraryLineAtmExample example = new OutLibraryLineAtmExample();
        example.createCriteria().andScheduleGreaterThan(0);
        List<OutLibraryLineAtm> lineAtms = outLibraryLineAtmMapper.selectByExample(example);

//        List<String> useBox = new ArrayList<>();
        for (OutLibraryLineAtm outLibraryLineAtm:lineAtms){
            List<String> deposit = JsonUtil.jackson2other(outLibraryLineAtm.getDepositBox(),List.class,String.class);
            List<String> supplement = JsonUtil.jackson2other(outLibraryLineAtm.getSupplementBox(),List.class,String.class);
//            useBox.addAll(deposit);
//            useBox.addAll(supplement);
            all.removeAll(deposit);
            all.removeAll(supplement);
        }
//        all.removeAll(useBox);
        if (param.getNumber() != null && !"".equals(param.getNumber())){
            param.setNumber("%"+param.getNumber()+"%");
        }
        if (all.size() == 0) all.add("");
        PageHelper.startPage(param.getPageNum(), param.getPageSize());
        List<Map<String,Object>> list = inLibraryLineMapper.findNoDepositBox(param.getType(),param.getNumber(),all);
        for (Map<String,Object> map:list){
            if (map.get("boxType").equals(1) || map.get("boxType").equals(2)) map.put("boxType","DB钞箱");
            else if (map.get("boxType").equals(3) || map.get("boxType").equals(4)) map.put("boxType","DF钞箱");
            else if (map.get("boxType").equals(5) || map.get("boxType").equals(6)) map.put("boxType","CRS钞箱");
            else if (map.get("boxType").equals(7) || map.get("boxType").equals(8)) map.put("boxType","SCRS钞箱");
            map.put("addMoneyUser",addMoneyUser);
        }
        return list;
    }

    @Override
    @Transactional
    public void getBox(Integer id, HttpServletRequest request, HttpServletResponse response) {
        InLibraryLine inLibraryLine1 = inLibraryLineMapper.selectByPrimaryKey(id);
        if (inLibraryLine1.getFaceState() == 0) throw new BusinessException(Code.MESSAGE_ERROR.getCode(),"还未交接完成，请先到交接岗完成交接");
        else if (inLibraryLine1.getLineState() == 1) throw new BusinessException(Code.MESSAGE_ERROR.getCode(),"已经接收，请不要重复提交");

        InLibraryLineExample inLibraryLineExample = new InLibraryLineExample();
        inLibraryLineExample.createCriteria().andTaskIdEqualTo(inLibraryLine1.getTaskId());
        long sumCount = inLibraryLineMapper.countByExample(inLibraryLineExample);
        inLibraryLineExample.getCriteria().andLineStateEqualTo(1);
        long overCount = inLibraryLineMapper.countByExample(inLibraryLineExample);

        InLibraryLine inLibraryLine = new InLibraryLine();
        inLibraryLine.setId(id);
        inLibraryLine.setLineState(1);
        inLibraryLineMapper.updateByPrimaryKeySelective(inLibraryLine);

        //如果所有线路都已接收，则任务改为已接收
        if (sumCount - overCount == 1){
            InLibraryTask inLibraryTask = new InLibraryTask();
            inLibraryTask.setId(inLibraryLine1.getTaskId());
            inLibraryTask.setTaskState(1);
            inLibraryTask.setTaskSchedule(4);
            inLibraryTaskMapper.updateByPrimaryKeySelective(inLibraryTask);
        }
    }

    @Override
    public List<InLibraryTask> findHandoverTask(HttpServletRequest request, HttpServletResponse response) {
        User user = ValidatePermissionsUtil.getUser(request,redisUtil);
        InLibraryTaskExample inLibraryTaskExample = new InLibraryTaskExample();
        inLibraryTaskExample.createCriteria().andLibraryIdEqualTo(user.getLibraryId())
                //只3查询当天
//                .andCreateTimeGreaterThan(DateFormatUtil.strToDate(DateFormatUtil.getStringDateShort()).getTime())
                ;
        inLibraryTaskExample.setOrderByClause(" task_state ");
        List<InLibraryTask> inLibraryTasks = inLibraryTaskMapper.selectByExample(inLibraryTaskExample);
        setOtherValue(inLibraryTasks);
        return inLibraryTasks;
    }

    @Override
    public List<Map<String, Object>> findHandoverLine(Integer taskId, HttpServletRequest request, HttpServletResponse response) {
        User user = ValidatePermissionsUtil.getUser(request,redisUtil);
        return inLibraryLineMapper.findLine(user.getLibraryId(), taskId, null);
    }

    @Override
    public List<Map<String, Object>> findHandoverTakeBox(Integer lineId, HttpServletRequest request, HttpServletResponse response) {
        List<Map<String, Object>> atms = inLibraryLineMapper.findAtm(lineId, null);
        for (Map<String, Object> map:atms){
            Set<String> noCheck = new HashSet<>();
            //应该卸出钞箱
            List<String> takeBox = JsonUtil.jackson2other(map.get("takeBox").toString(),List.class,String.class);
            List<String> originalTakeBox = JsonUtil.jackson2other(map.get("originalTakeBox").toString(),List.class,String.class);
            for (String str:originalTakeBox){
                if (!takeBox.contains(str))noCheck.add(str);
            }
            map.remove("takeBox");
            map.remove("originalTakeBox");
            map.put("check",takeBox);
            map.put("noCheck",noCheck);
        }
        return atms;
    }

    @Override
    public Map<String, Object> findHandoverNoDeposit(Integer lineId, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> data = new HashMap<>();
        List<Map<String, Object>> noDepositAtm = inLibraryLineMapper.findNoDepositAtm(lineId, null);
        List<Map<String, Object>> box = new ArrayList<>();

        InLibraryLine line = inLibraryLineMapper.selectByPrimaryKey(lineId);
        List<String> inventory = JsonUtil.jackson2other(line.getInventoryResult(),List.class,String.class);
        List<String> supplement = JsonUtil.jackson2other(line.getSupplementResult(),List.class,String.class);
        inventory.addAll(supplement);

        for (Map<String, Object> map:noDepositAtm){
            List<String> depositBox = JsonUtil.jackson2other(map.get("depositBox").toString(),List.class,String.class);
            List<String> supplementBox = JsonUtil.jackson2other(map.get("supplementBox").toString(),List.class,String.class);
            inventory.removeAll(depositBox);
            inventory.removeAll(supplementBox);
        }
        if (inventory.size() > 0) {
            box = inLibraryLineMapper.findNoDepositBox(null,null,inventory);
        }

        data.put("atm",noDepositAtm);
        data.put("box",box);
        return data;
    }

    @Override
    @Transactional
    public void supplementIn(SupplementBox param, HttpServletRequest request, HttpServletResponse response) {
        User user = ValidatePermissionsUtil.getUser(request,redisUtil);
        int i = outLibraryLineMapper.hasBoxByNumber(user.getLibraryId(), param.getNumber());
        if (i < 1) throw new BusinessException(Code.MESSAGE_ERROR.getCode(), "钞箱不存在");
        Map<String, Object> result = outLibraryLineMapper.supplementBox(user.getLibraryId(), param.getNumber());
        int state = (int) result.get("state");
        if (2 < state && state < 6) throw new BusinessException(Code.MESSAGE_ERROR.getCode(),"该钞箱已被使用");
        if (state == 7) throw new BusinessException(Code.MESSAGE_ERROR.getCode(),"该钞箱还未清机入库，不能使用");

        //入库线路详情
        InLibraryLine line = inLibraryLineMapper.selectByPrimaryKey(param.getLineId());
        List<String> supplementBoxNumbers = JsonUtil.jackson2other(line.getSupplementResult(), List.class, String.class);
        if (!supplementBoxNumbers.contains(param.getNumber())){
            supplementBoxNumbers.add(param.getNumber());
        }

        OutLibraryLineAtmExample example = new OutLibraryLineAtmExample();
        example.createCriteria().andLineIdEqualTo(param.getLineId()).andInStateIn(Arrays.asList(0,2));
        long count = outLibraryLineAtmMapper.countByExample(example);
        OutLibraryLineAtm outLibraryLineAtm = outLibraryLineAtmMapper.selectByPrimaryKey(param.getAtmId());
        List<String> takeBox = JsonUtil.jackson2other(outLibraryLineAtm.getTakeBox(),List.class,String.class);
        List<String> supplementTakeBox = JsonUtil.jackson2other(outLibraryLineAtm.getSupplementTakeBox(),List.class,String.class);
        List<String> originalTakeBox = JsonUtil.jackson2other(outLibraryLineAtm.getOriginalTakeBox(),List.class,String.class);
        if (!supplementTakeBox.contains(param.getNumber())){
            supplementTakeBox.add(param.getNumber());
        }
        takeBox.addAll(supplementTakeBox);
        if (takeBox.toString().equals(originalTakeBox.toString())) outLibraryLineAtm.setInState(1);
        else if (takeBox.containsAll(originalTakeBox) && originalTakeBox.containsAll(takeBox)) outLibraryLineAtm.setInState(1);
        else outLibraryLineAtm.setInState(2);
        outLibraryLineAtm.setSupplementBox(JsonUtil.list2str(supplementTakeBox));
        outLibraryLineAtmMapper.updateByPrimaryKeySelective(outLibraryLineAtm);

        //更新
        InLibraryLine inLibraryLine = new InLibraryLine();
        inLibraryLine.setId(line.getId());
        inLibraryLine.setSupplementResult(JsonUtil.list2str(supplementBoxNumbers));
        if (count == 1 && outLibraryLineAtm.getInState()==1){
            inLibraryLine.setCheckState(1);
        }else {
            inLibraryLine.setCheckState(2);
        }
        i = inLibraryLineMapper.updateByPrimaryKeySelective(inLibraryLine);
        if (i<1) throw new BusinessException(Code.MESSAGE_ERROR.getCode(), "补录数据出错");
    }

    private void setOtherValue(List<InLibraryTask> inLibraryTasks){
        for (InLibraryTask inLibraryTask:inLibraryTasks){
            InLibraryLineExample inLibraryLineExample = new InLibraryLineExample();
            inLibraryLineExample.createCriteria().andTaskIdEqualTo(inLibraryTask.getId());
            List<InLibraryLine> inLibraryLines = inLibraryLineMapper.selectByExample(inLibraryLineExample);
            int totalBox = 0;
            for (InLibraryLine inLibraryLine:inLibraryLines){
                totalBox += inLibraryLine.getTotalBox();
            }
            inLibraryTask.setTotalLine(inLibraryLines.size());
            inLibraryTask.setTotalBox(totalBox);

            User user1 = userMapper.selectByPrimaryKey(inLibraryTask.getPublisher());
            inLibraryTask.setPublisherName(user1.getName());
            OutLibraryTask task = outLibraryTaskMapper.selectByPrimaryKey(inLibraryTask.getId());
            inLibraryTask.setBatch(task.getBatch());
        }
    }

}
