package com.yusys.agile.versionmanager.domain;

import java.io.Serializable;

/**
 * @description 项目关联类
 *  
 * @date 2020/10/14
 */
public class ProjectRelation implements Serializable {

    private static final long serialVersionUID = 3703856947659377920L;

    private Long id;

    private Long YuDOProjectId;

    private Long cmpProjectId;

    private String state;

    private String tenantCode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getYuDOProjectId() {
        return YuDOProjectId;
    }

    public void setYuDOProjectId(Long YuDOProjectId) {
        this.YuDOProjectId = YuDOProjectId;
    }

    public Long getCmpProjectId() {
        return cmpProjectId;
    }

    public void setCmpProjectId(Long cmpProjectId) {
        this.cmpProjectId = cmpProjectId;
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

    @Override
    public String toString() {
        return "ProjectRelation{" +
                "id=" + id +
                ", YuDOProjectId=" + YuDOProjectId +
                ", cmpProjectId=" + cmpProjectId +
                ", state='" + state + '\'' +
                ", tenantCode='" + tenantCode + '\'' +
                '}';
    }
}