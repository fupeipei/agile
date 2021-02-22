package com.yusys.agile.headerfield.domain;

import java.io.Serializable;

public class HeaderField implements Serializable {
    private Long fieldId;

    private Long projectId;

    private String fieldCode;

    private String fieldPoolCode;

    private String fieldName;

    private Byte isSelect;

    private Byte isCustom;

    private Byte fieldType;

    private Byte category;

    private String fieldGroup;

    private Byte sortable;

    private String tenantCode;

    private String comment;

    private String required;

    private String state;

    private String applyType;

    private String apply;

    private String fieldContent;

    private String fieldTypeName;

    private static final long serialVersionUID = 1L;

    public Long getFieldId() {
        return fieldId;
    }

    public void setFieldId(Long fieldId) {
        this.fieldId = fieldId;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getFieldCode() {
        return fieldCode;
    }

    public void setFieldCode(String fieldCode) {
        this.fieldCode = fieldCode == null ? null : fieldCode.trim();
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName == null ? null : fieldName.trim();
    }

    public Byte getIsSelect() {
        return isSelect;
    }

    public void setIsSelect(Byte isSelect) {
        this.isSelect = isSelect;
    }

    public Byte getIsCustom() {
        return isCustom;
    }

    public void setIsCustom(Byte isCustom) {
        this.isCustom = isCustom;
    }

    public Byte getFieldType() {
        return fieldType;
    }

    public void setFieldType(Byte fieldType) {
        this.fieldType = fieldType;
    }

    public Byte getCategory() {
        return category;
    }

    public void setCategory(Byte category) {
        this.category = category;
    }

    public String getFieldGroup() {
        return fieldGroup;
    }

    public void setFieldGroup(String fieldGroup) {
        this.fieldGroup = fieldGroup == null ? null : fieldGroup.trim();
    }

    public Byte getSortable() {
        return sortable;
    }

    public void setSortable(Byte sortable) {
        this.sortable = sortable;
    }

    public String getTenantCode() {
        return tenantCode;
    }

    public void setTenantCode(String tenantCode) {
        this.tenantCode = tenantCode == null ? null : tenantCode.trim();
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment == null ? null : comment.trim();
    }

    public String getRequired() {
        return required;
    }

    public void setRequired(String required) {
        this.required = required == null ? null : required.trim();
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

    public String getApplyType() {
        return applyType;
    }

    public void setApplyType(String applyType) {
        this.applyType = applyType == null ? null : applyType.trim();
    }

    public String getApply() {
        return apply;
    }

    public void setApply(String apply) {
        this.apply = apply == null ? null : apply.trim();
    }

    public String getFieldContent() {
        return fieldContent;
    }

    public void setFieldContent(String fieldContent) {
        this.fieldContent = fieldContent == null ? null : fieldContent.trim();
    }

    public String getFieldTypeName() {
        return fieldTypeName;
    }

    public void setFieldTypeName(String fieldTypeName) {
        this.fieldTypeName = fieldTypeName;
    }

    public String getFieldPoolCode() {
        return fieldPoolCode;
    }

    public void setFieldPoolCode(String fieldPoolCode) {
        this.fieldPoolCode = fieldPoolCode;
    }
}