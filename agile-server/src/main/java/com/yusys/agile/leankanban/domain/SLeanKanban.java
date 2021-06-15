package com.yusys.agile.leankanban.domain;

import java.io.Serializable;
import java.util.Date;

public class SLeanKanban implements Serializable {
    private Long kanbanId;

    private String kanbanName;

    private String kanbanDesc;

    private Long teamId;

    private Date createTime;

    private Long createUid;

    private Long status;

    private Date updateTime;

    private Long updateUid;

    private String state;

    private String tenantCode;

    private static final long serialVersionUID = 1L;

    public Long getKanbanId() {
        return kanbanId;
    }

    public void setKanbanId(Long kanbanId) {
        this.kanbanId = kanbanId;
    }

    public String getKanbanName() {
        return kanbanName;
    }

    public void setKanbanName(String kanbanName) {
        this.kanbanName = kanbanName == null ? null : kanbanName.trim();
    }

    public String getKanbanDesc() {
        return kanbanDesc;
    }

    public void setKanbanDesc(String kanbanDesc) {
        this.kanbanDesc = kanbanDesc == null ? null : kanbanDesc.trim();
    }

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
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
}