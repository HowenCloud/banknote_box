package com.ixilink.banknote_box.message.command;

import com.ixilink.banknote_box.common.exception.BusinessException;
import com.ixilink.banknote_box.common.model.Code;
import com.ixilink.build_box.common.MinaCabinetBean;
import com.ixilink.build_box.tcp.NettyClient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: 张俊
 * @date: 2019-11-09 15:11
 */
public class BoxManager {

    private NettyClient nettyClient;
    private String ip;

    private BoxManager() {
    }

    public BoxManager(String ip) {
        this.ip = ip;
    }

    /**
     * 建立连接
     */
    public void connection() {
        Map<String,Object> resultMap = new HashMap<>();
        MinaCabinetBean.getInstance().connection(ip,resultMap);
        if (resultMap.get("status").equals(200)){
            nettyClient = (NettyClient)resultMap.get("connection");
        }else {
            throw new BusinessException(Code.MESSAGE_ERROR.getCode(), "连接失败");
        }
    }
    /**
     * 关闭连接
     */
    public void close() {
        try {
            MinaCabinetBean.getInstance().close(nettyClient,ip);
        }catch (Exception e){
            throw new BusinessException(Code.MESSAGE_ERROR.getCode(),"关闭连接异常");
        }
    }

    /**
     * 读写器盘点
     */
    public void scan() {
        Map<String, Object> resultMap = new HashMap<>();
        String result = MinaCabinetBean.getInstance().command(nettyClient, ip, "0209", "03");
        TCPMessage.analysis(result,resultMap);
        if (!resultMap.get("status").equals(200)){
            throw new BusinessException(Code.MESSAGE_ERROR.getCode(),resultMap.get("errorMsg").toString());
        }
    }

    /**
     * 查询读写器盘点结果
     * @return status 状态码
     */
    @SuppressWarnings("unchecked")
    public List<String> read() {
        Map<String, Object> resultMap = new HashMap<>();
        String result = MinaCabinetBean.getInstance().command(nettyClient, ip,"0002","");
        TCPMessage.analysis(result,resultMap);
        if (!resultMap.get("status").equals(200)){
            throw new BusinessException(Code.MESSAGE_ERROR.getCode(),resultMap.get("errorMsg").toString());
        }
        return (List<String>)resultMap.get("rfid");
    }

    /**
     * 清除读写器盘点结果
     */
    public void clean() {
        Map<String, Object> resultMap = new HashMap<>();
        String result = MinaCabinetBean.getInstance().command(nettyClient, ip, "0402", "");
        TCPMessage.analysis(result,resultMap);
    }

}
