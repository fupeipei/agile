package com.yusys.agile.versionmanager.dto;

import com.yusys.agile.issue.dto.IssueDTO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @ClassName VersionManagerDTO
 * @Description 版本管理DTO工具类
 * @Date 2021/2/5 16:28
 * @Version 1.0
 */
public class VersionManagerDTO implements Serializable {
    private Long id;

    private String versionName;

    private String versionCode;

    private Date planReleaseDate;

    private Date planDeployDate;

    private String versionState;

    private String versionStateName;

    public String getVersionStateName() {
        return versionStateName;
    }

    public void setVersionStateName(String versionStateName) {
        this.versionStateName = versionStateName;
    }

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

    private String deployTypeName;

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

    private Integer pageNum;

    private Integer pageSize;

    public Byte getMoveToDeployType() {
        return moveToDeployType;
    }

    public void setMoveToDeployType(Byte moveToDeployType) {
        this.moveToDeployType = moveToDeployType;
    }

    private Byte moveToDeployType;

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

    private Date fitstEditionDate;

    private Date sealedEditionDate;

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getOperateType() {
        return operateType;
    }

    public void setOperateType(Integer operateType) {
        this.operateType = operateType;
    }

    private Integer operateType;


    private List<SendRMSRequestDTO> relatedRequirementList;


    private  List<IssueDTO> relateIssueList;

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

    public String getDeployTypeName() {
        return deployTypeName;
    }

    public void setDeployTypeName(String deployTypeName) {
        this.deployTypeName = deployTypeName;
    }

    public List<SendRMSRequestDTO> getRelatedRequirementList() {
        return relatedRequirementList;
    }

    public void setRelatedRequirementList(List<SendRMSRequestDTO> relatedRequirementList) {
        this.relatedRequirementList = relatedRequirementList;
    }

    public List<IssueDTO> getRelateIssueList() {
        return relateIssueList;
    }

    public void setRelateIssueList(List<IssueDTO> relateIssueList) {
        this.relateIssueList = relateIssueList;
    }
}
