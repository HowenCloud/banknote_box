package com.ixilink.banknote_box.service.parameter_model.in_library;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @description:
 * @author: 张皓峰
 * @date: 2020-01-14 16:40
 */
@Data
@ApiModel("补录钞箱")
public class SupplementBox {
    @ApiModelProperty(value = "出库线路ID")
    private Integer lineId;
    @ApiModelProperty(value = "ATM任务ID")
    private Integer atmId;
    @ApiModelProperty(value = "钞箱编号")
    private String number;
}
