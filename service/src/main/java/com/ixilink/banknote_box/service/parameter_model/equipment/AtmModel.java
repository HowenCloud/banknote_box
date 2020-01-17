package com.ixilink.banknote_box.service.parameter_model.equipment;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @description:
 * @author: 张俊
 * @date: 2019-11-19 16:28
 */
@Data
@ApiModel
public class AtmModel {
    @ApiModelProperty(value = "ATM的ID")
    private Integer id;
    @ApiModelProperty(value = "中心控制器ID")
    private Integer controllerId;
    @ApiModelProperty(value = "ATM编号")
    private String number;
    @ApiModelProperty(value = "ATM名称")
    private String name;
    @ApiModelProperty(value = "渠道ID")
    private String channel;
    @ApiModelProperty(value = "通道ID")
    private Integer passageway;
}
