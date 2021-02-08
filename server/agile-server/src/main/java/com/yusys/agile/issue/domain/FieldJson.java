package com.yusys.agile.issue.domain;

public class FieldJson {
    private Long fieldId;
    private String fieldValue;
    /**
     *
     * @Description: 0:查询全部;1:根据value查询;2:查询为null的记录
     */
    private Integer queryType;

    public Integer getQueryType() {
        return queryType;
    }

    public void setQueryType(Integer queryType) {
        this.queryType = queryType;
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
        this.fieldValue = fieldValue;
    }
}
