package com.ixilink.banknote_box.common.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
@Data
@ApiModel
public class ReadyMoneyNormalResult {

    @ApiModelProperty(value = "DB钞箱数量")
    private Integer dbNum = 0;

    @ApiModelProperty(value = "DF钞箱数量")
    private Integer dfNum = 0;


    @ApiModelProperty(value = "CRS钞箱数量")
    private Integer crsNum = 0;


    @ApiModelProperty(value = "SCRS钞箱数量")
    private Integer scrsNum = 0;

    public ReadyMoneyNormalResult() {
    }

    public ReadyMoneyNormalResult(Integer dbNum, Integer dfNum, Integer crsNum, Integer scrsNum) {
        this.dbNum = dbNum;
        this.dfNum = dfNum;
        this.crsNum = crsNum;
        this.scrsNum = scrsNum;
    }
}
