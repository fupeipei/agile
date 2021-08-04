package com.yusys.agile.projectmanager.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
@Data
public class SProjectUserHour implements Serializable {
    private Integer hourId;

    private Integer reallyWorkload;

    private Date workDate;

    private Long workUid;

    private Long issueId;

    private Integer projectId;

    private Integer dayId;

    private String state;

    private Long createUid;

    private Date createTime;

    private Long updateUid;

    private Date updateTime;

    private String tenantCode;

    private static final long serialVersionUID = 1L;
}