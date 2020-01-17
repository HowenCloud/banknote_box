package com.ixilink.banknote_box.common.pojo;

import java.util.ArrayList;
import java.util.List;

public class PermissionsType {
    private Integer id;

    private String typeName;

    private String remarks;

    private Long createTime;

    private List<Permissions> childs = new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName == null ? null : typeName.trim();
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public List<Permissions> getChilds() {
        return childs;
    }

    public void setChilds(List<Permissions> childs) {
        this.childs = childs;
    }
}