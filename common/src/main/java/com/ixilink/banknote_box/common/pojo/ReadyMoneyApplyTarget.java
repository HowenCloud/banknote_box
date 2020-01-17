package com.ixilink.banknote_box.common.pojo;

import java.util.List;
import java.util.Map;

public class ReadyMoneyApplyTarget {
    private int dbNum;
    private int dfNum;
    private int scrsNum;
    private int crsNum;
    private int totalNumber;
    private int carType;

    public int getDbNum() {
        return dbNum;
    }

    public void setDbNum(int dbNum) {
        this.dbNum = dbNum;
    }

    public int getDfNum() {
        return dfNum;
    }

    public void setDfNum(int dfNum) {
        this.dfNum = dfNum;
    }

    public int getScrsNum() {
        return scrsNum;
    }

    public void setScrsNum(int scrsNum) {
        this.scrsNum = scrsNum;
    }

    public int getCrsNum() {
        return crsNum;
    }

    public void setCrsNum(int crsNum) {
        this.crsNum = crsNum;
    }

    public int getTotalNumber() {
        return totalNumber;
    }

    public void setTotalNumber(int totalNumber) {
        this.totalNumber = totalNumber;
    }

    public ReadyMoneyApplyTarget(List<Map<String, Object>> list) {
        for (Map<String, Object> map : list) {
            Integer totalBox = Integer.valueOf(map.get("totalBox").toString());
            switch (map.get("typeName").toString()) {
                case "DB":
                    this.dbNum = totalBox;
                    break;
                case "DF":
                    this.dfNum = totalBox;
                    break;
                case "CRS":
                    this.crsNum = totalBox;
                    break;
                case "SCRS":
                    this.scrsNum = totalBox;
                    break;
            }
        }
    }

    public ReadyMoneyApplyTarget() {
    }

    public ReadyMoneyApplyTarget(int dbNum, int dfNum, int scrsNum, int crsNum, int totalNumber) {
        this.dbNum = dbNum;
        this.dfNum = dfNum;
        this.scrsNum = scrsNum;
        this.crsNum = crsNum;
        this.totalNumber = totalNumber;
    }

    public int getCarType() {
        return carType;
    }

    public void setCarType(int carType) {
        this.carType = carType;
    }
}