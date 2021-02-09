package com.yusys.agile.sprint.dao;

import com.yusys.agile.sprint.domain.UserSprintHour;
import com.yusys.agile.sprint.domain.UserSprintHourExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface UserSprintHourMapper {
    long countByExample(UserSprintHourExample example);

    int deleteByExample(UserSprintHourExample example);

    int deleteByPrimaryKey(Long hourId);

    int insert(UserSprintHour record);

    int insertSelective(UserSprintHour record);

    List<UserSprintHour> selectByExample(UserSprintHourExample example);

    UserSprintHour selectByPrimaryKey(Long hourId);

    int updateByExampleSelective(@Param("record") UserSprintHour record, @Param("example") UserSprintHourExample example);

    int updateByExample(@Param("record") UserSprintHour record, @Param("example") UserSprintHourExample example);

    int updateByPrimaryKeySelective(UserSprintHour record);

    int updateByPrimaryKey(UserSprintHour record);

    int deleteBySprintId(Long sprintId);

    List<UserSprintHour> getUserIds4Sprint(Long sprintId);

    /**
     * 需求编码：task_200410_175857
     * 描述: 编辑业务需求
     */
    int countSprintUser4Sprint(Long sprintId);
}