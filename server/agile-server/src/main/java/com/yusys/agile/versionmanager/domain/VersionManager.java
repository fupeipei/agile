package com.yusys.agile.versionmanager.domain;

import java.io.Serializable;
import java.util.Date;

public class VersionManager implements Serializable {
    private Long id;

    private String versionName;

    private String versionCode;

    private Date planReleaseDate;

    private Date planDeployDate;

    private String versionState;

    private String versionDescribe;

    private Date changeReleaseDate;

    private String changeReason;

    private String changeDescription;

    private Integer sendToRmp;

    private Integer sendToCmp;

    private Integer sendToItc;

    private Integer reviewCount;

    private Date reviewStartTime;

    private Date reviewEndTime;

    private Integer planDeliveryNumber;

    private Byte baselineFlag;

    private Integer operationUid;

    private Date baselineTime;

    private Byte deployType;

    private String batchCode;

    private String deployPeriod;

    private String mainWatch;

    private String secondaryWatch;

    private String mainGuarantee;

    private String secondaryGuarantee;

    private Long createUid;

    private String createName;

    private Long projectId;

    private Date createTime;

    private Long updateUid;

    private Date updateTime;

    private String tenantCode;

    private Date fitstEditionDate;

    private Date sealedEditionDate;

    private String state;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName == null ? null : versionName.trim();
    }

    public String getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode == null ? null : versionCode.trim();
    }

    public Date getPlanReleaseDate() {
        return planReleaseDate;
    }

    public void setPlanReleaseDate(Date planReleaseDate) {
        this.planReleaseDate = planReleaseDate;
    }

    public Date getPlanDeployDate() {
        return planDeployDate;
    }

    public void setPlanDeployDate(Date planDeployDate) {
        this.planDeployDate = planDeployDate;
    }

    public String getVersionState() {
        return versionState;
    }

    public void setVersionState(String versionState) {
        this.versionState = versionState == null ? null : versionState.trim();
    }

    public String getVersionDescribe() {
        return versionDescribe;
    }

    public void setVersionDescribe(String versionDescribe) {
        this.versionDescribe = versionDescribe == null ? null : versionDescribe.trim();
    }

    public Date getChangeReleaseDate() {
        return changeReleaseDate;
    }

    public void setChangeReleaseDate(Date changeReleaseDate) {
        this.changeReleaseDate = changeReleaseDate;
    }

    public String getChangeReason() {
        return changeReason;
    }

    public void setChangeReason(String changeReason) {
        this.changeReason = changeReason == null ? null : changeReason.trim();
    }

    public String getChangeDescription() {
        return changeDescription;
    }

    public void setChangeDescription(String changeDescription) {
        this.changeDescription = changeDescription == null ? null : changeDescription.trim();
    }

    public Integer getSendToRmp() {
        return sendToRmp;
    }

    public void setSendToRmp(Integer sendToRmp) {
        this.sendToRmp = sendToRmp;
    }

    public Integer getSendToCmp() {
        return sendToCmp;
    }

    public void setSendToCmp(Integer sendToCmp) {
        this.sendToCmp = sendToCmp;
    }

    public Integer getSendToItc() {
        return sendToItc;
    }

    public void setSendToItc(Integer sendToItc) {
        this.sendToItc = sendToItc;
    }

    public Integer getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(Integer reviewCount) {
        this.reviewCount = reviewCount;
    }

    public Date getReviewStartTime() {
        return reviewStartTime;
    }

    public void setReviewStartTime(Date reviewStartTime) {
        this.reviewStartTime = reviewStartTime;
    }

    public Date getReviewEndTime() {
        return reviewEndTime;
    }

    public void setReviewEndTime(Date reviewEndTime) {
        this.reviewEndTime = reviewEndTime;
    }

    public Integer getPlanDeliveryNumber() {
        return planDeliveryNumber;
    }

    public void setPlanDeliveryNumber(Integer planDeliveryNumber) {
        this.planDeliveryNumber = planDeliveryNumber;
    }

    public Byte getBaselineFlag() {
        return baselineFlag;
    }

    public void setBaselineFlag(Byte baselineFlag) {
        this.baselineFlag = baselineFlag;
    }

    public Integer getOperationUid() {
        return operationUid;
    }

    public void setOperationUid(Integer operationUid) {
        this.operationUid = operationUid;
    }

    public Date getBaselineTime() {
        return baselineTime;
    }

    public void setBaselineTime(Date baselineTime) {
        this.baselineTime = baselineTime;
    }

    public Byte getDeployType() {
        return deployType;
    }

    public void setDeployType(Byte deployType) {
        this.deployType = deployType;
    }

    public String getBatchCode() {
        return batchCode;
    }

    public void setBatchCode(String batchCode) {
        this.batchCode = batchCode == null ? null : batchCode.trim();
    }

    public String getDeployPeriod() {
        return deployPeriod;
    }

    public void setDeployPeriod(String deployPeriod) {
        this.deployPeriod = deployPeriod == null ? null : deployPeriod.trim();
    }

    public String getMainWatch() {
        return mainWatch;
    }

    public void setMainWatch(String mainWatch) {
        this.mainWatch = mainWatch == null ? null : mainWatch.trim();
    }

    public String getSecondaryWatch() {
        return secondaryWatch;
    }

    public void setSecondaryWatch(String secondaryWatch) {
        this.secondaryWatch = secondaryWatch == null ? null : secondaryWatch.trim();
    }

    public String getMainGuarantee() {
        return mainGuarantee;
    }

    public void setMainGuarantee(String mainGuarantee) {
        this.mainGuarantee = mainGuarantee == null ? null : mainGuarantee.trim();
    }

    public String getSecondaryGuarantee() {
        return secondaryGuarantee;
    }

    public void setSecondaryGuarantee(String secondaryGuarantee) {
        this.secondaryGuarantee = secondaryGuarantee == null ? null : secondaryGuarantee.trim();
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

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
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

    public Date getFitstEditionDate() {
        return fitstEditionDate;
    }

    public void setFitstEditionDate(Date fitstEditionDate) {
        this.fitstEditionDate = fitstEditionDate;
    }

    public Date getSealedEditionDate() {
        return sealedEditionDate;
    }

    public void setSealedEditionDate(Date sealedEditionDate) {
        this.sealedEditionDate = sealedEditionDate;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }
}