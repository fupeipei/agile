package com.yusys.agile.fault.dto;

import java.util.Date;

/**
 * 缺陷DTO
 *
 *     
 * @create 2020-04-10 16:24
 */
public class FaultDTO {

    // 缺陷编号
    private Long faultId;
    // 缺陷标题
    private String faultName;
    // 状态
    private Integer status;

    private String state;
    // 缺陷类型
    private Long faultType;

    // 缺陷等级
    private Long faultLevel;
    // 工作项id
    private Long issueId;

    private Long projectId;
    // 修复时间
    private Date fixedTime;
    // 关闭时间
    private Date closeTime;
    // 修复人id
    private Long fixedUid;
    // 验证人id
    private Long testUid;
    // 迭代id
    private Long sprintId;

    private Long versionId;
    // 故事id
    private Long storyId;
    // 任务id
    private Long taskId;
    // 用例id
    private Long caseId;
    // 截止
    private Date deadline;

    private String file;
    // 紧急
    private String urgency;
    // 缺陷原因
    private String cause;

    private String detectedPhase;

    private Long manualCaseId;
    // 创建时间
    private Date createTime;

    private Long createUid;

    private Long updateUid;

    private Date updateTime;

    private String faultDesc;

    // 缺陷级别名
    private String faultLevelName;
    // 缺陷类型名
    private String faultTypeName;
    // 缺陷修复人
    private String fixedName;
    // 缺陷提出人
    private String createName;
    // 缺陷验证人
    private String testName;
    // case
    private String caseName;
    // 工时
    private Integer workHour;

    private String storyName;

    private String sprintName;






    public Long getFaultId() {
        return faultId;
    }

    public void setFaultId(Long faultId) {
        this.faultId = faultId;
    }

    public String getFaultName() {
        return faultName;
    }

    public void setFaultName(String faultName) {
        this.faultName = faultName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
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

    public Long getIssueId() {
        return issueId;
    }

    public void setIssueId(Long issueId) {
        this.issueId = issueId;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
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

    public Long getStoryId() {
        return storyId;
    }

    public void setStoryId(Long storyId) {
        this.storyId = storyId;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
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

    public String getFaultDesc() {
        return faultDesc;
    }

    public void setFaultDesc(String faultDesc) {
        this.faultDesc = faultDesc;
    }

    public String getFaultLevelName() {
        return faultLevelName;
    }

    public void setFaultLevelName(String faultLevelName) {
        this.faultLevelName = faultLevelName;
    }

    public String getFaultTypeName() {
        return faultTypeName;
    }

    public void setFaultTypeName(String faultTypeName) {
        this.faultTypeName = faultTypeName;
    }

    public String getFixedName() {
        return fixedName;
    }

    public void setFixedName(String fixedName) {
        this.fixedName = fixedName;
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public Long getSprintId() {
        return sprintId;
    }

    public void setSprintId(Long sprintId) {
        this.sprintId = sprintId;
    }

    public String getCaseName() {
        return caseName;
    }

    public void setCaseName(String caseName) {
        this.caseName = caseName;
    }

    public Integer getWorkHour() {
        return workHour;
    }

    public void setWorkHour(Integer workHour) {
        this.workHour = workHour;
    }

    public String getStoryName() {
        return storyName;
    }

    public void setStoryName(String storyName) {
        this.storyName = storyName;
    }

    public String getSprintName() {
        return sprintName;
    }

    public void setSprintName(String sprintName) {
        this.sprintName = sprintName;
    }
}