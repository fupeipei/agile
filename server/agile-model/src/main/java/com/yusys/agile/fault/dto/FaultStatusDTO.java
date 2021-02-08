package com.yusys.agile.fault.dto;

/**
 * 缺陷状态DTO
 *
 *     
 * @create 2020-04-21 16:19
 */
public class FaultStatusDTO {

    private Long stageId;

    private String status;

    public Long getStageId() {
        return stageId;
    }

    public void setStageId(Long stageId) {
        this.stageId = stageId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "FaultStatusDTO{" +
                "stageId=" + stageId +
                ", status='" + status + '\'' +
                '}';
    }
}