package com.ixilink.banknote_box.common.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @description:
 * @author: 张俊
 * @date: 2019-11-25 19:25
 */
@Data
@ApiModel
public class BackLibrary {
    @ApiModelProperty(value = "库id")
    private Integer id;
    @ApiModelProperty(value = "中心库（机构）名称")
    private String name;
}
