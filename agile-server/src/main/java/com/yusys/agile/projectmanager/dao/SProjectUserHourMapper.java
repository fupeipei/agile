package com.yusys.agile.projectmanager.dao;

import com.yusys.agile.projectmanager.domain.SProjectUserHour;
import com.yusys.agile.projectmanager.domain.SProjectUserHourExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SProjectUserHourMapper {
    long countByExample(SProjectUserHourExample example);

    int deleteByExample(SProjectUserHourExample example);

    int deleteByPrimaryKey(Integer hourId);

    int insert(SProjectUserHour record);

    int insertSelective(SProjectUserHour record);

    List<SProjectUserHour> selectByExample(SProjectUserHourExample example);

    SProjectUserHour selectByPrimaryKey(Integer hourId);

    int updateByExampleSelective(@Param("record") SProjectUserHour record, @Param("example") SProjectUserHourExample example);

    int updateByExample(@Param("record") SProjectUserHour record, @Param("example") SProjectUserHourExample example);

    int updateByPrimaryKeySelective(SProjectUserHour record);

    int updateByPrimaryKey(SProjectUserHour record);
}