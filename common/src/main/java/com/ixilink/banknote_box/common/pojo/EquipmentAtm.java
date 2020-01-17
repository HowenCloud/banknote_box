package com.ixilink.banknote_box.common.pojo;

public class EquipmentAtm {
    private Integer id;

    private Integer controllerId;

    private String number;

    private String name;

    private String channel;

    private Integer passageway;

    private Integer boxType = 0;

    private Integer state;

    private Integer libraryId;

    private Integer box1;

    private Integer box2;

    private Integer box3;

    private Integer box4;

    private Integer box5;

    private String bankBranch;//网点

    public EquipmentAtm() {
    }

    public EquipmentAtm(Integer id, Integer controllerId, String number, String name, String channel, Integer passageway, Integer state, Integer libraryId) {
        this.id = id;
        this.controllerId = controllerId;
        this.number = number;
        this.name = name;
        this.channel = channel;
        this.passageway = passageway;
        this.state = state;
        this.libraryId = libraryId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getControllerId() {
        return controllerId;
    }

    public void setControllerId(Integer controllerId) {
        this.controllerId = controllerId;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number == null ? null : number.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel == null ? null : channel.trim();
    }

    public Integer getPassageway() {
        return passageway;
    }

    public void setPassageway(Integer passageway) {
        this.passageway = passageway;
    }

    public Integer getBoxType() {
        return boxType;
    }

    public void setBoxType(Integer boxType) {
        this.boxType = boxType;
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

    public Integer getBox1() {
        return box1;
    }

    public void setBox1(Integer box1) {
        this.box1 = box1;
    }

    public Integer getBox2() {
        return box2;
    }

    public void setBox2(Integer box2) {
        this.box2 = box2;
    }

    public Integer getBox3() {
        return box3;
    }

    public void setBox3(Integer box3) {
        this.box3 = box3;
    }

    public Integer getBox4() {
        return box4;
    }

    public void setBox4(Integer box4) {
        this.box4 = box4;
    }

    public Integer getBox5() {
        return box5;
    }

    public void setBox5(Integer box5) {
        this.box5 = box5;
    }

    public String getBankBranch() {
        return bankBranch;
    }

    public void setBankBranch(String bankBranch) {
        this.bankBranch = bankBranch;
    }
}