package com.yusys.agile.scheduleplan.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class SystemInfoDTO {

    @ApiModelProperty("系统Id")
    private Long systemId;

    @ApiModelProperty("系统负责人")
    private Long systemUid;

    @ApiModelProperty("是否主办系统 0 是 ,1 否 ")
    private Byte master;

    @ApiModelProperty("系统名称")
    private String systemName;

    @ApiModelProperty("系统负责人名称")
    private String systemUserName;

    @ApiModelProperty("系统负责人账号")
    private String systemUserAccount;

}
