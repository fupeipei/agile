package com.yusys.agile.sprint.dto;

import java.util.Date;

public class UserSprintHourDTO {
    private Long hourId;

    private Long userId;

    private Long sprintId;

    private Integer reallyHours;

    private Date createTime;

    private Long createUid;

    private Date updateTime;

    private Long updateUid;

    private String userName;

    private String userAccount;

    private String tenantCode;

    public Long getHourId() {
        return hourId;
    }

    public void setHourId(Long hourId) {
        this.hourId = hourId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getSprintId() {
        return sprintId;
    }

    public void setSprintId(Long sprintId) {
        this.sprintId = sprintId;
    }

    public Integer getReallyHours() {
        return reallyHours;
    }

    public void setReallyHours(Integer reallyHours) {
        this.reallyHours = reallyHours;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getCreateUid() {
        return createUid;
    }

    public void setCreateUid(Long createUid) {
        this.createUid = createUid;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Long getUpdateUid() {
        return updateUid;
    }

    public void setUpdateUid(Long updateUid) {
        this.updateUid = updateUid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getTenantCode() {
        return tenantCode;
    }

    public void setTenantCode(String tenantCode) {
        this.tenantCode = tenantCode;
    }
}
