package com.yusys.agile.burndown.service;


import com.yusys.agile.burndown.dto.BurnDownChartDTO;
import com.yusys.agile.burndown.dto.BurnDownStoryDTO;
import com.yusys.agile.burndown.dto.BurnDownTaskDTO;
import com.yusys.agile.burndown.dto.HistogramTaskDTO;

import java.util.List;

public interface BurnDownChartService {

    void calculateWorkload();

    /**
     * 计算迭代周期内的工作量燃尽图
     *
     * @param sprintId 迭代ID
     * @return
     */
    BurnDownChartDTO getBySprint(Long sprintId);

    BurnDownTaskDTO getTasksBySprint(Long sprintId);

    BurnDownStoryDTO getStorysBySprint(Long sprintId);

    void calculateStorys();

    /**
     * 根据迭代ID获取当前迭代内团队成员工作量和任务数
     *
     * @param sprintId 迭代ID
     * @return com.yusys.agile.burndown.dto.HistogramTaskDTO
     */
    List<HistogramTaskDTO> getTaskMemberAnalysis(Long sprintId);
}
