package com.yusys.agile.versionmanager.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * @description 版本同步数据
 * @date 2020/09/22
 */
public class VersionSyncData implements Serializable {

    private static final long serialVersionUID = -6785433228061788041L;

    private Long id;

    private Long versionId;

    private Integer operateType;

    private Long projectId;

    private Byte syncSystem;

    private Byte syncResult;

    private Long createUid;

    private Date createTime;

    private Long updateUid;

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

    public Integer getOperateType() {
        return operateType;
    }

    public void setOperateType(Integer operateType) {
        this.operateType = operateType;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
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
}