package com.yusys.agile.sprintV3.dto;

import com.yusys.portal.model.facade.entity.SsoUser;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @Author zhaofeng
 * @Date 2021/5/11 15:57
 */
@Data
public class SprintListDTO {
    @ApiModelProperty("主键")
    private Long sprintId;
    @ApiModelProperty("迭代名称")
    private String sprintName;
    @ApiModelProperty("迭代状态")
    private Byte status;
    private String statusStr;
    @ApiModelProperty("开始时间")
    private Date startTime;
    @ApiModelProperty("结束时间")
    private Date endTime;
    @ApiModelProperty("完成时间")
    private Date finishTime;
    @ApiModelProperty("迭代日期")
    private String sprintDays;
    @ApiModelProperty("迭代天数")
    private Integer planDays;
    @ApiModelProperty("迭代周期")
    private List<Date> sprintDayList;
    @ApiModelProperty("团队id")
    private Long teamId;
    @ApiModelProperty("团队名称")
    private String teamName;
    @ApiModelProperty("迭代成员数")
    private Integer sprintUserCount;
    @ApiModelProperty("每天工作时长")
    private Integer workHours;
    @ApiModelProperty("迭代版本号")
    private String versionNumber;
    @ApiModelProperty("创建人id")
    private Long createUid;
    @ApiModelProperty("创建人")
    private SsoUser createUser;
    @ApiModelProperty("创建时间")
    private Date createTime;
    @ApiModelProperty("故事完成情况")
    private SprintTaskDTO story;
    @ApiModelProperty("故事完成情况")
    private SprintTaskDTO task;



}
