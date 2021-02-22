package com.yusys.agile.issue.domain;

import java.io.Serializable;
import java.util.Date;

public class IssueRule implements Serializable {
    private Long ruleId;

    private Long projectId;

    private String tenantCode;

    private Byte category;

    private Long fromStageId;

    private Long fromLaneId;

    private Long toStageId;

    private Long toLaneId;

    private Byte idCheck;

    private Long createUid;

    private Date createTime;

    private Long updateUid;

    private Date updateTime;

    private static final long serialVersionUID = 1L;

    public Long getRuleId() {
        return ruleId;
    }

    public void setRuleId(Long ruleId) {
        this.ruleId = ruleId;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getTenantCode() {
        return tenantCode;
    }

    public void setTenantCode(String tenantCode) {
        this.tenantCode = tenantCode == null ? null : tenantCode.trim();
    }

    public Byte getCategory() {
        return category;
    }

    public void setCategory(Byte category) {
        this.category = category;
    }

    public Long getFromStageId() {
        return fromStageId;
    }

    public void setFromStageId(Long fromStageId) {
        this.fromStageId = fromStageId;
    }

    public Long getFromLaneId() {
        return fromLaneId;
    }

    public void setFromLaneId(Long fromLaneId) {
        this.fromLaneId = fromLaneId;
    }

    public Long getToStageId() {
        return toStageId;
    }

    public void setToStageId(Long toStageId) {
        this.toStageId = toStageId;
    }

    public Long getToLaneId() {
        return toLaneId;
    }

    public void setToLaneId(Long toLaneId) {
        this.toLaneId = toLaneId;
    }

    public Byte getIdCheck() {
        return idCheck;
    }

    public void setIdCheck(Byte idCheck) {
        this.idCheck = idCheck;
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
}