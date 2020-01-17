package com.ixilink.banknote_box.common.pojo;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
public class SysLogExcel {

    @ApiModelProperty("操作时间")
    @ApiParam(value = "操作时间",required = false)
    private String createTime;
    @ApiModelProperty("操作人")
    @ApiParam(value = "操作人",required = false)
    private String operator;
    @ApiModelProperty("操作对象")
    @ApiParam(value = "操作对象",required = false)
    private String operationObject;
    @ApiModelProperty("操作类容")
    @ApiParam(value = "操作类容",required = false)
    private String operationContent;
    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getOperationObject() {
        return operationObject;
    }

    public void setOperationObject(String operationObject) {
        this.operationObject = operationObject;
    }

    public String getOperationContent() {
        return operationContent;
    }

    public void setOperationContent(String operationContent) {
        this.operationContent = operationContent;
    }
}