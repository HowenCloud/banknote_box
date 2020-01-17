package com.ixilink.banknote_box.common.pojo;

public class ReadyMoneyBoxParam {
    private  String serialNumber;
    private  Integer carType;

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public Integer getCarType() {
        return carType;
    }

    public void setCarType(Integer carType) {
        this.carType = carType;
    }

    public ReadyMoneyBoxParam(String serialNumber, Integer carType) {
        this.serialNumber = serialNumber;
        this.carType = carType;
    }

    public ReadyMoneyBoxParam() {
    }
}