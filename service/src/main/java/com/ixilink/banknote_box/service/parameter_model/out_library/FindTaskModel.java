package com.ixilink.banknote_box.service.parameter_model.out_library;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @description:
 * @author: 张皓峰
 * @date: 2019-12-06 15:28
 */
@Data
@ApiModel("出库任务查询参数")
public class FindTaskModel {
    @ApiModelProperty(value = "审批状态：0待审核 1通过 2未通过")
    private Integer applyState;
    @ApiModelProperty(value = "配箱进度：0待配箱 1已完成")
    private Integer boxingState;
    @ApiModelProperty(value = "校验状态：0待校验 1已校验")
    private Integer checkState;
    @ApiModelProperty(value = "任务进度：1进行中 2已完成")
    private Integer taskState;
    @ApiModelProperty(value = "页码")
    private Integer pageNum = 1;
    @ApiModelProperty(value = "数量")
    private Integer pageSize = 10;
}
