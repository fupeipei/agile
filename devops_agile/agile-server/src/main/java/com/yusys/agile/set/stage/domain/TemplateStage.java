package com.yusys.agile.set.stage.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * @description 模板阶段类
 *  
 * @date 2020/07/26
 */
public class TemplateStage implements Serializable {

    private static final long serialVersionUID = -7044194812307544202L;

    private Long configId;

    private Long templateId;

    private Long stageId;

    private String stageName;

    private Long parentId;

    private Byte isShow;

    private Byte level;

    private Integer orderId;

    private Long maxNumber;

    private Integer stayDays;

    private String state;

    private Date createTime;

    private Long createUid;

    private Date updateTime;

    private Long updateUid;

    private String tenantCode;

    public Long getConfigId() {
        return configId;
    }

    public void setConfigId(Long configId) {
        this.configId = configId;
    }

    public Long getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
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
        this.stageName = stageName == null ? null : stageName.trim();
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Byte getIsShow() {
        return isShow;
    }

    public void setIsShow(Byte isShow) {
        this.isShow = isShow;
    }

    public Byte getLevel() {
        return level;
    }

    public void setLevel(Byte level) {
        this.level = level;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Long getMaxNumber() {
        return maxNumber;
    }

    public void setMaxNumber(Long maxNumber) {
        this.maxNumber = maxNumber;
    }

    public Integer getStayDays() {
        return stayDays;
    }

    public void setStayDays(Integer stayDays) {
        this.stayDays = stayDays;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getCreateUid() {
        return createUid;
    }

    public void setCreateUid(Long createUid) {
        this.createUid = createUid;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Long getUpdateUid() {
        return updateUid;
    }

    public void setUpdateUid(Long updateUid) {
        this.updateUid = updateUid;
    }

    public String getTenantCode() {
        return tenantCode;
    }

    public void setTenantCode(String tenantCode) {
        this.tenantCode = tenantCode == null ? null : tenantCode.trim();
    }

    @Override
    public String toString() {
        return "TemplateStage{" +
                "configId=" + configId +
                ", templateId=" + templateId +
                ", stageId=" + stageId +
                ", stageName='" + stageName + '\'' +
                ", parentId=" + parentId +
                ", isShow=" + isShow +
                ", level=" + level +
                ", orderId=" + orderId +
                ", maxNumber=" + maxNumber +
                ", stayDays=" + stayDays +
                ", state='" + state + '\'' +
                ", createTime=" + createTime +
                ", createUid=" + createUid +
                ", updateTime=" + updateTime +
                ", updateUid=" + updateUid +
                ", tenantCode='" + tenantCode + '\'' +
                '}';
    }
}