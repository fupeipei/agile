package com.yusys.agile.projectmanager.dao;

import com.yusys.agile.projectmanager.domain.SProjectSystemRel;
import com.yusys.agile.projectmanager.domain.SProjectSystemRelExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SProjectSystemRelMapper {
    long countByExample(SProjectSystemRelExample example);

    int deleteByExample(SProjectSystemRelExample example);

    int deleteByPrimaryKey(Long projectSystemRelId);

    int insert(SProjectSystemRel record);

    int insertSelective(SProjectSystemRel record);

    List<SProjectSystemRel> selectByExample(SProjectSystemRelExample example);

    SProjectSystemRel selectByPrimaryKey(Long projectSystemRelId);

    int updateByExampleSelective(@Param("record") SProjectSystemRel record, @Param("example") SProjectSystemRelExample example);

    int updateByExample(@Param("record") SProjectSystemRel record, @Param("example") SProjectSystemRelExample example);

    int updateByPrimaryKeySelective(SProjectSystemRel record);

    int updateByPrimaryKey(SProjectSystemRel record);

    void batchInsertProjectSystemRelMapper(@Param("sProjectSystemRels")List<SProjectSystemRel> sProjectSystemRels);

}