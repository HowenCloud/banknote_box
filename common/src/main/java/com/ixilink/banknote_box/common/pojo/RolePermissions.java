package com.ixilink.banknote_box.common.pojo;

public class RolePermissions {
    private Integer id;

    private Integer roleId;

    private Integer permissionsId;

    private Integer libraryId;

    public RolePermissions() {
    }

    public RolePermissions(Integer roleId, Integer permissionsId, Integer libraryId) {
        this.roleId = roleId;
        this.permissionsId = permissionsId;
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

    public Integer getPermissionsId() {
        return permissionsId;
    }

    public void setPermissionsId(Integer permissionsId) {
        this.permissionsId = permissionsId;
    }

    public Integer getLibraryId() {
        return libraryId;
    }

    public void setLibraryId(Integer libraryId) {
        this.libraryId = libraryId;
    }
}