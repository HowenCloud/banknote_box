package com.ixilink.banknote_box.service.parameter_model.equipment;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @description:
 * @author: 张俊
 * @date: 2019-11-18 20:35
 */
@Data
@ApiModel
public class EquipmentCenterControllerModel {
    @ApiModelProperty(value = "设备ID")
    private Integer id;
    @ApiModelProperty(value = "设备编号")
    private String number;
    @ApiModelProperty(value = "设备名称")
    private String name;
    @ApiModelProperty(value = "设备ip")
    private String ip;
    @ApiModelProperty(value = "状态：1在线  2离线")
    private Integer status;
    @ApiModelProperty(value = "ATM机",required = true)
    private List<AtmModel> atms;
    @ApiModelProperty(value = "页码")
    private Integer pageNum = 1;
    @ApiModelProperty(value = "数量")
    private Integer pageSize = 10;
}
