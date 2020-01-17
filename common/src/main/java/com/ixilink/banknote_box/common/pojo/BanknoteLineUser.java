package com.ixilink.banknote_box.common.pojo;

public class BanknoteLineUser {
    private Integer id;

    private Integer lineId;

    private Integer userId;

    private Integer libraryId;

    public BanknoteLineUser() {
    }

    public BanknoteLineUser(Integer lineId, Integer userId, Integer libraryId) {
        this.lineId = lineId;
        this.userId = userId;
        this.libraryId = libraryId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getLineId() {
        return lineId;
    }

    public void setLineId(Integer lineId) {
        this.lineId = lineId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getLibraryId() {
        return libraryId;
    }

    public void setLibraryId(Integer libraryId) {
        this.libraryId = libraryId;
    }
}