package com.yusys.agile.issue.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Issue implements Serializable {
    private Long issueId;

    private String title;

    private Long parentId;

    private Byte issueType;

    private Long sprintId;

    private Long moduleId;

    private Long systemId;

    private Long handler;

    private Date beginDate;

    private Date endDate;

    private Integer planWorkload;

    private Integer reallyWorkload;

    private Byte priority;

    private Byte importance;

    private Long stageId;

    private Long laneId;

    private String state;

    private Byte status;

    private Long projectId;

    private Byte isCollect;

    private String completion;

    private Integer taskType;

    private Long createUid;

    private Date createTime;

    private Long updateUid;

    private Date updateTime;

    private Long faultType;

    private Long faultLevel;

    private Date fixedTime;

    private Date closeTime;

    private Long fixedUid;

    private Long testUid;

    private Long versionId;

    private Long caseId;

    private Date deadline;

    private String file;

    private String urgency;

    private String cause;

    private String detectedPhase;

    private Long manualCaseId;

    private Byte source;

    private Integer remainWorkload;

    private Long bugId;

    private Integer order;

    private Byte blockState;

    private Integer reopenTimes;

    private Byte assessIsPass;

    private String assessRemarks;

    private String tenantCode;

    private Byte cmpSyncResult;

    private Byte isArchive;

    private Byte isCancel;

    /**团队id*/
    private Long teamId;

    private static final long serialVersionUID = 1L;

}