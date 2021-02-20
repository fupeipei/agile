package com.yusys.agile.businesskanban.dto;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Date: 2021/2/1
 * @Description: 事务实体
 */
public class BusinessDTO {
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

    //预计开始时间区间 start
    private Date planStartTime;

    private Date planStartTimeEnd;
    //end

    //预计结束时间区间 start
    private Date planEndTime;

    private Date planEndTimeEnd;
    //end

    private Date startTime;

    private Date endTime;

    private Long createUid;

    private Date createTime;

    private Date updateTime;

    private Long updateUid;

    private Byte status;

    private String businessDesc;

    private String descText;

    private Integer pageNum;

    private Integer pageSize;

    private String state;

    private List<BusinessHistoryRecordDTO> historyRecordDTOS;

    private List<BusinessAttachmentDTO> attachmentDTOS;

    // 0 按类型  1 按人员
    private Integer selectType;

    private String tenantCode;

    private Map businessTypeMap;

    public Map getBusinessTypeMap() {
        return businessTypeMap;
    }

    public void setBusinessTypeMap(Map businessTypeMap) {
        this.businessTypeMap = businessTypeMap;
    }

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
        this.businessName = businessName;
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
        this.businessOwnerName = businessOwnerName;
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

    public Date getPlanStartTimeEnd() {
        return planStartTimeEnd;
    }

    public void setPlanStartTimeEnd(Date planStartTimeEnd) {
        this.planStartTimeEnd = planStartTimeEnd;
    }

    public Date getPlanEndTime() {
        return planEndTime;
    }

    public void setPlanEndTime(Date planEndTime) {
        this.planEndTime = planEndTime;
    }

    public Date getPlanEndTimeEnd() {
        return planEndTimeEnd;
    }

    public void setPlanEndTimeEnd(Date planEndTimeEnd) {
        this.planEndTimeEnd = planEndTimeEnd;
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

    public String getBusinessDesc() {
        return businessDesc;
    }

    public void setBusinessDesc(String businessDesc) {
        this.businessDesc = businessDesc;
    }

    public String getDescText() {
        return descText;
    }

    public void setDescText(String descText) {
        this.descText = descText;
    }

    public List<BusinessHistoryRecordDTO> getHistoryRecordDTOS() {
        return historyRecordDTOS;
    }

    public void setHistoryRecordDTOS(List<BusinessHistoryRecordDTO> historyRecordDTOS) {
        this.historyRecordDTOS = historyRecordDTOS;
    }

    public List<BusinessAttachmentDTO> getAttachmentDTOS() {
        return attachmentDTOS;
    }

    public void setAttachmentDTOS(List<BusinessAttachmentDTO> attachmentDTOS) {
        this.attachmentDTOS = attachmentDTOS;
    }

    public Integer getSelectType() {
        return selectType;
    }

    public void setSelectType(Integer selectType) {
        this.selectType = selectType;
    }

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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTenantCode() {
        return tenantCode;
    }

    public void setTenantCode(String tenantCode) {
        this.tenantCode = tenantCode;
    }

    @Override
    public String toString() {
        return "BusinessDTO{" +
                "businessId=" + businessId +
                ", projectId=" + projectId +
                ", kanbanId=" + kanbanId +
                ", businessName='" + businessName + '\'' +
                ", businessType=" + businessType +
                ", businessState=" + businessState +
                ", isVisible=" + isVisible +
                ", businessOwner=" + businessOwner +
                ", businessOwnerName='" + businessOwnerName + '\'' +
                ", businessLevel=" + businessLevel +
                ", businessImportance=" + businessImportance +
                ", planWorkload=" + planWorkload +
                ", actualWorkload=" + actualWorkload +
                ", planStartTime=" + planStartTime +
                ", planEndTime=" + planEndTime +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", createUid=" + createUid +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", updateUid=" + updateUid +
                ", status=" + status +
                '}';
    }
}