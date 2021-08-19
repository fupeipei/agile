package com.yusys.agile.sprintV3.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 迭代v3用户小时dto
 *
 * @date 2021/05/11
 */
@Data
public class SprintV3UserHourDTO implements Serializable {
    private static final long serialVersionUID = -9025863997356458545L;

    /**
     * 小时id
     */
    private Long hourId;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 迭代id
     */
    private Long sprintId;

    /**
     * 实际工作时长
     */
    private Integer reallyHours;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建人
     */
    private Long createUid;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 更新人
     */
    private Long updateUid;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 用户帐户
     */
    private String userAccount;

    /**
     * 租户id
     */
    private String tenantCode;

}
