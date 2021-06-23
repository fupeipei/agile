package com.yusys.agile.set.stage.domain;

import java.io.Serializable;
import java.util.List;

/**
 * @description 阶段模板配置类
 * @date 2020/08/24
 */
public class StageTemplateConfig implements Serializable {

    private static final long serialVersionUID = 8333002302669618767L;

    /**
     * 配置主键
     */
    private Long confId;

    /**
     * 阶段编号
     */
    private Long stageId;

    /**
     * 阶段名称
     */
    private String stageName;

    /**
     * 层级
     */
    private Byte level;

    /**
     * 父阶段编号
     */
    private Long parentId;

    /**
     * 排序序值
     */
    private Integer orderId;

    /**
     * 状态
     */
    private String state;

    /**
     * 租户编码
     */
    private String tenantCode;

    /**
     * 应用类型  （epic只应用第一阶段） 2 、feature 3、story 4 task
     */
    private Byte appType;


    /**
     * 是否终态 表示该阶段为完成类型，
     * 如果是终态在同一阶段内不允许有状态在该阶段后
     */
    private Byte isFinal;

    /**
     * 二阶段
     */
    List<StageTemplateConfig> secondStages;

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
        this.stageName = stageName == null ? null : stageName.trim();
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
        this.state = state == null ? null : state.trim();
    }

    public String getTenantCode() {
        return tenantCode;
    }

    public void setTenantCode(String tenantCode) {
        this.tenantCode = tenantCode == null ? null : tenantCode.trim();
    }

    public List<StageTemplateConfig> getSecondStages() {
        return secondStages;
    }

    public void setSecondStages(List<StageTemplateConfig> secondStages) {
        this.secondStages = secondStages;
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
    @Override
    public String toString() {
        return "StageTemplateConfig{" +
                "confId=" + confId +
                ", stageId=" + stageId +
                ", stageName='" + stageName + '\'' +
                ", level=" + level +
                ", parentId=" + parentId +
                ", orderId=" + orderId +
                ", state='" + state + '\'' +
                ", tenantCode='" + tenantCode + '\'' +
                ", secondStages=" + secondStages +
                '}';
    }
}