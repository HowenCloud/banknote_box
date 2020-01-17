package com.ixilink.banknote_box.common.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: 张皓峰
 * @date: 2019-12-04 11:34
 */
public class ExcelDataModel {
    private String maxTitle = "";
    private String[] titles;
    private Map<String,Object> other;
    private List<Map<String,Object>> data;

    public ExcelDataModel() {
        other = new HashMap<>();
        data = new ArrayList<>();
    }

    public ExcelDataModel(String maxTitle, String[] titles, Map<String, Object> other, List<Map<String, Object>> data) {
        this.maxTitle = maxTitle;
        this.titles = titles;
        this.other = other;
        this.data = data;
    }

    public String getMaxTitle() {
        return maxTitle;
    }

    public void setMaxTitle(String maxTitle) {
        this.maxTitle = maxTitle;
    }

    public String[] getTitles() {
        return titles;
    }

    public void setTitles(String[] titles) {
        this.titles = titles;
    }

    public Map<String, Object> getOther() {
        return other;
    }

    public void setOther(Map<String, Object> other) {
        this.other = other;
    }

    public List<Map<String, Object>> getData() {
        return data;
    }

    public void setData(List<Map<String, Object>> data) {
        this.data = data;
    }
}
