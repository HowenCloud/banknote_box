package com.ixilink.banknote_box.common.pojo;

public class OutLibraryLineBoxingUser {
    private Integer id;

    private Integer lineId;

    private Integer userId;

    public OutLibraryLineBoxingUser() {
    }

    public OutLibraryLineBoxingUser(Integer lineId, Integer userId) {
        this.lineId = lineId;
        this.userId = userId;
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
}