package com.yusys.agile.issue.dto;

import com.yusys.agile.sysextendfield.SysExtendFieldDetailDTO;
import com.yusys.agile.sprint.dto.SprintDTO;
import com.yusys.portal.model.facade.dto.SsoUserDTO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class IssueDTO {
    private Long issueId;

    private String title;

    private Long parentId;

    private Byte issueType;

    private Long sprintId;

    private Long moduleId;

    private Long systemId;

    private String handlerAccount;

    private Integer order;
    /**
     * @Date: 11:14
     * @Description: 需求满意度
     * @Return:
     */
    private String satisfaction;

    private String systemName;

    private String systemCode;

    private String moduleName;

    private Long handler;

    private String handlerName;

    private Date beginDate;

    private Date endDate;

    private Integer planWorkload;

    private Integer reallyWorkload;

    private Byte priority;

    private Byte importance;
    // 阶段id
    private Long stageId;

    private Long laneId;

    private String stageName;

    private String projectName;

    private Long projectId;

    private Byte isCollect;

    private Integer taskType;

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
    // 缺陷来源 0YuDO 1itc 2itsm
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
    // 和stageId对应的缺陷状态
    private String faultStatus;
    // itc同步过来的bugId
    private Long bugId;
    //迭代对象
    private SprintDTO sprintDTO;

    //故事验收标准
    private String acceptanceCriteria;

    public String getAcceptanceCriteria() {
        return acceptanceCriteria;
    }

    public void setAcceptanceCriteria(String acceptanceCriteria) {
        this.acceptanceCriteria = acceptanceCriteria;
    }


    public Byte getIsArchive() {
        return isArchive;
    }

    public void setIsArchive(Byte isArchive) {
        this.isArchive = isArchive;
    }

    private Byte isArchive;

    public Byte getIsCancel() {
        return isCancel;
    }

    public void setIsCancel(Byte isCancel) {
        this.isCancel = isCancel;
    }

    private Byte isCancel;

    public List<Long> getInVersionIssueList() {
        return inVersionIssueList;
    }

    public void setInVersionIssueList(List<Long> inVersionIssueList) {
        this.inVersionIssueList = inVersionIssueList;
    }

    /**
     * 功能描述  在版本中的需求id
     *
     * @date 2021/2/2
     * @return
     */
    List<Long> inVersionIssueList;

    public List<Long> getSysExtendList() {
        return sysExtendList;
    }

    public void setSysExtendList(List<Long> sysExtendList) {
        this.sysExtendList = sysExtendList;
    }

    /**
     * 功能描述 扩展表中需求id
     *
     * @date 2021/2/2
     * @return
     */
    List<Long> sysExtendList;


    public List<SysExtendFieldDetailDTO> getSysExtendFieldDetailDTOList() {
        return sysExtendFieldDetailDTOList;
    }

    public void setSysExtendFieldDetailDTOList(List<SysExtendFieldDetailDTO> sysExtendFieldDetailDTOList) {
        this.sysExtendFieldDetailDTOList = sysExtendFieldDetailDTOList;
    }

    private List<SysExtendFieldDetailDTO> sysExtendFieldDetailDTOList;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    private String reason;

    public Integer getOperateType() {
        return operateType;
    }

    public void setOperateType(Integer operateType) {
        this.operateType = operateType;
    }

    private Integer operateType;

    public String getLaneName() {
        return laneName;
    }

    public void setLaneName(String laneName) {
        this.laneName = laneName;
    }

    private String laneName;
    private Integer pageNum;
    private Integer pageSize;
    private String rootIds;

    public Long getMoveToVersionPlanId() {
        return moveToVersionPlanId;
    }

    public void setMoveToVersionPlanId(Long moveToVersionPlanId) {
        this.moveToVersionPlanId = moveToVersionPlanId;
    }

    private Long moveToVersionPlanId;

    public String getBizNum() {
        return bizNum;
    }

    public void setBizNum(String bizNum) {
        this.bizNum = bizNum;
    }

    public String getPartaReqnum() {
        return partaReqnum;
    }

    public void setPartaReqnum(String partaReqnum) {
        this.partaReqnum = partaReqnum;
    }

    private String bizNum;//业务需求编号
    private String partaReqnum;//局方需求编号

    public Long getRecordId() {
        return recordId;
    }

    public void setRecordId(Long recordId) {
        this.recordId = recordId;
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

    private Long recordId;
    private Long versionPlanId;
    private Long bizBacklogId;

    /**
     * @Date: 2021/2/9 10:31
     * @Description: 阶段数组
     */
    private Long[] stages;

    /**
     * @Date: 9:28
     * @Description: 关联子工作项ID列表
     */
    private List<Long> listIssueIds;

    /**
     * @Date: 2021/2/13 9:41
     * @Description: 系统列表
     */
    private List<Long> systemIds;

    public String getTenantCode() {
        return tenantCode;
    }

    public void setTenantCode(String tenantCode) {
        this.tenantCode = tenantCode;
    }

    private String tenantCode;

    public List<Long> getModuleIds() {
        return moduleIds;
    }

    public void setModuleIds(List<Long> moduleIds) {
        this.moduleIds = moduleIds;
    }

    /**
     * @Date: 2021/2/124  15:39
     * @Description: 模块列表
     */
    private List<Long> moduleIds;
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

    private List<IssueDTO> children;

    //阻塞状态，0：未阻塞 1：阻塞
    private Byte blockState;
    //评审是否通过
    private Byte assessIsPass;
    //评审备注
    private String assessRemarks;
    //故事验收标准
    private List<IssueAcceptanceDTO> issueAcceptanceDTOS;

    private String state;

    private Byte status;

    private Long createUid;

    private Date createTime;

    private Long updateUid;

    private Date updateTime;

    private String description;

    private String taskTypeDesc;

    //是否超时，超时为true,否则false
    private boolean isOverTime;

    private SsoUserDTO owner;

    //缺陷个数
    private Integer faultNum;

    //用例个数
    private Integer caseNum;
    //故事id或名称
    private String storyKeyWord;

    //任务名称
    private String taskKeyWord;

    private String completion;
    /**
     * 卡片在阶段中停留天数
     */
    private Integer stayDays;
    private List<Long> handlers;

    //是否是批量导入
    private boolean isBatchCreate = false;
    /**
     * 迭代中任务的阶段状态
     */
    private List<Long> stageIds;

    private List<Integer> taskTypes;

    public List<Long> getStageIds() {
        return stageIds;
    }

    public void setStageIds(List<Long> stageIds) {
        this.stageIds = stageIds;
    }

    public List<Integer> getTaskTypes() {
        return taskTypes;
    }

    public void setTaskTypes(List<Integer> taskTypes) {
        this.taskTypes = taskTypes;
    }

    public List<Long> getHandlers() {
        return handlers;
    }

    public void setHandlers(List<Long> handlers) {
        this.handlers = handlers;
    }

    public boolean isBatchCreate() {
        return isBatchCreate;
    }

    public void setBatchCreate(boolean batchCreate) {
        isBatchCreate = batchCreate;
    }

    public Integer getStayDays() {
        return stayDays;
    }

    public void setStayDays(Integer stayDays) {
        this.stayDays = stayDays;
    }

    public String getHandlerAccount() {
        return handlerAccount;
    }

    public void setHandlerAccount(String handlerAccount) {
        this.handlerAccount = handlerAccount;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public String getSatisfaction() {
        return satisfaction;
    }

    public void setSatisfaction(String satisfaction) {
        this.satisfaction = satisfaction;
    }

    public Long[] getStages() {
        return stages;
    }

    public void setStages(Long[] stages) {
        this.stages = stages;
    }

    public List<Long> getListIssueIds() {
        return listIssueIds;
    }

    public void setListIssueIds(List<Long> listIssueIds) {
        this.listIssueIds = listIssueIds;
    }

    public List<Long> getSystemIds() {
        return systemIds;
    }

    public void setSystemIds(List<Long> systemIds) {
        this.systemIds = systemIds;
    }

    public List<IssueAcceptanceDTO> getIssueAcceptanceDTOS() {
        return issueAcceptanceDTOS;
    }

    public void setIssueAcceptanceDTOS(List<IssueAcceptanceDTO> issueAcceptanceDTOS) {
        this.issueAcceptanceDTOS = issueAcceptanceDTOS;
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

    public Byte getBlockState() {
        return blockState;
    }

    public void setBlockState(Byte blockState) {
        this.blockState = blockState;
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

    public String getRootIds() {
        return rootIds;
    }

    public void setRootIds(String rootIds) {
        this.rootIds = rootIds;
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

    public String getTaskTypeDesc() {
        return taskTypeDesc;
    }

    public void setTaskTypeDesc(String taskTypeDesc) {
        this.taskTypeDesc = taskTypeDesc;
    }

    public boolean isOverTime() {
        return isOverTime;
    }

    public void setOverTime(boolean overTime) {
        isOverTime = overTime;
    }

    public SsoUserDTO getOwner() {
        return owner;
    }

    public void setOwner(SsoUserDTO owner) {
        this.owner = owner;
    }

    public String getFieldList() {
        return fieldList;
    }

    public void setFieldList(String fieldList) {
        this.fieldList = fieldList;
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

    public List<IssueCustomFieldDTO> getCustomFieldDetailDTOList() {
        return customFieldDetailDTOList;
    }

    public void setCustomFieldDetailDTOList(List<IssueCustomFieldDTO> customFieldDetailDTOList) {
        this.customFieldDetailDTOList = customFieldDetailDTOList;
    }

    private List<IssueHistoryRecordDTO> records;

    private List<IssueAttachmentDTO> attachments = new ArrayList<>();

    private List<IssueCustomFieldDTO> customFieldDetailDTOList;

    private static final long serialVersionUID = 1L;

    public String getStoryKeyWord() {
        return storyKeyWord;
    }

    public void setStoryKeyWord(String storyKeyWord) {
        this.storyKeyWord = storyKeyWord;
    }

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
        this.state = state == null ? null : state.trim();
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Integer getTaskType() {
        return taskType;
    }

    public void setTaskType(Integer taskType) {
        this.taskType = taskType;
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

    public String getFaultStatus() {
        return faultStatus;
    }

    public void setFaultStatus(String faultStatus) {
        this.faultStatus = faultStatus;
    }

    public Boolean getIsParent() {
        return isParent;
    }

    public void setIsParent(Boolean isParent) {
        this.isParent = isParent;
    }

    public List<IssueDTO> getChildren() {
        return children;
    }

    public void setChildren(List<IssueDTO> children) {
        this.children = children;
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

    public String getHandlerName() {
        return handlerName;
    }

    public void setHandlerName(String handlerName) {
        this.handlerName = handlerName;
    }

    public Long getBugId() {
        return bugId;
    }

    public void setBugId(Long bugId) {
        this.bugId = bugId;
    }

    public Boolean getParent() {
        return isParent;
    }

    public void setParent(Boolean parent) {
        isParent = parent;
    }

    public Integer getFaultNum() {
        return faultNum;
    }

    public void setFaultNum(Integer faultNum) {
        this.faultNum = faultNum;
    }

    public Integer getCaseNum() {
        return caseNum;
    }

    public void setCaseNum(Integer caseNum) {
        this.caseNum = caseNum;
    }

    public SprintDTO getSprintDTO() {
        return sprintDTO;
    }

    public void setSprintDTO(SprintDTO sprintDTO) {
        this.sprintDTO = sprintDTO;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getTaskKeyWord() {
        return taskKeyWord;
    }

    public void setTaskKeyWord(String taskKeyWord) {
        this.taskKeyWord = taskKeyWord;
    }

    public String getSystemCode() {
        return systemCode;
    }

    public void setSystemCode(String systemCode) {
        this.systemCode = systemCode;
    }
}
