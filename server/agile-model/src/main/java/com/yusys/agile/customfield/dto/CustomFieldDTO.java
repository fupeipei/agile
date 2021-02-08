package com.yusys.agile.customfield.dto;


import java.util.Date;

/**
 * 自定义字段DTO
 *
 * @create 2021/2/1
 */
public class CustomFieldDTO {
    private Long fieldId;

    private String fieldName;

    private String comment;

    private Integer fieldType;

    private String category;

    private String required;

    private String apply;

    private String state;

    private Integer sort;

    private String operatorId;

    private String operatorName;

    private Date createDate;

    private Date modifyDate;

    private String fieldContent;

    /**
     * 应用类型名称
     */
    private String applyTypeName;

    private Long projectId;

    public Long getFieldId() {
        return fieldId;
    }

    public void setFieldId(Long fieldId) {
        this.fieldId = fieldId;
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

    public Integer getFieldType() {
        return fieldType;
    }

    public void setFieldType(Integer fieldType) {
        this.fieldType = fieldType;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getRequired() {
        return required;
    }

    public void setRequired(String required) {
        this.required = required;
    }

    public String getApply() {
        return apply;
    }

    public void setApply(String apply) {
        this.apply = apply;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public String getFieldContent() {
        return fieldContent;
    }

    public void setFieldContent(String fieldContent) {
        this.fieldContent = fieldContent;
    }

    public String getApplyTypeName() {
        return applyTypeName;
    }

    public void setApplyTypeName(String applyTypeName) {
        this.applyTypeName = applyTypeName;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    @Override
    public String toString() {
        return "CustomFieldDTO{" +
                "fieldId=" + fieldId +
                ", fieldName='" + fieldName + '\'' +
                ", comment='" + comment + '\'' +
                ", fieldType=" + fieldType +
                ", category='" + category + '\'' +
                ", required='" + required + '\'' +
                ", apply='" + apply + '\'' +
                ", state='" + state + '\'' +
                ", sort=" + sort +
                ", operatorId='" + operatorId + '\'' +
                ", operatorName='" + operatorName + '\'' +
                ", createDate=" + createDate +
                ", modifyDate=" + modifyDate +
                ", fieldContent='" + fieldContent + '\'' +
                ", applyTypeName='" + applyTypeName + '\'' +
                ", projectId=" + projectId +
                '}';
    }
}