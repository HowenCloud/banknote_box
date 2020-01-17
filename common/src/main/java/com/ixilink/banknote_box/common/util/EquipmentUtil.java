package com.ixilink.banknote_box.common.util;

import com.ixilink.banknote_box.common.model.EquipmentCountModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: 张俊
 * @date: 2019-11-21 11:36
 */
public class EquipmentUtil {

    /**
     * 获取key
     * @param type
     * @return
     */
    public static String getKey(Integer type){
        String key = "";
        switch (type){
            case 1:
                key = "readerWriter";
                break;
            case 2:
                key = "centerControl";
                break;
            case 3:
                key = "handover";
                break;
            case 4:
                key = "boxing";
                break;
        }
        return key;
    }



    public static void setCount(Integer type, EquipmentCountModel count){
        switch (type){
            case 1:
                count.setReaderWriter(count.getReaderWriter()+1);
                break;
            case 2:
                count.setCenterControl(count.getCenterControl()+1);
                break;
            case 3:
                count.setHandover(count.getHandover()+1);
                break;
            case 4:
                count.setBoxing(count.getBoxing()+1);
                break;
        }
    }

    public static void setCountMap(Integer type, Integer libraryId, Map<String,Object> countMap){
        Object o = countMap.get(getKey(type) + "-" + libraryId);
        Integer value = o==null?0:(Integer) o;
        countMap.put(getKey(type) + "-" + libraryId,value+1);
    }

    public static void setList(Integer type,Integer id, List<Integer> ReaderWriter, List<Integer> CenterControl, List<Integer> Handover, List<Integer> Boxing){
        switch (type){
            case 1:
                ReaderWriter.add(id);
                break;
            case 2:
                CenterControl.add(id);
                break;
            case 3:
                Handover.add(id);
                break;
            case 4:
                Boxing.add(id);
                break;
        }
    }

    @SuppressWarnings("unchecked")
    public static void setList(Integer type, Integer libraryId,Integer id, Map<String,Object> listMap){
        Object o = listMap.get(getKey(type) + "-" + libraryId);
        List<Integer> list = new ArrayList<>();
        if (o != null){
            list = (ArrayList<Integer>) o;
        }
        list.add(id);
        listMap.put(getKey(type) + "-" + libraryId,list);
    }
}
