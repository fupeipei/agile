package com.yusys.agile.fault.dao;

import com.yusys.agile.fault.domain.FaultType;
import com.yusys.agile.fault.domain.FaultTypeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface FaultTypeMapper {
    long countByExample(FaultTypeExample example);

    int deleteByExample(FaultTypeExample example);

    int deleteByPrimaryKey(Long typeId);

    int insert(FaultType record);

    int insertSelective(FaultType record);

    List<FaultType> selectByExample(FaultTypeExample example);

    FaultType selectByPrimaryKey(Long typeId);

    int updateByExampleSelective(@Param("record") FaultType record, @Param("example") FaultTypeExample example);

    int updateByExample(@Param("record") FaultType record, @Param("example") FaultTypeExample example);

    int updateByPrimaryKeySelective(FaultType record);

    int updateByPrimaryKey(FaultType record);
}