package com.yusys.agile.sprintv3.responseModel;

import lombok.Data;

/**
 * 迭代统计信息
 *
 * @date 2021/05/24
 */
@Data
public class SprintStatisticalInformation {

    /**
     * 已完成用户故事
     */
    int userStory;

    /**
     * 用户故事和
     */
    int userStorySum;

    /**
     * 故事完成度
     */
    double userStoryCompleteness;

    /**
     * 已完成故事点
     */
    int storyPoint;

    /**
     * 故事点和
     */
    int storyPointSum;

    /**
     * 故事点完成度
     */
    double storyPointCompleteness;

    /**
     * 已完成工作量
     */
    int workload;

    /**
     * 工作量和
     */
    int workloadSum;

    /**
     * 工作负载完成度
     */
    double workloadCompleteness;

    /**
     * 已完成任务
     */
    int task;

    /**
     * 任务和
     */
    int taskSum;

    /**
     * task完成度
     */
    double taskCompleteness;
}
