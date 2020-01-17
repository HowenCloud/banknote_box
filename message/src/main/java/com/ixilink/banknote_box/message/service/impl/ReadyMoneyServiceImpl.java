package com.ixilink.banknote_box.message.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ixilink.banknote_box.common.dao.*;
import com.ixilink.banknote_box.common.exception.BusinessException;
import com.ixilink.banknote_box.common.model.Code;
import com.ixilink.banknote_box.common.model.Result;
import com.ixilink.banknote_box.common.pojo.*;
import com.ixilink.banknote_box.common.util.*;
import com.ixilink.banknote_box.message.command.BoxManager;
import com.ixilink.banknote_box.message.common.FacePublicData;
import com.ixilink.banknote_box.message.service.ReadyMoneyService;
import com.ixilink.banknote_box.message.websocket.BoxSocket;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class ReadyMoneyServiceImpl implements ReadyMoneyService {
    @Resource
    private  ReadyMoneyTaskMapper readyMoneyTaskMapper;
    @Resource
    private  UserMapper userMapper;
    @Resource
    private BoxSocket boxSocket;
    @Resource
    private RedisUtil redisUtil;
    @Resource
    private SystemSettingMapper systemSettingMapper;
    @Resource
    private OutLibraryLineMapper outLibraryLineMapper;
    @Resource
    private ReadyMoneyOutApplyMapper readyMoneyOutApplyMapper;
    @Resource
    private EquipmentMapper equipmentMapper;
    @Resource
    private ReadyMoneyLibraryerMapper readyMoneyLibraryerMapper;
    @Resource
    private ReadyMondyUtil readyMondyUtil;
    @Override
    public void selectNumberAndType(String activeRfid, HttpServletRequest request) {
        User user = ValidatePermissionsUtil.getUser(request, redisUtil);
        Map<String, Object> map = readyMoneyTaskMapper.selectNumberAndType(activeRfid);
        if(null==map||map.size()==0){
            boxSocket.sendMessage(String.valueOf(user.getId()),JSONObject.toJSONString( new Result(Code.MESSAGE_ERROR.getCode(),"钞箱不存!")));
            throw new BusinessException(Code.MESSAGE_ERROR.getCode(),"钞箱不存在！");
        }else {
            List<Integer> integers = Arrays.asList(2, 4, 6, 8);
            if (integers.contains(Integer.parseInt(map.get("carType").toString()))) {
                boxSocket.sendMessage(String.valueOf(user.getId()),JSONObject.toJSONString( new Result(Code.MESSAGE_ERROR.getCode(),"钞箱类型错误！！")));
                throw new BusinessException(Code.MESSAGE_ERROR.getCode(), "钞箱类型错误！！");
            }else {
                String name1 = ValidatePermissionsUtil.getUser(request, redisUtil).getName();
                Integer partnerId = ValidatePermissionsUtil.getUser(request, redisUtil).getPartnerId();
                String name2 = " ";
                if (partnerId != null) {
                    name2 = userMapper.selectByPrimaryKey(partnerId).getName();
                }
                map.put("name", name1 + " " + name2);
                boxSocket.sendMessage(String.valueOf(user.getId()), JSONObject.toJSONString(new Result(Code.SUCCESS.getCode(), Code.SUCCESS.getMessage(),map)));
            }
        }
    }

    @Override
    @Transactional
    public Map<String,Object> scanInBox(Map<String,Object> param, HttpServletRequest request, HttpServletResponse response){
        String storageTask=(String)param.get("storageTask");
        String storageTaskSupplement=(String)param.get("storageTaskSupplement");
        String storageTaskNum=(String)param.get("storageTaskNum");
        Integer id = Integer.parseInt(param.get("id").toString()) ;
        Integer number =(Integer) param.get("number");
        User user = ValidatePermissionsUtil.getUser(request,redisUtil);
        SystemSetting systemSetting = systemSettingMapper.selectByPrimaryKey(user.getLibraryId());
        BoxManager boxManager = new BoxManager(systemSetting.getAssignmentsReaderWriterIp());
        boxManager.connection();
        boxManager.clean();
        boxManager.scan();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        List<String> rfids = boxManager.read();
        if(rfids.size()==0){
            throw new BusinessException(Code.MESSAGE_ERROR.getCode(),"没扫描到标签");
        }
        boxManager.close();
        //查询费钞箱和空钞箱
        List<Integer> boxBadType =Arrays.asList(2,4,6,8);
        List<Integer> boxType =Arrays.asList(1,3,5,7);
        List<CarBox> carBoxLists = readyMoneyTaskMapper.getCarBoxList(user.getLibraryId(), rfids);
        List<String>  boxBadList = carBoxLists.stream().filter(carBoxList -> boxBadType.contains(carBoxList.getBoxType())).map(carBoxList -> carBoxList.getSerialNumber()).collect(Collectors.toList());
        List<String>  notUseBox = carBoxLists.stream().filter(carBoxList -> null !=carBoxList.getBoxUseState() && carBoxList.getBoxUseState() == 1).filter(carBoxList -> boxType.contains(carBoxList.getBoxType())).map(carBoxList -> carBoxList.getSerialNumber()).collect(Collectors.toList());
        if(boxBadList.size()!=0 && notUseBox.size()!=0 ){
            throw new BusinessException(Code.MESSAGE_ERROR.getCode(),"有废钞箱和没有装钞的钞箱，请仔细检查！");
        }
        if(boxBadList.size()!=0 &&  notUseBox.size()==0){
            throw new BusinessException(Code.MESSAGE_ERROR.getCode(),"有废钞箱，请仔细检查！");
        }
        if( notUseBox.size()!=0 && boxBadList.size()==0){
            throw new BusinessException(Code.MESSAGE_ERROR.getCode(),"有没有装钞的钞箱，请仔细检查！");
        }
        List<String>  box = carBoxLists.stream().filter(carBoxList -> null !=carBoxList.getBoxUseState() && carBoxList.getBoxUseState() == 2).filter(carBoxList -> boxType.contains(carBoxList.getBoxType())).map(carBoxList -> carBoxList.getSerialNumber()).collect(Collectors.toList());

        if(null==box || box.size()==0){
            throw new BusinessException(Code.MESSAGE_ERROR.getCode(),"没有钞箱或者钞箱的有源标签出现了问题！");
        }
        List<Map<String, Object>> inventory=null;
        if (box.size()>0){
            inventory = readyMoneyTaskMapper.inventory(user.getLibraryId(), box);
        }
        //扫描数据
        ReadyMoneyApplyTarget readyMoneyApplyScan = new ReadyMoneyApplyTarget(inventory);
        //插入数据之前先判断是不是第一次，如果第一次则先清空数据
        if(number==1){
            //redisUtil.getStringRedisTemplate().opsForHash().delete("inStorageTask", "id"+":"+taskId+":"+number);
            //redisUtil.getStringRedisTemplate().opsForHash().delete("inStorageTask", "id"+":"+taskId+":"+number+"supplement");
            Cursor<Map.Entry<Object,Object>> cursorTaskNum = redisUtil.getStringRedisTemplate().opsForHash().scan(storageTask,
                    ScanOptions.scanOptions().match(id+":"+"*").count(1000).build());
            while (cursorTaskNum.hasNext()) {
                redisUtil.getStringRedisTemplate().opsForHash().delete(storageTask,cursorTaskNum.next().getKey());
            }
            try {
                cursorTaskNum.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Cursor<Map.Entry<Object,Object>> cursorInStorageTaskNum = redisUtil.getStringRedisTemplate().opsForHash().scan(storageTaskNum,
                    ScanOptions.scanOptions().match(id + ":" +"*").count(1000).build());
            while (cursorInStorageTaskNum.hasNext()) {
                redisUtil.getStringRedisTemplate().opsForHash().delete(storageTaskNum,cursorInStorageTaskNum.next().getKey());
            }
            try {
                cursorInStorageTaskNum.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Cursor<Map.Entry<Object,Object>> supplement = redisUtil.getStringRedisTemplate().opsForHash().scan(storageTaskSupplement,
                    ScanOptions.scanOptions().match(id + ":" +"*").count(1000).build());
            while (supplement.hasNext()) {
                redisUtil.getStringRedisTemplate().opsForHash().delete(storageTaskSupplement,supplement.next().getKey());
            }
            try {
                supplement.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }




        //任务中插入数据
        List<Integer> sb = new ArrayList<>();
        for (Map<String, Object> map : inventory) {
            List<Integer> ids = Pattern.compile(",").splitAsStream(map.get("ids").toString()).map((x) -> Integer.parseInt(x))
                    .collect(Collectors.toList());
            sb.addAll(ids);
            }



            //加条件
        Cursor<Map.Entry<Object, Object>> scan = redisUtil.getStringRedisTemplate().opsForHash().scan(storageTask, ScanOptions.scanOptions().match(id + ":" + "*").count(1000).build());
        while (scan.hasNext()) {
            String key = scan.next().getKey().toString();
            List<Integer> l = JsonUtil.jackson2other(redisUtil.getStringRedisTemplate().opsForHash().get(storageTask,key).toString() , List.class, Integer.class);
            for(int i=0;i<l.size();i++){
                if(sb.contains(l.get(i))){
                    int detailsId = l.get(i);
                    l.remove(i);
                    i--;
                    //移除storageTask里面的id
                    redisUtil.getStringRedisTemplate().opsForHash().put(storageTask, key, JsonUtil.list2str(l));
                    //根据id查询是什么类型
                    String carType = readyMoneyTaskMapper.getCarType(detailsId);
                    String[] split = key.split(":");
                    if(split[1].equals("scan")){
                        String s = split[0]+":" + carType +":"+ split[2];
                        Object o = redisUtil.getStringRedisTemplate().opsForHash().get(storageTaskNum, s);
                        int count = Integer.parseInt(o.toString());
                        redisUtil.getStringRedisTemplate().opsForHash().put(storageTaskNum,split[0]+":" + carType +":"+ split[2],String.valueOf(count-1));
                    }else {
                        redisUtil.getStringRedisTemplate().opsForHash().put(storageTaskSupplement,split[0]+":" + carType +":"+ split[2],Integer.parseInt(redisUtil.getStringRedisTemplate().opsForHash().get(storageTaskSupplement,split[0]+":" + carType +":"+ split[2]).toString())-1);
                    }
                }
            }
        }
        try {
            scan.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        redisUtil.getStringRedisTemplate().opsForHash().put(storageTask, id + ":"+"scan" +":"+ number, JsonUtil.list2str(sb));
                redisUtil.getStringRedisTemplate().opsForHash().put(storageTaskNum, id + ":" + "db" + ":" + number, String.valueOf(readyMoneyApplyScan.getDbNum()));
                redisUtil.getStringRedisTemplate().opsForHash().put(storageTaskNum, id + ":" + "df" + ":" + number, String.valueOf(readyMoneyApplyScan.getDfNum()));
                redisUtil.getStringRedisTemplate().opsForHash().put(storageTaskNum, id + ":" + "crs" + ":" + number, String.valueOf(readyMoneyApplyScan.getCrsNum()));
                redisUtil.getStringRedisTemplate().opsForHash().put(storageTaskNum, id + ":" + "scrs" + ":" + number, String.valueOf(readyMoneyApplyScan.getScrsNum()));
        //db,df,crs,scrs展示
        ReadyMoneyApplyList readyMoneyApplyList = new ReadyMoneyApplyList(inventory);
        Map<String, Object> target;
        if(param.get("mark").toString().equals("in")){
            target = readyMoneyTaskMapper.selectApplyTarget(id);
        }else {
            target = readyMoneyTaskMapper.selectApplyOutTarget(id);
        }
        ReadyMoneyApplyTarget normalResult = (ReadyMoneyApplyTarget)JsonUtil.str2Obj((String) target.get("normalResult"), ReadyMoneyApplyTarget.class);
        normalResult.setTotalNumber((Integer) target.get("totalNumber"));
       Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("readyMoneyApplyScan",readyMondyUtil.getNum(id,storageTaskSupplement,storageTaskNum));
        returnMap.put("readyMoneyApplyList",readyMoneyApplyList);
        returnMap.put("target",normalResult);
        return returnMap;
    }
    @Override
    @Transactional
    public void startReadyMoneyFace(HttpServletRequest request, HttpServletResponse response) {
        User user = ValidatePermissionsUtil.getUser(request,redisUtil);
        //查询系统配置 获取ip 获取设备编号
        SystemSetting systemSetting = systemSettingMapper.selectByPrimaryKey(user.getLibraryId());
        EquipmentExample equipmentExample = new EquipmentExample();
        equipmentExample.createCriteria().andStateEqualTo(1).andLibraryIdEqualTo(user.getLibraryId()).andIpEqualTo(systemSetting.getAssignmentsAmeraIp());
        List<Equipment> equipment = equipmentMapper.selectByExample(equipmentExample);
        if (equipment.size()==0) {
            throw new BusinessException(Code.MESSAGE_ERROR.getCode(), "没有设备，请先录入");
        }
        if (!FacePublicData.getDataServerState()) FacePublicData.startDataServer();
        FacePublicData.startRecognitionFace(equipment.get(0).getNumber());
    }

    @Override
    @Transactional
    public void inHandover(HandOverModel param, HttpServletRequest request){
        User user = ValidatePermissionsUtil.getUser(request,redisUtil);
        Integer taskId = param.getId();
        Integer  libraryId= ValidatePermissionsUtil.getUser(request, redisUtil).getLibraryId();
        //查询系统配置 获取ip 获取设备编号
        SystemSetting systemSetting = systemSettingMapper.selectByPrimaryKey(user.getLibraryId());
        EquipmentExample equipmentExample = new EquipmentExample();
        equipmentExample.createCriteria().andStateEqualTo(1).andLibraryIdEqualTo(user.getLibraryId()).andIpEqualTo(systemSetting.getAssignmentsAmeraIp());
        List<Equipment> equipment = equipmentMapper.selectByExample(equipmentExample);
        if (equipment.size()>0){
            FacePublicData.stopRecognitionFace(equipment.get(0).getNumber());
            //删除临时图片
            File file = new File(FileUtil.getPath(),"\\temp\\face\\"+equipment.get(0).getNumber());
            FileUtil.del(file);
            //如果没有设备使用   则关闭人脸识别服务器
            if (FacePublicData.userId.isEmpty()) {
                FacePublicData.stopDataServer();

            }
        }
        //业务
        //比对是不是有提交的组长和要求的两个管库员
        Map<String, Object> map1;

        if(param.getMark().equals("in")) {
            map1 = readyMoneyTaskMapper.selectFaceGrouperLibraryer(libraryId, taskId);
        }else {
            map1 = readyMoneyTaskMapper.selectFaceGrouperOutLibraryer(libraryId, taskId);
        }
        Integer grouperIdOld = (Integer)map1.get("grouperId");
        List<Integer>  libraryerIdOld= Pattern.compile(",").splitAsStream(map1.get("libraryerId").toString().toString()).map((x) -> Integer.parseInt(x))
                .collect(Collectors.toList());
        List<Integer> peopleList =new ArrayList<>();
        //两个组长
        List<Integer> grouperId = param.getGrouperId();
        if(!grouperId.contains(grouperIdOld)){
            throw new BusinessException(Code.MESSAGE_ERROR.getCode(), "没有提交申请的组长,提交入库申请的组长必须在!");
        }
        //两个管库员
        List<Integer> libraryerId = param.getLibraryerId();
        Integer count =0;
        for(Integer libraryerId_:libraryerId){
            if(libraryerIdOld.contains(libraryerId_)){
                count++;
            }
        }
        if(count==0){
            throw new BusinessException(Code.MESSAGE_ERROR.getCode(), "要求的两个管库员都不在场!");
        }else if(count==1){
            throw new BusinessException(Code.MESSAGE_ERROR.getCode(), "只有一个要求的管库员在场!");
        }
        //监交岗id
        Integer postId = param.getPostId();
        if(true){
            //判断是不是监交岗的人员
        }
        peopleList.addAll(grouperId);
        peopleList.addAll(libraryerId);
        peopleList.add(postId);
        String approvalPeople=JsonUtil.listToString(peopleList,",");
        //从redis中组装数据
        Cursor<Map.Entry<Object, Object>> cursor  = redisUtil.getStringRedisTemplate().opsForHash().scan(param.getMark().equals("in")?"inStorageTask":"outStorageTask", ScanOptions.scanOptions().match(taskId + ":"+"*").build());
        List<Integer> allList = new ArrayList();
        while(cursor.hasNext()){
            Map.Entry<Object,Object> entry = cursor.next();
            List list = JsonUtil.jackson2other(entry.getValue().toString(), List.class, Integer.class);
            allList.addAll(list);
            String key = (String)entry.getKey();
        }
        try {
            cursor.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        int i;
        if(param.getMark().equals("in")) {
            i =readyMoneyTaskMapper.inHandover(approvalPeople, taskId, JsonUtil.list2str(allList));
        }else {
            i =readyMoneyTaskMapper.outHandover(approvalPeople, taskId, JsonUtil.list2str(allList));
        }
        if ( i<1) throw new BusinessException(Code.MESSAGE_ERROR.getCode(), "交接失败");
        Map<String, Object> map;
        if(param.getMark().equals("in")){
            map = readyMoneyTaskMapper.selectInventorySupplement(taskId);
        }else {
            map = readyMoneyTaskMapper.selectOutInventorySupplement(taskId);
        }
        if(param.getMark().equals("in")) {
            //申请组长id
            Integer grouperId_ = (Integer) map.get("grouperId");
            String libraryersId = JsonUtil.listToString(libraryerId, ",");
            //改变组长的入库
            int i1 = readyMoneyTaskMapper.updateOverHandGrouper(libraryersId,grouperId_, new Date().getTime(), allList);
            if (i1 < 1) throw new BusinessException(Code.MESSAGE_ERROR.getCode(), "交接失败");
            //插入管库员
            for (Integer id : allList) {
                readyMoneyLibraryerMapper.insertSelective(new ReadyMoneyLibraryer(null, JsonUtil.listToString(libraryerId, ","), new Date().getTime(), null, 1, id, grouperId_.toString(), libraryId));
            }
        }else{
            //改变管库员的为出库
            int i1 = readyMoneyTaskMapper.updateOutOverHandGrouper(new Date().getTime(), allList);
            if (i1 < 1) throw new BusinessException(Code.MESSAGE_ERROR.getCode(), "交接失败");
            //插入组长名下
            for (Integer id : allList) {
                readyMoneyTaskMapper.outHandOver(grouperIdOld,JsonUtil.listToString(libraryerId, ","),new Date().getTime(),id,libraryId);
            }
        }
    }
}
