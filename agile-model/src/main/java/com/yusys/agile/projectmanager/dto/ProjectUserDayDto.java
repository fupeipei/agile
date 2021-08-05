package com.yusys.agile.projectmanager.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @ClassName: ProjectUserDayDto
 * @Description:
 * @Author: fupp1
 * @CreateDate: 2021/08/04 10:09
 */
@Data
public class ProjectUserDayDto {
    @ApiModelProperty("用户id")
    private Long workUid;
    @ApiModelProperty("用户姓名")
    private String userName;
    @ApiModelProperty("用户账号")
    private String userAccount;
    @ApiModelProperty("项目id")
    private Long projectId;
    private List<UserHourDto> userHours;
    @ApiModelProperty("描述")
    private String workDesc;
    @ApiModelProperty("工时日期")
    private Date workDate;
    @ApiModelProperty("报工记录id")
    private Long dayId;
    @ApiModelProperty("实际总工时")
    private Integer totalReallyWorkload;
}
