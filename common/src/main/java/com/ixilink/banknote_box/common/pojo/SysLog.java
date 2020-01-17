package com.ixilink.banknote_box.common.pojo;

public class SysLog {
    private Long id;

    private Long createTime;

    private String operator;

    private Integer operationObject;

    private String operationContent;

    private Integer libraryId;

    public Integer getLibraryId() {
        return libraryId;
    }

    public void setLibraryId(Integer libraryId) {
        this.libraryId = libraryId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public Integer getOperationObject() {
        return operationObject;
    }

    public void setOperationObject(Integer operationObject) {
        this.operationObject = operationObject;
    }

    public String getOperationContent() {
        return operationContent;
    }

    public void setOperationContent(String operationContent) {
        this.operationContent = operationContent == null ? null : operationContent.trim();
    }
}