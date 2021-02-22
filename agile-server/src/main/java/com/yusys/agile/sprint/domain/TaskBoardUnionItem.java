package com.yusys.agile.sprint.domain;

import java.util.Date;

/**
 * @Date 2020/4/29
 * @Description 看板查询故事及任务信息
 */
public class TaskBoardUnionItem {
    private Long storyId;
    private String storyName;
    private String storyDesc;
    private Byte storyLevel;
    private String storyState;
    private Long moduleId;
    private String backlogName;
    private Long sprintId;
    private Long taskId;
    private String taskName;
    private String taskDesc;
    private Integer taskType;
    private String taskState;
    private Long taskOwner;
    private Integer planWorkload;
    private Integer remainWorkload;
    private Date startTime;
    private Date endTime;
    private Date taskCreateTime;
    private Long storyStageId;
    private Long taskStageId;

    private Date storyCreateTime;

    private Byte blockState;

    public String getTenantCode() {
        return tenantCode;
    }

    public void setTenantCode(String tenantCode) {
        this.tenantCode = tenantCode;
    }

    private String tenantCode;

    public Byte getBlockState() {
        return blockState;
    }

    public void setBlockState(Byte blockState) {
        this.blockState = blockState;
    }

    public Date getStoryCreateTime() {
        return storyCreateTime;
    }

    public void setStoryCreateTime(Date storyCreateTime) {
        this.storyCreateTime = storyCreateTime;
    }

    public Long getStoryId() {
        return storyId;
    }

    public void setStoryId(Long storyId) {
        this.storyId = storyId;
    }

    public String getStoryName() {
        return storyName;
    }

    public void setStoryName(String storyName) {
        this.storyName = storyName;
    }

    public String getStoryDesc() {
        return storyDesc;
    }

    public void setStoryDesc(String storyDesc) {
        this.storyDesc = storyDesc;
    }

    public Byte getStoryLevel() {
        return storyLevel;
    }

    public void setStoryLevel(Byte storyLevel) {
        this.storyLevel = storyLevel;
    }

    public String getStoryState() {
        return storyState;
    }

    public void setStoryState(String storyState) {
        this.storyState = storyState;
    }

    public Long getModuleId() {
        return moduleId;
    }

    public void setModuleId(Long moduleId) {
        this.moduleId = moduleId;
    }

    public String getBacklogName() {
        return backlogName;
    }

    public void setBacklogName(String backlogName) {
        this.backlogName = backlogName;
    }

    public Long getSprintId() {
        return sprintId;
    }

    public void setSprintId(Long sprintId) {
        this.sprintId = sprintId;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskDesc() {
        return taskDesc;
    }

    public void setTaskDesc(String taskDesc) {
        this.taskDesc = taskDesc;
    }

    public Integer getTaskType() {
        return taskType;
    }

    public void setTaskType(Integer taskType) {
        this.taskType = taskType;
    }

    public String getTaskState() {
        return taskState;
    }

    public void setTaskState(String taskState) {
        this.taskState = taskState;
    }

    public Long getTaskOwner() {
        return taskOwner;
    }

    public void setTaskOwner(Long taskOwner) {
        this.taskOwner = taskOwner;
    }

    public Integer getPlanWorkload() {
        return planWorkload;
    }

    public void setPlanWorkload(Integer planWorkload) {
        this.planWorkload = planWorkload;
    }

    public Integer getRemainWorkload() {
        return remainWorkload;
    }

    public void setRemainWorkload(Integer remainWorkload) {
        this.remainWorkload = remainWorkload;
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

    public Date getTaskCreateTime() {
        return taskCreateTime;
    }

    public void setTaskCreateTime(Date taskCreateTime) {
        this.taskCreateTime = taskCreateTime;
    }

    public Long getStoryStageId() {
        return storyStageId;
    }

    public void setStoryStageId(Long storyStageId) {
        this.storyStageId = storyStageId;
    }

    public Long getTaskStageId() {
        return taskStageId;
    }

    public void setTaskStageId(Long taskStageId) {
        this.taskStageId = taskStageId;
    }
}
