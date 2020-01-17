package com.ixilink.banknote_box.service.parameter_model.banknote_line;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @description:
 * @author: 张俊
 * @date: 2019-11-22 14:14
 */
@Data
@ApiModel
public class FindLineModel {
    @ApiModelProperty(value = "加钞人姓名(根据输入姓名查询时)")
    private String username;
    @ApiModelProperty(value = "加钞人员(根据下拉选择查询时)")
    private List<Integer> users;
    @ApiModelProperty(value = "出库任务id")
    private Integer taskId = 0;
    @ApiModelProperty(value = "页码")
    private Integer pageNum = 1;
    @ApiModelProperty(value = "数量")
    private Integer pageSize = 10;
}
