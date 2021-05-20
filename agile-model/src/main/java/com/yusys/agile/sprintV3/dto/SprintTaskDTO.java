package com.yusys.agile.sprintV3.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 迭代任务数
 * @Author zhaofeng
 * @Date 2021/5/12 15:05
 */
@Data
public class SprintTaskDTO {
    @ApiModelProperty("已完成")
    private Integer done;
    @ApiModelProperty("所有")
    private Integer all;
}
