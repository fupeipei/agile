package com.yusys.agile.team.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author zhaofeng
 * @Date 2021/4/25 15:21
 */
@Data
public class TeamSystemDTO {

    @ApiModelProperty(value="主键")
    private Long id;

    @ApiModelProperty(value="团队id")
    private Long teamId;

    @ApiModelProperty(value="系统id")
    private Long systemId;

    @ApiModelProperty(value="系统名称")
    private String systemName;

}
