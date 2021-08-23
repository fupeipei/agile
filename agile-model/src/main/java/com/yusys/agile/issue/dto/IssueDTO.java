package com.yusys.agile.issue.dto;

import com.yusys.agile.scheduleplan.dto.ScheduleplanDTO;
import com.yusys.agile.sprintV3.dto.SprintV3DTO;
import com.yusys.agile.sysextendfield.SysExtendFieldDetailDTO;
import com.yusys.portal.model.facade.dto.ProductLineDTO;
import com.yusys.portal.model.facade.dto.SsoUserDTO;
import com.yusys.portal.model.project.dto.ProjectManagerDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author hanke
 */
@Data
public class IssueDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long issueId;

    private String title;

    private Long parentId;

    private Byte issueType;

    private Long sprintId;

    private Long moduleId;

    private Long systemId;

    private String handlerAccount;

    private Integer order;
    /**
     * @Date: 11:14
     * @Description: 需求满意度
     * @Return:
     */
    private String satisfaction;

    private String systemName;

    private String systemCode;

    private String moduleName;

    private Long handler;

    private String handlerName;

    private Date beginDate;

    private Date endDate;

    private Integer planWorkload;

    private Integer reallyWorkload;

    private Byte priority;

    private Byte importance;
    // 阶段id
    private Long stageId;

    private Long laneId;

    private String stageName;

    private String projectName;

    private Long projectId;

    private Byte isCollect;

    private Integer taskType;

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
    // 缺陷来源 0YuDO 1itc 2itsm
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
    // 和stageId对应的缺陷状态
    private String faultStatus;
    // itc同步过来的bugId
    private Long bugId;
    //迭代对象
    private SprintV3DTO sprintDTO;

    //故事验收标准
    private String acceptanceCriteria;

    private Byte isArchive;

    private Byte isCancel;

    private Long teamId;

    /**
     * 功能描述  在版本中的需求id
     *
     * @date 2021/2/2
     * @return
     */
    private List<Long>  inVersionIssueList;
    /**
     * 功能描述 扩展表中需求id
     *
     * @date 2021/2/2
     * @return
     */
    private List<Long> sysExtendList;
    private String reason;
    private List<SysExtendFieldDetailDTO> sysExtendFieldDetailDTOList;
    private Integer operateType;
    private String laneName;
    private Integer pageNum;
    private Integer pageSize;
    private String rootIds;
    private Long moveToVersionPlanId;
    private String bizNum;//业务需求编号
    private String partaReqnum;//局方需求编号
    private Long recordId;
    private Long versionPlanId;
    private Long bizBacklogId;

    /**
     * @Date: 2021/2/9 10:31
     * @Description: 阶段数组
     */
    private Long[] stages;

    /**
     * @Date: 9:28
     * @Description: 关联子工作项ID列表
     */
    private List<Long> listIssueIds;

    /**
     * @Date: 2021/2/13 9:41
     * @Description: 系统列表
     */
    private List<Long> systemIds;

    private String tenantCode;
    /**
     * @Date: 2021/2/124  15:39
     * @Description: 模块列表
     */
    private List<Long> moduleIds;
    /**
     * @Description: 自定义字段json串
     */
    private String fieldList;
    /**
     * 功能描述  是否有child
     *
     * @date 2020/4/16
     * @param null
     * @return
     */
    private Boolean isParent;

    private List<IssueDTO> children;

    //阻塞状态，0：未阻塞 1：阻塞
    private Byte blockState;
    //评审是否通过
    private Byte assessIsPass;
    //评审备注
    private String assessRemarks;
    //故事验收标准
    private List<IssueAcceptanceDTO> issueAcceptanceDTOS;

    private String state;

    private Byte status;

    private Long createUid;

    private Date createTime;

    private Long updateUid;

    private Date updateTime;

    private String description;

    private String taskTypeDesc;

    //是否超时，超时为true,否则false
    private boolean isOverTime;

    private SsoUserDTO owner;

    //缺陷个数
    private Integer faultNum;

    //用例个数
    private Integer caseNum;
    //故事id或名称
    private String storyKeyWord;

    //任务名称
    private String taskKeyWord;

    private String completion;
    /**
     * 卡片在阶段中停留天数
     */
    private Integer stayDays;
    private List<Long> handlers;

    //是否是批量导入
    private boolean isBatchCreate = false;
    /**
     * 迭代中任务的阶段状态
     */
    private List<Long> stageIds;
    /**
     * 迭代中任务的二级状态
     */
    private List<Long> laneIds;

    private List<Integer> taskTypes;
    private List<IssueHistoryRecordDTO> records;

    private List<IssueAttachmentDTO> attachments;

    private List<IssueCustomFieldDTO> customFieldDetailDTOList;

    private Boolean parent;

    private String StoryStatusName;

    /**看板id*/
    private Long  kanbanId;

    @ApiModelProperty("feature下故事总数")
    private Integer storyTotalNum;

    @ApiModelProperty("feature下完成故事数")
    private Integer stroyFinishNum;

    @ApiModelProperty("故事下任务数量(除去缺陷类任务)")
    private Integer taskNum;

    @ApiModelProperty("故事下任务完成数量(除去缺陷类任务)")
    private Integer taskFinishNum;

    @ApiModelProperty("故事下缺陷类任务数量")
    private Integer bugTaskNum;

    @ApiModelProperty("故事下缺陷类任务完成数量")
    private Integer bugTaskFinishNum;

    @ApiModelProperty("排期计划")
    private ScheduleplanDTO scheduleplan;

    @ApiModelProperty("是否发起排期 0未发起 1 发起")
    private Byte startSchedule;

    @ApiModelProperty("上线时间")
    private Date releaseDate;

    @ApiModelProperty("提测时间")
    private Date raiseTestDate;

    /**
     * 排期名称
     */
    private String scheduleName;


    private String teamName;

    private Byte cmpSyncResult;


    @ApiModelProperty("系统信息")
    private List<ProjectManagerDTO> projectManagerDTOS;

    @ApiModelProperty("产品线信息")
    private List<ProductLineDTO> productLineDTOS;

}
