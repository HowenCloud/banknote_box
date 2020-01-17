package com.ixilink.banknote_box.common.pojo;

import lombok.Data;

@Data
public class OutLibraryLineAtm {
    private Integer id;

    private Integer lineId;

    private Integer atmId;

    private Integer totalBox;

    private Integer unit;

    private Integer totalMoney;

    private Integer schedule;

    private String remarks;

    private Integer state;

    private Integer libraryId;

    private String supplementBox;

    private String depositBox;

    private String takeBox;

    private String supplementTakeBox;

    private String originalTakeBox;

    private Integer inState;

    public OutLibraryLineAtm() {
    }

    public OutLibraryLineAtm(Integer id, Integer lineId, Integer atmId, Integer totalBox, Integer unit, Integer totalMoney, Integer schedule, String remarks, Integer state, Integer libraryId, String originalTakeBox) {
        this.id = id;
        this.lineId = lineId;
        this.atmId = atmId;
        this.totalBox = totalBox;
        this.unit = unit;
        this.totalMoney = totalMoney;
        this.schedule = schedule;
        this.remarks = remarks;
        this.state = state;
        this.libraryId = libraryId;
        this.originalTakeBox = originalTakeBox;
    }
}