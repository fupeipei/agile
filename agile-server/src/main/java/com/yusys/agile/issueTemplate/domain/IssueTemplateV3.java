package com.yusys.agile.issueTemplate.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@ApiModel(value = "IssueTemplateV3-domain-IssueTemplateV3")
@Data
public class IssueTemplateV3 implements Serializable {
    @ApiModelProperty(value = "")
    private Long issueTemplateId;

    /**
     * 自定义模板名称
     */
    @ApiModelProperty(value = "自定义模板名称")
    private String templateName;

    /**
     * 富文本内容
     */
    @ApiModelProperty(value = "富文本内容")
    private String description;

    /**
     * 类型 1:epic 2:feature 3:story 4:task 5:fault
     */
    @ApiModelProperty(value = "类型 1:epic 2:feature 3:story 4:task 5:fault")
    private Integer issueType;

    /**
     * 0:未应用, 1:应用
     */
    @ApiModelProperty(value = "0:未应用, 1:应用")
    private Integer apply;

    /**
     * 系统id
     */
    @ApiModelProperty(value = "系统id")
    private Long systemId;

    @ApiModelProperty(value = "")
    private Date createTime;

    @ApiModelProperty(value = "")
    private Long createUid;

    @ApiModelProperty(value = "")
    private Long updateUid;

    @ApiModelProperty(value = "")
    private Date updateTime;

    @ApiModelProperty(value = "")
    private Integer tenantCode;

    List<IssueCustomRelationV3> issueCustomRelations;

    private static final long serialVersionUID = 1L;
}