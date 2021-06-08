package com.yusys.agile.issue.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @Date 2021/2/4
 * @Description 工作项状况 类型 1:epic 2:feature 3:story 4:task
 */
@Data
public class IssueStatus {
    private Long projectId;
    private Long sprintId;
    private Date sprintDate;
    private Byte issueType;
    private Integer inSprint;
    private Integer finished;
    private Integer notStarted;
    @ApiModelProperty("统计每天完成的故事点数")
    private Integer finishedStoryPoint;
    @ApiModelProperty("统计每天完成的实际工作量")
    private Integer reallyWorkload;
    @ApiModelProperty("统计每天的计划工作量")
    private Integer planWorkload;
    private Date createTime;

}
