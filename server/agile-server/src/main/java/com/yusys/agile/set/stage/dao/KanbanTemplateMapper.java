package com.yusys.agile.set.stage.dao;

import com.yusys.agile.set.stage.domain.KanbanTemplate;
import com.yusys.agile.set.stage.domain.KanbanTemplateExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface KanbanTemplateMapper {
    long countByExample(KanbanTemplateExample example);

    int deleteByExample(KanbanTemplateExample example);

    int deleteByPrimaryKey(Long templateId);

    int insert(KanbanTemplate record);

    int insertSelective(KanbanTemplate record);

    List<KanbanTemplate> selectByExample(KanbanTemplateExample example);

    KanbanTemplate selectByPrimaryKey(Long templateId);

    int updateByExampleSelective(@Param("record") KanbanTemplate record, @Param("example") KanbanTemplateExample example);

    int updateByExample(@Param("record") KanbanTemplate record, @Param("example") KanbanTemplateExample example);

    int updateByPrimaryKeySelective(KanbanTemplate record);

    int updateByPrimaryKey(KanbanTemplate record);
}