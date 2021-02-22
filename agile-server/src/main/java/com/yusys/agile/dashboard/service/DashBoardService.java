package com.yusys.agile.dashboard.service;

public interface DashBoardService {

    /**
     * @param
     * @Date 2021/2/1
     * @Description 迭代中每日工作项状态个数统计
     * @Return void
     */
    void calculateIssueStatus();

    /**
     * @param
     * @Date 2021/2/1
     * @Description 项目中每日工作项状态个数统计
     * @Return void
     */
    void calculateProjectStatus();
}
