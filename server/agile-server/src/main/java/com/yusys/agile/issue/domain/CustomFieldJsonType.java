package com.yusys.agile.issue.domain;

import java.util.Date;
import java.util.List;

public class CustomFieldJsonType {


    private Long fieldId;

    private Byte fieldType;

    private List<String> dataString;

    private List<Byte> dataByte;

    private List<Long> dataLong;

    private List<Integer> dataInteger;

    private Date dateBegin;//开始
    private Date dateEnd;//结束

    private Long numBegin;//开始
    private Long numEnd;//结束

    private Integer queryType;//1:查询全部;2:根据value查询;3:查询为null的记录


    public Long getNumBegin() {
        return numBegin;
    }

    public void setNumBegin(Long numBegin) {
        this.numBegin = numBegin;
    }

    public Long getNumEnd() {
        return numEnd;
    }

    public void setNumEnd(Long numEnd) {
        this.numEnd = numEnd;
    }

    public Long getFieldId() {
        return fieldId;
    }

    public void setFieldId(Long fieldId) {
        this.fieldId = fieldId;
    }

    public Byte getFieldType() {
        return fieldType;
    }

    public void setFieldType(Byte fieldType) {
        this.fieldType = fieldType;
    }

    public Integer getQueryType() {
        return queryType;
    }

    public void setQueryType(Integer queryType) {
        this.queryType = queryType;
    }

    public List<String> getDataString() {
        return dataString;
    }

    public void setDataString(List<String> dataString) {
        this.dataString = dataString;
    }

    public List<Byte> getDataByte() {
        return dataByte;
    }

    public void setDataByte(List<Byte> dataByte) {
        this.dataByte = dataByte;
    }

    public List<Long> getDataLong() {
        return dataLong;
    }

    public void setDataLong(List<Long> dataLong) {
        this.dataLong = dataLong;
    }

    public List<Integer> getDataInteger() {
        return dataInteger;
    }

    public void setDataInteger(List<Integer> dataInteger) {
        this.dataInteger = dataInteger;
    }

    public Date getDateBegin() {
        return dateBegin;
    }

    public void setDateBegin(Date dateBegin) {
        this.dateBegin = dateBegin;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }
}
