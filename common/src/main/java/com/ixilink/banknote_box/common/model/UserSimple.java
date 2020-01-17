package com.ixilink.banknote_box.common.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @description:
 * @author: 张皓峰
 * @date: 2019-12-05 12:22
 */
@Data
@ApiModel
public class UserSimple {
    @ApiModelProperty(value = "用户ID")
    private Integer id;
    @ApiModelProperty(value = "姓名")
    private String name;
}
