package com.yusys.agile.customfield.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class SCustomFieldPool implements Serializable {
    /**
     * 自定义字段id
     */
    private Long fieldId;

    /**
     * 自定义字段名
     */
    private String fieldName;

    /**
     * 备注
     */
    private String comment;

    /**
     * 字段类型 0单选 1多选 2数字 3日期 4文本 5成员
     */
    private Integer fieldType;

    /**
     * 数据有效状态 U正常 E失效
     */
    private String state;

    /**
     * 系统id
     */
    private Long systemId;

    /**
     * 自定义字段内容
     */
    private String fieldContent;

    private Date createTime;

    private Long createUid;

    private Date updateTime;

    private Long updateUid;

    private String tenantCode;

    private Long projectId;

    private static final long serialVersionUID = 1L;

}