package com.ixilink.banknote_box.message.job;

import com.ixilink.banknote_box.common.dao.EquipmentMapper;
import com.ixilink.banknote_box.common.model.EquipmentStatus;
import com.ixilink.banknote_box.common.pojo.Equipment;
import com.ixilink.banknote_box.common.pojo.EquipmentExample;
import com.ixilink.banknote_box.common.spring.SpringUtils;
import com.ixilink.banknote_box.common.util.EquipmentUtil;
import com.ixilink.banknote_box.common.util.OnlineTest;
import com.ixilink.banknote_box.common.util.RedisUtil;
import com.ixilink.banknote_box.message.websocket.EquipmentSocket;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @description:
 * @author: 张俊
 * @date: 2019-11-20 19:38
 */
@Slf4j
public class EquipmentStatusJob  implements Runnable {

    private ApplicationContext applicationContext = SpringUtils.getApplicationContext();
    private EquipmentMapper equipmentMapper = applicationContext.getBean(EquipmentMapper.class);
    private RedisUtil redisUtil = applicationContext.getBean(RedisUtil.class);
    private EquipmentSocket socket = applicationContext.getBean(EquipmentSocket.class);
    private volatile Map<String,Object> message;

    @Override
    public void run() {

        init();

        job();
    }

    private void init(){
//        EquipmentCountModel sumCount = new EquipmentCountModel();
//        EquipmentCountModel onlineCount = new EquipmentCountModel();
        Map<String,Object> sumCountMap = new HashMap<>();
        Map<String,Object> onlineCountMap = new HashMap<>();

        Map<String,Object> onlineListMap = new HashMap<>();
        Map<String,Object> downListMap = new HashMap<>();

        EquipmentExample equipmentExample = new EquipmentExample();
        equipmentExample.createCriteria().andStateEqualTo(1);
        List<Equipment> equipments = equipmentMapper.selectByExample(equipmentExample);

        redisUtil.del("equipmentStatus");
        redisUtil.del("sumCount");
        redisUtil.del("onlineCount");
        redisUtil.del("onlineList");
        redisUtil.del("downList");
        ExecutorService cachedThread = Executors.newCachedThreadPool();
        equipments.forEach(equipment -> {
            EquipmentUtil.setCountMap(equipment.getEquipmentType(), equipment.getLibraryId(),sumCountMap);

            cachedThread.execute(new Runnable() {
                @Override
                public void run() {
                    Integer s = OnlineTest.ping(equipment.getIp())?1:2;
                    if (s == 1) {
                        EquipmentUtil.setCountMap(equipment.getEquipmentType(),equipment.getLibraryId(),onlineCountMap);
                        EquipmentUtil.setList(equipment.getEquipmentType(),equipment.getLibraryId(),equipment.getId(),onlineListMap);
                    }else {
                        EquipmentUtil.setList(equipment.getEquipmentType(),equipment.getLibraryId(),equipment.getId(),downListMap);
                    }
                    EquipmentStatus equipmentStatus = new EquipmentStatus(equipment.getIp(),equipment.getEquipmentType(),equipment.getLibraryId(),s);
                    redisUtil.hset("equipmentStatus",equipment.getId().toString(),equipmentStatus);
                }
            });
        });
        cachedThread.shutdown();
        redisUtil.hmset("sumCount",sumCountMap);
        while (true) {
            if (cachedThread.isTerminated()) {
                //在线数量
                redisUtil.hmset("onlineCount",onlineCountMap);
                //在线id集合
                redisUtil.hmset("onlineList",onlineListMap);
                //离线id集合
                redisUtil.hmset("downList",downListMap);
                log.info("初始化完毕");
                break;
            }
        }
    }

    @SuppressWarnings("unchecked")
    private void job(){
        while (true){
            ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
            Map<Object, Object> equipmentStatuses = redisUtil.hmget("equipmentStatus");
            Set<Object> keys = equipmentStatuses.keySet();
            for (Object key:keys){
                EquipmentStatus equipmentStatus = (EquipmentStatus)equipmentStatuses.get(key);
                Integer id = Integer.valueOf(key.toString());
                cachedThreadPool.execute(new Runnable() {
                    @Override
                    public void run() {
                        Integer s = OnlineTest.ping(equipmentStatus.getIp())?1:2;
                        if (!equipmentStatus.getStatus().equals(s)){
                            //更新状态
                            equipmentStatus.setStatus(s);
                            redisUtil.hset("equipmentStatus",key.toString(),equipmentStatus);
                            //返回数据计算
                            String typeKey = EquipmentUtil.getKey(equipmentStatus.getEquipmentType());
                            Integer keyValue = 0;
                            List<Integer> onlineList = new ArrayList<>();
                            List<Integer> downList = new ArrayList<>();
                            if (redisUtil.hHasKey("onlineCount", typeKey+ "-" +equipmentStatus.getLibraryId())){
                                keyValue = (Integer) redisUtil.hget("onlineCount", typeKey+ "-" +equipmentStatus.getLibraryId());
                            }
                            if (redisUtil.hHasKey("onlineList", typeKey+ "-" +equipmentStatus.getLibraryId())){
                                onlineList = (ArrayList<Integer>)redisUtil.hget("onlineList", typeKey+ "-" +equipmentStatus.getLibraryId());
                            }
                            if (redisUtil.hHasKey("downList", typeKey+ "-" +equipmentStatus.getLibraryId())){
                                downList = (ArrayList<Integer>)redisUtil.hget("downList", typeKey+ "-" +equipmentStatus.getLibraryId());
                            }
                            //新的在线数量值
//                            System.out.println("onlineList:"+onlineList+" downList"+downList+"  "+typeKey+ "-" +equipmentStatus.getLibraryId());
                            if (s==1){
                                keyValue++;
                                if (!onlineList.contains(id)){
                                    onlineList.add(id);
                                    downList.remove(id);
                                }
                            }else {
                                if (keyValue > 0) keyValue--;
                                if (!downList.contains(id)){
                                    downList.add(id);
                                    onlineList.remove(id);
                                }
                            }
                            redisUtil.hset("onlineList", typeKey+ "-" +equipmentStatus.getLibraryId(),onlineList);
                            redisUtil.hset("downList", typeKey+ "-" +equipmentStatus.getLibraryId(),downList);
                            redisUtil.hset("onlineCount", typeKey+ "-" +equipmentStatus.getLibraryId(),keyValue);
                            //返回数据
                            try {
                                message = new HashMap<>();
                                message.put("sumCount",onlineList.size()+downList.size());
                                message.put("onlineCount",onlineList.size());
                                message.put("downCount",downList.size());
                                message.put("type",equipmentStatus.getEquipmentType());
                                message.put("library",equipmentStatus.getLibraryId());
                                message.put("id",key);
                                socket.sendMessage(message);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
            }
            cachedThreadPool.shutdown();
            long start = System.currentTimeMillis();
            while (true) {
                if (cachedThreadPool.isTerminated()) {
                    long time = System.currentTimeMillis() - start;
                    log.debug("检测完毕，总耗时：" + time + " ms(毫秒)！\n");
                    break;
                }
            }
        }
    }

}
