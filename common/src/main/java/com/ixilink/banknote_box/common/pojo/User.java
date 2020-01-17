package com.ixilink.banknote_box.common.pojo;

public class User {
    private Integer id;

    private String loginName;

    private String password;

    private String name;

    private Integer sex;

    private Integer departmentId;

    private String departmentName;

    private Integer roleId;

    private String roleName;

    private Integer state;

    private Long createTime;

    private Integer partnerId;

    private Integer libraryId;

    private String libraryName;

    public User() {
    }
    public User(Integer id, String loginName, String password, String name, Integer sex, Integer departmentId, Integer roleId, Integer state, Long createTime, Integer libraryId) {
        this.id = id;
        this.loginName = loginName;
        this.password = password;
        this.name = name;
        this.sex = sex;
        this.departmentId = departmentId;
        this.roleId = roleId;
        this.state = state;
        this.createTime = createTime;
        this.libraryId = libraryId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName == null ? null : loginName.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Integer getLibraryId() {
        return libraryId;
    }

    public void setLibraryId(Integer libraryId) {
        this.libraryId = libraryId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Integer getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(Integer partnerId) {
        this.partnerId = partnerId;
    }

    public String getLibraryName() {
        return libraryName;
    }

    public void setLibraryName(String libraryName) {
        this.libraryName = libraryName;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", loginName='" + loginName + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", sex=" + sex +
                ", departmentId=" + departmentId +
                ", departmentName='" + departmentName + '\'' +
                ", roleId=" + roleId +
                ", roleName='" + roleName + '\'' +
                ", state=" + state +
                ", createTime=" + createTime +
                ", partnerId=" + partnerId +
                ", libraryId=" + libraryId +
                ", libraryName='" + libraryName + '\'' +
                '}';
    }
}