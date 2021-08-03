package com.yusys.agile.projectmanager.dao;

import com.yusys.agile.projectmanager.domain.SProjectUserHour;
import com.yusys.agile.projectmanager.domain.SProjectUserHourExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SProjectUserHourMapper {
    long countByExample(SProjectUserHourExample example);

    int deleteByExample(SProjectUserHourExample example);

    int deleteByPrimaryKey(Long hourId);

    int insert(SProjectUserHour record);

    int insertSelective(SProjectUserHour record);

    List<SProjectUserHour> selectByExampleWithBLOBs(SProjectUserHourExample example);

    List<SProjectUserHour> selectByExample(SProjectUserHourExample example);

    SProjectUserHour selectByPrimaryKey(Long hourId);

    int updateByExampleSelective(@Param("record") SProjectUserHour record, @Param("example") SProjectUserHourExample example);

    int updateByExampleWithBLOBs(@Param("record") SProjectUserHour record, @Param("example") SProjectUserHourExample example);

    int updateByExample(@Param("record") SProjectUserHour record, @Param("example") SProjectUserHourExample example);

    int updateByPrimaryKeySelective(SProjectUserHour record);

    int updateByPrimaryKeyWithBLOBs(SProjectUserHour record);

    int updateByPrimaryKey(SProjectUserHour record);
}