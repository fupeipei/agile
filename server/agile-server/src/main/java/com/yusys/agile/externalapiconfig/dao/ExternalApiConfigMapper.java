package com.yusys.agile.externalapiconfig.dao;

import com.yusys.agile.fault.domain.ExternalApiConfig;
import com.yusys.agile.fault.domain.ExternalApiConfigExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface ExternalApiConfigMapper {
    long countByExample(ExternalApiConfigExample example);

    int deleteByExample(ExternalApiConfigExample example);

    int deleteByPrimaryKey(Long id);

    int insert(ExternalApiConfig record);

    int insertSelective(ExternalApiConfig record);

    List<ExternalApiConfig> selectByExample(ExternalApiConfigExample example);

    ExternalApiConfig selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") ExternalApiConfig record, @Param("example") ExternalApiConfigExample example);

    int updateByExample(@Param("record") ExternalApiConfig record, @Param("example") ExternalApiConfigExample example);

    int updateByPrimaryKeySelective(ExternalApiConfig record);

    int updateByPrimaryKey(ExternalApiConfig record);
}