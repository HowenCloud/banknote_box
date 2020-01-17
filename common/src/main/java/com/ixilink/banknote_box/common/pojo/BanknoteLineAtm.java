package com.ixilink.banknote_box.common.pojo;

public class BanknoteLineAtm {
    private Integer id;

    private Integer lineId;

    private Integer atmId;

    private Integer libraryId;

    public BanknoteLineAtm() {
    }

    public BanknoteLineAtm(Integer lineId, Integer atmId, Integer libraryId) {
        this.lineId = lineId;
        this.atmId = atmId;
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

    public Integer getAtmId() {
        return atmId;
    }

    public void setAtmId(Integer atmId) {
        this.atmId = atmId;
    }

    public Integer getLibraryId() {
        return libraryId;
    }

    public void setLibraryId(Integer libraryId) {
        this.libraryId = libraryId;
    }
}