package com.ixilink.banknote_box.common.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.List;
@Data
@ApiModel
public class HandOverModel {
    @ApiModelProperty(value = "组长id")
    private List<Integer> grouperId;
    @ApiModelProperty(value = "管库员id")
    private List<Integer> libraryerId;
    @ApiModelProperty(value = "监交岗")
    private Integer postId;
    @ApiModelProperty(value = "任务id")
    private Integer id;
    @ApiModelProperty(value = "标记")
    private String mark;
}