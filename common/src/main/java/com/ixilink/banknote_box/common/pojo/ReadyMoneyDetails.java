package com.ixilink.banknote_box.common.pojo;


public class ReadyMoneyDetails {
    private Integer id;

    private Integer taskId;

    private String carBoxNumber;

    private Integer carMoney;

    private Integer fundId;

    private String executor;

    private Integer manageId;

    private Integer carType;

    private Integer libraryId;

    private Long  createTime;

    private String  fundBagName;

    public ReadyMoneyDetails(Integer taskId, String carBoxNumber, Integer carMoney, Integer fundId, String executor, Integer carType,Long createTime, Integer libraryId,String  fundBagName) {
        this.taskId = taskId;
        this.carBoxNumber = carBoxNumber;
        this.carMoney = carMoney;
        this.fundId = fundId;
        this.executor = executor;
        this.carType = carType;
        this.createTime = createTime;
        this.libraryId = libraryId;
        this.fundBagName = fundBagName;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Integer getCarType() {
        return carType;
    }

    public void setCarType(Integer carType) {
        this.carType = carType;
    }

    public Integer getId() {
        return id;
    }

    public String getFundBagName() {
        return fundBagName;
    }

    public void setFundBagName(String fundBagName) {
        this.fundBagName = fundBagName;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public String getCarBoxNumber() {
        return carBoxNumber;
    }

    public void setCarBoxNumber(String carBoxNumber) {
        this.carBoxNumber = carBoxNumber;
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

    public String getUsersId() {
        return executor;
    }

    public void setUsersId(String usersId) {
        this.executor = usersId == null ? null : usersId.trim();
    }

    public Integer getManageId() {
        return manageId;
    }

    public void setManageId(Integer manageId) {
        this.manageId = manageId;
    }

    public ReadyMoneyDetails() {
    }

    public ReadyMoneyDetails(Integer taskId, String carBoxNumber, Integer carMoney, Integer fundId, String executor, Integer carType,Long createTime) {
        this.taskId = taskId;
        this.carBoxNumber = carBoxNumber;
        this.carMoney = carMoney;
        this.fundId = fundId;
        this.executor = executor;
        this.carType = carType;
        this.createTime = createTime;
    }

    public String getExecutor() {
        return executor;
    }

    public void setExecutor(String executor) {
        this.executor = executor;
    }

    public Integer getLibraryId() {
        return libraryId;
    }

    public void setLibraryId(Integer libraryId) {
        this.libraryId = libraryId;
    }
}