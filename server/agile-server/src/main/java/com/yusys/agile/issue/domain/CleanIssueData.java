package com.yusys.agile.issue.domain;

import java.io.Serializable;

public class CleanIssueData implements Serializable {
    private Long id;

    private Long issueId;

    private String bizName;

    private String bizNum;

    private String bizType;

    private String bizSource;

    private String bizRes;

    private String bizScheduling;

    private String partaReqNum;

    private String schedulingNum;

    private String makeMan;

    private String makeDepart;

    private String reqGroup;

    private String bizPlanStatus;

    private Long bizStatus;

    private String askLineTime;

    private String issueTime;

    private String closeTime;

    private String systemName;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        this.bizName = bizName == null ? null : bizName.trim();
    }

    public String getBizNum() {
        return bizNum;
    }

    public void setBizNum(String bizNum) {
        this.bizNum = bizNum == null ? null : bizNum.trim();
    }

    public String getBizType() {
        return bizType;
    }

    public void setBizType(String bizType) {
        this.bizType = bizType;
    }

    public String getBizSource() {
        return bizSource;
    }

    public void setBizSource(String bizSource) {
        this.bizSource = bizSource;
    }

    public String getBizRes() {
        return bizRes;
    }

    public void setBizRes(String bizRes) {
        this.bizRes = bizRes == null ? null : bizRes.trim();
    }

    public String getBizScheduling() {
        return bizScheduling;
    }

    public void setBizScheduling(String bizScheduling) {
        this.bizScheduling = bizScheduling == null ? null : bizScheduling.trim();
    }

    public String getPartaReqNum() {
        return partaReqNum;
    }

    public void setPartaReqNum(String partaReqNum) {
        this.partaReqNum = partaReqNum == null ? null : partaReqNum.trim();
    }

    public String getSchedulingNum() {
        return schedulingNum;
    }

    public void setSchedulingNum(String schedulingNum) {
        this.schedulingNum = schedulingNum == null ? null : schedulingNum.trim();
    }

    public String getMakeMan() {
        return makeMan;
    }

    public void setMakeMan(String makeMan) {
        this.makeMan = makeMan == null ? null : makeMan.trim();
    }

    public String getMakeDepart() {
        return makeDepart;
    }

    public void setMakeDepart(String makeDepart) {
        this.makeDepart = makeDepart;
    }

    public String getReqGroup() {
        return reqGroup;
    }

    public void setReqGroup(String reqGroup) {
        this.reqGroup = reqGroup == null ? null : reqGroup.trim();
    }

    public String getBizPlanStatus() {
        return bizPlanStatus;
    }

    public void setBizPlanStatus(String bizPlanStatus) {
        this.bizPlanStatus = bizPlanStatus;
    }

    public Long getBizStatus() {
        return bizStatus;
    }

    public void setBizStatus(Long bizStatus) {
        this.bizStatus = bizStatus;
    }

    public String getAskLineTime() {
        return askLineTime;
    }

    public void setAskLineTime(String askLineTime) {
        this.askLineTime = askLineTime;
    }

    public String getIssueTime() {
        return issueTime;
    }

    public void setIssueTime(String issueTime) {
        this.issueTime = issueTime;
    }

    public String getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(String closeTime) {
        this.closeTime = closeTime == null ? null : closeTime.trim();
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName == null ? null : systemName.trim();
    }
}