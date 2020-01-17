package com.ixilink.banknote_box.common.pojo;
public class ReadyMoneyBoxNumMoney {
       private  Integer id;
       private  Integer taskId;
       private  Integer boxMoney;
       private  Integer number;
       private  Integer variableNumber;
       private  Integer type;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public Integer getBoxMoney() {
        return boxMoney;
    }

    public void setBoxMoney(Integer boxMoney) {
        this.boxMoney = boxMoney;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getVariableNumber() {
        return variableNumber;
    }

    public void setVariableNumber(Integer variableNumber) {
        this.variableNumber = variableNumber;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public ReadyMoneyBoxNumMoney(Integer taskId, Integer boxMoney, Integer number, Integer variableNumber, Integer type) {
        this.taskId = taskId;
        this.boxMoney = boxMoney;
        this.number = number;
        this.variableNumber = variableNumber;
        this.type = type;
    }
}
