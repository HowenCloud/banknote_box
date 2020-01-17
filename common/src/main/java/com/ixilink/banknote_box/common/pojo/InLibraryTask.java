package com.ixilink.banknote_box.common.pojo;

import lombok.Data;

@Data
public class InLibraryTask {
    private Integer id;

    private Integer publisher;

    private Long createTime;

    private Integer taskState;

    private Integer taskSchedule;

    private Long downTime;

    private Long handoverTime;

    private Long receiveTime;

    private Integer libraryId;

    private String publisherName;

    private Integer totalLine;

    private Integer totalBox;

    private Integer batch;

    public InLibraryTask() {
    }

    public InLibraryTask(Integer id, Integer publisher, Long createTime, Integer taskState, Integer taskSchedule, Long downTime, Long handoverTime, Long receiveTime, Integer libraryId) {
        this.id = id;
        this.publisher = publisher;
        this.createTime = createTime;
        this.taskState = taskState;
        this.taskSchedule = taskSchedule;
        this.downTime = downTime;
        this.handoverTime = handoverTime;
        this.receiveTime = receiveTime;
        this.libraryId = libraryId;
    }
}