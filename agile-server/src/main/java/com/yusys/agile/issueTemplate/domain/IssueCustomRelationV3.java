package com.yusys.agile.issueTemplate.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
    * 自定义字段issue关联关系表
    */
@ApiModel(value="IssueTemplateV3-domain-IssueCustomRelationV3")
@Data
public class IssueCustomRelationV3 implements Serializable {
    @ApiModelProperty(value="")
    private Long id;

    /**
    * 类型 1:epic 2:feature 3:story 4:task 5:fault
    */
    @ApiModelProperty(value="类型 1:epic 2:feature 3:story 4:task 5:fault")
    private Integer issueType;

    /**
    * 自定义字段id
    */
    @ApiModelProperty(value="自定义字段id")
    private Long fieldId;

    /**
    * 自定义字段名
    */
    @ApiModelProperty(value="自定义字段名")
    private String fieldName;

    /**
    * 是否必填 U必填 E不必填
    */
    @ApiModelProperty(value="是否必填 U必填 E不必填")
    private String required;

    /**
    * 0：number,1：text,2：time_date,3：select,4：tree,5：date,6：textarea
    */
    @ApiModelProperty(value="0：number,1：text,2：time_date,3：select,4：tree,5：date,6：textarea")
    private Integer fieldType;

    /**
    * 排序
    */
    @ApiModelProperty(value="排序")
    private Integer sort;

    /**
    * 自定义字段内容
    */
    @ApiModelProperty(value="自定义字段内容")
    private String fieldContent;

    @ApiModelProperty(value="")
    private Long projectId;

    /**
    * 系统id
    */
    @ApiModelProperty(value="系统id")
    private Long systemId;

    /**
    * 数据有效状态 U：正常 E：失效
    */
    @ApiModelProperty(value="数据有效状态 U：正常 E：失效")
    private String state;

    @ApiModelProperty(value="")
    private Date createTime;

    @ApiModelProperty(value="")
    private Long createUid;

    @ApiModelProperty(value="")
    private Long updateUid;

    @ApiModelProperty(value="")
    private Date updateTime;

    @ApiModelProperty(value="")
    private String tenantCode;

    private static final long serialVersionUID = 1L;
}