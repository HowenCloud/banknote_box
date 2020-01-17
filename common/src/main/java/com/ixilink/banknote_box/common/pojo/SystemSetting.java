package com.ixilink.banknote_box.common.pojo;

public class SystemSetting {
    private Integer libraryId;

    private String assignmentsReaderWriterIp;

    private String handoverReaderWriterIp;

    private String assignmentsAmeraIp;

    private String handoverAmeraIp;

    public Integer getLibraryId() {
        return libraryId;
    }

    public void setLibraryId(Integer libraryId) {
        this.libraryId = libraryId;
    }

    public String getAssignmentsReaderWriterIp() {
        return assignmentsReaderWriterIp;
    }

    public void setAssignmentsReaderWriterIp(String assignmentsReaderWriterIp) {
        this.assignmentsReaderWriterIp = assignmentsReaderWriterIp == null ? null : assignmentsReaderWriterIp.trim();
    }

    public String getHandoverReaderWriterIp() {
        return handoverReaderWriterIp;
    }

    public void setHandoverReaderWriterIp(String handoverReaderWriterIp) {
        this.handoverReaderWriterIp = handoverReaderWriterIp == null ? null : handoverReaderWriterIp.trim();
    }

    public String getAssignmentsAmeraIp() {
        return assignmentsAmeraIp;
    }

    public void setAssignmentsAmeraIp(String assignmentsAmeraIp) {
        this.assignmentsAmeraIp = assignmentsAmeraIp == null ? null : assignmentsAmeraIp.trim();
    }

    public String getHandoverAmeraIp() {
        return handoverAmeraIp;
    }

    public void setHandoverAmeraIp(String handoverAmeraIp) {
        this.handoverAmeraIp = handoverAmeraIp == null ? null : handoverAmeraIp.trim();
    }
}