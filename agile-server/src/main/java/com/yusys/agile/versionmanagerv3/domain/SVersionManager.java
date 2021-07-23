package com.yusys.agile.versionmanagerv3.domain;

import java.io.Serializable;
import java.util.Date;

public class SVersionManager implements Serializable {
    private Long versionManagerId;

    private String versionName;

    private Date raiseTestDate;

    private Date releaseDate;

    private Long createUid;

    private String createName;

    private Date createTime;

    private Long updateUid;

    private Date updateTime;

    private String tenantCode;

    private String state;

    private Long systemId;

    private String versionDescribe;

    private static final long serialVersionUID = 1L;

    public Long getVersionManagerId() {
        return versionManagerId;
    }

    public void setVersionManagerId(Long versionManagerId) {
        this.versionManagerId = versionManagerId;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName == null ? null : versionName.trim();
    }

    public Date getRaiseTestDate() {
        return raiseTestDate;
    }

    public void setRaiseTestDate(Date raiseTestDate) {
        this.raiseTestDate = raiseTestDate;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Long getCreateUid() {
        return createUid;
    }

    public void setCreateUid(Long createUid) {
        this.createUid = createUid;
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName == null ? null : createName.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getUpdateUid() {
        return updateUid;
    }

    public void setUpdateUid(Long updateUid) {
        this.updateUid = updateUid;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getTenantCode() {
        return tenantCode;
    }

    public void setTenantCode(String tenantCode) {
        this.tenantCode = tenantCode == null ? null : tenantCode.trim();
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

    public Long getSystemId() {
        return systemId;
    }

    public void setSystemId(Long systemId) {
        this.systemId = systemId;
    }

    public String getVersionDescribe() {
        return versionDescribe;
    }

    public void setVersionDescribe(String versionDescribe) {
        this.versionDescribe = versionDescribe == null ? null : versionDescribe.trim();
    }
}