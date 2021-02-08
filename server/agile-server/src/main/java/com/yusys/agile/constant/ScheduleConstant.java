package com.yusys.agile.constant;

public class ScheduleConstant {
    public interface TaskHandler {

        /**
         * @Date: 2020/2/1
         * @Description: 每日燃尽图
         * @Param: * @param null
         * @Return:
         */
        String BURNDOWN_DAY = "burndownDay";

        /**
         * 迭代补偿
         */
        String SPRINT_RECOUP = "sprintRecoup";

        /**
         * 故事补偿
         */
        String STORY_RECOUP = "storyRecoup";

        /**
         * 缺陷解决补偿
         */
        String FAULT_RESOLVED_RECOUP = "faultResolved";

        /**
         * @Date 2020/2/1
         * @Description 迭代修改状态，未开始修改为进行中
         * @param null
         * @Return
         */
        String SPRINT_CHANGE_STATUS = "sprintChangeStatus";

        /**
         * 业务超期
         */
        String ISSUE_OVERDUE = "IssueOverDueTask";

        /**
         * 业务超时
         */
        String ISSUE_OVERTIME = "IssueOverTimeTask";

        String BUSINESS_OVERDUE = "businessOverDueHandler";


        /**
         * 仪表盘图标-获取每日迭代工作项情况
         */
        String DASH_BOARD_DAY = "DashBoardTaskDay";

    }
}
