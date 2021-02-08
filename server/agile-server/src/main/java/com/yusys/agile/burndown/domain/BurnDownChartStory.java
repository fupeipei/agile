package com.yusys.agile.burndown.domain;

import java.util.Date;

public class BurnDownChartStory {
    private Long projectId;
    private Long sprintId;
    private Date sprintTime;
    private String storyId;
    private Byte storyState;
    private Date createTime;

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

    public Long getSprintId() {
        return sprintId;
    }

    public void setSprintId(Long sprintId) {
        this.sprintId = sprintId;
    }

    public Date getSprintTime() {
        return sprintTime;
    }

    public void setSprintTime(Date sprintTime) {
        this.sprintTime = sprintTime;
    }

    public String getStoryId() {
        return storyId;
    }

    public void setStoryId(String storyId) {
        this.storyId = storyId;
    }

    public Byte getStoryState() {
        return storyState;
    }

    public void setStoryState(Byte storyState) {
        this.storyState = storyState;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
