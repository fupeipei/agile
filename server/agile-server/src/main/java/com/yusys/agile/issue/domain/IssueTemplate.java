package com.yusys.agile.issue.domain;

import java.io.Serializable;
import java.util.Date;

public class IssueTemplate implements Serializable {
    private Long issueTemplateId;

    private String templateName;

    private Byte issueType;

    private Byte apply;

    private Long projectId;

    private Date createTime;

    private Long createUid;

    private Long updateUid;

    private Date updateTime;

    private String tenantCode;

    private String description;

    private static final long serialVersionUID = 1L;

    public Long getIssueTemplateId() {
        return issueTemplateId;
    }

    public void setIssueTemplateId(Long issueTemplateId) {
        this.issueTemplateId = issueTemplateId;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName == null ? null : templateName.trim();
    }

    public Byte getIssueType() {
        return issueType;
    }

    public void setIssueType(Byte issueType) {
        this.issueType = issueType;
    }

    public Byte getApply() {
        return apply;
    }

    public void setApply(Byte apply) {
        this.apply = apply;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
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
        this.tenantCode = tenantCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }
}