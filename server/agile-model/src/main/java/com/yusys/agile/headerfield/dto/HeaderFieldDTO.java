package com.yusys.agile.headerfield.dto;

/**
 * :
 *
 * @Date: 2020/4/13
 * @Description: TODO
 */
public class HeaderFieldDTO {
    private Long fieldId;

    private Long projectId;

    private String fieldCode;

    private String fieldName;

    private Byte isSelect;

    private Byte isCustom;

    private Byte fieldType;

    private Byte category;

    private String fieldGroup;

    private Byte sortable;

    private String tenantCode;

    private String comment;

    /**
     * 是否必填
     */
    private String required;

    private String state;
    /**
     * 应用类型
     */
    private String applyType;
    /**
     * 是否应用
     */
    private String apply;

    private String fieldContent;

    /**
     * 应用类型名称
     */
    private String applyTypeName;


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

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getRequired() {
        return required;
    }

    public void setRequired(String required) {
        this.required = required;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getApplyType() {
        return applyType;
    }

    public void setApplyType(String applyType) {
        this.applyType = applyType;
    }

    public String getApply() {
        return apply;
    }

    public void setApply(String apply) {
        this.apply = apply;
    }

    public String getApplyTypeName() {
        return applyTypeName;
    }

    public void setApplyTypeName(String applyTypeName) {
        this.applyTypeName = applyTypeName;
    }


}
