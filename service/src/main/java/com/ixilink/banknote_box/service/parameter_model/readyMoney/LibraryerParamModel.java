package com.ixilink.banknote_box.service.parameter_model.readyMoney;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel
public class LibraryerParamModel {
    @ApiModelProperty(value = "页码")
    private Integer page;
    @ApiModelProperty(value = "页数")
    private Integer pageSize;
    @ApiModelProperty(value = "库id",hidden = true)
    private Integer libraryId;
    @ApiModelProperty(value = "1在持, 2出库")
    private Integer state;
    @ApiModelProperty(value = "库管员id")
    private Integer libraryerId;
    @ApiModelProperty(value = "开始时间")
    private Long beginTime;
    @ApiModelProperty(value = "结束时间")
    private Long  endTime;
}
