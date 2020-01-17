package com.ixilink.banknote_box.service.service.impl;


import com.ixilink.banknote_box.common.dao.*;
import com.ixilink.banknote_box.common.exception.BusinessException;
import com.ixilink.banknote_box.common.model.Code;
import com.ixilink.banknote_box.common.pojo.*;
import com.ixilink.banknote_box.common.util.JsonUtil;
import com.ixilink.banknote_box.common.util.ReadyMondyUtil;
import com.ixilink.banknote_box.common.util.RedisUtil;
import com.ixilink.banknote_box.common.util.ValidatePermissionsUtil;
import com.ixilink.banknote_box.service.service.ReadyMoneyOutService;
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
public class ReadyMoneyOutServiceImpl implements ReadyMoneyOutService {
    @Resource
    private RedisUtil redisUtil;
    @Resource
    private ReadyMoneyOutApplyMapper readyMoneyOutApplyMapper;
    @Resource
    private ReadyMoneyTaskMapper readyMoneyTaskMapper;
    @Resource
    private ReadyMondyUtil readyMondyUtil;
    @Resource
    private OutLibraryLineMapper outLibraryLineMapper;

    @Override
    public List<Map<String,Object>> selectApplyOut(HttpServletRequest request){
        Integer  libraryId= ValidatePermissionsUtil.getUser(request, redisUtil).getLibraryId();
        return readyMoneyOutApplyMapper.selectApplyOut(libraryId);
    }

    @Override
    public Map<String,Object> selectFaceOutGrouperLibraryer(Integer id, HttpServletRequest request){
        Integer  libraryId= ValidatePermissionsUtil.getUser(request, redisUtil).getLibraryId();
        Map<String, Object> map = readyMoneyOutApplyMapper.selectFaceOutGrouperLibraryer(libraryId, id);
        map.get("grouperId");
        List<String> libraryerId = Arrays.asList(map.get("libraryerId").toString().split(","));
        map.put("libraryerId",libraryerId);
        return map;
    }

    @Override
    @Transactional
    public ReadyMoneyApplyTarget outSupplement(Map<String, Object> param, HttpServletRequest request, HttpServletResponse response) {
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
            Cursor<Map.Entry<Object, Object>> cursor = redisUtil.getStringRedisTemplate().opsForHash().scan("outStorageTask", ScanOptions.scanOptions().match("id" + ":" + id + ":" + "*").build());
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
                    Boolean dbB = redisUtil.getStringRedisTemplate().opsForHash().hasKey("outStorageTaskSupplement", id + ":" + "db"+":"+count);
                    if (!dbB) {
                        redisUtil.getStringRedisTemplate().opsForHash().put("outStorageTaskSupplement", id + ":" + "db"+":"+count, String.valueOf(1));
                    } else {
                        redisUtil.getStringRedisTemplate().opsForHash().put("outStorageTaskSupplement", id + ":" + "db"+":"+count, String.valueOf(Integer.parseInt(redisUtil.getStringRedisTemplate().opsForHash().get("outStorageTaskSupplement", id + ":" + "db"+":"+count).toString()) + 1));
                    }
                } else if ((Integer) map.get("carType") == 3) {
                    Boolean dfB = redisUtil.getStringRedisTemplate().opsForHash().hasKey("outStorageTaskSupplement", id + ":" + "df"+":"+count);
                    if (!dfB) {
                        redisUtil.getStringRedisTemplate().opsForHash().put("outStorageTaskSupplement", id + ":" + "df"+":"+count, String.valueOf(1));
                    } else {
                        redisUtil.getStringRedisTemplate().opsForHash().put("outStorageTaskSupplement", id + ":" + "df"+":"+count, String.valueOf(Integer.parseInt(redisUtil.getStringRedisTemplate().opsForHash().get("outStorageTaskSupplement", id + ":" + "df"+":"+count).toString()) + 1));
                    }
                } else if ((Integer) map.get("carType") == 5) {
                    Boolean crsB = redisUtil.getStringRedisTemplate().opsForHash().hasKey("outStorageTaskSupplement"+":"+count, id + ":" + "crs");
                    if (!crsB) {
                        redisUtil.getStringRedisTemplate().opsForHash().put("outStorageTaskSupplement", id + ":" + "crs"+":"+count, String.valueOf(1));
                    } else {
                        redisUtil.getStringRedisTemplate().opsForHash().put("outStorageTaskSupplement", id + ":" + "crs"+":"+count, String.valueOf(Integer.parseInt(redisUtil.getStringRedisTemplate().opsForHash().get("outStorageTaskSupplement", id + ":" + "crs"+":"+count).toString()) + 1));
                    }
                } else if ((Integer) map.get("carType") == 7) {
                    Boolean scrsB = redisUtil.getStringRedisTemplate().opsForHash().hasKey("outStorageTaskSupplement", id + ":" + "scrs"+":"+count);
                    if (!scrsB) {
                        redisUtil.getStringRedisTemplate().opsForHash().put("outStorageTaskSupplement", id + ":" + "scrs"+":"+count, String.valueOf(1));
                    } else {
                        redisUtil.getStringRedisTemplate().opsForHash().put("outStorageTaskSupplement", id + ":" + "scrs"+":"+count, String.valueOf(Integer.parseInt(redisUtil.getStringRedisTemplate().opsForHash().get("outStorageTaskSupplement", id + ":" + "scrs"+":"+count).toString()) + 1));
                    }
                }


                //储存id
                if (!redisUtil.getStringRedisTemplate().opsForHash().hasKey("outStorageTask", "id" + ":" + id + ":" +count+ "supplement")) {
                    List list = new ArrayList<>();
                    list.add(detailsId);
                    redisUtil.getStringRedisTemplate().opsForHash().put("outStorageTask", "id" + ":" + id + ":"+count + "supplement", JsonUtil.list2str(list));
                } else {
                    List list = JsonUtil.jackson2other(redisUtil.getStringRedisTemplate().opsForHash().get("outStorageTask", "id" + ":" + id + ":" +count+ "supplement").toString(), List.class, Integer.class);
                    list.add(detailsId);
                    redisUtil.getStringRedisTemplate().opsForHash().put("outStorageTask", "id" + ":" + id + ":" +count+ "supplement", JsonUtil.list2str(list));
                }
                //returnNum = new ReadyMoneyApplyTarget(dbNum, dfNum, scrsNum, crsNum, dbNum + dfNum + scrsNum + crsNum);
            }
        }
        //.
        //ReadyMoneyApplyTarget num = readyMondyUtil.getNum(id);
        //num.setCarType((Integer)map.get("carType"));
        return null;
    }
    @Override
    public void outHandover(HandOverModel param, HttpServletRequest request){
        Integer  libraryId= ValidatePermissionsUtil.getUser(request, redisUtil).getLibraryId();
        Integer userId = ValidatePermissionsUtil.getUser(request, redisUtil).getId();
        Integer partnerId = ValidatePermissionsUtil.getUser(request, redisUtil).getPartnerId();
        List<Integer> peopleList =new ArrayList<>();
        List<Integer> grouperId = param.getGrouperId();
        List<Integer> libraryerId = param.getLibraryerId();
        peopleList.addAll(grouperId);
        peopleList.addAll(libraryerId);
        peopleList.add(param.getPostId());
        String approvalPeople=JsonUtil.listToString(peopleList,",");
        int i = readyMoneyOutApplyMapper.outHandover(approvalPeople, param.getId());
        if ( i<1) throw new BusinessException(Code.MESSAGE_ERROR.getCode(), "交接失败");
        //inventoryResult supplementResult
        Map<String, Object> map = readyMoneyOutApplyMapper.selectOutInventorySupplement(param.getId());
        List<Integer> list = new ArrayList<>();
        Object inventoryResult = map.get("inventoryResult");
        Object supplementResult = map.get("supplementResult");
        if(null!=map.get("inventoryResult")){
            List<Integer> collect = Pattern.compile(",").splitAsStream(inventoryResult.toString()).map((x) -> Integer.parseInt(x))
                    .collect(Collectors.toList());
            list.addAll(collect);
        }
        if(null!=map.get("supplementResult")){
            List<Integer> collect = Pattern.compile(",").splitAsStream(supplementResult.toString()).map((x) -> Integer.parseInt(x))
                    .collect(Collectors.toList());
            list.addAll(collect);
        }
        //申请组长id
        Integer grouperId_ =(Integer) map.get("grouperId");
        //改变库管员出库
        int i1 = readyMoneyOutApplyMapper.updateOutLibrary(grouperId_, new Date().getTime(), list);
        if ( i1<1) throw new BusinessException(Code.MESSAGE_ERROR.getCode(), "交接失败");
        //插入管库员
        //(Integer id, String libraryerId, Long inLibraryTime, Long outLibraryTime, Integer state, Integer detailsId, String handOverPeopleId, Integer libraryId)
        for(Integer id:list) {
            //readyMoneyLibraryerMapper.insertSelective(new ReadyMoneyLibraryer(null, JsonUtil.listToString(libraryerId, ","), new Date().getTime(), null, 1, id, grouperId_.toString(), libraryId));
        }
    }
    @Override
    public ReadyMoneyApplyTarget selectApplyOutTarget(Integer id){
        Map<String, Object> map = readyMoneyTaskMapper.selectApplyOutTarget(id);
        ReadyMoneyApplyTarget normalResult = (ReadyMoneyApplyTarget)JsonUtil.str2Obj((String) map.get("normalResult"), ReadyMoneyApplyTarget.class);
        normalResult.setTotalNumber(Integer.parseInt( map.get("totalNumber").toString()));
        return normalResult;
    }
}
