package com.yusys.agile.issue.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;


public class Issue implements Serializable {
    private Long issueId;

    private String title;

    private Long parentId;

    private Byte issueType;

    private Long sprintId;

    private Long moduleId;

    private Long systemId;

    private Long handler;

    private Date beginDate;

    private Date endDate;

    private Integer planWorkload;

    private Integer reallyWorkload;

    private Byte priority;

    private Byte importance;

    private Long stageId;

    private Long laneId;

    private String state;

    private Byte status;

    private Long projectId;

    private Byte isCollect;

    private String completion;

    private Integer taskType;

    private Long createUid;

    private Date createTime;

    private Long updateUid;

    private Date updateTime;

    private Long faultType;

    private Long faultLevel;

    private Date fixedTime;

    private Date closeTime;

    private Long fixedUid;

    private Long testUid;

    private Long versionId;

    private Long caseId;

    private Date deadline;

    private String file;

    private String urgency;

    private String cause;

    private String detectedPhase;

    private Long manualCaseId;

    private Byte source;

    private Integer remainWorkload;

    private Long bugId;

    private Integer order;

    private Byte blockState;

    private Integer reopenTimes;

    private Byte assessIsPass;

    private String assessRemarks;

    private String tenantCode;

    private Byte cmpSyncResult;

    private Byte isArchive;

    private Byte isCancel;

    /**团队id*/
    private Long teamId;

    /**看板id*/
    private Long  kanbanId;

    private static final long serialVersionUID = 1L;

    public Long getIssueId() {
        return issueId;
    }

    public void setIssueId(Long issueId) {
        this.issueId = issueId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Byte getIssueType() {
        return issueType;
    }

    public void setIssueType(Byte issueType) {
        this.issueType = issueType;
    }

    public Long getSprintId() {
        return sprintId;
    }

    public void setSprintId(Long sprintId) {
        this.sprintId = sprintId;
    }

    public Long getModuleId() {
        return moduleId;
    }

    public void setModuleId(Long moduleId) {
        this.moduleId = moduleId;
    }

    public Long getSystemId() {
        return systemId;
    }

    public void setSystemId(Long systemId) {
        this.systemId = systemId;
    }

    public Long getHandler() {
        return handler;
    }

    public void setHandler(Long handler) {
        this.handler = handler;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Integer getPlanWorkload() {
        return planWorkload;
    }

    public void setPlanWorkload(Integer planWorkload) {
        this.planWorkload = planWorkload;
    }

    public Integer getReallyWorkload() {
        return reallyWorkload;
    }

    public void setReallyWorkload(Integer reallyWorkload) {
        this.reallyWorkload = reallyWorkload;
    }

    public Byte getPriority() {
        return priority;
    }

    public void setPriority(Byte priority) {
        this.priority = priority;
    }

    public Byte getImportance() {
        return importance;
    }

    public void setImportance(Byte importance) {
        this.importance = importance;
    }

    public Long getStageId() {
        return stageId;
    }

    public void setStageId(Long stageId) {
        this.stageId = stageId;
    }

    public Long getLaneId() {
        return laneId;
    }

    public void setLaneId(Long laneId) {
        this.laneId = laneId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Byte getIsCollect() {
        return isCollect;
    }

    public void setIsCollect(Byte isCollect) {
        this.isCollect = isCollect;
    }

    public String getCompletion() {
        return completion;
    }

    public void setCompletion(String completion) {
        this.completion = completion;
    }

    public Integer getTaskType() {
        return taskType;
    }

    public void setTaskType(Integer taskType) {
        this.taskType = taskType;
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

    public Long getFaultType() {
        return faultType;
    }

    public void setFaultType(Long faultType) {
        this.faultType = faultType;
    }

    public Long getFaultLevel() {
        return faultLevel;
    }

    public void setFaultLevel(Long faultLevel) {
        this.faultLevel = faultLevel;
    }

    public Date getFixedTime() {
        return fixedTime;
    }

    public void setFixedTime(Date fixedTime) {
        this.fixedTime = fixedTime;
    }

    public Date getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(Date closeTime) {
        this.closeTime = closeTime;
    }

    public Long getFixedUid() {
        return fixedUid;
    }

    public void setFixedUid(Long fixedUid) {
        this.fixedUid = fixedUid;
    }

    public Long getTestUid() {
        return testUid;
    }

    public void setTestUid(Long testUid) {
        this.testUid = testUid;
    }

    public Long getVersionId() {
        return versionId;
    }

    public void setVersionId(Long versionId) {
        this.versionId = versionId;
    }

    public Long getCaseId() {
        return caseId;
    }

    public void setCaseId(Long caseId) {
        this.caseId = caseId;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getUrgency() {
        return urgency;
    }

    public void setUrgency(String urgency) {
        this.urgency = urgency;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public String getDetectedPhase() {
        return detectedPhase;
    }

    public void setDetectedPhase(String detectedPhase) {
        this.detectedPhase = detectedPhase;
    }

    public Long getManualCaseId() {
        return manualCaseId;
    }

    public void setManualCaseId(Long manualCaseId) {
        this.manualCaseId = manualCaseId;
    }

    public Byte getSource() {
        return source;
    }

    public void setSource(Byte source) {
        this.source = source;
    }

    public Integer getRemainWorkload() {
        return remainWorkload;
    }

    public void setRemainWorkload(Integer remainWorkload) {
        this.remainWorkload = remainWorkload;
    }

    public Long getBugId() {
        return bugId;
    }

    public void setBugId(Long bugId) {
        this.bugId = bugId;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public Byte getBlockState() {
        return blockState;
    }

    public void setBlockState(Byte blockState) {
        this.blockState = blockState;
    }

    public Integer getReopenTimes() {
        return reopenTimes;
    }

    public void setReopenTimes(Integer reopenTimes) {
        this.reopenTimes = reopenTimes;
    }

    public Byte getAssessIsPass() {
        return assessIsPass;
    }

    public void setAssessIsPass(Byte assessIsPass) {
        this.assessIsPass = assessIsPass;
    }

    public String getAssessRemarks() {
        return assessRemarks;
    }

    public void setAssessRemarks(String assessRemarks) {
        this.assessRemarks = assessRemarks;
    }

    public String getTenantCode() {
        return tenantCode;
    }

    public void setTenantCode(String tenantCode) {
        this.tenantCode = tenantCode;
    }

    public Byte getCmpSyncResult() {
        return cmpSyncResult;
    }

    public void setCmpSyncResult(Byte cmpSyncResult) {
        this.cmpSyncResult = cmpSyncResult;
    }

    public Byte getIsArchive() {
        return isArchive;
    }

    public void setIsArchive(Byte isArchive) {
        this.isArchive = isArchive;
    }

    public Byte getIsCancel() {
        return isCancel;
    }

    public void setIsCancel(Byte isCancel) {
        this.isCancel = isCancel;
    }

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public Long getKanbanId() {
        return kanbanId;
    }

    public void setKanbanId(Long kanbanId) {
        this.kanbanId = kanbanId;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }
}