package com.ixilink.banknote_box.service.parameter_model.banknote_line;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @description: 线路参数模型
 * @author: 张俊
 * @date: 2019-11-21 16:54
 */
@Data
@ApiModel
public class BanknoteLineModel {
    @ApiModelProperty(value = "修改的线路ID")
    private Integer id;
    @ApiModelProperty(value = "线路名称")
    private String name;
    @ApiModelProperty(value = "备注")
    private String remarks;
    @ApiModelProperty(value = "线路人员id")
    private List<Integer> users;
    @ApiModelProperty(value = "ATM列表id")
    private List<Integer> atms;
}
