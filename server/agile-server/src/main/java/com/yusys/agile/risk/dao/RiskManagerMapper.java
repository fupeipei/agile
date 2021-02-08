package com.yusys.agile.risk.dao;

import com.yusys.agile.risk.domain.RiskManager;
import com.yusys.agile.risk.domain.RiskManagerExample;
import com.yusys.agile.risk.dto.RiskManagerDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RiskManagerMapper {
    long countByExample(RiskManagerExample example);

    int deleteByExample(RiskManagerExample example);

    int deleteByPrimaryKey(Long riskId);

    int insert(RiskManager record);

    int insertSelective(RiskManager record);

    List<RiskManager> selectByExample(RiskManagerExample example);

    List<RiskManagerDTO> selectByExampleWithDTO(RiskManagerExample example);

    RiskManager selectByPrimaryKey(Long riskId);

    int updateByExampleSelective(@Param("record") RiskManager record, @Param("example") RiskManagerExample example);

    int updateByExample(@Param("record") RiskManager record, @Param("example") RiskManagerExample example);

    int updateByPrimaryKeySelective(RiskManager record);

    int updateByPrimaryKey(RiskManager record);
}