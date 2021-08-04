package com.yusys.agile.projectmanager.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * @ClassName: ProjectUserTotalHourDto
 * @Description:
 * @Author: fupp1
 * @CreateDate: 2021/08/04 10:05
 */
@Data
public class ProjectUserTotalHourDto {
    @ApiModelProperty("累计报工天数")
    private Integer totalDays;
    @ApiModelProperty("标准工时")
    private Integer totalPlanWorkload;
    @ApiModelProperty("实际总工时")
    private Integer totalReallyWorkload;
    @ApiModelProperty("用户id")
    private Long userId;
    @ApiModelProperty("用户姓名")
    private String userName;
    @ApiModelProperty("用户账号")
    private String userAccount;
    @ApiModelProperty("进入项目时间")
    private String createTime;
    @ApiModelProperty("项目id")
    private Long projectId;
}
