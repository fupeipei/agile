package com.yusys.agile.set.stage.dto;

import java.io.Serializable;

/**
 * @description 阶段模板配置类
 * @date 2020/08/25
 */
public class StageTemplateConfigDTO implements Serializable {

    private static final long serialVersionUID = 2793686336765535098L;

    private Long confId;

    private Long stageId;

    private String stageName;

    private Byte level;

    private Long parentId;

    private Integer orderId;

    private String state;

    private Byte appType;

    private Byte isFinal;

    private String tenantCode;

    public Long getConfId() {
        return confId;
    }

    public void setConfId(Long confId) {
        this.confId = confId;
    }

    public Long getStageId() {
        return stageId;
    }

    public void setStageId(Long stageId) {
        this.stageId = stageId;
    }

    public String getStageName() {
        return stageName;
    }

    public void setStageName(String stageName) {
        this.stageName = stageName;
    }

    public Byte getLevel() {
        return level;
    }

    public void setLevel(Byte level) {
        this.level = level;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTenantCode() {
        return tenantCode;
    }

    public Byte getAppType() {
        return appType;
    }

    public void setAppType(Byte appType) {
        this.appType = appType;
    }

    public Byte getIsFinal() {
        return isFinal;
    }

    public void setIsFinal(Byte isFinal) {
        this.isFinal = isFinal;
    }

    public void setTenantCode(String tenantCode) {
        this.tenantCode = tenantCode;
    }

    @Override
    public String toString() {
        return "StageTemplateConfigDTO{" +
                "confId=" + confId +
                ", stageId=" + stageId +
                ", stageName='" + stageName + '\'' +
                ", level=" + level +
                ", parentId=" + parentId +
                ", orderId=" + orderId +
                ", state='" + state + '\'' +
                ", tenantCode='" + tenantCode + '\'' +
                '}';
    }
}
