package com.ixilink.banknote_box.common.pojo;

public class BanknoteLine {
    private Integer id;

    private String name;

    private String remarks;

    private Long createTime;

    private Integer state;

    private Integer libraryId;

    private Integer atmCount;

    private String username;

    public BanknoteLine() {
    }

    public BanknoteLine(Integer id, String name, String remarks, Long createTime, Integer state, Integer libraryId) {
        this.id = id;
        this.name = name;
        this.remarks = remarks;
        this.createTime = createTime;
        this.state = state;
        this.libraryId = libraryId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getLibraryId() {
        return libraryId;
    }

    public void setLibraryId(Integer libraryId) {
        this.libraryId = libraryId;
    }

    public Integer getAtmCount() {
        return atmCount;
    }

    public void setAtmCount(Integer atmCount) {
        this.atmCount = atmCount;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}