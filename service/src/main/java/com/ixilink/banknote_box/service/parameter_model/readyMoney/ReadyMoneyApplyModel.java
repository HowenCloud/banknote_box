package com.ixilink.banknote_box.service.parameter_model.readyMoney;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class ReadyMoneyApplyModel {
    @ApiModelProperty(value = "审批表id",hidden = true)
    private Integer id;
    @ApiModelProperty(value = "DB钞箱数量")
    private Integer db=0;
    @ApiModelProperty(value = "DF钞箱数量")
    private Integer df=0;
    @ApiModelProperty(value = "CRS钞箱数量")
    private Integer crs=0;
    @ApiModelProperty(value = "SCRS钞箱数量")
    private Integer scrs=0;
    @ApiModelProperty(value = "创建时间",hidden = true)
    private Long createTime;
    @ApiModelProperty(value = "整点组长id")
    private Integer grouperId;
    private List<Integer> libraryerIds;
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDb() {
        return db;
    }

    public void setDb(Integer db) {
        this.db = db;
    }

    public Integer getDf() {
        return df;
    }

    public void setDf(Integer df) {
        this.df = df;
    }

    public Integer getCrs() {
        return crs;
    }

    public void setCrs(Integer crs) {
        this.crs = crs;
    }

    public Integer getScrs() {
        return scrs;
    }

    public void setScrs(Integer scrs) {
        this.scrs = scrs;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Integer getGrouperId() {
        return grouperId;
    }

    public void setGrouperId(Integer grouperId) {
        this.grouperId = grouperId;
    }

    public List<Integer> getLibraryerIds() {
        return libraryerIds;
    }

    public void setLibraryerIds(List<Integer> libraryerIds) {
        this.libraryerIds = libraryerIds;
    }
}