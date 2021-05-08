package com.yusys.agile.team.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Team列表查询条件
 * @Author zhao
 * @Date 2021/4/22 10:39
 */
@Data
public class TeamQueryDTO {

    @ApiModelProperty("团队名称")
    private String teamName;
    @ApiModelProperty("po名称")
    private String teamPo;
    @ApiModelProperty("sm名称")
    private String teamSm;
    @ApiModelProperty("子系统名称")
    private String systemName;
    @ApiModelProperty("类型，1:我参与的，2:我创建的，3:与我相关的")
    private Integer type;
    @ApiModelProperty("当前页")
    private Integer pageNum;
    @ApiModelProperty("每页显示数")
    private Integer pageSize;
}
