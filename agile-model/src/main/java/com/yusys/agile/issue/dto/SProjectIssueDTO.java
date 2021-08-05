package com.yusys.agile.issue.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName SProjectIssueDTO
 * @Description 项目需求列表返回
 * @Author wangpf6
 * @Date 2021/8/3 20:13
 * @Version 1.0
 **/
@Data
public class SProjectIssueDTO {
    @ApiModelProperty("需求id")
    private Long issueId;
    @ApiModelProperty("项目id")
    private Long projectId;
    @ApiModelProperty("项目名称")
    private String projectName;
    @ApiModelProperty("项目代码")
    private String projectCode;
    @ApiModelProperty("需求标题")
    private String demandTitle;
    @ApiModelProperty("产品线")
    private String productLine;
    @ApiModelProperty("系统id")
    private Long systemId;
    @ApiModelProperty("系统名称")
    private String systemName;
    @ApiModelProperty("处理人")
    private String handler;
    @ApiModelProperty("处理人Id")
    private Long handlerId;
}
