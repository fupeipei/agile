package com.yusys.agile.versionmanager.dto;

import java.io.Serializable;

/**
 *    maxp
 * @Date 2020/11/11
 * @Description 导出版本关联工作项信息
 * @Return
 */
public class VersionIssueDTO implements Serializable {
    /**
     * 版本名称
     */
    private String versionName;
    /**
     * 工作项ID
     */
    private Long issueId;

    /**
     * 需求名称
     */
    private String bizName;
    /**
     * 客户需求编号
     */
    private String bizNum;
    /**
     * 局方需求编号
     */
    private String formalReqCode;
    /**
     * 审批状态
     */
    private String approvalStatus;
    /**
     * 审批结果
     */
    private String approvalResult;

    /**
     * 局方分组
     */
    private String reqGroup;

    /**
     * 局方需求负责人
     */
    private String responsiblePerson;
    /**
     * 需求业务状态
     */
    private String bizStatus;
    /**
     * 需求计划状态
     */
    private String bizPlanStates;

    /**
     * CRM状态
     */
    private String crmBizStatus;

    /**
     * BOSS状态
     */
    private String bossBizStatus;
    /**
     * 电子商务状态
     */
    private String businessBizStatus;

    /**
     * CRM测试责任人
     */
    private String crmDutyUser;
    /**
     * BOSS测试责任人
     */
    private String bossDutyUser;
    /**
     * 电子商务测试责任人
     */
    private String businessDutyUser;

    /**
     * CRM-BA
     */
    private String crmBa;
    /**
     * BOSS-BA
     */
    private String bossBa;
    /**
     * 电子商务-BA
     */
    private String businessBa;
    /**
     * BOSS部署说明
     */
    private String bossDeployDesc;
    /**
     * 是否移除
     */
    private String  versionIsRemove;

    /**
     * CRM-实际完成时间
     */
    private String crmActualFinishTime;
    /**
     * BOSS-实际完成时间
     */
    private String bossActualFinishTime;
    /**
     * 电子商务-实际完成时间
     */
    private String businessActualFinishTime;

    /**
     * CRM-联调实际完成时间
     */
    private String crmDebugActualFinishTime;
    /**
     * BOSS-联调实际完成时间
     */
    private String bossDebugActualFinishTime;
    /**
     * 电子商务-联调实际完成时间
     */
    private String businessDebugActualFinishTime;


    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public Long getIssueId() {
        return issueId;
    }

    public void setIssueId(Long issueId) {
        this.issueId = issueId;
    }

    public String getBizName() {
        return bizName;
    }

    public void setBizName(String bizName) {
        this.bizName = bizName;
    }

    public String getBizNum() {
        return bizNum;
    }

    public void setBizNum(String bizNum) {
        this.bizNum = bizNum;
    }

    public String getFormalReqCode() {
        return formalReqCode;
    }

    public void setFormalReqCode(String formalReqCode) {
        this.formalReqCode = formalReqCode;
    }

    public String getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(String approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public String getApprovalResult() {
        return approvalResult;
    }

    public void setApprovalResult(String approvalResult) {
        this.approvalResult = approvalResult;
    }

    public String getBizStatus() {
        return bizStatus;
    }

    public void setBizStatus(String bizStatus) {
        this.bizStatus = bizStatus;
    }

    public String getCrmBizStatus() {
        return crmBizStatus;
    }

    public void setCrmBizStatus(String crmBizStatus) {
        this.crmBizStatus = crmBizStatus;
    }

    public String getBossBizStatus() {
        return bossBizStatus;
    }

    public void setBossBizStatus(String bossBizStatus) {
        this.bossBizStatus = bossBizStatus;
    }

    public String getBusinessBizStatus() {
        return businessBizStatus;
    }

    public void setBusinessBizStatus(String businessBizStatus) {
        this.businessBizStatus = businessBizStatus;
    }

    public String getBizPlanStates() {
        return bizPlanStates;
    }

    public void setBizPlanStates(String bizPlanStates) {
        this.bizPlanStates = bizPlanStates;
    }

    public String getCrmDutyUser() {
        return crmDutyUser;
    }

    public void setCrmDutyUser(String crmDutyUser) {
        this.crmDutyUser = crmDutyUser;
    }

    public String getBossDutyUser() {
        return bossDutyUser;
    }

    public void setBossDutyUser(String bossDutyUser) {
        this.bossDutyUser = bossDutyUser;
    }

    public String getBusinessDutyUser() {
        return businessDutyUser;
    }

    public void setBusinessDutyUser(String businessDutyUser) {
        this.businessDutyUser = businessDutyUser;
    }

    public String getCrmBa() {
        return crmBa;
    }

    public void setCrmBa(String crmBa) {
        this.crmBa = crmBa;
    }

    public String getBossBa() {
        return bossBa;
    }

    public void setBossBa(String bossBa) {
        this.bossBa = bossBa;
    }

    public String getBusinessBa() {
        return businessBa;
    }

    public void setBusinessBa(String businessBa) {
        this.businessBa = businessBa;
    }

    public String getBossDeployDesc() {
        return bossDeployDesc;
    }

    public void setBossDeployDesc(String bossDeployDesc) {
        this.bossDeployDesc = bossDeployDesc;
    }

    public String getVersionIsRemove() {
        return versionIsRemove;
    }

    public void setVersionIsRemove(String versionIsRemove) {
        this.versionIsRemove = versionIsRemove;
    }

    public String getReqGroup() {
        return reqGroup;
    }

    public void setReqGroup(String reqGroup) {
        this.reqGroup = reqGroup;
    }

    public String getResponsiblePerson() {
        return responsiblePerson;
    }

    public void setResponsiblePerson(String responsiblePerson) {
        this.responsiblePerson = responsiblePerson;
    }

    public String getCrmActualFinishTime() {
        return crmActualFinishTime;
    }

    public void setCrmActualFinishTime(String crmActualFinishTime) {
        this.crmActualFinishTime = crmActualFinishTime;
    }

    public String getBossActualFinishTime() {
        return bossActualFinishTime;
    }

    public void setBossActualFinishTime(String bossActualFinishTime) {
        this.bossActualFinishTime = bossActualFinishTime;
    }

    public String getBusinessActualFinishTime() {
        return businessActualFinishTime;
    }

    public void setBusinessActualFinishTime(String businessActualFinishTime) {
        this.businessActualFinishTime = businessActualFinishTime;
    }

    public String getCrmDebugActualFinishTime() {
        return crmDebugActualFinishTime;
    }

    public void setCrmDebugActualFinishTime(String crmDebugActualFinishTime) {
        this.crmDebugActualFinishTime = crmDebugActualFinishTime;
    }

    public String getBossDebugActualFinishTime() {
        return bossDebugActualFinishTime;
    }

    public void setBossDebugActualFinishTime(String bossDebugActualFinishTime) {
        this.bossDebugActualFinishTime = bossDebugActualFinishTime;
    }

    public String getBusinessDebugActualFinishTime() {
        return businessDebugActualFinishTime;
    }

    public void setBusinessDebugActualFinishTime(String businessDebugActualFinishTime) {
        this.businessDebugActualFinishTime = businessDebugActualFinishTime;
    }
}
