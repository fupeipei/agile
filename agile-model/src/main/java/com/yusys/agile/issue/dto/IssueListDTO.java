package com.yusys.agile.issue.dto;

import com.yusys.agile.sysextendfield.SysExtendFieldDetailDTO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class IssueListDTO {
    private Long issueId;

    private String title;

    private Long parentId;

    private Map issueType;

    private Map sprintId;

    //之前是long类型，改成Map对象类型，为了满足高级查询，列表展示模块名称
    private Map moduleId;

    private Map systemId;

    private Map teamId;

    private Map handler;

    public String getHandlerName() {
        return handlerName;
    }

    public void setHandlerName(String handlerName) {
        this.handlerName = handlerName;
    }

    private String handlerName;

    private Date beginDate;

    private Date endDate;

    private Integer planWorkload;

    private Integer reallyWorkload;

    private Map priority;

    private Map importance;
    // 阶段id
    private Map stageId;

    private Map laneId;

    private String stageName;

    private String projectName;

    private Long projectId;

    private Map projectIdMap;

    private Byte isCollect;

    private Map taskType;

    private Map faultType;

    private Map faultLevel;

    private Date fixedTime;

    private Date closeTime;

    private Map fixedUid;

    private Map testUid;

    private Long versionId;

    private Long caseId;

    private Date deadline;

    private String file;

    private String urgency;

    private String cause;

    private String detectedPhase;

    private Long manualCaseId;

    private Byte source;

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
    private Integer remainWorkload;

    public Map getProjectIdMap() {
        return projectIdMap;
    }

    public void setProjectIdMap(Map projectIdMap) {
        this.projectIdMap = projectIdMap;
    }

    public Map getFaultStatus() {
        return faultStatus;
    }

    public void setFaultStatus(Map faultStatus) {
        this.faultStatus = faultStatus;
    }

    public Boolean getParent() {
        return isParent;
    }

    public void setParent(Boolean parent) {
        isParent = parent;
    }

    /*=========缺陷管理增加高级搜索能力   start  string改成map==========*/
    // 和stageId对应的缺陷状态
    private Map faultStatus;
    /*=========缺陷管理增加高级搜索能力   end  string改成map==========*/

    public String getTenantCode() {
        return tenantCode;
    }

    public void setTenantCode(String tenantCode) {
        this.tenantCode = tenantCode;
    }

    private String tenantCode;

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

    public Map getCompletion() {
        return completion;
    }

    public void setCompletion(Map completion) {
        this.completion = completion;
    }

    private Map completion;

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

    private String state;

    private Byte status;

    private Map createUid;

    private Date createTime;

    private Map updateUid;

    private Date updateTime;

    private String description;

    public String getFieldList() {
        return fieldList;
    }

    public void setFieldList(String fieldList) {
        this.fieldList = fieldList;
    }

    /**
     * @Description: 自定义字段json串
     */
    private String fieldList;
    /**
     * 功能描述  是否有child
     *
     * @date 2020/4/16
     * @param null
     * @return
     */
    private Boolean isParent;

    private List<IssueListDTO> children;

    private Integer order;

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }


    public List<IssueHistoryRecordDTO> getRecords() {
        return records;
    }

    public void setRecords(List<IssueHistoryRecordDTO> records) {
        this.records = records;
    }

    public List<IssueAttachmentDTO> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<IssueAttachmentDTO> attachments) {
        this.attachments = attachments;
    }

    public List<IssueCustomFieldDTO> getCustomFieldList() {
        return customFieldList;
    }

    public void setCustomFieldList(List<IssueCustomFieldDTO> customFieldList) {
        this.customFieldList = customFieldList;
    }

    private List<IssueHistoryRecordDTO> records;

    private List<IssueAttachmentDTO> attachments = new ArrayList<>();

    private List<IssueCustomFieldDTO> customFieldList;

    //版本计划相关字段 审批状态
    private Map assessIsPass;
    //版本计划相关字段 审批结果
    private Map assessIsPassResult;
    //版本计划相关字段 是否移除
    private Map versionIsRemove;
    //版本计划名称
    private Map versionName;

    public Map getVersionName() {
        return versionName;
    }

    public void setVersionName(Map versionName) {
        this.versionName = versionName;
    }

    private List<SysExtendFieldDetailDTO> sysExtendFieldDetailList;

    public Map getVersionIsRemove() {
        return versionIsRemove;
    }

    public void setVersionIsRemove(Map versionIsRemove) {
        this.versionIsRemove = versionIsRemove;
    }

    public Map getAssessIsPassResult() {
        return assessIsPassResult;
    }

    public void setAssessIsPassResult(Map assessIsPassResult) {
        this.assessIsPassResult = assessIsPassResult;
    }

    public Map getAssessIsPass() {
        return assessIsPass;
    }

    public void setAssessIsPass(Map assessIsPass) {
        this.assessIsPass = assessIsPass;
    }

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
        this.title = title == null ? null : title.trim();
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Map getIssueType() {
        return issueType;
    }

    public void setIssueType(Map issueType) {
        this.issueType = issueType;
    }

    public Map getSprintId() {
        return sprintId;
    }

    public void setSprintId(Map sprintId) {
        this.sprintId = sprintId;
    }

    public Map getModuleId() {
        return moduleId;
    }

    public void setModuleId(Map moduleId) {
        this.moduleId = moduleId;
    }

    public Map getSystemId() {
        return systemId;
    }

    public void setSystemId(Map systemId) {
        this.systemId = systemId;
    }

    public Map getHandler() {
        return handler;
    }

    public void setHandler(Map handler) {
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

    public Map getPriority() {
        return priority;
    }

    public void setPriority(Map priority) {
        this.priority = priority;
    }

    public Map getImportance() {
        return importance;
    }

    public void setImportance(Map importance) {
        this.importance = importance;
    }

    public Map getStageId() {
        return stageId;
    }

    public void setStageId(Map stageId) {
        this.stageId = stageId;
    }

    public Map getLaneId() {
        return laneId;
    }

    public void setLaneId(Map laneId) {
        this.laneId = laneId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Map getCreateUid() {
        return createUid;
    }

    public void setCreateUid(Map createUid) {
        this.createUid = createUid;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Map getUpdateUid() {
        return updateUid;
    }

    public void setUpdateUid(Map updateUid) {
        this.updateUid = updateUid;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Map getTaskType() {
        return taskType;
    }

    public void setTaskType(Map taskType) {
        this.taskType = taskType;
    }

    public Map getFaultType() {
        return faultType;
    }

    public void setFaultType(Map faultType) {
        this.faultType = faultType;
    }

    public Map getFaultLevel() {
        return faultLevel;
    }

    public void setFaultLevel(Map faultLevel) {
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

    public Map getFixedUid() {
        return fixedUid;
    }

    public void setFixedUid(Map fixedUid) {
        this.fixedUid = fixedUid;
    }

    public Map getTestUid() {
        return testUid;
    }

    public void setTestUid(Map testUid) {
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

    public Integer getRemainWorkload() {
        return remainWorkload;
    }

    public void setRemainWorkload(Integer remainWorkload) {
        this.remainWorkload = remainWorkload;
    }

    public Boolean getIsParent() {
        return isParent;
    }

    public void setIsParent(Boolean isParent) {
        this.isParent = isParent;
    }

    public List<IssueListDTO> getChildren() {
        return children;
    }

    public void setChildren(List<IssueListDTO> children) {
        this.children = children;
    }

    public List<SysExtendFieldDetailDTO> getSysExtendFieldDetailList() {
        return sysExtendFieldDetailList;
    }

    public void setSysExtendFieldDetailList(List<SysExtendFieldDetailDTO> sysExtendFieldDetailList) {
        this.sysExtendFieldDetailList = sysExtendFieldDetailList;
    }

    public Map getTeamId() {
        return teamId;
    }

    public void setTeamId(Map teamId) {
        this.teamId = teamId;
    }
}
