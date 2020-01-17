package com.ixilink.banknote_box.service.parameter_model.Department;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class DepartmentModel {

    @ApiModelProperty(value = "id")
    private Integer id;
    @ApiModelProperty(value = "部门名称",required = true)
    private String departmentName;
    @ApiModelProperty(value = "备注描述",required = true)
    private String remarks;
    @ApiModelProperty(value = "创建时间")
    private Long createTime;
    @ApiModelProperty(value = "部门状态")
    private Integer state;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName == null ? null : departmentName.trim();
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}