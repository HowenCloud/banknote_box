package com.ixilink.banknote_box.service.parameter_model.app;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @description:
 * @author: 张皓峰
 * @date: 2019-12-21 9:30
 */
@Data
@ApiModel
public class SubmitBoxs {
    @ApiModelProperty(value = "钞箱位置号（1~5）")
    private Integer position;
    @ApiModelProperty(value = "钞箱编号")
    private String number;
    @ApiModelProperty(value = "钞箱金额")
    private Integer money;
    @ApiModelProperty(value = "钞箱类型")
    private String typeName;
    @ApiModelProperty(value = "识别类型：1正常识别，2补录")
    private Integer checkType = 1;
}
