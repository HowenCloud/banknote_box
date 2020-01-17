package com.ixilink.banknote_box.common.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.HashMap;
import java.util.Map;

/**
 * description:
 * author: 张俊
 * date: 2019-11-05 10:26
 */
@ApiModel(description= "返回响应数据")
public class Result {
    @ApiModelProperty(value = "状态码")
    private Integer code;
    @ApiModelProperty(value = "状态消息")
    private String msg;
    @ApiModelProperty(value = "具体数据")
    private Map<String,Object> data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Map<String,Object> getData() {
        return data;
    }

    public void setData(Map<String,Object> data) {
        this.data = data;
    }

    public Result(Integer code, String message){
        this.code = code;
        this.msg = message;
        this.data = new HashMap<>();
    }

    public Result(Integer code, String message, Map<String,Object> data){
        this.code = code;
        this.msg = message;
        this.data = data;
    }
}

