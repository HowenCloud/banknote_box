package com.ixilink.banknote_box.common.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class Department {

    @ApiModelProperty(value = "id",hidden=true)
    private Integer id;
    @ApiModelProperty(value = "部门名称",required = true)
    private String departmentName;
    @ApiModelProperty(value = "备注描述",required = true)
    private String remarks;
    @ApiModelProperty(value = "创建时间",hidden=true)
    private Long createTime;
    @ApiModelProperty(value = "部门状态",hidden=true)
    private Integer state;
    @ApiModelProperty(value = "组织机构中心库id",hidden=true)
    private Integer libraryId;

    public Department() {
    }

    public Department(Integer id, String departmentName, String remarks, Integer state) {
        this.id = id;
        this.departmentName = departmentName;
        this.remarks = remarks;
        this.state = state;
    }
}