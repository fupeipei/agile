package com.yusys.agile.scheduleplan.dao;

import com.yusys.agile.scheduleplan.domain.SSchedule;
import com.yusys.agile.scheduleplan.domain.SScheduleExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SScheduleMapper {
    long countByExample(SScheduleExample example);

    int deleteByExample(SScheduleExample example);

    int deleteByPrimaryKey(Long scheduleId);

    int insert(SSchedule record);

    int insertSelective(SSchedule record);

    List<SSchedule> selectByExample(SScheduleExample example);

    SSchedule selectByPrimaryKey(Long scheduleId);

    int updateByExampleSelective(@Param("record") SSchedule record, @Param("example") SScheduleExample example);

    int updateByExample(@Param("record") SSchedule record, @Param("example") SScheduleExample example);

    int updateByPrimaryKeySelective(SSchedule record);

    int updateByPrimaryKey(SSchedule record);
}