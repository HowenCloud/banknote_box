package com.ixilink.banknote_box.service.parameter_model.readyMoney;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel
public class InsertReadyMoneyTaskModel {
    @ApiModelProperty(value = "预钞箱任务id",hidden = true)
    private Integer id;
    @ApiModelProperty(value = "任务接受人接受人的id，用逗号分隔")
    private String receivePeople;
    @ApiModelProperty(value = "db钞箱")
    private List<ReadyMoneyBoxNumMoneyModel> db;
    @ApiModelProperty(value = "df钞箱")
    private List<ReadyMoneyBoxNumMoneyModel> df;
    @ApiModelProperty(value = "crs钞箱")
    private List<ReadyMoneyBoxNumMoneyModel> crs;
    @ApiModelProperty(value = "scrs钞箱")
    private List<ReadyMoneyBoxNumMoneyModel> scrs;
}
