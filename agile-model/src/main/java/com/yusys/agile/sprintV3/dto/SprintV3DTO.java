package com.yusys.agile.sprintV3.dto;

import com.yusys.agile.project.dto.ProjectFaultDTO;
import com.yusys.agile.project.dto.ProjectStoryDTO;
import com.yusys.agile.project.dto.ProjectTaskDTO;
import com.yusys.agile.teamV3.dto.TeamV3DTO;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 迭代v3dto
 *
 * @date 2021/05/11
 */
@Data
public class SprintV3DTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 迭代id
     */
    private Long sprintId;

    /**
     * 迭代的名字
     */
    private String sprintName;

    /**
     * 迭代desc
     */
    private String sprintDesc;

    /**
     * 迭代状态，1：有效，-1：无效，0：取消，暂停，2：未开始，3：进行中，4：已完成
     */
    private Byte status;

    /**
     * 数据有效状态 'U'：正常 'E'：失效
     */
    private String state;

    /**
     * 开始时间
     */
    private Date startTime;

    /**
     * 结束时间
     */
    private Date endTime;

    /**
     * 完成时间
     */
    private Date finishTime;

    /**
     * 团队id
     */
    private Long teamId;

    /**
     * 团队的名字
     */
    private String teamName;

    /**
     * 工作时间
     */
    private Integer workHours;

    /**
     * 版本号
     */
    private String versionNumber;

    /**
     * 创建uid
     */
    private Long createUid;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新人
     */
    private Long updateUid;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 持有
     */
    private String hold;


    private String improve;

    private String terminate;

    /**
     * 迭代天数
     */
    private String sprintDays;

    /**
     * 迭代日期
     */
    private List<Date> sprintDayList;

    /**
     * 成员
     */
    private List<SprintV3UserHourDTO> members;

    private List<TeamV3DTO> teamDTOList;

    private Double sprintRate;

    private List<Long> issueIds;

    private Byte issueType;

    /**
     * 计划天数
     */
    private Integer planDays;

    private ProjectStoryDTO story;

    private ProjectTaskDTO task;

    private ProjectFaultDTO fault;

    private Integer planWorkload;

    private Integer finishWorkload;

    private Integer userNum;

    private Integer pageNum;

    private Integer pageSize;

    /**
     * 未完成故事列表
     */
    private List<Long> listStorys;

    /**
     * 判断sprintType 是否为需求规划（demandPlan）是返回迭代下关联的故事ID（storys）
     */
    private String sprintType;

    /**
     * 迭代下关联的故事IDS
     */
    private List<Long> storyIds;


    private Double progress;

    /**
     * 租户的id
     */
    private String tenantCode;



}
