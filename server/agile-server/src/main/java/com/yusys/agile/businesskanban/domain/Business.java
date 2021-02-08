package com.yusys.agile.businesskanban.domain;

import java.io.Serializable;
import java.util.Date;

public class Business implements Serializable {
    private Long businessId;

    private Long projectId;

    private Long kanbanId;

    private String businessName;

    private Long businessType;

    private Byte businessState;

    private Byte isVisible;

    private Long businessOwner;

    private String businessOwnerName;

    private Byte businessLevel;

    private Byte businessImportance;

    private Integer planWorkload;

    private Integer actualWorkload;

    private Date planStartTime;

    private Date planEndTime;

    private Date startTime;

    private Date endTime;

    private Long createUid;

    private Date createTime;

    private Date updateTime;

    private Long updateUid;

    private Byte status;

    private String state;

    private String tenantCode;

    private static final long serialVersionUID = 1L;

    public Long getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Long businessId) {
        this.businessId = businessId;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Long getKanbanId() {
        return kanbanId;
    }

    public void setKanbanId(Long kanbanId) {
        this.kanbanId = kanbanId;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName == null ? null : businessName.trim();
    }

    public Long getBusinessType() {
        return businessType;
    }

    public void setBusinessType(Long businessType) {
        this.businessType = businessType;
    }

    public Byte getBusinessState() {
        return businessState;
    }

    public void setBusinessState(Byte businessState) {
        this.businessState = businessState;
    }

    public Byte getIsVisible() {
        return isVisible;
    }

    public void setIsVisible(Byte isVisible) {
        this.isVisible = isVisible;
    }

    public Long getBusinessOwner() {
        return businessOwner;
    }

    public void setBusinessOwner(Long businessOwner) {
        this.businessOwner = businessOwner;
    }

    public String getBusinessOwnerName() {
        return businessOwnerName;
    }

    public void setBusinessOwnerName(String businessOwnerName) {
        this.businessOwnerName = businessOwnerName == null ? null : businessOwnerName.trim();
    }

    public Byte getBusinessLevel() {
        return businessLevel;
    }

    public void setBusinessLevel(Byte businessLevel) {
        this.businessLevel = businessLevel;
    }

    public Byte getBusinessImportance() {
        return businessImportance;
    }

    public void setBusinessImportance(Byte businessImportance) {
        this.businessImportance = businessImportance;
    }

    public Integer getPlanWorkload() {
        return planWorkload;
    }

    public void setPlanWorkload(Integer planWorkload) {
        this.planWorkload = planWorkload;
    }

    public Integer getActualWorkload() {
        return actualWorkload;
    }

    public void setActualWorkload(Integer actualWorkload) {
        this.actualWorkload = actualWorkload;
    }

    public Date getPlanStartTime() {
        return planStartTime;
    }

    public void setPlanStartTime(Date planStartTime) {
        this.planStartTime = planStartTime;
    }

    public Date getPlanEndTime() {
        return planEndTime;
    }

    public void setPlanEndTime(Date planEndTime) {
        this.planEndTime = planEndTime;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
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

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Long getUpdateUid() {
        return updateUid;
    }

    public void setUpdateUid(Long updateUid) {
        this.updateUid = updateUid;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

    public String getTenantCode() {
        return tenantCode;
    }

    public void setTenantCode(String tenantCode) {
        this.tenantCode = tenantCode;
    }
}