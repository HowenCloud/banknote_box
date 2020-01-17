package com.ixilink.banknote_box.common.pojo;

public class Equipment {
    private Integer id;

    private Integer equipmentType;

    private String number;

    private String name;

    private String ip;

    private Integer state;

    private Integer libraryId;

    private Integer status; //设备在线状态 1在线 2离线

    private Integer atmCount=0;

    public Equipment() {

    }

    public Equipment(Integer id, Integer equipmentType, String number, String name, String ip, Integer state, Integer libraryId) {
        this.id = id;
        this.equipmentType = equipmentType;
        this.number = number;
        this.name = name;
        this.ip = ip;
        this.state = state;
        this.libraryId = libraryId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getEquipmentType() {
        return equipmentType;
    }

    public void setEquipmentType(Integer equipmentType) {
        this.equipmentType = equipmentType;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number == null ? null : number.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
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

    public Integer getAtmCount() {
        return atmCount;
    }

    public void setAtmCount(Integer atmCount) {
        this.atmCount = atmCount;
    }
}