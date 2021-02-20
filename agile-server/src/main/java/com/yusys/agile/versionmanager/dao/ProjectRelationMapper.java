package com.yusys.agile.versionmanager.dao;

import com.yusys.agile.versionmanager.domain.ProjectRelation;
import com.yusys.agile.versionmanager.domain.ProjectRelationExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProjectRelationMapper {
    long countByExample(ProjectRelationExample example);

    int deleteByExample(ProjectRelationExample example);

    int deleteByPrimaryKey(Long id);

    int insert(ProjectRelation record);

    int insertSelective(ProjectRelation record);

    List<ProjectRelation> selectByExample(ProjectRelationExample example);

    ProjectRelation selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") ProjectRelation record, @Param("example") ProjectRelationExample example);

    int updateByExample(@Param("record") ProjectRelation record, @Param("example") ProjectRelationExample example);

    int updateByPrimaryKeySelective(ProjectRelation record);

    int updateByPrimaryKey(ProjectRelation record);

    List<Long> getCmpProjectIdList(Long YuDOProjectId);
}