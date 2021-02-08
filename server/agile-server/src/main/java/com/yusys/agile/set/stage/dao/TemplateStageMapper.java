package com.yusys.agile.set.stage.dao;

import com.yusys.agile.set.stage.domain.TemplateStage;
import com.yusys.agile.set.stage.domain.TemplateStageExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TemplateStageMapper {
    long countByExample(TemplateStageExample example);

    int deleteByExample(TemplateStageExample example);

    int deleteByPrimaryKey(Long configId);

    int insert(TemplateStage record);

    int insertSelective(TemplateStage record);

    List<TemplateStage> selectByExample(TemplateStageExample example);

    TemplateStage selectByPrimaryKey(Long configId);

    int updateByExampleSelective(@Param("record") TemplateStage record, @Param("example") TemplateStageExample example);

    int updateByExample(@Param("record") TemplateStage record, @Param("example") TemplateStageExample example);

    int updateByPrimaryKeySelective(TemplateStage record);

    int updateByPrimaryKey(TemplateStage record);

    int batchInsert(@Param("list") List<TemplateStage> records);

    int updateByTemplateStageId(TemplateStage record);

    int updateByTemplateIdSelective(TemplateStage record);
}