package com.yusys.agile.team.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Team列表查询条件
 * @Author zhao
 * @Date 2021/4/22 10:39
 */
@Data
public class TeamQueryDTO {

    @ApiModelProperty("团队名称/编号")
    private String team;
    @ApiModelProperty("po姓名/账号")
    private String po;
    @ApiModelProperty("sm姓名/账号")
    private String sm;
    @ApiModelProperty("lean姓名/账号")
    private String lean;
    @ApiModelProperty("系统名称/编号")
    private String system;
    @ApiModelProperty("团队类型， M:敏捷团队，N:精益团队")
    private String teamType;
    @ApiModelProperty("当前页")
    private Integer pageNum;
    @ApiModelProperty("每页显示数")
    private Integer pageSize;
    @ApiModelProperty("系统ID")
    private Long systemId;
}
