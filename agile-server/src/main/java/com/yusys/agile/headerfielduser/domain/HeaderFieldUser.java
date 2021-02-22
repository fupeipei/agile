package com.yusys.agile.headerfielduser.domain;

import java.io.Serializable;

public class HeaderFieldUser implements Serializable {
    private Long headerUserId;

    private Long fieldId;

    private Long userId;

    private Long projectId;

    private Integer orderNo;

    private Byte fieldType;

    private Byte apply;


    private Byte category;

    private Byte isFilter;

    private String tenantCode;

    public Byte getIsFilter() {
        return isFilter;
    }

    public void setIsFilter(Byte isFilter) {
        this.isFilter = isFilter;
    }

    public Byte getCategory() {
        return category;
    }

    public void setCategory(Byte category) {
        this.category = category;
    }

    private String fieldContent;

    private static final long serialVersionUID = 1L;

    public Long getHeaderUserId() {
        return headerUserId;
    }

    public void setHeaderUserId(Long headerUserId) {
        this.headerUserId = headerUserId;
    }

    public Long getFieldId() {
        return fieldId;
    }

    public void setFieldId(Long fieldId) {
        this.fieldId = fieldId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Integer getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Integer orderNo) {
        this.orderNo = orderNo;
    }

    public Byte getFieldType() {
        return fieldType;
    }

    public void setFieldType(Byte fieldType) {
        this.fieldType = fieldType;
    }

    public Byte getApply() {
        return apply;
    }

    public void setApply(Byte apply) {
        this.apply = apply;
    }

    public String getFieldContent() {
        return fieldContent;
    }

    public void setFieldContent(String fieldContent) {
        this.fieldContent = fieldContent == null ? null : fieldContent.trim();
    }

    public String getTenantCode() {
        return tenantCode;
    }

    public void setTenantCode(String tenantCode) {
        this.tenantCode = tenantCode;
    }
}