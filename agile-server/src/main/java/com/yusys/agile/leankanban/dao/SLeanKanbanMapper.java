package com.yusys.agile.leankanban.dao;

import com.yusys.agile.leankanban.domain.SLeanKanban;
import com.yusys.agile.leankanban.domain.SLeanKanbanExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SLeanKanbanMapper {
    long countByExample(SLeanKanbanExample example);

    int deleteByExample(SLeanKanbanExample example);

    int deleteByPrimaryKey(Long kanbanId);

    int insert(SLeanKanban record);

    int insertSelective(SLeanKanban record);

    List<SLeanKanban> selectByExample(SLeanKanbanExample example);

    SLeanKanban selectByPrimaryKey(Long kanbanId);

    int updateByExampleSelective(@Param("record") SLeanKanban record, @Param("example") SLeanKanbanExample example);

    int updateByExample(@Param("record") SLeanKanban record, @Param("example") SLeanKanbanExample example);

    int updateByPrimaryKeySelective(SLeanKanban record);

    int updateByPrimaryKey(SLeanKanban record);
}