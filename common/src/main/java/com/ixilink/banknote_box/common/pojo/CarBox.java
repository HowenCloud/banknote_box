package com.ixilink.banknote_box.common.pojo;


import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;

public class CarBox {
    @ApiModelProperty("id")
    @ApiParam(value = "id",hidden = true)
    private Integer id;
    @ApiModelProperty("钞箱编号")
    @ApiParam(value = "钞箱编号")
    private String serialNumber;
    @ApiModelProperty("钞箱类型")
    @ApiParam(value = "钞箱类型")
    private Integer boxType;
    @ApiModelProperty("位置")
    @ApiParam(value = "位置",hidden = true)
    private String site;
    @ApiModelProperty("使用状态")
    @ApiParam(value = "使用状态",hidden = true)
    private Integer boxUseState;
    @ApiModelProperty("运输状态")
    @ApiParam(value = "运输状态",hidden = true)
    private Integer boxTransportState;
    @ApiModelProperty("有源rfid")
    @ApiParam(value = "有源rfid",required = false)
    private String activeRfid;
    @ApiModelProperty("无源rfid")
    @ApiParam(value = "无源rfid")
    private String passiveRfid;
    @ApiModelProperty("创建时间")
    @ApiParam(value = "创建时间",hidden = true)
    private Long createTime;
    private Integer libraryId;

    public Integer getLibraryId() {
        return libraryId;
    }

    public void setLibraryId(Integer libraryId) {
        this.libraryId = libraryId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber == null ? null : serialNumber.trim();
    }

    public Integer getBoxType() {
        return boxType;
    }

    public void setBoxType(Integer boxType) {
        this.boxType = boxType;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public void setBoxUseState(Integer boxUseState) {
        this.boxUseState = boxUseState;
    }

    public void setBoxTransportState(Integer boxTransportState) {
        this.boxTransportState = boxTransportState;
    }

    public String getActiveRfid() {
        return activeRfid;
    }

    public void setActiveRfid(String activeRfid) {
        this.activeRfid = activeRfid == null ? null : activeRfid.trim();
    }

    public String getPassiveRfid() {
        return passiveRfid;
    }

    public void setPassiveRfid(String passiveRfid) {
        this.passiveRfid = passiveRfid == null ? null : passiveRfid.trim();
    }

    public Integer getBoxUseState() {
        return boxUseState;
    }

    public Integer getBoxTransportState() {
        return boxTransportState;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }
}