package com.yusys.agile.burndown.dto;

import java.util.Date;

public class BurnDownStory {
    /**
     * 迭代时间
     */
    private Date sprintTime;
    /**
     * 每日剩余故事数
     */
    private Integer remainStory;

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

    public Integer getRemainStory() {
        return remainStory;
    }

    public void setRemainStory(Integer remainStory) {
        this.remainStory = remainStory;
    }
}
