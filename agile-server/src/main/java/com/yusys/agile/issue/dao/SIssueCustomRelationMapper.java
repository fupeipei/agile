package com.yusys.agile.issue.dao;

import com.yusys.agile.issue.domain.SIssueCustomRelation;
import com.yusys.agile.issue.domain.SIssueCustomRelationExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SIssueCustomRelationMapper {
    long countByExample(SIssueCustomRelationExample example);

    int deleteByExample(SIssueCustomRelationExample example);

    int deleteByPrimaryKey(Long id);

    int insert(SIssueCustomRelation record);

    int insertSelective(SIssueCustomRelation record);

    List<SIssueCustomRelation> selectByExampleWithBLOBs(SIssueCustomRelationExample example);

    List<SIssueCustomRelation> selectByExample(SIssueCustomRelationExample example);

    SIssueCustomRelation selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") SIssueCustomRelation record, @Param("example") SIssueCustomRelationExample example);

    int updateByExampleWithBLOBs(@Param("record") SIssueCustomRelation record, @Param("example") SIssueCustomRelationExample example);

    int updateByExample(@Param("record") SIssueCustomRelation record, @Param("example") SIssueCustomRelationExample example);

    int updateByPrimaryKeySelective(SIssueCustomRelation record);

    int updateByPrimaryKeyWithBLOBs(SIssueCustomRelation record);

    int updateByPrimaryKey(SIssueCustomRelation record);

    int deleteByProjectIdAndIssueType(@Param("projectId") Long projectId, @Param("issueType") Byte issueType);

    List<Long> getAppliedByissueType(@Param("projectId") Long projectId, @Param("issueType") Byte issueType);

}