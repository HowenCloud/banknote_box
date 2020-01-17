package com.ixilink.banknote_box.login.parameter_model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @description:
 * @author: 张俊
 * @date: 2019-11-11 15:47
 */
@Data
@ApiModel
public class LoginModel {
    @ApiModelProperty(value = "登录名",required = true)
    private String loginName;
    @ApiModelProperty(value = "登录密码",required = true)
    private String password;

    public LoginModel() {
    }
    public LoginModel(String loginName, String password) {
        this.loginName = loginName;
        this.password = password;
    }

}
