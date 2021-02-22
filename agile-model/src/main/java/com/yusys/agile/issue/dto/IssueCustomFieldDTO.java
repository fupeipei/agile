package com.yusys.agile.issue.dto;

import java.util.Date;

public class IssueCustomFieldDTO {
    private Long extendId;

    private Long fieldId;

    private Date updateTime;

    private Long updateUid;

    private String fieldCode;

    private String fieldPoolCode;
    /**
     * 自定义字段填入值
     */
    private String fieldValue;

    private Long issueId;

    private Date createTime;

    private Long createUid;

    private String tenantCode;

    /** 以下是对应的字段**/
    /**
     * id 对应extendId
     */
    private Long detailId;
    /**
     * 对应issueId
     */
    private Long subjectId;

    private String category;

    private String state;
    /**
     * 自定义字段名
     */
    private String fieldName;
    /**
     * 自定义字段类型
     */
    private Byte fieldType;
    /**
     * 是否必填
     */
    private String required;
    /**
     * 是否应用
     */
    private Byte isSelect;
    /**
     * 是否应用
     */
    private String apply;
    /**
     * 自定义字段设置内容
     */
    private String fieldContent;


    public String getFieldPoolCode() {
        return fieldPoolCode;
    }

    public void setFieldPoolCode(String fieldPoolCode) {
        this.fieldPoolCode = fieldPoolCode;
    }

    public String getTenantCode() {
        return tenantCode;
    }

    public void setTenantCode(String tenantCode) {
        this.tenantCode = tenantCode;
    }


    public String getFieldContent() {
        return fieldContent;
    }

    public void setFieldContent(String fieldContent) {
        this.fieldContent = fieldContent;
    }

    public Byte getFieldType() {
        return fieldType;
    }

    public void setFieldType(Byte fieldType) {
        this.fieldType = fieldType;
    }

    public String getFieldCode() {
        return fieldCode;
    }

    public void setFieldCode(String fieldCode) {
        this.fieldCode = fieldCode;
    }


    private static final long serialVersionUID = 1L;

    public Long getExtendId() {
        return extendId;
    }

    public void setExtendId(Long extendId) {
        this.extendId = extendId;
    }

    public Long getFieldId() {
        return fieldId;
    }

    public void setFieldId(Long fieldId) {
        this.fieldId = fieldId;
    }

    public String getFieldValue() {
        return fieldValue;
    }

    public void setFieldValue(String fieldValue) {
        this.fieldValue = fieldValue == null ? null : fieldValue.trim();
    }

    public Long getIssueId() {
        return issueId;
    }

    public void setIssueId(Long issueId) {
        this.issueId = issueId;
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

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getRequired() {
        return required;
    }

    public void setRequired(String required) {
        this.required = required;
    }

    public Byte getIsSelect() {
        return isSelect;
    }

    public void setIsSelect(Byte isSelect) {
        this.isSelect = isSelect;
    }

    public Long getDetailId() {
        return detailId;
    }

    public void setDetailId(Long detailId) {
        this.detailId = detailId;
    }

    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getApply() {
        return apply;
    }

    public void setApply(String apply) {
        this.apply = apply;
    }
}
