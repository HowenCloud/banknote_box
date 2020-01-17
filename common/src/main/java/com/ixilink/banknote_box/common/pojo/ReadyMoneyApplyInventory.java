package com.ixilink.banknote_box.common.pojo;

import java.util.List;
import java.util.Map;

public class ReadyMoneyApplyInventory {
    private String dbIds =new String();
    private String dfIds=new String();
    private String crsIds=new String();
    private String scrsIds=new String();

    public String getDbIds() {
        return dbIds;
    }

    public void setDbIds(String dbIds) {
        this.dbIds = dbIds;
    }

    public String getDfIds() {
        return dfIds;
    }

    public void setDfIds(String dfIds) {
        this.dfIds = dfIds;
    }

    public String getCrsIds() {
        return crsIds;
    }

    public void setCrsIds(String crsIds) {
        this.crsIds = crsIds;
    }

    public String getScrsIds() {
        return scrsIds;
    }

    public void setScrsIds(String scrsIds) {
        this.scrsIds = scrsIds;
    }

    public ReadyMoneyApplyInventory() {
    }
    public ReadyMoneyApplyInventory(List<Map<String, Object>> list) {
        for (Map<String, Object> map : list) {
            String ids = map.get("ids").toString().toString();
            switch (map.get("typeName").toString()) {
                case "DB":
                    this.dbIds =ids;
                    break;
                case "DF":
                    this.dfIds = ids;
                    break;
                case "CRS":
                    this.crsIds = ids;
                    break;
                case "SCRS":
                    this.scrsIds = ids;
                    break;
            }
        }
    }
}