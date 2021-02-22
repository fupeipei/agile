package com.yusys.agile.burndown.dto;

import java.util.Date;

public class BurnDownTask {
    private Date sprintTime;
    private Integer remainTask;
    private String tenantCode;

    public String getTenantCode() {
        return tenantCode;
    }

    public void setTenantCode(String tenantCode) {
        this.tenantCode = tenantCode;
    }

    public Date getSprintTime() {
        return sprintTime;
    }

    public void setSprintTime(Date sprintTime) {
        this.sprintTime = sprintTime;
    }

    public Integer getRemainTask() {
        return remainTask;
    }

    public void setRemainTask(Integer remainTask) {
        this.remainTask = remainTask;
    }

}
