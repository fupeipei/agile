package com.yusys.agile.burndown.dto;

import java.util.Date;

public class BurnDownWorkloadDTO {
    private Date sprintTime;
    private Integer remainWorkload;

    public String getTenantCode() {
        return tenantCode;
    }

    public void setTenantCode(String tenantCode) {
        this.tenantCode = tenantCode;
    }

    private String tenantCode;

    public Date getSprintTime() {
        return sprintTime;
    }

    public void setSprintTime(Date sprintTime) {
        this.sprintTime = sprintTime;
    }

    public Integer getRemainWorkload() {
        return remainWorkload;
    }

    public void setRemainWorkload(Integer remainWorkload) {
        this.remainWorkload = remainWorkload;
    }

}
