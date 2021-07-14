package com.yusys.agile.issue.dto;

import com.yusys.agile.sysextendfield.SysExtendFieldDetailDTO;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
@Data
public class IssueListDTO {
    private Long issueId;

    private String title;

    private Long parentId;

    private Map issueType;

    private Map sprintId;

    //之前是long类型，改成Map对象类型，为了满足高级查询，列表展示模块名称
    private Map moduleId;

    private Map systemId;

    private Map teamId;

    private Map handler;

    private String handlerName;

    private Date beginDate;

    private Date endDate;

    private Integer planWorkload;

    private Integer reallyWorkload;

    private Map priority;

    private Map importance;
    // 阶段id
    private Map stageId;

    private Map laneId;

    private String stageName;

    private String projectName;

    private Long projectId;

    private Map projectIdMap;

    private Byte isCollect;

    private Map taskType;

    private Map faultType;

    private Map faultLevel;

    private Date fixedTime;

    private Date closeTime;

    private Map fixedUid;

    private Map testUid;

    private Long versionId;

    private Long caseId;

    private Date deadline;

    private String file;

    private String urgency;

    private String cause;

    private String detectedPhase;

    private Long manualCaseId;

    private Byte source;

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
    private Integer remainWorkload;

    private Map isArchive;


    // 和stageId对应的缺陷状态
    private Map faultStatus;


    private String tenantCode;


    private Map completion;


    private String state;

    private Byte status;

    private Map createUid;

    private Date createTime;

    private Map updateUid;

    private Date updateTime;

    private String description;

    /**
     * @Description: 自定义字段json串
     */
    private String fieldList;
    /**
     * 功能描述  是否有child
     *
     */
    private Boolean isParent;

    private List<IssueListDTO> children;

    private Integer order;

    private List<IssueHistoryRecordDTO> records;

    private List<IssueAttachmentDTO> attachments = new ArrayList<>();

    private List<IssueCustomFieldDTO> customFieldList;

    //版本计划相关字段 审批状态
    private Map assessIsPass;
    //版本计划相关字段 审批结果
    private Map assessIsPassResult;
    //版本计划相关字段 是否移除
    private Map versionIsRemove;
    //版本计划名称
    private Map versionName;

    private List<SysExtendFieldDetailDTO> sysExtendFieldDetailList;

    private static final long serialVersionUID = 1L;

}
