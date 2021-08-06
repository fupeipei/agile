package com.yusys.agile.projectmanager.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @ClassName: ProjectUserHourDto
 * @Description:
 * @Author: fupp1
 * @CreateDate: 2021/08/03 17:05
 */
@Data
public class ProjectUserHourDto {
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
    @ApiModelProperty("项目id")
    private Long projectId;
    @ApiModelProperty("工时日期 格式:2021-08-01")
    private String workDate;
    @ApiModelProperty("开始日期 格式:2021-08-01")
    private String startDate;
    @ApiModelProperty("结束日期 格式:2021-08-01")
    private String endDate;
    @ApiModelProperty("每页几条")
    private Integer pageSize;
    @ApiModelProperty("第几页")
    private Integer pageNum;
}
