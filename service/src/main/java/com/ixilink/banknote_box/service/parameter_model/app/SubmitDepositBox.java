package com.ixilink.banknote_box.service.parameter_model.app;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @description:
 * @author: 张皓峰
 * @date: 2019-12-21 9:33
 */
@Data
@ApiModel
public class SubmitDepositBox {
    @ApiModelProperty(value = "加钞amt任务主键id")
    private Integer id;
    @ApiModelProperty(value = "加钞钞箱信息集合")
    private List<SubmitBoxs> box;
}
