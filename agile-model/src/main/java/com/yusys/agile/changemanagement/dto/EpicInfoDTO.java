package com.yusys.agile.changemanagement.dto;

/**
 * 外部系统调用返回epic封装信息
 *
 * @create 2020-11-30 09:44
 */
public class EpicInfoDTO {

    private Long epicId;

    private String title;

    private String state;

    /**
     * 是否上线 0否 1是
     */
    private String hasReleased;

    /**
     * 正式需求编号（局方）
     */
    private String formalReqCode;

    /**
     * 客户需求编码
     */
    private String bizNum;

    /**
     * 局方排期编号
     */
    private String businessReqCode;

    /**
     * 需求负责人
     */
    private String responsiblePerson;

    /**
     * 需求负责人id
     */
    private String responsiblePersonId;
    /**
     * 局方分组
     */
    private String reqGroup;
    /**
     * 局方分组名
     * 001:个人业务组
     * 002:集团业务组
     * 003:基础服务组
     * 004:电子稽核组
     * 005:规划建设组
     * 012:IT支撑室需求组1组
     * 013:系统运维组
     * 023:IT支撑室需求组2组
     * 024:业务运维组
     * 025:服务支撑组
     * 050:话管需求组
     */
    private String reqGroupName;

    /**
     * 需求计划状态
     */
    private String planStates;

    /**
     * 需求计划状态名
     * 8880需求取消
     * 8881需求挂起
     * 8882需求取消挂起
     * 8883延期
     * 8884无需部署
     * 8888:按期
     */
    private String planStatesName;

    /**
     * 部署批次
     */
    private String planDeployDate;
    /**
     * 信息部计划上线时间
     */
    private String onlineBatchTime;

    /**
     * 需求实际上线时间
     */
    private String actualOnlineTime;

    /**
     * 需求上线版本批次
     */
    private String planOnlineBatch;

    /**
     * 系统相关性
     */
    private String relatedSystem;

    /**
     * 系统相关性名
     * 1:CRM
     * 2:NGBOSS
     * 3:电子商务系统
     * 8:ESOP
     * 12:渠道管理系统
     * 33:APP客户端
     */
    private String relatedSystemName;

    /**
     * 是否需求交维：0是，1否
     */
    private String ifTm;
    /**
     * 交维类型（一级）
     * 0:评审纳入
     * 1:需求告知
     */
    private String interType;
    /**
     * 交维类型（一级）名
     * 0:评审纳入
     * 1:需求告知
     */
    private String interTypeName;
    /**
     * 交维子类型（二级）
     */
    private String subInterType;

    /**
     * 交维子类型（二级）名
     * 1001：业务影响判断-新增业务流程
     * 1002：业务影响判断-原有流程变更
     * 1003：系统影响-新增或变更接口、服务、功能
     * 1004：系统影响-客服弹屏
     * 1005：系统影响-影响集团考核
     * 1006：系统影响-新增库表、运维影响-原有库表结构变更
     * 1007：运维影响-新增MRT
     * 1008：运维影响-MRT变更（逻辑变更)
     * 1009：应用性能影响-预计影响性能的促销
     */
    private String subInterTypeName;

    /**
     * 部署类型 0：热部署，1：临时部署，3常规部署
     */
    private String type;

    /**
     * 部署类型名 0：热部署，1：临时部署，3常规部署
     */
    private String typeName;

    /**
     * 是否是重点需求√ 0是 ，1：否
     */
    private String ifKey;

    public Long getEpicId() {
        return epicId;
    }

    public void setEpicId(Long epicId) {
        this.epicId = epicId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFormalReqCode() {
        return formalReqCode;
    }

    public void setFormalReqCode(String formalReqCode) {
        this.formalReqCode = formalReqCode;
    }

    public String getBizNum() {
        return bizNum;
    }

    public void setBizNum(String bizNum) {
        this.bizNum = bizNum;
    }

    public String getBusinessReqCode() {
        return businessReqCode;
    }

    public void setBusinessReqCode(String businessReqCode) {
        this.businessReqCode = businessReqCode;
    }

    public String getResponsiblePerson() {
        return responsiblePerson;
    }

    public void setResponsiblePerson(String responsiblePerson) {
        this.responsiblePerson = responsiblePerson;
    }

    public String getReqGroup() {
        return reqGroup;
    }

    public void setReqGroup(String reqGroup) {
        this.reqGroup = reqGroup;
    }

    public String getReqGroupName() {
        return reqGroupName;
    }

    public void setReqGroupName(String reqGroupName) {
        this.reqGroupName = reqGroupName;
    }

    public String getPlanStates() {
        return planStates;
    }

    public void setPlanStates(String planStates) {
        this.planStates = planStates;
    }

    public String getPlanStatesName() {
        return planStatesName;
    }

    public void setPlanStatesName(String planStatesName) {
        this.planStatesName = planStatesName;
    }

    public String getPlanDeployDate() {
        return planDeployDate;
    }

    public void setPlanDeployDate(String planDeployDate) {
        this.planDeployDate = planDeployDate;
    }

    public String getOnlineBatchTime() {
        return onlineBatchTime;
    }

    public void setOnlineBatchTime(String onlineBatchTime) {
        this.onlineBatchTime = onlineBatchTime;
    }

    public String getActualOnlineTime() {
        return actualOnlineTime;
    }

    public void setActualOnlineTime(String actualOnlineTime) {
        this.actualOnlineTime = actualOnlineTime;
    }

    public String getPlanOnlineBatch() {
        return planOnlineBatch;
    }

    public void setPlanOnlineBatch(String planOnlineBatch) {
        this.planOnlineBatch = planOnlineBatch;
    }

    public String getRelatedSystem() {
        return relatedSystem;
    }

    public void setRelatedSystem(String relatedSystem) {
        this.relatedSystem = relatedSystem;
    }

    public String getRelatedSystemName() {
        return relatedSystemName;
    }

    public void setRelatedSystemName(String relatedSystemName) {
        this.relatedSystemName = relatedSystemName;
    }

    public String getIfTm() {
        return ifTm;
    }

    public void setIfTm(String ifTm) {
        this.ifTm = ifTm;
    }

    public String getInterType() {
        return interType;
    }

    public void setInterType(String interType) {
        this.interType = interType;
    }

    public String getInterTypeName() {
        return interTypeName;
    }

    public void setInterTypeName(String interTypeName) {
        this.interTypeName = interTypeName;
    }

    public String getSubInterType() {
        return subInterType;
    }

    public void setSubInterType(String subInterType) {
        this.subInterType = subInterType;
    }

    public String getSubInterTypeName() {
        return subInterTypeName;
    }

    public void setSubInterTypeName(String subInterTypeName) {
        this.subInterTypeName = subInterTypeName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getIfKey() {
        return ifKey;
    }

    public void setIfKey(String ifKey) {
        this.ifKey = ifKey;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getResponsiblePersonId() {
        return responsiblePersonId;
    }

    public void setResponsiblePersonId(String responsiblePersonId) {
        this.responsiblePersonId = responsiblePersonId;
    }

    public String getHasReleased() {
        return hasReleased;
    }

    public void setHasReleased(String hasReleased) {
        this.hasReleased = hasReleased;
    }

    @Override
    public String toString() {
        return "EpicInfoDTO{" +
                "epicId=" + epicId +
                ", title='" + title + '\'' +
                ", state='" + state + '\'' +
                ", hasReleased='" + hasReleased + '\'' +
                ", formalReqCode='" + formalReqCode + '\'' +
                ", bizNum='" + bizNum + '\'' +
                ", businessReqCode='" + businessReqCode + '\'' +
                ", responsiblePerson='" + responsiblePerson + '\'' +
                ", responsiblePersonId='" + responsiblePersonId + '\'' +
                ", reqGroup='" + reqGroup + '\'' +
                ", reqGroupName='" + reqGroupName + '\'' +
                ", planStates='" + planStates + '\'' +
                ", planStatesName='" + planStatesName + '\'' +
                ", planDeployDate='" + planDeployDate + '\'' +
                ", onlineBatchTime='" + onlineBatchTime + '\'' +
                ", actualOnlineTime='" + actualOnlineTime + '\'' +
                ", planOnlineBatch='" + planOnlineBatch + '\'' +
                ", relatedSystem='" + relatedSystem + '\'' +
                ", relatedSystemName='" + relatedSystemName + '\'' +
                ", ifTm='" + ifTm + '\'' +
                ", interType='" + interType + '\'' +
                ", interTypeName='" + interTypeName + '\'' +
                ", subInterType='" + subInterType + '\'' +
                ", subInterTypeName='" + subInterTypeName + '\'' +
                ", type='" + type + '\'' +
                ", typeName='" + typeName + '\'' +
                ", ifKey='" + ifKey + '\'' +
                '}';
    }
}