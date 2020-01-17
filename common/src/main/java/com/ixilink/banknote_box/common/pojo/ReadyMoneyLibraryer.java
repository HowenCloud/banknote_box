package com.ixilink.banknote_box.common.pojo;

public class ReadyMoneyLibraryer {
    private Integer id;

    private String libraryerId;

    private Long inLibraryTime;

    private Long outLibraryTime;

    private Integer state;

    private Integer detailsId;

    private String handOverPeopleId;

    private Integer libraryId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLibraryerId() {
        return libraryerId;
    }

    public void setLibraryerId(String libraryerId) {
        this.libraryerId = libraryerId == null ? null : libraryerId.trim();
    }

    public Long getInLibraryTime() {
        return inLibraryTime;
    }

    public void setInLibraryTime(Long inLibraryTime) {
        this.inLibraryTime = inLibraryTime;
    }

    public Long getOutLibraryTime() {
        return outLibraryTime;
    }

    public void setOutLibraryTime(Long outLibraryTime) {
        this.outLibraryTime = outLibraryTime;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getDetailsId() {
        return detailsId;
    }

    public void setDetailsId(Integer detailsId) {
        this.detailsId = detailsId;
    }

    public String getHandOverPeopleId() {
        return handOverPeopleId;
    }

    public void setHandOverPeopleId(String handOverPeopleId) {
        this.handOverPeopleId = handOverPeopleId == null ? null : handOverPeopleId.trim();
    }

    public Integer getLibraryId() {
        return libraryId;
    }

    public void setLibraryId(Integer libraryId) {
        this.libraryId = libraryId;
    }

    public ReadyMoneyLibraryer() {
    }

    public ReadyMoneyLibraryer(Integer id, String libraryerId, Long inLibraryTime, Long outLibraryTime, Integer state, Integer detailsId, String handOverPeopleId, Integer libraryId) {
        this.id = id;
        this.libraryerId = libraryerId;
        this.inLibraryTime = inLibraryTime;
        this.outLibraryTime = outLibraryTime;
        this.state = state;
        this.detailsId = detailsId;
        this.handOverPeopleId = handOverPeopleId;
        this.libraryId = libraryId;
    }
}