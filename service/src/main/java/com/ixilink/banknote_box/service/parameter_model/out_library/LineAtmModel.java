package com.ixilink.banknote_box.service.parameter_model.out_library;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @description:
 * @author: 张皓峰
 * @date: 2019-12-05 11:07
 */
@Data
@ApiModel
public class LineAtmModel {
    @ApiModelProperty(value = "ATM的ID",required = true)
    private Integer atmId;
    @ApiModelProperty(value = "网点名称",required = true)
    private String bankBranch;
    @ApiModelProperty(value = "ATM编号",required = true)
    private String number;
    @ApiModelProperty(value = "ATM名称",required = true)
    private String name;
    @ApiModelProperty(value = "渠道ID",required = true)
    private String channel;
    @ApiModelProperty(value = "钞箱总数",required = true)
    private Integer totalBox;
    @ApiModelProperty(value = "单箱金额(单位)",required = true)
    private Integer unit;
    @ApiModelProperty(value = "总金额",required = true)
    private Integer totalMoney;
    @ApiModelProperty(value = "备注",required = true)
    private String remarks;

    public LineAtmModel() {
    }

    public LineAtmModel(Integer atmId, String bankBranch, String number, String name, String channel, Integer totalBox, Integer unit, Integer totalMoney, String remarks) {
        this.atmId = atmId;
        this.bankBranch = bankBranch;
        this.number = number;
        this.name = name;
        this.channel = channel;
        this.totalBox = totalBox;
        this.unit = unit;
        this.totalMoney = totalMoney;
        this.remarks = remarks;
    }
}
