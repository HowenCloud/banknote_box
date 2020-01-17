package com.ixilink.banknote_box.common.pojo;

public class FundBagManagement {
    private Integer id;

    private Long installationTime;

    private Integer baggingAmount;

    private String baggingPersonnel;

    private Integer state;

    private Integer number;

    private String fundBagName;

    private Integer baggingAmountAll;


    private Integer libraryId;

    private String baggingLockBarNumber;


    private String baggingPersonnelId;

    public String getBaggingPersonnelId() {
        return baggingPersonnelId;
    }

    public void setBaggingPersonnelId(String baggingPersonnelId) {
        this.baggingPersonnelId = baggingPersonnelId;
    }

    public String getBaggingLockBarNumber() {
        return baggingLockBarNumber;
    }

    public void setBaggingLockBarNumber(String baggingLockBarNumber) {
        this.baggingLockBarNumber = baggingLockBarNumber;
    }


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

    public Long getInstallationTime() {
        return installationTime;
    }

    public void setInstallationTime(Long installationTime) {
        this.installationTime = installationTime;
    }

    public Integer getBaggingAmount() {
        return baggingAmount;
    }

    public void setBaggingAmount(Integer baggingAmount) {
        this.baggingAmount = baggingAmount;
    }

    public String getBaggingPersonnel() {
        return baggingPersonnel;
    }

    public void setBaggingPersonnel(String baggingPersonnel) {
        this.baggingPersonnel = baggingPersonnel == null ? null : baggingPersonnel.trim();
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getFundBagName() {
        return fundBagName;
    }

    public void setFundBagName(String fundBagName) {
        this.fundBagName = fundBagName == null ? null : fundBagName.trim();
    }

    public Integer getBaggingAmountAll() {
        return baggingAmountAll;
    }

    public void setBaggingAmountAll(Integer baggingAmountAll) {
        this.baggingAmountAll = baggingAmountAll;
    }
}