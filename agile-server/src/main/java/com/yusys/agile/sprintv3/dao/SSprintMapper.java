package com.yusys.agile.sprintv3.dao;

import com.yusys.agile.sprintv3.domain.SSprint;
import com.yusys.agile.sprintv3.domain.SSprintExample;
import com.yusys.agile.sprintv3.domain.SSprintWithBLOBs;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SSprintMapper {
    long countByExample(SSprintExample example);

    int deleteByExample(SSprintExample example);

    int deleteByPrimaryKey(Long sprintId);

    int insert(SSprintWithBLOBs record);

    int insertSelective(SSprintWithBLOBs record);

    List<SSprintWithBLOBs> selectByExampleWithBLOBs(SSprintExample example);

    List<SSprint> selectByExample(SSprintExample example);

    SSprintWithBLOBs selectByPrimaryKey(Long sprintId);

    int updateByExampleSelective(@Param("record") SSprintWithBLOBs record, @Param("example") SSprintExample example);

    int updateByExampleWithBLOBs(@Param("record") SSprintWithBLOBs record, @Param("example") SSprintExample example);

    int updateByExample(@Param("record") SSprint record, @Param("example") SSprintExample example);

    int updateByPrimaryKeySelective(SSprintWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(SSprintWithBLOBs record);

    int updateByPrimaryKey(SSprint record);
}