package com.yusys.agile.issuev3.domain;

import java.io.Serializable;
import java.util.Date;

public class SIssueHistoryRecord implements Serializable {
    private Long recordId;

    private String operationField;

    private Long issueId;

    private Byte recordType;

    private Byte isCustom;

    private String label;

    private Date createTime;

    private Long createUid;

    private String tenantCode;

    private static final long serialVersionUID = 1L;

    public Long getRecordId() {
        return recordId;
    }

    public void setRecordId(Long recordId) {
        this.recordId = recordId;
    }

    public String getOperationField() {
        return operationField;
    }

    public void setOperationField(String operationField) {
        this.operationField = operationField == null ? null : operationField.trim();
    }

    public Long getIssueId() {
        return issueId;
    }

    public void setIssueId(Long issueId) {
        this.issueId = issueId;
    }

    public Byte getRecordType() {
        return recordType;
    }

    public void setRecordType(Byte recordType) {
        this.recordType = recordType;
    }

    public Byte getIsCustom() {
        return isCustom;
    }

    public void setIsCustom(Byte isCustom) {
        this.isCustom = isCustom;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label == null ? null : label.trim();
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

    public String getTenantCode() {
        return tenantCode;
    }

    public void setTenantCode(String tenantCode) {
        this.tenantCode = tenantCode == null ? null : tenantCode.trim();
    }
}