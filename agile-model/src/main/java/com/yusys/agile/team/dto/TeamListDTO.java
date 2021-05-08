package com.yusys.agile.team.dto;

import com.yusys.portal.model.facade.dto.SsoSystemRestDTO;
import com.yusys.portal.model.facade.dto.SsoUserDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Team列表返回结果
 * @Author zhao
 * @Date 2021/4/22 10:45
 */
@Data
public class TeamListDTO {
    @ApiModelProperty("团队id")
    private Long teamId;
    @ApiModelProperty("团队名称")
    private String teamName;
    @ApiModelProperty("创建时间")
    private Date createTime;
    @ApiModelProperty("创建人id")
    private Long createUid;
    @ApiModelProperty("创建人")
    private SsoUserDTO createUser;
    @ApiModelProperty("团队po集合")
    private List<TeanUserDTO> teamPoNames;
    @ApiModelProperty("团队sm集合")
    private List<TeanUserDTO> teamSmNames;
    @ApiModelProperty("系统集合")
    private List<TeamSystemDTO> systemNames;
    @ApiModelProperty("团队所有人员")
    private List<TeanUserDTO> teamUsers;
}
