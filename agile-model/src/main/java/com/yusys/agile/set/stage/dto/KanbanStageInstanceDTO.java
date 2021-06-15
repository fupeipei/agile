package com.yusys.agile.set.stage.dto;

import com.yusys.agile.issue.dto.IssueDTO;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class KanbanStageInstanceDTO implements Serializable {

    private static final long serialVersionUID = 4295287985522055786L;

    private Long instanceId;

    private Long stageId;

    private String stageName;

    private Long parentId;

    private Byte typeId;

    private Long projectId;

    private Integer orderId;

    private Integer maxNumbers;

    private Integer stayDays;

    private String admittanceRule;

    private Byte isShow;

    private Byte level;

    private String state;

    private Byte enableCancel;

    private Long createUid;

    private Date createTime;

    private Long updateUid;

    private Date updateTime;

    private List<IssueDTO> issueDTOS;

    private Boolean hasNext;

    private String tenantCode;

    /**
     * 负责人
     */
    private Long principal;

    /**
     * 模板编号
     */
    private Long templateId;

    /**
     * 团队id
     */
    private Long teamId;

    /**
     * 看板id
     */
    private Long  kanbanId;

    /**
     * 阶段类型
     */
    private Byte stageType;

    /**
     * 二级阶段信息
     */
    private List<KanbanStageInstanceDTO> secondStages;

    public Long getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(Long instanceId) {
        this.instanceId = instanceId;
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

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Byte getTypeId() {
        return typeId;
    }

    public void setTypeId(Byte typeId) {
        this.typeId = typeId;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getMaxNumbers() {
        return maxNumbers;
    }

    public void setMaxNumbers(Integer maxNumbers) {
        this.maxNumbers = maxNumbers;
    }

    public Integer getStayDays() {
        return stayDays;
    }

    public void setStayDays(Integer stayDays) {
        this.stayDays = stayDays;
    }

    public String getAdmittanceRule() {
        return admittanceRule;
    }

    public void setAdmittanceRule(String admittanceRule) {
        this.admittanceRule = admittanceRule;
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Byte getEnableCancel() {
        return enableCancel;
    }

    public void setEnableCancel(Byte enableCancel) {
        this.enableCancel = enableCancel;
    }

    public Long getCreateUid() {
        return createUid;
    }

    public void setCreateUid(Long createUid) {
        this.createUid = createUid;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getUpdateUid() {
        return updateUid;
    }

    public void setUpdateUid(Long updateUid) {
        this.updateUid = updateUid;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public List<IssueDTO> getIssueDTOS() {
        return issueDTOS;
    }

    public void setIssueDTOS(List<IssueDTO> issueDTOS) {
        this.issueDTOS = issueDTOS;
    }

    public Boolean getHasNext() {
        return hasNext;
    }

    public void setHasNext(Boolean hasNext) {
        this.hasNext = hasNext;
    }

    public String getTenantCode() {
        return tenantCode;
    }

    public void setTenantCode(String tenantCode) {
        this.tenantCode = tenantCode;
    }

    public Long getPrincipal() {
        return principal;
    }

    public void setPrincipal(Long principal) {
        this.principal = principal;
    }

    public Long getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
    }


    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public Long getKanbanId() {
        return kanbanId;
    }

    public void setKanbanId(Long kanbanId) {
        this.kanbanId = kanbanId;
    }

    public Byte getStageType() {
        return stageType;
    }

    public void setStageType(Byte stageType) {
        this.stageType = stageType;
    }

    public List<KanbanStageInstanceDTO> getSecondStages() {
        return secondStages;
    }

    public void setSecondStages(List<KanbanStageInstanceDTO> secondStages) {
        this.secondStages = secondStages;
    }


    @Override
    public String toString() {
        return "KanbanStageInstanceDTO{" +
                "instanceId=" + instanceId +
                ", stageId=" + stageId +
                ", stageName='" + stageName + '\'' +
                ", parentId=" + parentId +
                ", typeId=" + typeId +
                ", projectId=" + projectId +
                ", orderId=" + orderId +
                ", maxNumbers=" + maxNumbers +
                ", stayDays=" + stayDays +
                ", admittanceRule='" + admittanceRule + '\'' +
                ", isShow=" + isShow +
                ", level=" + level +
                ", state='" + state + '\'' +
                ", enableCancel=" + enableCancel +
                ", createUid=" + createUid +
                ", createTime=" + createTime +
                ", updateUid=" + updateUid +
                ", updateTime=" + updateTime +
                ", issueDTOS=" + issueDTOS +
                ", hasNext=" + hasNext +
                ", tenantCode='" + tenantCode + '\'' +
                ", principal=" + principal +
                ", templateId=" + templateId +
                '}';
    }

}
