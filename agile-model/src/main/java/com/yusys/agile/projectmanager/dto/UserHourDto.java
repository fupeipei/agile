package com.yusys.agile.projectmanager.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName: UserHourDto
 * @Description:
 * @Author: fupp1
 * @CreateDate: 2021/08/03 19:29
 */
@Data
public class UserHourDto {
    @ApiModelProperty("工作项id")
    private Long issueId;
    @ApiModelProperty("实际工时")
    private Integer reallyWorkload;
}
