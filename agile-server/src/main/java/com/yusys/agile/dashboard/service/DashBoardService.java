package com.yusys.agile.dashboard.service;

public interface DashBoardService {

    /**
     * @Date 2021/2/1
     * @Description 迭代中每日工作项状态个数统计
     * @param
     * @Return void
     */
    void calculateIssueStatus();
    /**
     * @Date 2021/2/1
     * @Description 项目中每日工作项状态个数统计
     * @param
     * @Return void
     */
    void calculateProjectStatus();
}
