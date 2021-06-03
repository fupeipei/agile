package com.yusys.agile.issue.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class SIssueCustomField implements Serializable {
    private Long extendId;

    private Long fieldId;

    private String fieldValue;

    private Long issueId;

    private String state;

    private Date createTime;

    private Long createUid;

    private Date updateTime;

    private Long updateUid;

    private String tenantCode;

    private static final long serialVersionUID = 1L;

}