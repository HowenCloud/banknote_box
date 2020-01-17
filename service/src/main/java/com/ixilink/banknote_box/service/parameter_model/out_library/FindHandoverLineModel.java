package com.ixilink.banknote_box.service.parameter_model.out_library;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @description:
 * @author: 张皓峰
 * @date: 2020-01-03 11:11
 */
@Data
@ApiModel
public class FindHandoverLineModel {
    @ApiModelProperty(value = "出库任务id",required = true)
    private Integer taskId;
    @ApiModelProperty(value = "交接进度：0钞箱校验 1交接岗 2加钞岗 3识别完成",required = true)
    private Integer checkSchedule = 0;
//    @ApiModelProperty(value = "线路名称")
//    private String lineName;
}
