package com.yusys.agile.burndown.dto;

import java.util.List;

public class BurnDownTaskDTO {
    private Integer planTask;
    private Integer actualRemainTask;
    private List<BurnDownTask> tasks;

    public String getTenantCode() {
        return tenantCode;
    }

    public void setTenantCode(String tenantCode) {
        this.tenantCode = tenantCode;
    }

    private String tenantCode;

    public Integer getPlanTask() {
        return planTask;
    }

    public void setPlanTask(Integer planTask) {
        this.planTask = planTask;
    }

    public Integer getActualRemainTask() {
        return actualRemainTask;
    }

    public void setActualRemainTask(Integer actualRemainTask) {
        this.actualRemainTask = actualRemainTask;
    }

    public List<BurnDownTask> getTasks() {
        return tasks;
    }

    public void setTasks(List<BurnDownTask> tasks) {
        this.tasks = tasks;
    }

}
