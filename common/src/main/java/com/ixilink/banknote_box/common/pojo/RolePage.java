package com.ixilink.banknote_box.common.pojo;

public class RolePage {
    private Integer id;

    private Integer roleId;

    private Integer pageId;

    private Integer libraryId;

    public RolePage() {
    }

    public RolePage(Integer roleId, Integer pageId, Integer libraryId) {
        this.roleId = roleId;
        this.pageId = pageId;
        this.libraryId = libraryId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getPageId() {
        return pageId;
    }

    public void setPageId(Integer pageId) {
        this.pageId = pageId;
    }

    public Integer getLibraryId() {
        return libraryId;
    }

    public void setLibraryId(Integer libraryId) {
        this.libraryId = libraryId;
    }
}