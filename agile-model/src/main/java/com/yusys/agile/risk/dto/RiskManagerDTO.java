package com.yusys.agile.risk.dto;

import java.util.Date;

/**
 * @ClassName RiskDTO
 * @Description 风险管理DTO类
 * @Date 2020/8/11 11:42
 * @Version 1.0
 */
public class RiskManagerDTO {
    private Long riskId;

    private Long projectId;

    private String projectName;

    private Long systemId;

    private String systemName;

    private String tenantCode;

    private String title;

    private Byte riskLevel;

    private String riskLevelName;

    private Byte riskStatus;

    private String riskStatusName;

    private Date riskStartTime;

    private Date riskEndTime;

    private String remark;

    private Long createUid;

    private String createName;

    private String createUserAccount;

    private Date createTime;

    private Long updateUid;

    private Date updateTime;

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

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Long getSystemId() {
        return systemId;
    }

    public void setSystemId(Long systemId) {
        this.systemId = systemId;
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public String getTenantCode() {
        return tenantCode;
    }

    public void setTenantCode(String tenantCode) {
        this.tenantCode = tenantCode;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Byte getRiskLevel() {
        return riskLevel;
    }

    public void setRiskLevel(Byte riskLevel) {
        this.riskLevel = riskLevel;
    }

    public String getRiskLevelName() {
        return riskLevelName;
    }

    public void setRiskLevelName(String riskLevelName) {
        this.riskLevelName = riskLevelName;
    }

    public Byte getRiskStatus() {
        return riskStatus;
    }

    public void setRiskStatus(Byte riskStatus) {
        this.riskStatus = riskStatus;
    }

    public String getRiskStatusName() {
        return riskStatusName;
    }

    public void setRiskStatusName(String riskStatusName) {
        this.riskStatusName = riskStatusName;
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
        this.remark = remark;
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
        this.createName = createName;
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


    public String getCreateUserAccount() {
        return createUserAccount;
    }

    public void setCreateUserAccount(String createUserAccount) {
        this.createUserAccount = createUserAccount;
    }

    @Override
    public String toString() {
        return "RiskManagerDTO{" +
                "riskId=" + riskId +
                ", projectId=" + projectId +
                ", systemId=" + systemId +
                ", title='" + title + '\'' +
                '}';
    }
}
