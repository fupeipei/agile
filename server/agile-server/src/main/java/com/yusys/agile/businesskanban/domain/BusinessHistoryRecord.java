package com.yusys.agile.businesskanban.domain;

import java.io.Serializable;
import java.util.Date;

public class BusinessHistoryRecord implements Serializable {
    private Long recordId;

    private String operationField;

    private Date createTime;

    private Long createUid;

    private Long businessId;

    private Byte recordType;

    private String label;

    private String tenantCode;

    private static final long serialVersionUID = 1L;

    public BusinessHistoryRecord(String operationField, Long businessId, Long createUid,Byte recordType){
        this.operationField = operationField;
        this.createUid = createUid;
        this.businessId = businessId;
        this.recordType = recordType;
    }

    public BusinessHistoryRecord(){

    }


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

    public Long getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Long businessId) {
        this.businessId = businessId;
    }

    public Byte getRecordType() {
        return recordType;
    }

    public void setRecordType(Byte recordType) {
        this.recordType = recordType;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label == null ? null : label.trim();
    }

    public String getTenantCode() {
        return tenantCode;
    }

    public void setTenantCode(String tenantCode) {
        this.tenantCode = tenantCode;
    }
}