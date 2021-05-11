package com.yusys.agile.sprintv3.dao;

import com.yusys.agile.sprint.domain.UserSprintHour;
import com.yusys.agile.sprintv3.domain.SSprintUserHour;
import com.yusys.agile.sprintv3.domain.SSprintUserHourExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SSprintUserHourMapper {
    long countByExample(SSprintUserHourExample example);

    int deleteByExample(SSprintUserHourExample example);

    int deleteByPrimaryKey(Long hourId);

    int insert(SSprintUserHour record);

    int insertSelective(SSprintUserHour record);

    List<SSprintUserHour> selectByExample(SSprintUserHourExample example);

    SSprintUserHour selectByPrimaryKey(Long hourId);

    int updateByExampleSelective(@Param("record") SSprintUserHour record, @Param("example") SSprintUserHourExample example);

    int updateByExample(@Param("record") SSprintUserHour record, @Param("example") SSprintUserHourExample example);

    int updateByPrimaryKeySelective(SSprintUserHour record);

    int updateByPrimaryKey(SSprintUserHour record);

    List<UserSprintHour> getUserIds4Sprint(Long sprintId);
}