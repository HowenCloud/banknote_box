package com.ixilink.banknote_box.service.parameter_model.out_library;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @description:
 * @author: 张皓峰
 * @date: 2019-12-06 20:02
 */
@Data
@ApiModel
public class FindLineAtmModel {
    @ApiModelProperty(value = "网点名称")
    private String bankBranch = "";
    @ApiModelProperty(value = "ATM编号")
    private String number = "";
    @ApiModelProperty(value = "出库线路ID",required = true)
    private Integer outLineId = 0;
    @ApiModelProperty(value = "页码")
    private Integer pageNum = 1;
    @ApiModelProperty(value = "数量")
    private Integer pageSize = 10;
}
