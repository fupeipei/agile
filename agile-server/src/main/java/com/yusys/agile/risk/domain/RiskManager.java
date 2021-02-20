package com.yusys.agile.risk.domain;

import java.io.Serializable;
import java.util.Date;

public class RiskManager implements Serializable {
    private Long riskId;

    private Long projectId;

    private Long systemId;

    private String tenantCode;

    private String title;

    private Byte riskLevel;

    private Byte riskStatus;

    private Date riskStartTime;

    private Date riskEndTime;

    private String remark;

    private Long createUid;

    private String createName;

    private Date createTime;

    private Long updateUid;

    private Date updateTime;

    private static final long serialVersionUID = 1L;

    public Long getRiskId() {
        return riskId;
    }

    public void setRiskId(Long riskId) {
        this.riskId = riskId;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Long getSystemId() {
        return systemId;
    }

    public void setSystemId(Long systemId) {
        this.systemId = systemId;
    }

    public String getTenantCode() {
        return tenantCode;
    }

    public void setTenantCode(String tenantCode) {
        this.tenantCode = tenantCode == null ? null : tenantCode.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public Byte getRiskLevel() {
        return riskLevel;
    }

    public void setRiskLevel(Byte riskLevel) {
        this.riskLevel = riskLevel;
    }

    public Byte getRiskStatus() {
        return riskStatus;
    }

    public void setRiskStatus(Byte riskStatus) {
        this.riskStatus = riskStatus;
    }

    public Date getRiskStartTime() {
        return riskStartTime;
    }

    public void setRiskStartTime(Date riskStartTime) {
        this.riskStartTime = riskStartTime;
    }

    public Date getRiskEndTime() {
        return riskEndTime;
    }

    public void setRiskEndTime(Date riskEndTime) {
        this.riskEndTime = riskEndTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
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
}