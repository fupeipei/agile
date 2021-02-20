package com.yusys.agile.businesskanban.dto;

import java.util.Date;
/**
 * @Date: 2021/2/1
 * @Description:
 */
public class BusinessHistoryRecordDTO {
    private Long recordId;

    private String operationField;

    private Date createTime;

    private Long createUid;

    private Long businessId;

    private Byte recordType;

    private String label;

    private String tenantCode;

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

    @Override
    public String toString() {
        return "BusinessHistoryRecordDTO{" +
                "recordId=" + recordId +
                ", operationField='" + operationField + '\'' +
                ", createTime=" + createTime +
                ", createUid=" + createUid +
                ", businessId=" + businessId +
                ", recordType=" + recordType +
                ", label='" + label + '\'' +
                '}';
    }
}