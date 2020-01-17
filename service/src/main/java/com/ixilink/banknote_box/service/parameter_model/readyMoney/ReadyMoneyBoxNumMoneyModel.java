package com.ixilink.banknote_box.service.parameter_model.readyMoney;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class ReadyMoneyBoxNumMoneyModel {
    @ApiModelProperty(value = "金额")
    private  Integer boxMoney;
    @ApiModelProperty(value = "数量")
    private  Integer number;
}
