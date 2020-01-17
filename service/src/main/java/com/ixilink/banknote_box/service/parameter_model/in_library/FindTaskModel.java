package com.ixilink.banknote_box.service.parameter_model.in_library;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @description:
 * @author: 张皓峰
 * @date: 2019-12-06 15:28
 */
@Data
@ApiModel("入库任务查询参数")
public class FindTaskModel {
    @ApiModelProperty(value = "任务进度：1进行中 2已完成")
    private Integer state;
    @ApiModelProperty(value = "页码")
    private Integer pageNum = 1;
    @ApiModelProperty(value = "数量")
    private Integer pageSize = 10;
}
