package com.ixilink.banknote_box.service.parameter_model.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @description:
 * @author: 张俊
 * @date: 2019-11-13 14:58
 */
@Data
@ApiModel
public class FindUserModel {

    @ApiModelProperty(value = "姓名")
    private String name;
    @ApiModelProperty(value = "部门ID")
    private Integer departmentId;
    @ApiModelProperty(value = "所属中心库")
    private Integer libraryId;
    @ApiModelProperty(value = "页码")
    private Integer pageNum = 1;
    @ApiModelProperty(value = "数量")
    private Integer pageSize = 10;
}
