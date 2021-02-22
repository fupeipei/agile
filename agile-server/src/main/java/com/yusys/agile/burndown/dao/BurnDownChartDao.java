package com.yusys.agile.burndown.dao;

import com.yusys.agile.burndown.domain.BurnDownChart;
import com.yusys.agile.burndown.dto.BurnDownTask;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BurnDownChartDao {
    int create(BurnDownChart chart);

    List<BurnDownChart> getBySprint(@Param("sprintId") Long sprintId,
                                    @Param("planWorkload") Integer planWorkload);

    int cancel(String taskId);

    List<BurnDownTask> getTasksBySprint(@Param("sprintId") Long sprintId,
                                        @Param("planTask") Integer planTask);


    int cancelByStorys(@Param("sprintId") Long sprintId, @Param("storyIds") List<Long> storyIds);

    int cancelByFaults(@Param("sprintId") Long sprintId, @Param("faultIdList") List<Long> faultIdList);
}
