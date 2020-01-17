package com.ixilink.banknote_box.service.parameter_model.out_library;

import com.ixilink.banknote_box.common.model.UserSimple;
import com.ixilink.banknote_box.common.pojo.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @description:
 * @author: 张皓峰
 * @date: 2019-12-04 17:55
 */
@Data
@ApiModel(value = "加钞线路")
public class LibraryLineModel {
    @ApiModelProperty(value = "线路名称",required = true)
    private String lineName;
    @ApiModelProperty(value = "钞箱总数",required = true)
    private Integer totalBox;
    @ApiModelProperty(value = "总金额",required = true)
    private Integer totalMoney;
    @ApiModelProperty(value = "加钞人员",required = true)
    private List<UserSimple> lineUser;
    @ApiModelProperty(value = "批次",required = true)
    private Integer batch;
    @ApiModelProperty(value = "备注",required = true)
    private String remarks;
    @ApiModelProperty(value = "该线路的ATM机",required = true)
    private List<LineAtmModel> atms;

    public LibraryLineModel() {
    }

    public LibraryLineModel(String lineName, Integer totalBox, Integer totalMoney, List<UserSimple> lineUser, Integer batch, String remarks, List<LineAtmModel> atms) {
        this.lineName = lineName;
        this.totalBox = totalBox;
        this.totalMoney = totalMoney;
        this.lineUser = lineUser;
        this.batch = batch;
        this.remarks = remarks;
        this.atms = atms;
    }
}
