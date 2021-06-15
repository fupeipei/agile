package com.yusys.agile.teamv3.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class STeam implements Serializable {
    private Long teamId;

    private String teamName;

    private String state;

    private String teamType;

    private Long createUid;

    private Date createTime;

    private Long updateUid;

    private Date updateTime;

    private String tenantCode;

    private String teamDesc;

    /**
     * 团队关联系统
     */
    @ApiModelProperty(value = "团队系统")
    private List<Long> systemIds;

    /**
     * 团队关联成员
     */
    @ApiModelProperty(value = "团队成员")
    private List<STeamMember> teamUsers;

    /**
     * 团队po
     */
    @ApiModelProperty(value = "团队po")
    private List<STeamMember> teamPoS;

    /**
     * 团队SM
     */
    @ApiModelProperty(value = "团队sm")
    private List<STeamMember> teamSmS;

    /**
     * 团队精益教练
     */
    @ApiModelProperty(value = "团队精益教练")
    private List<STeamMember> teamLean;

    private static final long serialVersionUID = 1L;
}