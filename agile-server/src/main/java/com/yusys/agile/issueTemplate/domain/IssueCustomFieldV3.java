package com.yusys.agile.issueTemplate.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * issue自定义字段内容表
 */
@ApiModel(value = "IssueTemplateV3-domain-IssueCustomFieldV3")
@Data
public class IssueCustomFieldV3 implements Serializable {
    @ApiModelProperty(value = "")
    private Long extendId;

    /**
     * 关联表主键
     */
    @ApiModelProperty(value = "关联表主键")
    private Long fieldId;

    /**
     * issue输入自定义字段内容
     */
    @ApiModelProperty(value = "issue输入自定义字段内容")
    private String fieldValue;

    @ApiModelProperty(value = "")
    private Long issueId;

    /**
     * 数据有效状态 U：正常 E：失效
     */
    @ApiModelProperty(value = "数据有效状态 U：正常 E：失效")
    private String state;

    @ApiModelProperty(value = "")
    private Date createTime;

    @ApiModelProperty(value = "")
    private Long createUid;

    @ApiModelProperty(value = "")
    private Date updateTime;

    @ApiModelProperty(value = "")
    private Long updateUid;

    /**
     * 租户code
     */
    @ApiModelProperty(value = "租户code")
    private String tenantCode;

    private static final long serialVersionUID = 1L;
}