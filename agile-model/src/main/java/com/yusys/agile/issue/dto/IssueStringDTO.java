package com.yusys.agile.issue.dto;


import lombok.Data;

import java.io.Serializable;
import java.util.List;
@Data
public class IssueStringDTO implements Serializable {

    private static final long serialVersionUID = -3129918408584131673L;

    private String issueId;

    private String title;

    private String parentId;

    private String issueType;

    private String sprintId;

    private String moduleId;

    private String systemId;

    private String satisfaction;

    private String systemName;

    private String moduleName;

    private String handler;

    private String order;

    private String handlerName;

    private String beginDate;

    private String endDate;

    private Integer planWorkload;

    private Integer reallyWorkload;

    private String priority;

    private String importance;
    // 阶段id
    private String stageId;

    private String laneId;

    private String stageName;

    private String projectName;

    private String projectId;

    private String isCollect;

    private Integer taskType;

    private String faultType;

    private String faultLevel;

    private String fixedTime;

    private String closeTime;

    private String fixedUid;

    private String testUid;

    private String versionId;

    private String versionName;

    private String caseId;

    private String deadline;

    private String file;

    private String urgency;

    private String cause;

    private String detectedPhase;

    private String manualCaseId;
    // 缺陷来源 0YuDO
    private String source;

    // 缺陷级别名
    private String faultLevelName;
    // 缺陷类型名
    private String faultTypeName;
    // 缺陷修复人
    private String fixedName;
    // 缺陷提出人
    private String createName;
    // 缺陷验证人
    private String testName;
    // case
    private String caseName;
    // 缺陷时对应storyName
    private String parentName;
    // 迭代名
    private String sprintName;
    // 剩余工作量
    private String remainWorkload;
    // 和stageId对应的缺陷状态
    private String faultStatus;
    // itc同步过来的bugId
    private String bugId;

    private String pageNum;

    private String pageSize;

    private String rootIds;

    private String state;

    private String status;

    private String createUid;

    private String createTime;

    private String updateUid;

    private String updateTime;

    private String description;
    //缺陷id或者name（高级搜索）
    private String idOrTitle;

    private String completion;

    private String teamId;

    /**
     * 查询标识，不为null表示看板查询
     */
    private String queryFlag;

    //指定工作项
    private List<Long> issueIds;

    private String tenantCode;

    private String isArchive;

}
