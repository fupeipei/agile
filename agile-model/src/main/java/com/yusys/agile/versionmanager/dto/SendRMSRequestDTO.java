package com.yusys.agile.versionmanager.dto;

import java.io.Serializable;
import java.util.Date;

public class SendRMSRequestDTO implements Serializable {
    private static final long serialVersionUID = -9025863997356458545L;

    public String getAnalyst() {
        return analyst;
    }

    public void setAnalyst(String analyst) {
        this.analyst = analyst;
    }

    public Long getBizId() {
        return bizId;
    }

    public void setBizId(Long bizId) {
        this.bizId = bizId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getBizPlanStatus() {
        return bizPlanStatus;
    }

    public void setBizPlanStatus(Long bizPlanStatus) {
        this.bizPlanStatus = bizPlanStatus;
    }

    public String getBizScheduling() {
        return bizScheduling;
    }

    public void setBizScheduling(String bizScheduling) {
        this.bizScheduling = bizScheduling;
    }

    public String getBizSource() {
        return bizSource;
    }

    public void setBizSource(String bizSource) {
        this.bizSource = bizSource;
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

    public String getMakeDepart() {
        return makeDepart;
    }

    public void setMakeDepart(String makeDepart) {
        this.makeDepart = makeDepart;
    }

    public String getMakeMan() {
        return makeMan;
    }

    public void setMakeMan(String makeMan) {
        this.makeMan = makeMan;
    }

    public String getOfficialPrescriptionGroup() {
        return officialPrescriptionGroup;
    }

    public void setOfficialPrescriptionGroup(String officialPrescriptionGroup) {
        this.officialPrescriptionGroup = officialPrescriptionGroup;
    }

    public Integer getOperateType() {
        return operateType;
    }

    public void setOperateType(Integer operateType) {
        this.operateType = operateType;
    }

    public String getPartaReqnum() {
        return partaReqnum;
    }

    public void setPartaReqnum(String partaReqnum) {
        this.partaReqnum = partaReqnum;
    }

    public String getSchedulingNum() {
        return schedulingNum;
    }

    public void setSchedulingNum(String schedulingNum) {
        this.schedulingNum = schedulingNum;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
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

    private String analyst;
    private Long bizId;
    private Long id;
    private String bizName;
    private String bizNum;
    private Long bizPlanStatus;
    private String bizScheduling;
    private String bizSource;
    private Date createTime;
    private Long createUid;
    private String makeDepart;
    private String makeMan;
    private String officialPrescriptionGroup;
    private Integer operateType;
    private String partaReqnum;
    private String schedulingNum;
    private Long status;
    private Date updateTime;
    private Long updateUid;
    private Date askLineTime;

    public Date getActualAskLineTime() {
        return actualAskLineTime;
    }

    public void setActualAskLineTime(Date actualAskLineTime) {
        this.actualAskLineTime = actualAskLineTime;
    }

    private Date actualAskLineTime;

    public Date getAskLineTime() {
        return askLineTime;
    }

    public void setAskLineTime(Date askLineTime) {
        this.askLineTime = askLineTime;
    }

    public Long getMoveToVersionPlanId() {
        return moveToVersionPlanId;
    }

    public void setMoveToVersionPlanId(Long moveToVersionPlanId) {
        this.moveToVersionPlanId = moveToVersionPlanId;
    }

    private Long moveToVersionPlanId;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    private String reason;
}
