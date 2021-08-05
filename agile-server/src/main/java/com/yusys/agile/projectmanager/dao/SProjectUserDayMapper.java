package com.yusys.agile.projectmanager.dao;

import com.yusys.agile.projectmanager.domain.SProjectUserDay;
import com.yusys.agile.projectmanager.domain.SProjectUserDayExample;
import java.util.List;

import com.yusys.agile.projectmanager.dto.ProjectUserTotalHourDto;
import org.apache.ibatis.annotations.Param;

public interface SProjectUserDayMapper {
    long countByExample(SProjectUserDayExample example);

    int deleteByExample(SProjectUserDayExample example);

    int deleteByPrimaryKey(Long dayId);

    int insert(SProjectUserDay record);

    int insertSelective(SProjectUserDay record);

    List<SProjectUserDay> selectByExampleWithBLOBs(SProjectUserDayExample example);

    List<SProjectUserDay> selectByExample(SProjectUserDayExample example);

    SProjectUserDay selectByPrimaryKey(Long dayId);

    int updateByExampleSelective(@Param("record") SProjectUserDay record, @Param("example") SProjectUserDayExample example);

    int updateByExampleWithBLOBs(@Param("record") SProjectUserDay record, @Param("example") SProjectUserDayExample example);

    int updateByExample(@Param("record") SProjectUserDay record, @Param("example") SProjectUserDayExample example);

    int updateByPrimaryKeySelective(SProjectUserDay record);

    int updateByPrimaryKeyWithBLOBs(SProjectUserDay record);

    int updateByPrimaryKey(SProjectUserDay record);

    List<ProjectUserTotalHourDto> getTotalDays(@Param("projectId") Long projectId, @Param("userId") Long userId,
                                               @Param("startDate") String startDate, @Param("endDate") String endDate);

    Long getTotalReallyWorkloadByProId(Long projectId);
}