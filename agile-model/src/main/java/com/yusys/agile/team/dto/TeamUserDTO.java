package com.yusys.agile.team.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author zhaofeng
 * @Date 2021/4/25 15:21
 */
@Data
public class TeamUserDTO {
    @ApiModelProperty(value = "主键")
    private Long id;
    @ApiModelProperty(value = "团队id")
    private Long teamId;
    @ApiModelProperty(value = "userId")
    private Long userId;
    @ApiModelProperty(value = "用户账号")
    private String userAccount;
    @ApiModelProperty(value = "用户名称")
    private String userName;
    @ApiModelProperty(value = "系统id")
    private Long systemId;
    @ApiModelProperty(value = "角色id")
    private Integer roleId;
}
