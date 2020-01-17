package com.ixilink.banknote_box.common.pojo;


public class ReadyMoneyReturnDetails {
    private Integer id;

    private String carBoxNumber;

    private String carType;

    private Integer carMoney;

    private Integer fundId;

    private String fundBagName;

    private String executor;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCarBoxNumber() {
        return carBoxNumber;
    }

    public void setCarBoxNumber(String carBoxNumber) {
        this.carBoxNumber = carBoxNumber;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public Integer getCarMoney() {
        return carMoney;
    }

    public void setCarMoney(Integer carMoney) {
        this.carMoney = carMoney;
    }

    public Integer getFundId() {
        return fundId;
    }

    public void setFundId(Integer fundId) {
        this.fundId = fundId;
    }

    public String getFundBagName() {
        return fundBagName;
    }

    public void setFundBagName(String fundBagName) {
        this.fundBagName = fundBagName;
    }

    public String getExecutor() {
        return executor;
    }

    public void setExecutor(String executor) {
        this.executor = executor;
    }
}