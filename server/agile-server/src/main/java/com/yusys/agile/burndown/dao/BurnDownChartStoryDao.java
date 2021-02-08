package com.yusys.agile.burndown.dao;

import com.yusys.agile.burndown.domain.BurnDownChartStory;
import com.yusys.agile.burndown.dto.BurnDownStory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BurnDownChartStoryDao {
    int create(BurnDownChartStory story);

    List<BurnDownStory> getStorysBySprint(@Param("sprintId") Long sprintId, @Param("planStory") Integer planStory);

    int cancelByStorys(@Param("sprintId") Long sprintId, @Param("storyIds") List<Long> storyIds);
}
