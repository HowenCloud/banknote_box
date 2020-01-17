package com.ixilink.banknote_box.common.pojo;

public class OutLibraryLine {
    private Integer id;

    private Integer taskId;

    private String lineName;

    private Integer totalBox;

    private Integer totalMoney;

    private Integer batch;

    private String remarks;

    private Integer boxingState;

    private Integer checkState;

    private Integer handoverCheckState;

    private Integer addCheckState;

    private Integer checkSchedule;

    private Integer taskState;

    private Integer libraryId;

    private String normalResult;

    private String inventoryResult;

    private String supplementResult;

    private Integer atmCount;

    private Integer overCount;

    private String username;

    public OutLibraryLine() {
    }

    public OutLibraryLine(Integer id, Integer taskId, String lineName, Integer totalBox, Integer totalMoney, Integer batch, String remarks, Integer boxingState, Integer handoverCheckState,
                          Integer addCheckState, Integer checkSchedule, Integer libraryId, String normalResult, String inventoryResult, String supplementResult) {
        this.id = id;
        this.taskId = taskId;
        this.lineName = lineName;
        this.totalBox = totalBox;
        this.totalMoney = totalMoney;
        this.batch = batch;
        this.remarks = remarks;
        this.boxingState = boxingState;
        this.handoverCheckState = handoverCheckState;
        this.addCheckState = addCheckState;
        this.checkSchedule = checkSchedule;
        this.libraryId = libraryId;
        this.normalResult = normalResult;
        this.inventoryResult = inventoryResult;
        this.supplementResult = supplementResult;
    }

    public Integer getId() {
        return id;
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

    public String getLineName() {
        return lineName;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName == null ? null : lineName.trim();
    }

    public Integer getTotalBox() {
        return totalBox;
    }

    public void setTotalBox(Integer totalBox) {
        this.totalBox = totalBox;
    }

    public Integer getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(Integer totalMoney) {
        this.totalMoney = totalMoney;
    }

    public Integer getBatch() {
        return batch;
    }

    public void setBatch(Integer batch) {
        this.batch = batch;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }

    public Integer getBoxingState() {
        return boxingState;
    }

    public void setBoxingState(Integer boxingState) {
        this.boxingState = boxingState;
    }

    public Integer getCheckState() {
        return checkState;
    }

    public void setCheckState(Integer checkState) {
        this.checkState = checkState;
    }

    public Integer getHandoverCheckState() {
        return handoverCheckState;
    }

    public void setHandoverCheckState(Integer handoverCheckState) {
        this.handoverCheckState = handoverCheckState;
    }

    public Integer getAddCheckState() {
        return addCheckState;
    }

    public void setAddCheckState(Integer addCheckState) {
        this.addCheckState = addCheckState;
    }

    public Integer getCheckSchedule() {
        return checkSchedule;
    }

    public void setCheckSchedule(Integer checkSchedule) {
        this.checkSchedule = checkSchedule;
    }

    public Integer getTaskState() {
        return taskState;
    }

    public void setTaskState(Integer taskState) {
        this.taskState = taskState;
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
        this.normalResult = normalResult == null ? null : normalResult.trim();
    }

    public String getInventoryResult() {
        return inventoryResult;
    }

    public void setInventoryResult(String inventoryResult) {
        this.inventoryResult = inventoryResult == null ? null : inventoryResult.trim();
    }

    public String getSupplementResult() {
        return supplementResult;
    }

    public void setSupplementResult(String supplementResult) {
        this.supplementResult = supplementResult == null ? null : supplementResult.trim();
    }

    public Integer getAtmCount() {
        return atmCount;
    }

    public void setAtmCount(Integer atmCount) {
        this.atmCount = atmCount;
    }

    public Integer getOverCount() {
        return overCount;
    }

    public void setOverCount(Integer overCount) {
        this.overCount = overCount;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}