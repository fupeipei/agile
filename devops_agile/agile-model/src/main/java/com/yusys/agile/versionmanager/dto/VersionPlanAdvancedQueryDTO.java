package com.yusys.agile.versionmanager.dto;

import java.io.Serializable;
import java.util.List;

public class VersionPlanAdvancedQueryDTO implements Serializable {
    private static final long serialVersionUID = -8783838658875545224L;

    // 版本计划查询条件
    private Long versionPlanId; //版本计划主键id
    private String versionCode; // 版本编号
    private String versionName; // 版本名称
    private String planReleaseDate; // 计划发版时间
    private String planDeployDate; // 计划部署时间
    private String versionState; // 版本状态

    // 业务需求查询条件
    private String bizName; // 客户需求名称
    private String bizNum; // 客户需求编号
    private String bizScheduling; // 需求排期
    private String partaReqnum; // 局方需求编号
    private String schedulingNum; // 局方排期编号
    private String beginAskLineTime; // 要求上线时间-开始
    private String endAskLineTime; // 要求上线时间-结束
    private String bizRes; // 局方需求责任人
    private Long bizStatus; // 业务状态

    // 需要查询关联需求的版本计划ID列表
    private List<Long> versionPlanIds;
    // 需要分页查询的客户需求主键Id的值
    private List<Long> requirementIds;

    public Long getVersionPlanId() {
        return versionPlanId;
    }

    public void setVersionPlanId(Long versionPlanId) {
        this.versionPlanId = versionPlanId;
    }

    public String getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getPlanReleaseDate() {
        return planReleaseDate;
    }

    public void setPlanReleaseDate(String planReleaseDate) {
        this.planReleaseDate = planReleaseDate;
    }

    public String getPlanDeployDate() {
        return planDeployDate;
    }

    public void setPlanDeployDate(String planDeployDate) {
        this.planDeployDate = planDeployDate;
    }

    public String getVersionState() {
        return versionState;
    }

    public void setVersionState(String versionState) {
        this.versionState = versionState;
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

    public String getBizScheduling() {
        return bizScheduling;
    }

    public void setBizScheduling(String bizScheduling) {
        this.bizScheduling = bizScheduling;
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

    public String getBizRes() {
        return bizRes;
    }

    public void setBizRes(String bizRes) {
        this.bizRes = bizRes;
    }

    public String getBeginAskLineTime() {
        return beginAskLineTime;
    }

    public void setBeginAskLineTime(String beginAskLineTime) {
        this.beginAskLineTime = beginAskLineTime;
    }

    public String getEndAskLineTime() {
        return endAskLineTime;
    }

    public void setEndAskLineTime(String endAskLineTime) {
        this.endAskLineTime = endAskLineTime;
    }

    public List<Long> getVersionPlanIds() {
        return versionPlanIds;
    }

    public void setVersionPlanIds(List<Long> versionPlanIds) {
        this.versionPlanIds = versionPlanIds;
    }

    public Long getBizStatus() {
        return bizStatus;
    }

    public void setBizStatus(Long bizStatus) {
        this.bizStatus = bizStatus;
    }

    public List<Long> getRequirementIds() {
        return requirementIds;
    }

    public void setRequirementIds(List<Long> requirementIds) {
        this.requirementIds = requirementIds;
    }
}
