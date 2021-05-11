package com.yusys.agile.sprintV3.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 封装列表查询条件
 * @Author zhaofeng
 * @Date 2021/5/11 15:48
 */
@Data
public class SprintQueryDTO {
    @ApiModelProperty("迭代名称或编号")
    private String sprint;
    @ApiModelProperty("团队名称或编号")
    private String team;
    @ApiModelProperty("每页显示数")
    private Integer pageSize;
    @ApiModelProperty("当前页号")
    private Integer pageNum;
}
