package com.ixilink.banknote_box.common.model;

/**
 * @description:
 * @author: 张俊
 * @date: 2019-11-21 11:08
 */
public class EquipmentCountModel {
    private int readerWriter = 0;
    private int centerControl = 0;
    private int handover = 0;
    private int boxing = 0;

    public int getReaderWriter() {
        return readerWriter;
    }

    public void setReaderWriter(int readerWriter) {
        this.readerWriter = readerWriter;
    }

    public int getCenterControl() {
        return centerControl;
    }

    public void setCenterControl(int centerControl) {
        this.centerControl = centerControl;
    }

    public int getHandover() {
        return handover;
    }

    public void setHandover(int handover) {
        this.handover = handover;
    }

    public int getBoxing() {
        return boxing;
    }

    public void setBoxing(int boxing) {
        this.boxing = boxing;
    }
}
