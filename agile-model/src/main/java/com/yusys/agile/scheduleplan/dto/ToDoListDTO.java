package com.yusys.agile.scheduleplan.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 *  @Description: 待办事项实体
 *  @author: zhao_yd
 *  @Date: 2021/7/20 3:20 下午
 *
 */

@Data
public class ToDoListDTO {

    @ApiModelProperty("关联Id")
    private Long relateId;

    @ApiModelProperty("是否处理 ：0 未处理 1 已处理")
    private Byte isHandle;

    @ApiModelProperty("Epic主键")
    private Long epicId;

    @ApiModelProperty("标题")
    private String title;

    @ApiModelProperty("创建时间")
    private Date createDate;

    @ApiModelProperty("申请人")
    private Long createUid;

    @ApiModelProperty("申请人名称")
    private String createUserName;

    @ApiModelProperty("申请人账号")
    private String createUserAccount;
}
