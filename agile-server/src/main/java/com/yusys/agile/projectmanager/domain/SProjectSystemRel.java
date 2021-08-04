package com.yusys.agile.projectmanager.domain;

import java.io.Serializable;
import java.util.Date;

public class SProjectSystemRel implements Serializable {
    private Long projectSystemRelId;

    private Long projectId;

    private Long relSystemId;

    private Long createUid;

    private Date createTime;

    private Long updateUid;

    private Date updateTime;

    private String tenantCode;

    private String state;

    private static final long serialVersionUID = 1L;

    public Long getProjectSystemRelId() {
        return projectSystemRelId;
    }

    public void setProjectSystemRelId(Long projectSystemRelId) {
        this.projectSystemRelId = projectSystemRelId;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Long getRelSystemId() {
        return relSystemId;
    }

    public void setRelSystemId(Long relSystemId) {
        this.relSystemId = relSystemId;
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

    public String getTenantCode() {
        return tenantCode;
    }

    public void setTenantCode(String tenantCode) {
        this.tenantCode = tenantCode == null ? null : tenantCode.trim();
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }
}