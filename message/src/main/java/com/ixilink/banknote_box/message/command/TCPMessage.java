package com.ixilink.banknote_box.message.command;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: 张俊
 * @date: 2019-11-09 15:25
 */
class TCPMessage {

    /**
     * tcp命令返回报文解析
     * @param data 报文
     * @param returnMap 解析后，返回数据集合
     */
    static void analysis(String data, Map<String, Object> returnMap){
        //状态码
        String code = data.substring(2,4);
        //指令类型
        String obj = data.substring(6, 8);
        //数据长度
        int length = Integer.parseInt(data.substring(10, 12), 16);
        //数据
        String parm = data.substring(12, data.length());
        returnMap.put("status", 200);
        switch (code){
            case "00":{
                switch (obj) {
                    case "02":
                        List<String> rfids = new ArrayList<>();
                        int count = parm.length() / 12;
                        for (int i=0; i<count; i++){
                            String rfid = parm.substring(12*i,12*(i+1));
                            rfids.add(rfid.substring(0,6));
                        }
                        returnMap.put("rfid",rfids);
                        break;
                    case "09":
                        break;
                    case "0E":
                        break;
                    default:
                        break;
                }
                break;
            }
            case "01":{
                if (obj.equals("02")){
                    returnMap.put("status", 200);
                    returnMap.put("rfid",new ArrayList<>());
                }else {
                    returnMap.put("status", 445);
                    returnMap.put("errorMsg", "操作无效");
                }
                break;
            }
            case "02":{
                returnMap.put("status", 445);
                returnMap.put("errorMsg", "不支持的命令");
                break;
            }
            case "03":{
                returnMap.put("status", 445);
                returnMap.put("errorMsg", "不支持的对象");
                break;
            }
            case "04":{
                returnMap.put("status", 445);
                returnMap.put("errorMsg", "参数错误");
                break;
            }
            case "05":{
                returnMap.put("status", 447);
                returnMap.put("errorMsg", "设备忙，暂时没有反应");
                break;
            }
        }
    }

}
