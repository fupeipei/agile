package com.yusys.agile.issue.dto;


import java.io.Serializable;
import java.util.List;

public class IssueStringDTO implements Serializable {

    private static final long serialVersionUID = -3129918408584131673L;

    private String issueId;

    private String title;

    private String parentId;

    private String issueType;

    private String sprintId;

    private String moduleId;

    private String systemId;

    /**
     *
     * @Date: 11:14
     * @Description: 需求满意度
     * @Return:
     */
    private String satisfaction;

    private String systemName;

    private String moduleName;

    private String handler;

    private String order;

    private String handlerName;

    private String beginDate;

    private String endDate;

    private Integer planWorkload;

    private Integer reallyWorkload;

    private String priority;

    private String importance;
    // 阶段id
    private String stageId;

    private String laneId;

    private String stageName;

    private String projectName;

    private String projectId;

    private String isCollect;

    private Integer taskType;

    private String faultType;

    private String faultLevel;

    private String fixedTime;

    private String closeTime;

    private String fixedUid;

    private String testUid;

    private String versionId;

    private String versionName;

    private String caseId;

    private String deadline;

    private String file;

    private String urgency;

    private String cause;

    private String detectedPhase;

    private String manualCaseId;
    // 缺陷来源 0YuDO 1itc 2itsm
    private String source;

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
    // 缺陷时对应storyName
    private String parentName;
    // 迭代名
    private String sprintName;
    // 剩余工作量
    private String remainWorkload;
    // 和stageId对应的缺陷状态
    private String faultStatus;
    // itc同步过来的bugId
    private String bugId;

    private String pageNum;

    private String pageSize;

    private String rootIds;

    private String state;

    private String status;

    private String createUid;

    private String createTime;

    private String updateUid;

    private String updateTime;

    private String description;
    //缺陷id或者name（高级搜索）
    private String idOrTitle;

    private String completion;

    public String getQueryFlag() {
        return queryFlag;
    }

    public void setQueryFlag(String queryFlag) {
        this.queryFlag = queryFlag;
    }

    /**
     * 查询标识，不为null表示看板查询
     */
    private String queryFlag;

    //指定工作项
    private List<Long> issueIds;

    public String getTenantCode() {
        return tenantCode;
    }

    public void setTenantCode(String tenantCode) {
        this.tenantCode = tenantCode;
    }

    private String tenantCode;

    public String getIssueId() {
        return issueId;
    }

    public void setIssueId(String issueId) {
        this.issueId = issueId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getIssueType() {
        return issueType;
    }

    public void setIssueType(String issueType) {
        this.issueType = issueType;
    }

    public String getSprintId() {
        return sprintId;
    }

    public void setSprintId(String sprintId) {
        this.sprintId = sprintId;
    }

    public String getModuleId() {
        return moduleId;
    }

    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
    }

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public String getSatisfaction() {
        return satisfaction;
    }

    public void setSatisfaction(String satisfaction) {
        this.satisfaction = satisfaction;
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getHandler() {
        return handler;
    }

    public void setHandler(String handler) {
        this.handler = handler;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getHandlerName() {
        return handlerName;
    }

    public void setHandlerName(String handlerName) {
        this.handlerName = handlerName;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
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

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getImportance() {
        return importance;
    }

    public void setImportance(String importance) {
        this.importance = importance;
    }

    public String getStageId() {
        return stageId;
    }

    public void setStageId(String stageId) {
        this.stageId = stageId;
    }

    public String getLaneId() {
        return laneId;
    }

    public void setLaneId(String laneId) {
        this.laneId = laneId;
    }

    public String getStageName() {
        return stageName;
    }

    public void setStageName(String stageName) {
        this.stageName = stageName;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getIsCollect() {
        return isCollect;
    }

    public void setIsCollect(String isCollect) {
        this.isCollect = isCollect;
    }

    public Integer getTaskType() {
        return taskType;
    }

    public void setTaskType(Integer taskType) {
        this.taskType = taskType;
    }

    public String getFaultType() {
        return faultType;
    }

    public void setFaultType(String faultType) {
        this.faultType = faultType;
    }

    public String getFaultLevel() {
        return faultLevel;
    }

    public void setFaultLevel(String faultLevel) {
        this.faultLevel = faultLevel;
    }

    public String getFixedTime() {
        return fixedTime;
    }

    public void setFixedTime(String fixedTime) {
        this.fixedTime = fixedTime;
    }

    public String getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(String closeTime) {
        this.closeTime = closeTime;
    }

    public String getFixedUid() {
        return fixedUid;
    }

    public void setFixedUid(String fixedUid) {
        this.fixedUid = fixedUid;
    }

    public String getTestUid() {
        return testUid;
    }

    public void setTestUid(String testUid) {
        this.testUid = testUid;
    }

    public String getVersionId() {
        return versionId;
    }

    public void setVersionId(String versionId) {
        this.versionId = versionId;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
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

    public String getManualCaseId() {
        return manualCaseId;
    }

    public void setManualCaseId(String manualCaseId) {
        this.manualCaseId = manualCaseId;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
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

    public String getCaseName() {
        return caseName;
    }

    public void setCaseName(String caseName) {
        this.caseName = caseName;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getSprintName() {
        return sprintName;
    }

    public void setSprintName(String sprintName) {
        this.sprintName = sprintName;
    }

    public String getRemainWorkload() {
        return remainWorkload;
    }

    public void setRemainWorkload(String remainWorkload) {
        this.remainWorkload = remainWorkload;
    }

    public String getFaultStatus() {
        return faultStatus;
    }

    public void setFaultStatus(String faultStatus) {
        this.faultStatus = faultStatus;
    }

    public String getBugId() {
        return bugId;
    }

    public void setBugId(String bugId) {
        this.bugId = bugId;
    }

    public String getPageNum() {
        return pageNum;
    }

    public void setPageNum(String pageNum) {
        this.pageNum = pageNum;
    }

    public String getPageSize() {
        return pageSize;
    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }

    public String getRootIds() {
        return rootIds;
    }

    public void setRootIds(String rootIds) {
        this.rootIds = rootIds;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreateUid() {
        return createUid;
    }

    public void setCreateUid(String createUid) {
        this.createUid = createUid;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateUid() {
        return updateUid;
    }

    public void setUpdateUid(String updateUid) {
        this.updateUid = updateUid;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIdOrTitle() {
        return idOrTitle;
    }

    public void setIdOrTitle(String idOrTitle) {
        this.idOrTitle = idOrTitle;
    }

    public String getCompletion() {
        return completion;
    }

    public void setCompletion(String completion) {
        this.completion = completion;
    }

    public List<Long> getIssueIds() {
        return issueIds;
    }

    public void setIssueIds(List<Long> issueIds) {
        this.issueIds = issueIds;
    }
}
