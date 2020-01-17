package com.ixilink.banknote_box.common.pojo;
public class    ReadyMoneyTask {
    private Integer id;

    private Integer initiator;

    private Integer initiatorStr;

    private String receivePeople;

    private String executor;

    private Integer state;

    private Integer libraryId;

    private Integer receiveP;

    private Long createTime;

    private Integer dbNum;
    private Integer dbTotalMoney;
    private Integer dfNum;
    private Integer dfTotalMoney;
    private Integer crsNum;
    private Integer crsTotalMoney;
    private Integer scrsNum;
    private Integer scrsTotalMoney;
    public ReadyMoneyTask(Integer initiator, String receivePeople,Integer libraryId,Long createTime) {
        this.initiator = initiator;
        this.receivePeople = receivePeople;
        this.libraryId = libraryId;
        this.createTime = createTime;
    }

    public ReadyMoneyTask(Integer id, Integer initiator, String receivePeople, String executor, Integer handoverPeople, Integer state, Integer libraryId, Integer receiveP, Long createTime, Integer dbNum, Integer dbTotalMoney, Integer dfNum, Integer dfTotalMoney, Integer crsNum, Integer crsTotalMoney, Integer scrsNum, Integer scrsTotalMoney) {
        this.id = id;
        this.initiator = initiator;
        this.receivePeople = receivePeople;
        this.executor = executor;
        this.state = state;
        this.libraryId = libraryId;
        this.receiveP = receiveP;
        this.createTime = createTime;
        this.dbNum = dbNum;
        this.dbTotalMoney = dbTotalMoney;
        this.dfNum = dfNum;
        this.dfTotalMoney = dfTotalMoney;
        this.crsNum = crsNum;
        this.crsTotalMoney = crsTotalMoney;
        this.scrsNum = scrsNum;
        this.scrsTotalMoney = scrsTotalMoney;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getInitiator() {
        return initiator;
    }

    public void setInitiator(Integer initiator) {
        this.initiator = initiator;
    }

    public String getReceivePeople() {
        return receivePeople;
    }

    public void setReceivePeople(String receivePeople) {
        this.receivePeople = receivePeople;
    }

    public String getExecutor() {
        return executor;
    }

    public void setExecutor(String executor) {
        this.executor = executor;
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

    public Integer getReceiveP() {
        return receiveP;
    }

    public void setReceiveP(Integer receiveP) {
        this.receiveP = receiveP;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Integer getDbNum() {
        return dbNum;
    }

    public void setDbNum(Integer dbNum) {
        this.dbNum = dbNum;
    }

    public Integer getDbTotalMoney() {
        return dbTotalMoney;
    }

    public void setDbTotalMoney(Integer dbTotalMoney) {
        this.dbTotalMoney = dbTotalMoney;
    }

    public Integer getDfNum() {
        return dfNum;
    }

    public void setDfNum(Integer dfNum) {
        this.dfNum = dfNum;
    }

    public Integer getDfTotalMoney() {
        return dfTotalMoney;
    }

    public void setDfTotalMoney(Integer dfTotalMoney) {
        this.dfTotalMoney = dfTotalMoney;
    }

    public Integer getCrsNum() {
        return crsNum;
    }

    public void setCrsNum(Integer crsNum) {
        this.crsNum = crsNum;
    }

    public Integer getCrsTotalMoney() {
        return crsTotalMoney;
    }

    public void setCrsTotalMoney(Integer crsTotalMoney) {
        this.crsTotalMoney = crsTotalMoney;
    }

    public Integer getScrsNum() {
        return scrsNum;
    }

    public void setScrsNum(Integer scrsNum) {
        this.scrsNum = scrsNum;
    }

    public Integer getScrsTotalMoney() {
        return scrsTotalMoney;
    }

    public void setScrsTotalMoney(Integer scrsTotalMoney) {
        this.scrsTotalMoney = scrsTotalMoney;
    }

    public ReadyMoneyTask() {
    }

    public Integer getInitiatorStr() {
        return initiatorStr;
    }

    public void setInitiatorStr(Integer initiatorStr) {
        this.initiatorStr = initiatorStr;
    }
}