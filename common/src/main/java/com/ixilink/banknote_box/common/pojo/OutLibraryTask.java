package com.ixilink.banknote_box.common.pojo;

public class OutLibraryTask {
    private Integer id;

    private Integer totalLine;

    private Integer totalBox;

    private Integer totalMoney;

    private Integer publisher;

    private Long createTime;

    private Integer applyUser;

    private Integer applyState;

    private String approvalReason;

    private Integer checkState;

    private Integer boxingState;

    private Integer taskState;

    private Integer taskSchedule;

    private Integer batch;

    private Integer libraryId;

    private String publisherName;

    public OutLibraryTask() {

    }

    public OutLibraryTask(Integer id, Integer totalLine, Integer totalBox, Integer totalMoney, Integer applyState, Integer applyUser, Integer publisher, Long createTime, Integer checkState,
                          Integer boxingState, Integer taskState, Integer libraryId) {
        this.id = id;
        this.totalLine = totalLine;
        this.totalBox = totalBox;
        this.totalMoney = totalMoney;
        this.applyState = applyState;
        this.applyUser = applyUser;
        this.publisher = publisher;
        this.createTime = createTime;
        this.checkState = checkState;
        this.boxingState = boxingState;
        this.taskState = taskState;
        this.libraryId = libraryId;
    }

    public OutLibraryTask(Integer id, Integer totalLine, Integer totalBox, Integer totalMoney, Integer applyState, Integer applyUser, Integer publisher, Long createTime, Integer checkState,
                          Integer boxingState, Integer taskState, Integer batch, Integer libraryId) {
        this.id = id;
        this.totalLine = totalLine;
        this.totalBox = totalBox;
        this.totalMoney = totalMoney;
        this.applyState = applyState;
        this.applyUser = applyUser;
        this.publisher = publisher;
        this.createTime = createTime;
        this.checkState = checkState;
        this.boxingState = boxingState;
        this.taskState = taskState;
        this.batch = batch;
        this.libraryId = libraryId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTotalLine() {
        return totalLine;
    }

    public void setTotalLine(Integer totalLine) {
        this.totalLine = totalLine;
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

    public Integer getPublisher() {
        return publisher;
    }

    public void setPublisher(Integer publisher) {
        this.publisher = publisher;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Integer getApplyUser() {
        return applyUser;
    }

    public void setApplyUser(Integer applyUser) {
        this.applyUser = applyUser;
    }

    public Integer getApplyState() {
        return applyState;
    }

    public void setApplyState(Integer applyState) {
        this.applyState = applyState;
    }

    public String getApprovalReason() {
        return approvalReason;
    }

    public void setApprovalReason(String approvalReason) {
        this.approvalReason = approvalReason == null ? null : approvalReason.trim();
    }

    public Integer getCheckState() {
        return checkState;
    }

    public void setCheckState(Integer checkState) {
        this.checkState = checkState;
    }

    public Integer getBoxingState() {
        return boxingState;
    }

    public void setBoxingState(Integer boxingState) {
        this.boxingState = boxingState;
    }

    public Integer getTaskState() {
        return taskState;
    }

    public void setTaskState(Integer taskState) {
        this.taskState = taskState;
    }

    public Integer getTaskSchedule() {
        return taskSchedule;
    }

    public void setTaskSchedule(Integer taskSchedule) {
        this.taskSchedule = taskSchedule;
    }

    public Integer getBatch() {
        return batch;
    }

    public void setBatch(Integer batch) {
        this.batch = batch;
    }

    public Integer getLibraryId() {
        return libraryId;
    }

    public void setLibraryId(Integer libraryId) {
        this.libraryId = libraryId;
    }

    public String getPublisherName() {
        return publisherName;
    }

    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }
}