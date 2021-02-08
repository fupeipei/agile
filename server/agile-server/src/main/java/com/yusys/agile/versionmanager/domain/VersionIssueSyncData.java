package com.yusys.agile.versionmanager.domain;

import java.io.Serializable;
import java.util.Date;

public class VersionIssueSyncData implements Serializable {

    private static final long serialVersionUID = -6682481635640062724L;

    private Long id;

    private Long versionId;

    private Long issueId;

    private Byte issueType;

    private Long projectId;

    private Byte operateType;

    private Byte syncSystem;

    private Byte syncResult;

    private Long createUid;

    private Date createTime;

    private Integer updateUid;

    private Date updateTime;

    private String tenantCode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVersionId() {
        return versionId;
    }

    public void setVersionId(Long versionId) {
        this.versionId = versionId;
    }

    public Long getIssueId() {
        return issueId;
    }

    public void setIssueId(Long issueId) {
        this.issueId = issueId;
    }

    public Byte getIssueType() {
        return issueType;
    }

    public void setIssueType(Byte issueType) {
        this.issueType = issueType;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Byte getOperateType() {
        return operateType;
    }

    public void setOperateType(Byte operateType) {
        this.operateType = operateType;
    }

    public Byte getSyncSystem() {
        return syncSystem;
    }

    public void setSyncSystem(Byte syncSystem) {
        this.syncSystem = syncSystem;
    }

    public Byte getSyncResult() {
        return syncResult;
    }

    public void setSyncResult(Byte syncResult) {
        this.syncResult = syncResult;
    }

    public Long getCreateUid() {
        return createUid;
    }

    public void setCreateUid(Long createUid) {
        this.createUid = createUid;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getUpdateUid() {
        return updateUid;
    }

    public void setUpdateUid(Integer updateUid) {
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
}