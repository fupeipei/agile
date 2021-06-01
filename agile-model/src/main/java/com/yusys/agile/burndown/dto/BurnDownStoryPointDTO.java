package com.yusys.agile.burndown.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @Author zhaofeng
 * @Date 2021/5/26 11:17
 */
@Data
public class BurnDownStoryPointDTO {
    @ApiModelProperty("计划故事点数")
    private Integer planStoryPoint;
    @ApiModelProperty("实际故事点数")
    private Integer actualRemainStoryPoint;
    @ApiModelProperty("每日剩余数据集合")
    private List<BurnDownStoryPoint> storyPoints;
}
