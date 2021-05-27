package com.yusys.agile.burndown.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @Author zhaofeng
 * @Date 2021/5/26 11:17
 */
@Data
public class BurnDownStoryPoint {
    @ApiModelProperty("迭代时间")
    private Date sprintTime;
    @ApiModelProperty("剩余故事点数")
    private Integer remainStoryPoint;
    @ApiModelProperty("租户编码")
    private String tenantCode;
}
