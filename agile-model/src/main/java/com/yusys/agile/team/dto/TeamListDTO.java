package com.yusys.agile.team.dto;

import com.yusys.portal.model.facade.dto.SsoUserDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Team列表返回结果
 * @Author zhao
 * @Date 2021/4/22 10:45
 */
@Data
public class TeamListDTO implements Serializable {
    private static final long serialVersionUID = -2654457780624482505L;
    @ApiModelProperty("团队id")
    private Long teamId;
    @ApiModelProperty("团队名称")
    private String teamName;
    @ApiModelProperty("团队类型")
    private String teamType;
    @ApiModelProperty("创建时间")
    private Date createTime;
    @ApiModelProperty("创建人id")
    private Long createUid;
    @ApiModelProperty("创建人")
    private SsoUserDTO createUser;
    @ApiModelProperty("团队po集合")
    private List<TeamUserDTO> teamPoNames;
    @ApiModelProperty("团队sm集合")
    private List<TeamUserDTO> teamSmNames;
    @ApiModelProperty("团队lean集合")
    private List<TeamUserDTO> teamLeanNames;
    @ApiModelProperty("系统集合")
    private List<TeamSystemDTO> systemNames;
    @ApiModelProperty("团队所有人员")
    private List<TeamUserDTO> teamUsers;
    @ApiModelProperty("团队所有人员")
    private Integer teamUserCount;
    /**
     * 当前访问人是否关注此项目，不为空时 1:关注，0：未关注
     */
    @ApiModelProperty("是否关注")
    private Integer attentionFlag;

    @ApiModelProperty("加入时间")
    private Date updateTime;
}
