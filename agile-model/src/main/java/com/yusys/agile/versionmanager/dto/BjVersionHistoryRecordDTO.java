package com.yusys.agile.versionmanager.dto;

import java.util.Date;

public class BjVersionHistoryRecordDTO {
    private Long recordId;

    private String operationField;

    private Date createTime;

    private Long createUid;

    private Long versionPlanId;

    private Long bizBacklogId;

    private Long sysBranchId;

    private String username;

    private Integer recordType;

    private String formatCreateTime;

    private String unbindingReason;

    private String unbindingComments;

    private String newVersionId;

    private Integer page;

    private Integer pageSize;

    // 客户需求编号
    private String bizNum;

    public Long getRecordId() {
        return recordId;
    }

    public void setRecordId(Long recordId) {
        this.recordId = recordId;
    }

    public String getOperationField() {
        return operationField;
    }

    public void setOperationField(String operationField) {
        this.operationField = operationField == null ? null : operationField.trim();
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getRecordType() {
        return recordType;
    }

    public void setRecordType(Integer recordType) {
        this.recordType = recordType;
    }

    public String getFormatCreateTime() {
        return formatCreateTime;
    }

    public void setFormatCreateTime(String formatCreateTime) {
        this.formatCreateTime = formatCreateTime;
    }

    public String getUnbindingReason() {
        return unbindingReason;
    }

    public void setUnbindingReason(String unbindingReason) {
        this.unbindingReason = unbindingReason == null ? null : unbindingReason.trim();
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getBizNum() {
        return bizNum;
    }

    public void setBizNum(String bizNum) {
        this.bizNum = bizNum;
    }

    public String getUnbindingComments() {
        return unbindingComments;
    }

    public void setUnbindingComments(String unbindingComments) {
        this.unbindingComments = unbindingComments == null ? null : unbindingComments.trim();
    }

    public String getNewVersionId() {
        return newVersionId;
    }

    public void setNewVersionId(String newVersionId) {
        this.newVersionId = newVersionId == null ? null : newVersionId.trim();
    }
}