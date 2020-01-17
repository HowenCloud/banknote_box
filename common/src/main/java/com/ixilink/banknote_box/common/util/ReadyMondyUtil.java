package com.ixilink.banknote_box.common.util;


import com.ixilink.banknote_box.common.pojo.ReadyMoneyApplyTarget;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Map;

@Component
public class ReadyMondyUtil {

	@Resource
	private RedisUtil redisUtil;

	public ReadyMoneyApplyTarget getNum(Integer id,String storageTaskSupplement,String storageTaskNum){

		Cursor<Map.Entry<Object, Object>> cursorDb = redisUtil.getStringRedisTemplate().opsForHash().scan(storageTaskNum, ScanOptions.scanOptions().match(id + ":" + "db" + ":" + "*").build());
		int dbNum = 0;
		while (cursorDb.hasNext()) {
			Map.Entry<Object, Object> entry = cursorDb.next();
			Integer value = Integer.parseInt(entry.getValue().toString());
			dbNum += value;
		}
		try {
			cursorDb.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Cursor<Map.Entry<Object, Object>> cursorDf = redisUtil.getStringRedisTemplate().opsForHash().scan(storageTaskNum, ScanOptions.scanOptions().match(id + ":" + "df" + ":" + "*").build());
		int dfNum = 0;
		while (cursorDf.hasNext()) {
			Map.Entry<Object, Object> entry = cursorDf.next();
			Integer value = Integer.parseInt(entry.getValue().toString());
			dfNum += value;
		}
		try {
			cursorDf.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Cursor<Map.Entry<Object, Object>> cursorCrs = redisUtil.getStringRedisTemplate().opsForHash().scan(storageTaskNum, ScanOptions.scanOptions().match(id + ":" + "crs" + ":" + "*").build());
		int crsNum = 0;
		while (cursorCrs.hasNext()) {
			Map.Entry<Object, Object> entry = cursorCrs.next();
			Integer value = Integer.parseInt(entry.getValue().toString());
			crsNum += value;
		}
		try {
			cursorCrs.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Cursor<Map.Entry<Object, Object>> cursorScrs = redisUtil.getStringRedisTemplate().opsForHash().scan(storageTaskNum, ScanOptions.scanOptions().match(id + ":" + "scrs" + ":" + "*").build());
		int scrsNum = 0;
		while (cursorScrs.hasNext()) {
			Map.Entry<Object, Object> entry = cursorScrs.next();
			Integer value = Integer.parseInt(entry.getValue().toString());
			scrsNum += value;
		}
		try {
			cursorScrs.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		Cursor<Map.Entry<Object, Object>> dbSum = redisUtil.getStringRedisTemplate().opsForHash().scan(storageTaskSupplement, ScanOptions.scanOptions().match(id + ":" + "db" + ":" + "*").build());
		while (dbSum.hasNext()) {
			Map.Entry<Object, Object> entry = dbSum.next();
			Integer value = Integer.parseInt(entry.getValue().toString());
			dbNum += value;
		}
		try {
			dbSum.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Cursor<Map.Entry<Object, Object>> dfSum = redisUtil.getStringRedisTemplate().opsForHash().scan(storageTaskSupplement, ScanOptions.scanOptions().match(id + ":" + "df" + ":" + "*").build());
		while (dfSum.hasNext()) {
			Map.Entry<Object, Object> entry = dfSum.next();
			Integer value = Integer.parseInt(entry.getValue().toString());
			dfNum += value;
		}
		try {
			dfSum.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Cursor<Map.Entry<Object, Object>> crsSum = redisUtil.getStringRedisTemplate().opsForHash().scan(storageTaskSupplement, ScanOptions.scanOptions().match(id + ":" + "crs" + ":" + "*").build());
		while (crsSum.hasNext()) {
			Map.Entry<Object, Object> entry = crsSum.next();
			Integer value = Integer.parseInt(entry.getValue().toString());
			crsNum += value;
		}
		try {
			crsSum.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Cursor<Map.Entry<Object, Object>> scrsSum = redisUtil.getStringRedisTemplate().opsForHash().scan(storageTaskSupplement, ScanOptions.scanOptions().match(id + ":" + "scrs" + ":" + "*").build());
		while (scrsSum.hasNext()) {
			Map.Entry<Object, Object> entry = scrsSum.next();
			Integer value = Integer.parseInt(entry.getValue().toString());
			scrsNum += value;
		}
		try {
			scrsSum.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new ReadyMoneyApplyTarget(dbNum, dfNum, scrsNum, crsNum, dbNum + dfNum + scrsNum + crsNum);
	}




}
