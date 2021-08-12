package com.yusys.agile.issue.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class SEpicReviewRecord implements Serializable {
    private Long recordId;

    private Long issueId;

    private String reviewScore;

    private String reviewDesc;

    private String state;

    private Long createUid;

    private Date createTime;

    private Long updateUid;

    private Date updateTime;

    private String tenantCode;

    private static final long serialVersionUID = 1L;
}