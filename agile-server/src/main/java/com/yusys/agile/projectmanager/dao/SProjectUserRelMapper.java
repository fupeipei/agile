package com.yusys.agile.projectmanager.dao;

import com.yusys.agile.projectmanager.domain.SProjectUserRel;
import com.yusys.agile.projectmanager.domain.SProjectUserRelExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SProjectUserRelMapper {
    long countByExample(SProjectUserRelExample example);

    int deleteByExample(SProjectUserRelExample example);

    int deleteByPrimaryKey(Long projectUserRelId);

    int insert(SProjectUserRel record);

    int insertSelective(SProjectUserRel record);

    List<SProjectUserRel> selectByExample(SProjectUserRelExample example);

    SProjectUserRel selectByPrimaryKey(Long projectUserRelId);

    int updateByExampleSelective(@Param("record") SProjectUserRel record, @Param("example") SProjectUserRelExample example);

    int updateByExample(@Param("record") SProjectUserRel record, @Param("example") SProjectUserRelExample example);

    int updateByPrimaryKeySelective(SProjectUserRel record);

    int updateByPrimaryKey(SProjectUserRel record);

    void batchInsertProjectUsers(@Param("projectUserRels")  List<SProjectUserRel> projectUserRels);
}