package com.ixilink.banknote_box.service.parameter_model.equipment;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @description:
 * @author: 张俊
 * @date: 2019-11-22 18:11
 */
@Data
@ApiModel
public class FindATM {
    @ApiModelProperty(value = "网点名称")
    private String bankBranch;
    @ApiModelProperty(value = "ATM编号")
    private String number;
    @ApiModelProperty(value = "页码")
    private Integer pageNum = 1;
    @ApiModelProperty(value = "数量")
    private Integer pageSize = 10;
}
