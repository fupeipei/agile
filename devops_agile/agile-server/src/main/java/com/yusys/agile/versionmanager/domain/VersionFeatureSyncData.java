package com.yusys.agile.versionmanager.domain;

import java.io.Serializable;
import java.util.Date;

public class VersionFeatureSyncData implements Serializable {


    private static final long serialVersionUID = -6794850642899988229L;

    private Long id;

    private Long requireId;

    private Long branchId;

    private Long projectId;

    private Byte operateType;

    private Byte cmpSyncResult;

    private Byte itcSyncResult;

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

    public Long getRequireId() {
        return requireId;
    }

    public void setRequireId(Long requireId) {
        this.requireId = requireId;
    }

    public Long getBranchId() {
        return branchId;
    }

    public void setBranchId(Long branchId) {
        this.branchId = branchId;
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

    public Byte getCmpSyncResult() {
        return cmpSyncResult;
    }

    public void setCmpSyncResult(Byte cmpSyncResult) {
        this.cmpSyncResult = cmpSyncResult;
    }

    public Byte getItcSyncResult() {
        return itcSyncResult;
    }

    public void setItcSyncResult(Byte itcSyncResult) {
        this.itcSyncResult = itcSyncResult;
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

    @Override
    public String toString() {
        return "VersionFeatureSyncData{" +
                "id=" + id +
                ", requireId=" + requireId +
                ", branchId=" + branchId +
                ", projectId=" + projectId +
                ", operateType=" + operateType +
                ", cmpSyncResult=" + cmpSyncResult +
                ", itcSyncResult=" + itcSyncResult +
                ", createUid=" + createUid +
                ", createTime=" + createTime +
                ", updateUid=" + updateUid +
                ", updateTime=" + updateTime +
                ", tenantCode='" + tenantCode + '\'' +
                '}';
    }
}