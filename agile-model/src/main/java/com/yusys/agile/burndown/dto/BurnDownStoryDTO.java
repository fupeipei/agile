package com.yusys.agile.burndown.dto;

import java.util.List;

public class BurnDownStoryDTO {
    /**
     * 计划故事数
     */
    private Integer planStory;
    /**
     * 实际故事数
     */
    private Integer actualRemainStory;
    /**
     * 每日剩余数据集合
     */
    private List<BurnDownStory> storys;

    public String getTenantCode() {
        return tenantCode;
    }

    public void setTenantCode(String tenantCode) {
        this.tenantCode = tenantCode;
    }

    private String tenantCode;

    public Integer getPlanStory() {
        return planStory;
    }

    public void setPlanStory(Integer planStory) {
        this.planStory = planStory;
    }

    public Integer getActualRemainStory() {
        return actualRemainStory;
    }

    public void setActualRemainStory(Integer actualRemainStory) {
        this.actualRemainStory = actualRemainStory;
    }

    public List<BurnDownStory> getStorys() {
        return storys;
    }

    public void setStorys(List<BurnDownStory> storys) {
        this.storys = storys;
    }
}
