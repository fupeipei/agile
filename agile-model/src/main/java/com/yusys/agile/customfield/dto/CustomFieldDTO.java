package com.yusys.agile.customfield.dto;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 自定义字段DTO
 *
 * @create 2021/2/1
 */
@Data
public class CustomFieldDTO {
    /**
     * 自定义字段id
     */
    @ApiModelProperty(value = "自定义字段id")
    private Long fieldId;

    /**
     * 自定义字段名
     */
    @ApiModelProperty(value = "自定义字段名")
    private String fieldName;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String comment;

    /**
     * 自定义字段内容
     */
    @ApiModelProperty(value = "自定义字段内容")
    private String fieldContent;

    /**
     * 字段类型 0单选 1多选 2数字 3日期 4文本 5成员
     */
    @ApiModelProperty(value = "字段类型 0单选 1多选 2数字 3日期 4文本 5成员")
    private Integer fieldType;

    /**
     * 数据有效状态 U正常 E失效
     */
    @ApiModelProperty(value = "数据有效状态 U正常 E失效")
    private String state;

    /**
     * 系统id
     */
    @ApiModelProperty(value = "系统id")
    private Long systemId;

    private String category;

    private String required;

    private String apply;

    private Integer sort;

    private String operatorId;

    private String operatorName;

    private Date createDate;

    private Date modifyDate;

    /**
     * 应用类型名称
     */
    private String applyTypeName;

    private Long projectId;


}