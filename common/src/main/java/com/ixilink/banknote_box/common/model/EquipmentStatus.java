package com.ixilink.banknote_box.common.model;

/**
 * @description:
 * @author: 张俊
 * @date: 2019-11-20 19:53
 */
public class EquipmentStatus {

    private String ip;

    private Integer equipmentType;

    private Integer libraryId;

    private Integer status; //设备在线状态 1在线 2离线

    public EquipmentStatus() {
    }

    public EquipmentStatus(String ip, Integer equipmentType, Integer libraryId, Integer status) {
        this.ip = ip;
        this.equipmentType = equipmentType;
        this.libraryId = libraryId;
        this.status = status;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getEquipmentType() {
        return equipmentType;
    }

    public void setEquipmentType(Integer equipmentType) {
        this.equipmentType = equipmentType;
    }

    public Integer getLibraryId() {
        return libraryId;
    }

    public void setLibraryId(Integer libraryId) {
        this.libraryId = libraryId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
