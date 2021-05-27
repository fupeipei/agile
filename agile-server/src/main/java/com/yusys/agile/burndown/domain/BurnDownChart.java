package com.yusys.agile.burndown.domain;

import java.util.Date;

public class BurnDownChart {
    private Long projectId;
    private Long sprintId;
    private Integer remainWorkload;
    private Integer finishedWorkload;
    private Date sprintTime;
    private String taskId;
    private Byte taskState;
    private String tenantCode;
    private Date createTime;

    public String getTenantCode() {
        return tenantCode;
    }

    public void setTenantCode(String tenantCode) {
        this.tenantCode = tenantCode;
    }

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

    public Integer getRemainWorkload() {
        return remainWorkload;
    }

    public void setRemainWorkload(Integer remainWorkload) {
        this.remainWorkload = remainWorkload;
    }

    public Integer getFinishedWorkload() {
        return finishedWorkload;
    }

    public void setFinishedWorkload(Integer finishedWorkload) {
        this.finishedWorkload = finishedWorkload;
    }

    public Date getSprintTime() {
        return sprintTime;
    }

    public void setSprintTime(Date sprintTime) {
        this.sprintTime = sprintTime;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public Byte getTaskState() {
        return taskState;
    }

    public void setTaskState(Byte taskState) {
        this.taskState = taskState;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
