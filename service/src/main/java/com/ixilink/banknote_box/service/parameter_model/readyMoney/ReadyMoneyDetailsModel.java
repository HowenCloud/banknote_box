package com.ixilink.banknote_box.service.parameter_model.readyMoney;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class ReadyMoneyDetailsModel {
    @ApiModelProperty(value = "预钞箱任务id",hidden = true)
    private Integer id;
    @ApiModelProperty(value = "预钞箱任务id")
    private Integer taskId;
    @ApiModelProperty(value = "钞箱编号")
    private String carBoxNumber;
    @ApiModelProperty(value = "钞箱金额")
    private Integer carMoney;
    @ApiModelProperty(value = "基金袋id")
    private Integer fundId;
    @ApiModelProperty(value = "整点钞操作岗人员id",hidden = true)
    private String usersId;
    @ApiModelProperty(value = "钞箱类型")
    private Integer carType;
    @ApiModelProperty(value = "基金袋名字")
    private String fundBagName;

}
