package com.ixilink.banknote_box.service.parameter_model.role;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @description:
 * @author: 张俊
 * @date: 2019-11-13 20:26
 */
@Data
@ApiModel
public class AddRoleModel {
    @ApiModelProperty(value = "角色ID")
    private Integer id;
    @ApiModelProperty(value = "角色名称")
    private String roleName;
    @ApiModelProperty(value = "角色描述")
    private String remarks;
    @ApiModelProperty(value = "页面权限")
    private List<Integer> pages;
    @ApiModelProperty(value = "功能权限")
    private List<Integer> permissions;
}
