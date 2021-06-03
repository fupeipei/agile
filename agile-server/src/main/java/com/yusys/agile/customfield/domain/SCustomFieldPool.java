package com.yusys.agile.customfield.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class SCustomFieldPool implements Serializable {
    private Long fieldId;

    private String fieldName;

    private String comment;

    private Integer fieldType;

    private String state;

    private Long projectId;

    private Long systemId;

    private Date createTime;

    private Long createUid;

    private Date updateTime;

    private Long updateUid;

    private String tenantCode;

    private String fieldContent;

    private static final long serialVersionUID = 1L;

}