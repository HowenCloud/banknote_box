package com.ixilink.banknote_box.common.pojo;

import java.util.ArrayList;
import java.util.List;

public class Page {
    private Integer id;

    private Integer pageLevel;

    private String title;

    private String icon;

    private String pageUrl;

    private Integer parentId;

    private Integer state;

    private String remarks;

    private List<Page> childs = new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPageLevel() {
        return pageLevel;
    }

    public void setPageLevel(Integer pageLevel) {
        this.pageLevel = pageLevel;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon == null ? null : icon.trim();
    }

    public String getPageUrl() {
        return pageUrl;
    }

    public void setPageUrl(String pageUrl) {
        this.pageUrl = pageUrl == null ? null : pageUrl.trim();
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }

    public List<Page> getChilds() {
        return childs;
    }

    public void setChilds(List<Page> childs) {
        this.childs = childs;
    }
}