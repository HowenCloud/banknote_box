package com.ixilink.banknote_box.service.service.impl;


import com.ixilink.banknote_box.common.dao.*;
import com.ixilink.banknote_box.common.exception.BusinessException;
import com.ixilink.banknote_box.common.model.Code;
import com.ixilink.banknote_box.common.model.ReadyMoneyNormalResult;
import com.ixilink.banknote_box.common.pojo.*;
import com.ixilink.banknote_box.common.util.JsonUtil;
import com.ixilink.banknote_box.common.util.ReadyMondyUtil;
import com.ixilink.banknote_box.common.util.RedisUtil;
import com.ixilink.banknote_box.common.util.ValidatePermissionsUtil;
import com.ixilink.banknote_box.service.parameter_model.readyMoney.*;
import com.ixilink.banknote_box.service.service.WebSocket;
import com.ixilink.banknote_box.service.service.ReadyMoneyTaskService;

import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class ReadyMoneyTaskServiceImpl implements ReadyMoneyTaskService {
    @Resource
    private ReadyMoneyTaskMapper readyMoneyTaskMapper;
    @Resource
    private FundBagManagementMapper fundBagManagementMapper;
    @Resource
    private CarBoxMapper carBoxMapper;
    @Resource
    private RedisUtil redisUtil;
    @Resource
    private WebSocket webSocket;
    @Resource
    private OutLibraryLineMapper outLibraryLineMapper;
    @Resource
    private UserMapper userMapper;
    @Resource
    private ReadyMoneyOutApplyMapper readyMoneyOutApplyMapper;
    @Resource
    private ReadyMondyUtil readyMondyUtil;


    @Override
    @Transactional
    public void insert(InsertReadyMoneyTaskModel record, HttpServletRequest request) {
        Integer libraryId = ValidatePermissionsUtil.getUser(request, redisUtil).getLibraryId();
        Integer  userId= ValidatePermissionsUtil.getUser(request, redisUtil).getId();
        ReadyMoneyTask readyMoneyTask = new ReadyMoneyTask(userId, record.getReceivePeople(),libraryId,new Date().getTime());
        int insert = readyMoneyTaskMapper.insert(readyMoneyTask);
        if (insert<0){
            throw new BusinessException(Code.MESSAGE_ERROR.getCode(), "插入失败");
        }
         Integer taskId = readyMoneyTask.getId();
        List<ReadyMoneyBoxNumMoney> readyMoneyBoxNumMoneys = new ArrayList<ReadyMoneyBoxNumMoney>();
        readyMoneyBoxNumMoneys.addAll(installBoxNumMoney(record.getDb(),1,taskId));
        readyMoneyBoxNumMoneys.addAll(installBoxNumMoney(record.getDf(),3,taskId));
        readyMoneyBoxNumMoneys.addAll(installBoxNumMoney(record.getCrs(),5,taskId));
        readyMoneyBoxNumMoneys.addAll(installBoxNumMoney(record.getScrs(),7,taskId));
        readyMoneyTaskMapper.insertBoxNumMoney(readyMoneyBoxNumMoneys);
    }
    @Override
    public List<ReadyMoneyTaskReturn> selectByInitiator(HttpServletRequest request) {
        Integer libraryId = ValidatePermissionsUtil.getUser(request, redisUtil).getLibraryId();
        //Integer  userId= ValidatePermissionsUtil.getUser(request, redisUtil).getId();
        List<ReadyMoneyTaskReturn> insertReadyMoneyTasks = readyMoneyTaskMapper.selectByInitiator(libraryId);//, userId
        List<ReadyMoneyTaskReturn> readyMoneyTaskReturns = get(insertReadyMoneyTasks);
        return readyMoneyTaskReturns;
    }

 @Override
    public List<ReadyMoneyTaskReturn> selectApproving(HttpServletRequest request) {
        Integer libraryId = ValidatePermissionsUtil.getUser(request, redisUtil).getLibraryId();
        //Integer  userId= ValidatePermissionsUtil.getUser(request, redisUtil).getId();
        List<ReadyMoneyTaskReturn> insertReadyMoneyTasks = readyMoneyTaskMapper.selectApproving(libraryId);//, userId
        List<ReadyMoneyTaskReturn> readyMoneyTaskReturns = get(insertReadyMoneyTasks);
        return readyMoneyTaskReturns;
 }
    public  List<ReadyMoneyTaskReturn> get(List<ReadyMoneyTaskReturn> param){
        for(ReadyMoneyTaskReturn insertReadyMoneyTask:param){
            List<Map<String, Object>> maps = readyMoneyTaskMapper.selectBoxNumMoneyByTaskId(insertReadyMoneyTask.getId());
            Map<String, Object> totalMoneyAndNumber = readyMoneyTaskMapper.selectApprovedAndPerformingNumberAndMoney(insertReadyMoneyTask.getId());
            insertReadyMoneyTask.setTotalNumber(Integer.parseInt(totalMoneyAndNumber.get("totalNumber").toString()));
            insertReadyMoneyTask.setTotalMoney(Integer.parseInt(totalMoneyAndNumber.get("totalMoney").toString()));
            for(Map<String,Object> map:maps){
                int type = (int)map.get("type");
                if(type==1) {
                    int totalMoney = Integer.parseInt(map.get("totalMoney").toString()) ;
                    int number = Integer.parseInt(map.get("number").toString());
                    insertReadyMoneyTask.setDbTotalMoney(totalMoney);
                    insertReadyMoneyTask.setDbNum(number);
                }if(type==3) {
                    int totalMoney = Integer.parseInt(map.get("totalMoney").toString()) ;
                    int number = Integer.parseInt(map.get("number").toString());
                    insertReadyMoneyTask.setDfTotalMoney(totalMoney);
                    insertReadyMoneyTask.setDfNum(number);
                }if(type==5) {
                    int totalMoney = Integer.parseInt(map.get("totalMoney").toString()) ;
                    int number = Integer.parseInt(map.get("number").toString());
                    insertReadyMoneyTask.setCrsTotalMoney(totalMoney);
                    insertReadyMoneyTask.setCrsNum(number);
                }if(type==7) {
                    int totalMoney = Integer.parseInt(map.get("totalMoney").toString()) ;
                    int number = Integer.parseInt(map.get("number").toString());
                    insertReadyMoneyTask.setScrsTotalMoney(totalMoney);
                    insertReadyMoneyTask.setScrsNum(number);
                }
            }
        }
        return param;
    }
    public  List<ReadyMoneyBoxNumMoney> installBoxNumMoney(List<ReadyMoneyBoxNumMoneyModel> dbs, int type, int taskId){
        List<ReadyMoneyBoxNumMoney> readyMoneyBoxNumMoneys =new ArrayList<ReadyMoneyBoxNumMoney>();
        for(ReadyMoneyBoxNumMoneyModel db:dbs){
            ReadyMoneyBoxNumMoney readyMoneyBoxNumMoney = new ReadyMoneyBoxNumMoney(taskId, db.getBoxMoney(), db.getNumber(), db.getNumber(),type);
            readyMoneyBoxNumMoneys.add(readyMoneyBoxNumMoney);
        }
        return readyMoneyBoxNumMoneys;
    }

    @Override
    public List<ReadyMoneyTaskReturn> selectApprovedAndPerforming(HttpServletRequest request) {
        Integer libraryId = ValidatePermissionsUtil.getUser(request, redisUtil).getLibraryId();
        Integer userId = ValidatePermissionsUtil.getUser(request, redisUtil).getId();
        List<ReadyMoneyTaskReturn> maps = readyMoneyTaskMapper.selectApprovedAndPerforming(libraryId);
        List<ReadyMoneyTaskReturn> readyMoneyTaskReturns = get(maps);
        return readyMoneyTaskReturns;
    }
    @Override
    public List<ReadyMoneyTaskReturn> selectPerformed(HttpServletRequest request) {
        Integer libraryId = ValidatePermissionsUtil.getUser(request, redisUtil).getLibraryId();
        List<ReadyMoneyTaskReturn> maps = readyMoneyTaskMapper.selectPerformed(libraryId);
        List<ReadyMoneyTaskReturn> readyMoneyTaskReturns = get(maps);
        return readyMoneyTaskReturns;
    }

    @Override
    public void approve(Map<String,Object> param,HttpServletRequest request) {
        Integer  userId= ValidatePermissionsUtil.getUser(request, redisUtil).getId();
        Integer approve = readyMoneyTaskMapper.approve(userId, new Date().getTime(), (Integer)param.get("id"),(Integer)param.get("state"));
        if (approve<0){
            throw new BusinessException(Code.MESSAGE_ERROR.getCode(), "审批失败");
        }
    }

    @Override
    @Transactional
    public Integer insertOperation(ReadyMoneyDetailsModel readyMoneyDetailsModel,HttpServletRequest request) {
      Integer libraryId = ValidatePermissionsUtil.getUser(request, redisUtil).getLibraryId();
        Integer  userId= ValidatePermissionsUtil.getUser(request, redisUtil).getId();
        Integer  partner= ValidatePermissionsUtil.getUser(request, redisUtil).getPartnerId();
        Integer taskId = readyMoneyDetailsModel.getTaskId();
        Integer carType = readyMoneyDetailsModel.getCarType();
        Integer boxMoney = readyMoneyDetailsModel.getCarMoney();
        String carTypeCha = null;
        //每次插入的时候先判断是否任务已经完成
        int totalNum = readyMoneyTaskMapper.selectAllNum(taskId);
        if(totalNum>0) {
            Integer num = readyMoneyTaskMapper.selectLeftBox(taskId, carType, boxMoney);
            if(null==num){
                throw new BusinessException(Code.MESSAGE_ERROR.getCode(), "预钞箱金额输入错误！");
            }
            if(carType==1){
                carTypeCha="db";
            }else if(carType==3){
                carTypeCha="df";
            }else if(carType==5){
                carTypeCha="crs";
            }else if(carType==7){
                carTypeCha="scrs";
            }
            if (num > 0) {
                //钞箱数量减1
                readyMoneyTaskMapper.reduceBoxNum(taskId, carType, boxMoney);
                //修改基金袋的金额
                Integer fundId = readyMoneyDetailsModel.getFundId();
                Integer carMoney = readyMoneyDetailsModel.getCarMoney();
                int remainFundMoney = fundBagManagementMapper.selecLeftMoneyById(fundId);
                if (carMoney > remainFundMoney) {
                    throw new BusinessException(Code.MESSAGE_ERROR.getCode(), "基金袋的余额不足！");
                }
                if(carMoney==remainFundMoney) {
                    fundBagManagementMapper.updateMoneyByOperator(carMoney, fundId,3);
                }else {
                    fundBagManagementMapper.updateMoneyByOperator(carMoney, fundId, 2);
                }
                //把钞箱变成有钱
                String carBoxNumber = readyMoneyDetailsModel.getCarBoxNumber();
                Integer i2 = carBoxMapper.selectStateByOperator(carBoxNumber, libraryId);
                if(i2!=1){
                    throw new BusinessException(Code.MESSAGE_ERROR.getCode(), "预钞箱不是空闲状态！");
                }
                carBoxMapper.updateStateByOperator(carMoney,carBoxNumber, libraryId);
                ReadyMoneyDetails readyMoneyDetails = new ReadyMoneyDetails(readyMoneyDetailsModel.getTaskId(), readyMoneyDetailsModel.getCarBoxNumber(), readyMoneyDetailsModel.getCarMoney(), readyMoneyDetailsModel.getFundId(), userId + "," + partner, readyMoneyDetailsModel.getCarType(),new Date().getTime(),libraryId,readyMoneyDetailsModel.getFundBagName());
                int i = readyMoneyTaskMapper.insertOperation(readyMoneyDetails);
                if (i < 1) {
                    throw new BusinessException(Code.MESSAGE_ERROR.getCode(), "插入失败");
                }
                //每次提交
                Map<String,Object> map= new HashMap<>();
                Long holdTime  = new Date().getTime();
                map.put("state",1);
                map.put("holdTime",holdTime);
                map.put("libraryId",libraryId);
                map.put("handOverPeopleId",userId+","+partner);
                map.put("detailsId",readyMoneyDetails.getId());
                readyMoneyTaskMapper.handOverOne(map);
                if(totalNum==1){
                    readyMoneyTaskMapper.updateTaskToPerformed(taskId,new Date().getTime());
                  /*  List<Integer> ids = readyMoneyTaskMapper.selectDetailsIdByTaskedId(libraryId, taskId);
                    Long holdTime  = new Date().getTime();
                    List list=new ArrayList();
                    for(Integer l:ids){
                        Map<String,Object> map= new HashMap<>();
                        map.put("state",1);
                        map.put("holdTime",holdTime);
                        map.put("libraryId",libraryId);
                        map.put("handOverPeopleId",userId+","+partner);
                        map.put("detailsId",l);
                        list.add(map);
                    }
                    readyMoneyTaskMapper.handOver(list);*/
                }
                return readyMoneyDetails.getId();
            } else {
                throw new BusinessException(Code.MESSAGE_ERROR.getCode(), "预钞箱任务的"+carTypeCha+"已经完成");
            }
        }else {
            throw new BusinessException(Code.MESSAGE_ERROR.getCode(), "预钞箱任务已经完成");
        }
    }

    @Override
    public List<ReadyMoneyExecutorTaskModel> selectExecutorTaskDetails(Integer taskId) {
        List<ReadyMoneyExecutorTaskModel> executorTaskModels = readyMoneyTaskMapper.selectByIdCarType(taskId);
        for(ReadyMoneyExecutorTaskModel executorTaskModel:executorTaskModels){
            List<Map<Integer, Integer>> unfinishNumberAndMoney =new ArrayList<>();
            String unfinishNumberMoney = executorTaskModel.getUnfinishNumberMoney();
            String[] splits= unfinishNumberMoney.split(",");
            for(String split:splits){
                Map<Integer, Integer> mapr= new HashMap<>();
                String[] split1 = split.split("-");
                int i = Integer.parseInt(split1[0]);
                if(i!=0) {
                    mapr.put(Integer.parseInt(split1[1]), Integer.parseInt(split1[0]));
                    unfinishNumberAndMoney.add(mapr);
                }
            }
            executorTaskModel.setUnfinishNumberAndMoney(unfinishNumberAndMoney);
        }
        return executorTaskModels;
    }

    @Override
    public List<ReadyMoneyReturnDetails> performedDetails(Map<String,Object> param) {
        return readyMoneyTaskMapper.performedDetails((Integer) param.get("id"),(String) param.get("carBoxNumber"),(Integer) param.get("carType"));
    }

    @Override
    public Map<String, Object> performedHead(Integer taskId) {
        return readyMoneyTaskMapper.performedHead(taskId);
    }

    @Override
    public void handOver(Map<String, Object> param,HttpServletRequest request) {
        Integer grouperId = (Integer) param.get("handoverPeople");
        Long holdTime  = new Date().getTime();
        Integer state=1;
        Integer libraryId = ValidatePermissionsUtil.getUser(request, redisUtil).getLibraryId();
        List idList=(List) param.get("ids");
        List list=new ArrayList();
        for(Object l:idList){
            Map<String,Object> map= new HashMap<>();
            map.put("state",state);
            map.put("holdTime",holdTime);
            map.put("grouperId",grouperId);
            map.put("libraryId",libraryId);
            map.put("detailsId",(Integer)l);
            list.add(map);
        }
        readyMoneyTaskMapper.handOver(list);
    }

    @Override
    public Map<String,Object> selectUnderGroupLeaderDetails(Map<String, Object> param, HttpServletRequest request) {
        Map<String, Object> retrunMap =new HashMap<>();
        Integer  libraryId= ValidatePermissionsUtil.getUser(request, redisUtil).getLibraryId();
        String userName = ValidatePermissionsUtil.getUser(request, redisUtil).getName();
        param.put("libraryId",libraryId);
        List<Map<String, Object>> underGroupLeaderDetails = readyMoneyTaskMapper.selectUnderGroupLeaderDetails(param);
        ReadyMoneyLibraryReturn readyMoneyLibraryReturn =new ReadyMoneyLibraryReturn();
        StringBuilder sb =new StringBuilder();
        String ids =null;
        if(underGroupLeaderDetails.size()==0){
            retrunMap.put("underGroupLeaderDetails",underGroupLeaderDetails);
            retrunMap.put("underGroupLeaderHeadDetails",readyMoneyLibraryReturn);
            return retrunMap;
        }
        if(underGroupLeaderDetails.size()!=0){
        for(Map<String,Object> underGroupLeaderDetail:underGroupLeaderDetails){
            sb.append(underGroupLeaderDetail.get("did")).append(",");
            if((Integer)param.get("state")==1){
                if(null==param.get("grouperId") || "".equals(param.get("grouperId"))) {
                    underGroupLeaderDetail.put("grouper", userName);
                }else
                    userName= userMapper.userInfoById((Integer) param.get("grouperId")).getName();
                    underGroupLeaderDetail.put("grouper", userName);
                }
            }
        }
        ids=sb.toString().substring(0,sb.length()-1);
        List<Map<String, Object>> underGroupLeaderHeadDetails = readyMoneyTaskMapper.selectUnderGroupLeaderHead(ids);

        Integer totalMoney = 0;
        Integer totalNumber = 0;
        for(Map<String,Object> underGroupLeaderHeadDetail:underGroupLeaderHeadDetails){
            Integer type=(Integer) underGroupLeaderHeadDetail.get("carType");
            if(type==1) {
                int money = Integer.parseInt(underGroupLeaderHeadDetail.get("money").toString());
                int number = Integer.parseInt(underGroupLeaderHeadDetail.get("number").toString());
                readyMoneyLibraryReturn.setDbTotalMoney(money);
                readyMoneyLibraryReturn.setDbNum(number);
            }if(type==3) {
                int money = Integer.parseInt(underGroupLeaderHeadDetail.get("money").toString()) ;
                int number = Integer.parseInt(underGroupLeaderHeadDetail.get("number").toString());
                readyMoneyLibraryReturn.setDfTotalMoney(money);
                readyMoneyLibraryReturn.setDfNum(number);
            }if(type==5) {
                int money = Integer.parseInt(underGroupLeaderHeadDetail.get("money").toString()) ;
                int number = Integer.parseInt(underGroupLeaderHeadDetail.get("number").toString());
                readyMoneyLibraryReturn.setCrsTotalMoney(money);
                readyMoneyLibraryReturn.setCrsNum(number);
            }if(type==7) {
                int money = Integer.parseInt(underGroupLeaderHeadDetail.get("money").toString()) ;
                int number = Integer.parseInt(underGroupLeaderHeadDetail.get("number").toString());
                readyMoneyLibraryReturn.setScrsTotalMoney(money);
                readyMoneyLibraryReturn.setScrsNum(number);
            }
            Integer number =Integer.parseInt(underGroupLeaderHeadDetail.get("number").toString());
            Integer money =Integer.parseInt(underGroupLeaderHeadDetail.get("money").toString());
            totalMoney+=money;
            totalNumber+=number;
        }
        readyMoneyLibraryReturn.setTotalMoney(totalMoney);
        readyMoneyLibraryReturn.setTotalNumber(totalNumber);
        retrunMap.put("underGroupLeaderDetails",underGroupLeaderDetails);
        retrunMap.put("underGroupLeaderHeadDetails",readyMoneyLibraryReturn);

        return retrunMap;
    }
    @Override
    public Map<String,Object> selectUnderLibraryDetails(LibraryerParamModel param, HttpServletRequest request) {
        Map<String, Object> retrunMap =new HashMap<>();
        Integer  libraryId= ValidatePermissionsUtil.getUser(request, redisUtil).getLibraryId();
        Map<String,Object> pra =new HashMap<>();
        pra.put("libraryId",libraryId);
        pra.put("state",param.getState());
        pra.put("libraryerId",param.getLibraryerId());
        pra.put("beginTime",param.getBeginTime());
        pra.put("endTime",param.getEndTime());
        pra.put("page",param.getPage());
        pra.put("pageSize",param.getPageSize());

        List<Map<String, Object>> underGroupLeaderDetails = readyMoneyTaskMapper.selectUnderLibraryDetails(pra);
        ReadyMoneyLibraryReturn readyMoneyLibraryReturn =new ReadyMoneyLibraryReturn();
        if(underGroupLeaderDetails.size()!=0){
        String ids =null;
        StringBuilder sb =new StringBuilder();
        for(Map<String,Object> underGroupLeaderDetail:underGroupLeaderDetails){
            sb.append(underGroupLeaderDetail.get("did")).append(",");
        }
        ids=sb.toString().substring(0,sb.length()-1);
        List<Map<String, Object>> underGroupLeaderHeadDetails = readyMoneyTaskMapper.selectUnderGroupLeaderHead(ids);

        Integer totalMoney = 0;
        Integer totalNumber = 0;
        for(Map<String,Object> underGroupLeaderHeadDetail:underGroupLeaderHeadDetails){
            Integer type=(Integer) underGroupLeaderHeadDetail.get("carType");
            if(type==1) {
                int money = Integer.parseInt(underGroupLeaderHeadDetail.get("money").toString());
                int number = Integer.parseInt(underGroupLeaderHeadDetail.get("number").toString());
                readyMoneyLibraryReturn.setDbTotalMoney(money);
                readyMoneyLibraryReturn.setDbNum(number);
            }if(type==3) {
                int money = Integer.parseInt(underGroupLeaderHeadDetail.get("money").toString()) ;
                int number = Integer.parseInt(underGroupLeaderHeadDetail.get("number").toString());
                readyMoneyLibraryReturn.setDfTotalMoney(money);
                readyMoneyLibraryReturn.setDfNum(number);
            }if(type==5) {
                int money = Integer.parseInt(underGroupLeaderHeadDetail.get("money").toString()) ;
                int number = Integer.parseInt(underGroupLeaderHeadDetail.get("number").toString());
                readyMoneyLibraryReturn.setCrsTotalMoney(money);
                readyMoneyLibraryReturn.setCrsNum(number);
            }if(type==7) {
                int money = Integer.parseInt(underGroupLeaderHeadDetail.get("money").toString()) ;
                int number = Integer.parseInt(underGroupLeaderHeadDetail.get("number").toString());
                readyMoneyLibraryReturn.setScrsTotalMoney(money);
                readyMoneyLibraryReturn.setScrsNum(number);
            }
            Integer number =Integer.parseInt(underGroupLeaderHeadDetail.get("number").toString());
            Integer money =Integer.parseInt(underGroupLeaderHeadDetail.get("money").toString());
            totalMoney+=money;
            totalNumber+=number;
        }
        readyMoneyLibraryReturn.setTotalMoney(totalMoney);
        readyMoneyLibraryReturn.setTotalNumber(totalNumber);
        }
        retrunMap.put("underLibraryerDetails",underGroupLeaderDetails);
        retrunMap.put("underLibraryerHeadDetails",readyMoneyLibraryReturn);

        return retrunMap;
    }
    @Override
    public List<Map<String, Object>> selectHandoverPerson(HttpServletRequest request) {
        Integer  libraryId= ValidatePermissionsUtil.getUser(request, redisUtil).getLibraryId();
        return readyMoneyTaskMapper.selectHandoverPerson(libraryId);
    }

    @Override
    public Map<String, Object> selectNumberAndType(String activeRfid) {
        Map<String, Object> map = readyMoneyTaskMapper.selectNumberAndType(activeRfid);
        String serialNumber =(String) map.get("serialNumber");//serialNumber  carType
        Integer carType =(Integer) map.get("carType");
        webSocket.sendMessage(serialNumber+"_"+carType);
        return map;
    }
    @Override
    public List<Map<String,Object>> selectGrouperNameAndId(HttpServletRequest request){
        Integer  libraryId= ValidatePermissionsUtil.getUser(request, redisUtil).getLibraryId();
        return readyMoneyTaskMapper.selectGrouperNameAndId(libraryId);
    }
    @Override
    public List<Map<String,Object>> selectLibraryNameAndId(HttpServletRequest request){
        Integer  libraryId= ValidatePermissionsUtil.getUser(request, redisUtil).getLibraryId();
        return readyMoneyTaskMapper.selectGrouperNameAndId(libraryId);
    }

    @Override
    public List<Map<String,Object>> selectApplyByGrouper(HttpServletRequest request){
        Integer  libraryId= ValidatePermissionsUtil.getUser(request, redisUtil).getLibraryId();
        return readyMoneyTaskMapper.selectApplyByGrouper(libraryId);
    }

    @Override
    public ReadyMoneyApplyTarget selectApplyTarget(Integer id){
        Map<String, Object> map = readyMoneyTaskMapper.selectApplyTarget(id);
        ReadyMoneyApplyTarget normalResult = (ReadyMoneyApplyTarget)JsonUtil.str2Obj((String) map.get("normalResult"), ReadyMoneyApplyTarget.class);
        normalResult.setTotalNumber(Integer.parseInt( map.get("totalNumber").toString()));
        return normalResult;
    }

    @Override
    public Map<String,Object> selectApplyCarBox(String carBoxNumber,HttpServletRequest request){
        Integer  libraryId= ValidatePermissionsUtil.getUser(request, redisUtil).getLibraryId();
        Map<String, Object> map = readyMoneyTaskMapper.selectApplyCarBox(carBoxNumber, libraryId);
        if(null == map) {
            throw new BusinessException(Code.MESSAGE_ERROR.getCode(), "钞箱不存在!");
        }
        return map;
    }

    @Override
    @Transactional
    public void refund(Integer fundId,HttpServletRequest request){//libraryId fundId executor1
        Integer  libraryId= ValidatePermissionsUtil.getUser(request, redisUtil).getLibraryId();
        Integer  userId= ValidatePermissionsUtil.getUser(request, redisUtil).getId();
        Integer  partnerId= ValidatePermissionsUtil.getUser(request, redisUtil).getPartnerId();
        readyMoneyTaskMapper.refund(libraryId,fundId,userId+","+partnerId,partnerId+","+userId);

        readyMoneyTaskMapper.updateFundToNull(libraryId,fundId,userId+","+partnerId,partnerId+","+userId);
    }
    @Override
    @Transactional
    public void updateFund(Integer fundId,HttpServletRequest request){
        Integer  libraryId= ValidatePermissionsUtil.getUser(request, redisUtil).getLibraryId();
        Integer  userId= ValidatePermissionsUtil.getUser(request, redisUtil).getId();
        //Integer  partnerId= ValidatePermissionsUtil.getUser(request, redisUtil).getPartnerId();
        Integer  partnerId=1;
        readyMoneyTaskMapper.updateFundReduce(libraryId,fundId,userId+","+partnerId,partnerId+","+userId);
        readyMoneyTaskMapper.updateFund(libraryId,fundId,userId+","+partnerId,partnerId+","+userId);
    }


    @Override
    @Transactional
    public ReadyMoneyApplyTarget supplement(Map<String, Object> param, HttpServletRequest request, HttpServletResponse response) {
        String storageTask=(String)param.get("storageTask");
        String storageTaskSupplement=(String)param.get("storageTaskSupplement");
        String storageTaskNum=(String)param.get("storageTaskNum");

        Integer  libraryId= ValidatePermissionsUtil.getUser(request, redisUtil).getLibraryId();
        Integer id=(Integer)param.get("id");
        String number = param.get("number").toString();
        Integer detailsId = (Integer)param.get("detailsId");
        Integer count = (Integer)param.get("count");
        int i = outLibraryLineMapper.hasBoxByNumber(libraryId, number);
        if (i < 1) throw new BusinessException(Code.MESSAGE_ERROR.getCode(), "钞箱不存在");
        Map<String, Object> map = readyMoneyTaskMapper.selectBoxBySerialType(number, libraryId);
        if ( map== null) throw new BusinessException(Code.MESSAGE_ERROR.getCode(), "钞箱类型错误");
        ReadyMoneyApplyTarget returnNum = null;
        if(i>0 && map!=null) {
            //遍历查询看是不是已经存在details的id
            int mark = 0;
            Cursor<Map.Entry<Object, Object>> cursor = redisUtil.getStringRedisTemplate().opsForHash().scan(storageTask, ScanOptions.scanOptions().match(id + ":" + "*").build());
            while (cursor.hasNext()) {
                Map.Entry<Object, Object> entry = cursor.next();
                List list = JsonUtil.jackson2other(entry.getValue().toString(), List.class, Integer.class);
                if (list.contains(detailsId)) {
                    mark = 1;
                    throw new BusinessException(Code.MESSAGE_ERROR.getCode(), "钞箱已经扫描");
                }
            }
            try {
                cursor.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (mark == 0) {
                if ((Integer) map.get("carType") == 1) {
                    Boolean dbB = redisUtil.getStringRedisTemplate().opsForHash().hasKey(storageTaskSupplement, id + ":" + "db"+":"+count);
                    if (!dbB) {
                        redisUtil.getStringRedisTemplate().opsForHash().put(storageTaskSupplement, id + ":" + "db"+":"+count, String.valueOf(1));
                    } else {
                        redisUtil.getStringRedisTemplate().opsForHash().put(storageTaskSupplement, id + ":" + "db"+":"+count, String.valueOf(Integer.parseInt(redisUtil.getStringRedisTemplate().opsForHash().get(storageTaskSupplement, id + ":" + "db"+":"+count).toString()) + 1));
                    }
                } else if ((Integer) map.get("carType") == 3) {
                    Boolean dfB = redisUtil.getStringRedisTemplate().opsForHash().hasKey(storageTaskSupplement, id + ":" + "df"+":"+count);
                    if (!dfB) {
                        redisUtil.getStringRedisTemplate().opsForHash().put(storageTaskSupplement, id + ":" + "df"+":"+count, String.valueOf(1));
                    } else {
                        redisUtil.getStringRedisTemplate().opsForHash().put(storageTaskSupplement, id + ":" + "df"+":"+count, String.valueOf(Integer.parseInt(redisUtil.getStringRedisTemplate().opsForHash().get(storageTaskSupplement, id + ":" + "df"+":"+count).toString()) + 1));
                    }
                } else if ((Integer) map.get("carType") == 5) {
                    Boolean crsB = redisUtil.getStringRedisTemplate().opsForHash().hasKey(storageTaskSupplement+":"+count, id + ":" + "crs");
                    if (!crsB) {
                        redisUtil.getStringRedisTemplate().opsForHash().put(storageTaskSupplement, id + ":" + "crs"+":"+count, String.valueOf(1));
                    } else {
                        redisUtil.getStringRedisTemplate().opsForHash().put(storageTaskSupplement, id + ":" + "crs"+":"+count, String.valueOf(Integer.parseInt(redisUtil.getStringRedisTemplate().opsForHash().get(storageTaskSupplement, id + ":" + "crs"+":"+count).toString()) + 1));
                    }
                } else if ((Integer) map.get("carType") == 7) {
                    Boolean scrsB = redisUtil.getStringRedisTemplate().opsForHash().hasKey(storageTaskSupplement, id + ":" + "scrs"+":"+count);
                    if (!scrsB) {
                        redisUtil.getStringRedisTemplate().opsForHash().put(storageTaskSupplement, id + ":" + "scrs"+":"+count, String.valueOf(1));
                    } else {
                        redisUtil.getStringRedisTemplate().opsForHash().put(storageTaskSupplement, id + ":" + "scrs"+":"+count, String.valueOf(Integer.parseInt(redisUtil.getStringRedisTemplate().opsForHash().get(storageTaskSupplement, id + ":" + "scrs"+":"+count).toString()) + 1));
                    }
                }


                //储存id
                if (!redisUtil.getStringRedisTemplate().opsForHash().hasKey(storageTask,  id + ":" + "supplement"+":"+count)) {
                    List list = new ArrayList<>();
                    list.add(detailsId);
                    redisUtil.getStringRedisTemplate().opsForHash().put(storageTask, id + ":" + "supplement"+":"+count, JsonUtil.list2str(list));
                } else {
                    List list = JsonUtil.jackson2other(redisUtil.getStringRedisTemplate().opsForHash().get(storageTask, id + ":" + "supplement"+":"+count).toString(), List.class, Integer.class);
                    list.add(detailsId);
                    redisUtil.getStringRedisTemplate().opsForHash().put(storageTask, id + ":" + "supplement"+":"+count, JsonUtil.list2str(list));
                }
                //returnNum = new ReadyMoneyApplyTarget(dbNum, dfNum, scrsNum, crsNum, dbNum + dfNum + scrsNum + crsNum);
            }
        }
        //.
        ReadyMoneyApplyTarget num = readyMondyUtil.getNum(id,storageTaskSupplement,storageTaskNum);
        num.setCarType((Integer)map.get("carType"));
        return num;
    }

    @Override
    public Map<String,Object> selectFaceGrouperLibraryer(Integer id, HttpServletRequest request){
        Integer  libraryId= ValidatePermissionsUtil.getUser(request, redisUtil).getLibraryId();
        Map<String, Object> map = readyMoneyTaskMapper.selectFaceGrouperLibraryer(libraryId, id);
        map.get("grouperId");
        List<Integer> libraryerId = Pattern.compile(",").splitAsStream(map.get("libraryerId").toString()).map((x) -> Integer.parseInt(x))
                .collect(Collectors.toList());
        map.put("libraryerId",libraryerId);
        return map;
    }

    @Override
    public void updateTaskSupplement(Map<String,Object> param, HttpServletRequest request){
        Integer  libraryId= ValidatePermissionsUtil.getUser(request, redisUtil).getLibraryId();
        int i = readyMoneyTaskMapper.updateTaskSupplement(JsonUtil.listToString((List) param.get("supplementResult"), ","), (Integer) param.get("id"));
        if ( i<1) throw new BusinessException(Code.MESSAGE_ERROR.getCode(), "补录失败");
    }

    @Override
    public void insertApply(ReadyMoneyApplyModel readyMoneyApplyModel, HttpServletRequest request) {
        Integer  userId= ValidatePermissionsUtil.getUser(request, redisUtil).getId();
        Integer  libraryId= ValidatePermissionsUtil.getUser(request, redisUtil).getLibraryId();
        List<Integer> inventoryResult = new ArrayList<>();
        List<Integer> supplementResult = new ArrayList<>();
        ReadyMoneyNormalResult normalResult = new ReadyMoneyNormalResult(readyMoneyApplyModel.getDb(), readyMoneyApplyModel.getDf(), readyMoneyApplyModel.getCrs(), readyMoneyApplyModel.getScrs());
        Integer totalNumber = readyMoneyApplyModel.getDb() +readyMoneyApplyModel.getDf() +readyMoneyApplyModel.getCrs() +readyMoneyApplyModel.getScrs();
        ReadyMoneyApply readyMoneyApply = new ReadyMoneyApply(null, new Date().getTime(), userId,JsonUtil.listToString(readyMoneyApplyModel.getLibraryerIds(),","), null, null, 1, totalNumber, libraryId, JsonUtil.obj2str(normalResult), JsonUtil.list2str(supplementResult), JsonUtil.list2str(inventoryResult));
        int i = readyMoneyTaskMapper.insertApply(readyMoneyApply);
        if(i<1){
            throw new BusinessException(Code.MESSAGE_ERROR.getCode(), "预装钞箱入库申请失败");
        }
    }
    @Override
    public void outLibraryApply(ReadyMoneyApplyModel readyMoneyApplyModel, HttpServletRequest request) {
        Integer  libraryId= ValidatePermissionsUtil.getUser(request, redisUtil).getLibraryId();
        Integer partnerId = ValidatePermissionsUtil.getUser(request, redisUtil).getPartnerId();
        //(Integer id, Long createTime, Integer grouperId, String libraryerId, Long approvalTime, String approvalPeople, Integer state, Integer totalNumber, Integer libraryId, String normalResult, String supplementResult, String inventoryResult)
        Integer totalNumber = readyMoneyApplyModel.getDb() +readyMoneyApplyModel.getDf() +readyMoneyApplyModel.getCrs() +readyMoneyApplyModel.getScrs();
        ReadyMoneyNormalResult normalResult = new ReadyMoneyNormalResult(readyMoneyApplyModel.getDb(), readyMoneyApplyModel.getDf(), readyMoneyApplyModel.getCrs(), readyMoneyApplyModel.getScrs());
        List<Integer> inventoryResult = new ArrayList<>();
        List<Integer> supplementResult = new ArrayList<>();
        int i= readyMoneyOutApplyMapper.insertSelective(new ReadyMoneyOutApply(null, new Date().getTime(), readyMoneyApplyModel.getGrouperId(), libraryId + "," + partnerId, null, null, 1, totalNumber, libraryId, JsonUtil.obj2str(normalResult), JsonUtil.list2str(supplementResult), JsonUtil.list2str(inventoryResult)));
        if(i<1){
            throw new BusinessException(Code.MESSAGE_ERROR.getCode(), "预装钞箱出库申请失败");
        }
    }

    @Override
    public ReadyMoneyApplyTarget clearLastStep(Map<String, Object> param, HttpServletRequest request, HttpServletResponse response){
        String storageTask=(String)param.get("storageTask");
        String storageTaskSupplement=(String)param.get("storageTaskSupplement");
        String storageTaskNum=(String)param.get("storageTaskNum");
        Integer  libraryId= ValidatePermissionsUtil.getUser(request, redisUtil).getLibraryId();
        Integer taskId=(Integer)param.get("id");
        Integer number = (Integer)param.get("number");
        redisUtil.getStringRedisTemplate().opsForHash().delete(storageTask, taskId+":"+"*"+":"+number);
        Cursor<Map.Entry<Object,Object>> cursorInStorageTaskNum = redisUtil.getStringRedisTemplate().opsForHash().scan(storageTaskNum,
                ScanOptions.scanOptions().match(taskId + ":" +"*"+":"+number).count(1000).build());
        while (cursorInStorageTaskNum.hasNext()) {
            redisUtil.getStringRedisTemplate().opsForHash().delete(storageTaskNum,cursorInStorageTaskNum.next().getKey());
        }
        try {
            cursorInStorageTaskNum.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Cursor<Map.Entry<Object,Object>> supplement = redisUtil.getStringRedisTemplate().opsForHash().scan(storageTaskSupplement,
                ScanOptions.scanOptions().match(taskId + ":" +"*"+":"+number).count(1000).build());
        while (supplement.hasNext()) {
            redisUtil.getStringRedisTemplate().opsForHash().delete(storageTaskSupplement,supplement.next().getKey());
        }
        try {
            supplement.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return readyMondyUtil.getNum(taskId,storageTaskSupplement,storageTaskNum);
    }
    @Override
    public void boxHandOver(List<Integer> libraryerId,HttpServletRequest request){
        Integer  libraryId= ValidatePermissionsUtil.getUser(request, redisUtil).getLibraryId();
        readyMoneyTaskMapper.boxHandOver(JsonUtil.listToString(libraryerId,","),libraryId);
    }
}
