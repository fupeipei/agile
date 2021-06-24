package com.yusys.agile.issue.domain;

import java.util.List;

/**
 * :
 *
 * @Date: 2021/2/16
 * @Description: 高级搜索   paramter
 */
public class IssueRecord {
    private Long projectId;
    private Long issueId;
    private String title;
    private FieldJsonType issueTypes;
    private FieldJsonType prioritys;
    private FieldJsonType importances;
    private FieldJsonType completions;
    private FieldJsonType stageIds;
    private FieldJsonType faultLevels;
    private FieldJsonType faultTypes;
    private FieldJsonType orders;
    private FieldJsonType sprintIds;
    private FieldJsonType systemIds;
    private FieldJsonType teamIds;
    private FieldJsonType moduleIds;
    private FieldJsonType fixedUids;
    private FieldJsonType testUids;
    private FieldJsonType createUids;
    private FieldJsonType updateUids;
    private FieldJsonType handlers;
    private FieldJsonType createTime;
    private FieldJsonType beginDate;
    private FieldJsonType endDate;
    private FieldJsonType updateTime;
    private String BAPerson;

    public String getBAPerson() {
        return BAPerson;
    }

    public void setBAPerson(String BAPerson) {
        this.BAPerson = BAPerson;
    }

    public String getQueryFlag() {
        return queryFlag;
    }

    public void setQueryFlag(String queryFlag) {
        this.queryFlag = queryFlag;
    }

    private String queryFlag;
    private Integer pageNum;
    private Integer pageSize;
    private Integer form;
    private List<Long> issueIds;

    //任务查询加上“阻塞中”状态   start
    private Byte blockState;
    //任务查询加上“阻塞中”状态   end

    public Byte getBlockState() {
        return blockState;
    }

    public void setBlockState(Byte blockState) {
        this.blockState = blockState;
    }

    public String getTenantCode() {
        return tenantCode;
    }

    public void setTenantCode(String tenantCode) {
        this.tenantCode = tenantCode;
    }

    private String tenantCode;

    public List<Long> getIssueIds() {
        return issueIds;
    }

    public void setIssueIds(List<Long> issueIds) {
        this.issueIds = issueIds;
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

    public int getFrom() {
        return pageSize * (pageNum - 1);
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
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
        this.title = title;
    }

    public FieldJsonType getIssueTypes() {
        return issueTypes;
    }

    public void setIssueTypes(FieldJsonType issueTypes) {
        this.issueTypes = issueTypes;
    }

    public FieldJsonType getPrioritys() {
        return prioritys;
    }

    public void setPrioritys(FieldJsonType prioritys) {
        this.prioritys = prioritys;
    }

    public FieldJsonType getImportances() {
        return importances;
    }

    public void setImportances(FieldJsonType importances) {
        this.importances = importances;
    }

    public FieldJsonType getCompletions() {
        return completions;
    }

    public void setCompletions(FieldJsonType completions) {
        this.completions = completions;
    }

    public FieldJsonType getStageIds() {
        return stageIds;
    }

    public void setStageIds(FieldJsonType stageIds) {
        this.stageIds = stageIds;
    }

    public FieldJsonType getFaultLevels() {
        return faultLevels;
    }

    public void setFaultLevels(FieldJsonType faultLevels) {
        this.faultLevels = faultLevels;
    }

    public FieldJsonType getSystemIds() {
        return systemIds;
    }

    public void setSystemIds(FieldJsonType systemIds) {
        this.systemIds = systemIds;
    }

    public FieldJsonType getFaultTypes() {
        return faultTypes;
    }

    public void setFaultTypes(FieldJsonType faultTypes) {
        this.faultTypes = faultTypes;
    }

    public FieldJsonType getOrders() {
        return orders;
    }

    public void setOrders(FieldJsonType orders) {
        this.orders = orders;
    }

    public FieldJsonType getSprintIds() {
        return sprintIds;
    }

    public void setSprintIds(FieldJsonType sprintIds) {
        this.sprintIds = sprintIds;
    }

    public FieldJsonType getModuleIds() {
        return moduleIds;
    }

    public void setModuleIds(FieldJsonType moduleIds) {
        this.moduleIds = moduleIds;
    }

    public FieldJsonType getFixedUids() {
        return fixedUids;
    }

    public void setFixedUids(FieldJsonType fixedUids) {
        this.fixedUids = fixedUids;
    }

    public FieldJsonType getTestUids() {
        return testUids;
    }

    public void setTestUids(FieldJsonType testUids) {
        this.testUids = testUids;
    }

    public FieldJsonType getCreateUids() {
        return createUids;
    }

    public void setCreateUids(FieldJsonType createUids) {
        this.createUids = createUids;
    }

    public FieldJsonType getUpdateUids() {
        return updateUids;
    }

    public void setUpdateUids(FieldJsonType updateUids) {
        this.updateUids = updateUids;
    }

    public FieldJsonType getHandlers() {
        return handlers;
    }

    public void setHandlers(FieldJsonType handlers) {
        this.handlers = handlers;
    }

    public FieldJsonType getCreateTime() {
        return createTime;
    }

    public void setCreateTime(FieldJsonType createTime) {
        this.createTime = createTime;
    }

    public FieldJsonType getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(FieldJsonType beginDate) {
        this.beginDate = beginDate;
    }

    public FieldJsonType getEndDate() {
        return endDate;
    }

    public void setEndDate(FieldJsonType endDate) {
        this.endDate = endDate;
    }

    public FieldJsonType getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(FieldJsonType updateTime) {
        this.updateTime = updateTime;
    }

    public FieldJsonType getTeamIds() {
        return teamIds;
    }

    public void setTeamIds(FieldJsonType teamIds) {
        this.teamIds = teamIds;
    }
}
