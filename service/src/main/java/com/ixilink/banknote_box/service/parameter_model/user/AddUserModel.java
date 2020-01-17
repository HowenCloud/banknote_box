package com.ixilink.banknote_box.service.parameter_model.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @description:
 * @author: 张俊
 * @date: 2019-11-13 15:26
 */
@Data
@ApiModel
public class AddUserModel {
    @ApiModelProperty(value = "用户ID")
    private Integer id;
    @ApiModelProperty(value = "登录名")
    private String loginName;
    @ApiModelProperty(value = "姓名")
    private String name;
    @ApiModelProperty(value = "性别：1男 2女")
    private Integer sex;
    @ApiModelProperty(value = "部门ID")
    private Integer departmentId;
    @ApiModelProperty(value = "角色ID")
    private Integer roleId;
    @ApiModelProperty(value = "密码")
    private String password;
    @ApiModelProperty(value = "所属中心库")
    private Integer libraryId;
}
