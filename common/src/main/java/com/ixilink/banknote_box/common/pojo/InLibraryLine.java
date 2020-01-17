package com.ixilink.banknote_box.common.pojo;

import lombok.Data;

@Data
public class InLibraryLine {
    private Integer id;

    private Integer taskId;

    private String lineName;

    private Integer totalBox;

    private Integer checkState;

    private Integer faceState;

    private Integer lineState;

    private Integer libraryId;

    private String normalResult;

    private String inventoryResult;

    private String supplementResult;

    public InLibraryLine() {
    }

    public InLibraryLine(Integer id, Integer taskId, String lineName, Integer totalBox, Integer libraryId, String normalResult, String inventoryResult, String supplementResult) {
        this.id = id;
        this.taskId = taskId;
        this.lineName = lineName;
        this.totalBox = totalBox;
        this.libraryId = libraryId;
        this.normalResult = normalResult;
        this.inventoryResult = inventoryResult;
        this.supplementResult = supplementResult;
    }
}