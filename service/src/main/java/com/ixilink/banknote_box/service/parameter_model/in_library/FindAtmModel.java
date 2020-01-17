package com.ixilink.banknote_box.service.parameter_model.in_library;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @description:
 * @author: 张皓峰
 * @date: 2020-01-11 10:54
 */
@Data
@ApiModel("查询未加钞ATM参数")
public class FindAtmModel {
    @ApiModelProperty(value = "设备编号")
    private String number;
    @ApiModelProperty(value = "线路id")
    private Integer lineId = 0;
    @ApiModelProperty(value = "页码")
    private Integer pageNum = 1;
    @ApiModelProperty(value = "数量")
    private Integer pageSize = 10;
}
