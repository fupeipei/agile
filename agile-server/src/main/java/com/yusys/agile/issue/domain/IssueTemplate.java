package com.yusys.agile.issue.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class IssueTemplate implements Serializable {
    private Long issueTemplateId;

    private String templateName;

    private Byte issueType;

    private Byte apply;

    private Long systemId;

    private Date createTime;

    private Long createUid;

    private Long updateUid;

    private Date updateTime;

    private String tenantCode;

    private String description;

    private static final long serialVersionUID = 1L;
}