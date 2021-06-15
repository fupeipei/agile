package com.yusys.agile.leankanban.dto;

import com.yusys.agile.issue.dto.IssueDTO;
import com.yusys.agile.set.stage.dto.KanbanStageInstanceDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class SLeanKanbanDTO {

    @ApiModelProperty("看板Id")
    private Long kanbanId;

    @ApiModelProperty("看板名称")
    private String kanbanName;

    @ApiModelProperty("看板描述")
    private String kanbanDesc;

    @ApiModelProperty("团队Id")
    private Long teamId;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("创建人Id")
    private Long createUid;

    @ApiModelProperty("状态 1有效 2失效")
    private Long status;

    @ApiModelProperty("更新时间")
    private Date updateTime;

    @ApiModelProperty("更新人")
    private Long updateUid;

    @ApiModelProperty("状态 U 有效 E 删除")
    private String state;

    @ApiModelProperty("租户码")
    private String tenantCode;

    @ApiModelProperty("看板阶段信息")
    private List<KanbanStageInstanceDTO> kanbanStageInstances;

    @ApiModelProperty("工作项信息")
    private List<IssueDTO> issueDTOS;
}
