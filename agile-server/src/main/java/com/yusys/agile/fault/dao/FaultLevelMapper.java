package com.yusys.agile.fault.dao;

import com.yusys.agile.fault.domain.FaultLevel;
import com.yusys.agile.fault.domain.FaultLevelExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface FaultLevelMapper {
    long countByExample(FaultLevelExample example);

    int deleteByExample(FaultLevelExample example);

    int deleteByPrimaryKey(Long levelId);

    int insert(FaultLevel record);

    int insertSelective(FaultLevel record);

    List<FaultLevel> selectByExample(FaultLevelExample example);

    FaultLevel selectByPrimaryKey(Long levelId);

    int updateByExampleSelective(@Param("record") FaultLevel record, @Param("example") FaultLevelExample example);

    int updateByExample(@Param("record") FaultLevel record, @Param("example") FaultLevelExample example);

    int updateByPrimaryKeySelective(FaultLevel record);

    int updateByPrimaryKey(FaultLevel record);
}