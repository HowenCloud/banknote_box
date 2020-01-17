package com.ixilink.banknote_box.common.pojo;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ReadyMoneyApplyList {
    private List<String> db =new ArrayList<>();
    private List<String> df =new ArrayList<>();
    private List<String> crs =new ArrayList<>();
    private List<String> scrs =new ArrayList<>();

    public List<String> getDb() {
        return db;
    }

    public void setDb(List<String> db) {
        this.db = db;
    }

    public List<String> getDf() {
        return df;
    }

    public void setDf(List<String> df) {
        this.df = df;
    }

    public List<String> getCrs() {
        return crs;
    }

    public void setCrs(List<String> crs) {
        this.crs = crs;
    }

    public List<String> getScrs() {
        return scrs;
    }

    public void setScrs(List<String> scrs) {
        this.scrs = scrs;
    }

    public ReadyMoneyApplyList() {
    }
    public ReadyMoneyApplyList(List<Map<String, Object>> list) {
        for (Map<String, Object> map : list) {

            switch (map.get("typeName").toString()) {
                case "DB":
                    this.db =Arrays.asList(map.get("serialNumber").toString().split(","));
                    break;
                case "DF":
                    this.df = Arrays.asList(map.get("serialNumber").toString().split(","));
                    break;
                case "CRS":
                    this.crs = Arrays.asList(map.get("serialNumber").toString().split(","));
                    break;
                case "SCRS":
                    this.scrs = Arrays.asList(map.get("serialNumber").toString().split(","));
                    break;
            }
        }
    }
}