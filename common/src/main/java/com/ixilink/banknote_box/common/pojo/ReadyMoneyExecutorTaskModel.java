package com.ixilink.banknote_box.common.pojo;


import java.util.List;
import java.util.Map;

public class ReadyMoneyExecutorTaskModel {
    private Integer type;
    private Integer  totalMoney;
    private Integer number;
    private Integer  performedNumber;
    private Integer performedMoney;
    private Integer  performingNumber;
    private Integer performingMoney;
    private String unfinishNumberMoney;
    private List<Map<Integer,Integer>> unfinishNumberAndMoney;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(Integer totalMoney) {
        this.totalMoney = totalMoney;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getPerformedNumber() {
        return performedNumber;
    }

    public void setPerformedNumber(Integer performedNumber) {
        this.performedNumber = performedNumber;
    }

    public Integer getPerformedMoney() {
        return performedMoney;
    }

    public void setPerformedMoney(Integer performedMoney) {
        this.performedMoney = performedMoney;
    }

    public String getUnfinishNumberMoney() {
        return unfinishNumberMoney;
    }

    public void setUnfinishNumberMoney(String unfinishNumberMoney) {
        this.unfinishNumberMoney = unfinishNumberMoney;
    }

    public List<Map<Integer, Integer>> getUnfinishNumberAndMoney() {
        return unfinishNumberAndMoney;
    }

    public void setUnfinishNumberAndMoney(List<Map<Integer, Integer>> unfinishNumberAndMoney) {
        this.unfinishNumberAndMoney = unfinishNumberAndMoney;
    }

    public Integer getPerformingNumber() {
        return performingNumber;
    }

    public void setPerformingNumber(Integer performingNumber) {
        this.performingNumber = performingNumber;
    }

    public Integer getPerformingMoney() {
        return performingMoney;
    }

    public void setPerformingMoney(Integer performingMoney) {
        this.performingMoney = performingMoney;
    }
}
