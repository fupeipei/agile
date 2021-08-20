package com.yusys.agile.team.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author zhaofeng
 * @Date 2021/4/25 15:21
 */
@Data
public class TeamSystemDTO implements Serializable {
    private static final long serialVersionUID = -9025863997356458545L;

    @ApiModelProperty(value="主键")
    private Long id;

    @ApiModelProperty(value="团队id")
    private Long teamId;

    @ApiModelProperty(value="系统id")
    private Long systemId;

    @ApiModelProperty(value="系统名称")
    private String systemName;

}
