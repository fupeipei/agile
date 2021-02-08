package com.yusys.agile.versionmanager.dto;

import java.util.Date;
import java.util.List;

public class BjVersionChangeSyncDTO {
    // 记录ID
    private Long recordId;
    // 操作类型
    private String operationType;
    // 版本计划主键
    private Long versionPlanId;
    // 版本计划关联的业务需求Id
    private Long bizBacklogId;
    // 版本计划关联的系统分支需求Id
    private Long sysBranchId;
    // 创建时间
    private Date createTime;
    // 是否审批
    private Integer reviewStatus;
    // 版本计划中的需求Id列表
    private List<Long> bizBacklogIds;
    // 版本计划中的系统分支Id列表
    private List<Long> systemBranchIds;
    // 外部系统API标识
    private String systemApi;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private String status;

    public Long getRecordId() {
        return recordId;
    }

    public void setRecordId(Long recordId) {
        this.recordId = recordId;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType == null ? null : operationType.trim();
    }

    public Long getVersionPlanId() {
        return versionPlanId;
    }

    public void setVersionPlanId(Long versionPlanId) {
        this.versionPlanId = versionPlanId;
    }

    public Long getBizBacklogId() {
        return bizBacklogId;
    }

    public void setBizBacklogId(Long bizBacklogId) {
        this.bizBacklogId = bizBacklogId;
    }

    public Long getSysBranchId() {
        return sysBranchId;
    }

    public void setSysBranchId(Long sysBranchId) {
        this.sysBranchId = sysBranchId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getReviewStatus() {
        return reviewStatus;
    }

    public void setReviewStatus(Integer reviewStatus) {
        this.reviewStatus = reviewStatus;
    }

    public List<Long> getBizBacklogIds() {
        return bizBacklogIds;
    }

    public void setBizBacklogIds(List<Long> bizBacklogIds) {
        this.bizBacklogIds = bizBacklogIds;
    }

    public List<Long> getSystemBranchIds() {
        return systemBranchIds;
    }

    public void setSystemBranchIds(List<Long> systemBranchIds) {
        this.systemBranchIds = systemBranchIds;
    }

    public String getSystemApi() {
        return systemApi;
    }

    public void setSystemApi(String systemApi) {
        this.systemApi = systemApi;
    }
}