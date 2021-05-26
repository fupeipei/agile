package com.yusys.agile.sprintv3.responseModel;

import lombok.Data;

/**
 * 迭代成员工作小时
 *
 * @date 2021/05/25
 */
@Data
public class SprintMembersWorkHours {

    /**
     * 用户id
     */
    private long userId;

    /**
     * 用户帐户
     */
    private String userAccount;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 实际工作量
     */
    private int ActualWorkload;

    /**
     * 剩余的工作量
     */
    private int residueWorkload;

    /**
     * 任务数量
     */
    private int taskNumber;
}
