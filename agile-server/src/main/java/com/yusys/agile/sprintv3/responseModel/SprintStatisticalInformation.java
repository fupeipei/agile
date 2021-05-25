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
     * 用户故事
     */
    int userStory;

    /**
     * 用户故事和
     */
    int userStorySum;

    /**
     * 故事点
     */
    int storyPoint;

    /**
     * 故事点和
     */
    int storyPointSum;

    /**
     * 工作量
     */
    int workload;

    /**
     * 工作量和
     */
    int workloadSum;

    /**
     * 任务
     */
    int task;

    /**
     * 任务和
     */
    int taskSum;
}
