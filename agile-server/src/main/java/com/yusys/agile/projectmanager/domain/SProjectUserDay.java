package com.yusys.agile.projectmanager.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class SProjectUserDay implements Serializable {
    private Long dayId;

    private Integer reallyWorkload;

    private Date workDate;

    private Long workUid;

    private Long projectId;

    private String state;

    private Long createUid;

    private Date createTime;

    private Long updateUid;

    private Date updateTime;

    private String tenantCode;

    private String workDesc;

    private static final long serialVersionUID = 1L;
}