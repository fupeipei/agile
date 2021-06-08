package com.yusys.agile.issue.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class SIssueCustomRelation implements Serializable {
    private Long id;

    private Byte issueType;

    private Long fieldId;

    private String fieldName;

    private String required;

    private Byte fieldType;

    private Integer sort;

    private Long projectId;

    private Long systemId;

    private String state;

    private Date createTime;

    private Long createUid;

    private Long updateUid;

    private Date updateTime;

    private String tenantCode;

    private String fieldContent;

    private static final long serialVersionUID = 1L;

}