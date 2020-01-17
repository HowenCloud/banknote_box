package com.ixilink.banknote_box.common.pojo;

public class Role {
    private Integer id;

    private String roleName;

    private String remarks;

    private Long createTime;

    private Integer modifiable;

    private Integer deleting;

    private Integer needLogin;

    private Integer state;

    private Integer isSelect;

    private Integer libraryId;

    public Role() {
    }

    public Role(Integer id, String roleName, String remarks, Long createTime, Integer modifiable, Integer deleting, Integer needLogin, Integer state, Integer isSelect, Integer libraryId) {
        this.id = id;
        this.roleName = roleName;
        this.remarks = remarks;
        this.createTime = createTime;
        this.modifiable = modifiable;
        this.deleting = deleting;
        this.needLogin = needLogin;
        this.state = state;
        this.isSelect = isSelect;
        this.libraryId = libraryId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName == null ? null : roleName.trim();
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

    public Integer getModifiable() {
        return modifiable;
    }

    public void setModifiable(Integer modifiable) {
        this.modifiable = modifiable;
    }

    public Integer getDeleting() {
        return deleting;
    }

    public void setDeleting(Integer deleting) {
        this.deleting = deleting;
    }

    public Integer getNeedLogin() {
        return needLogin;
    }

    public void setNeedLogin(Integer needLogin) {
        this.needLogin = needLogin;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getIsSelect() {
        return isSelect;
    }

    public void setIsSelect(Integer isSelect) {
        this.isSelect = isSelect;
    }

    public Integer getLibraryId() {
        return libraryId;
    }

    public void setLibraryId(Integer libraryId) {
        this.libraryId = libraryId;
    }
}