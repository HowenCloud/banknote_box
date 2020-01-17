package com.ixilink.banknote_box.common.pojo;

public class ReadyMoneyApply {
    private Integer id;

    private Long createTime;

    private Integer grouperId;

    private String libraryerId;

    private Long approvalTime;

    private String approvalPeople;

    private Integer state;

    private Integer totalNumber;

    private Integer libraryId;

    private String normalResult;

    private String supplementResult;

    private String inventoryResult;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getLibraryerId() {
        return libraryerId;
    }

    public void setLibraryerId(String libraryerId) {
        this.libraryerId = libraryerId;
    }

    public Long getApprovalTime() {
        return approvalTime;
    }

    public void setApprovalTime(Long approvalTime) {
        this.approvalTime = approvalTime;
    }

    public String getApprovalPeople() {
        return approvalPeople;
    }

    public void setApprovalPeople(String approvalPeople) {
        this.approvalPeople = approvalPeople;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getTotalNumber() {
        return totalNumber;
    }

    public void setTotalNumber(Integer totalNumber) {
        this.totalNumber = totalNumber;
    }

    public Integer getLibraryId() {
        return libraryId;
    }

    public void setLibraryId(Integer libraryId) {
        this.libraryId = libraryId;
    }

    public String getNormalResult() {
        return normalResult;
    }

    public void setNormalResult(String normalResult) {
        this.normalResult = normalResult;
    }

    public String getSupplementResult() {
        return supplementResult;
    }

    public void setSupplementResult(String supplementResult) {
        this.supplementResult = supplementResult;
    }

    public String getInventoryResult() {
        return inventoryResult;
    }

    public void setInventoryResult(String inventoryResult) {
        this.inventoryResult = inventoryResult;
    }

    public ReadyMoneyApply() {
    }

    public ReadyMoneyApply(Integer id, Long createTime, Integer grouperId, String libraryerId, Long approvalTime, String approvalPeople, Integer state, Integer totalNumber, Integer libraryId, String normalResult, String supplementResult, String inventoryResult) {

        this.id = id;
        this.createTime = createTime;
        this.grouperId = grouperId;
        this.libraryerId = libraryerId;
        this.approvalTime = approvalTime;
        this.approvalPeople = approvalPeople;
        this.state = state;
        this.totalNumber = totalNumber;
        this.libraryId = libraryId;
        this.normalResult = normalResult;
        this.supplementResult = supplementResult;
        this.inventoryResult = inventoryResult;
    }
}