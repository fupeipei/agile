package com.yusys.agile.set.stage.dao;

import com.yusys.agile.set.stage.domain.StageTemplateConfig;
import com.yusys.agile.set.stage.domain.StageTemplateConfigExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StageTemplateConfigMapper {
    long countByExample(StageTemplateConfigExample example);

    int deleteByExample(StageTemplateConfigExample example);

    int deleteByPrimaryKey(Long confId);

    int insert(StageTemplateConfig record);

    int insertSelective(StageTemplateConfig record);

    List<StageTemplateConfig> selectByExample(StageTemplateConfigExample example);

    StageTemplateConfig selectByPrimaryKey(Long confId);

    int updateByExampleSelective(@Param("record") StageTemplateConfig record, @Param("example") StageTemplateConfigExample example);

    int updateByExample(@Param("record") StageTemplateConfig record, @Param("example") StageTemplateConfigExample example);

    int updateByPrimaryKeySelective(StageTemplateConfig record);

    int updateByPrimaryKey(StageTemplateConfig record);
}