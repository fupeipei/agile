package com.yusys.agile.burndown.dto;

import java.util.List;

public class BurnDownChartDTO {
    private Integer planWorkload;// 预计剩余工作量
    private Integer actualRemainWorkload;// 实际剩余工作量
    private List<BurnDownWorkloadDTO> workloads;

    public String getTenantCode() {
        return tenantCode;
    }

    public void setTenantCode(String tenantCode) {
        this.tenantCode = tenantCode;
    }

    private String tenantCode;

    public Integer getPlanWorkload() {
        return planWorkload;
    }

    public void setPlanWorkload(Integer planWorkload) {
        this.planWorkload = planWorkload;
    }

    public Integer getActualRemainWorkload() {
        return actualRemainWorkload;
    }

    public void setActualRemainWorkload(Integer actualRemainWorkload) {
        this.actualRemainWorkload = actualRemainWorkload;
    }

    public List<BurnDownWorkloadDTO> getWorkloads() {
        return workloads;
    }

    public void setWorkloads(List<BurnDownWorkloadDTO> workloads) {
        this.workloads = workloads;
    }

}
