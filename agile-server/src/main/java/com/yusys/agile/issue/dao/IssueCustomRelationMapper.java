package com.yusys.agile.issue.dao;

import com.yusys.agile.issue.domain.IssueCustomRelation;
import com.yusys.agile.issue.domain.IssueCustomRelationExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface IssueCustomRelationMapper {
    long countByExample(IssueCustomRelationExample example);

    int deleteByExample(IssueCustomRelationExample example);

    int deleteByPrimaryKey(Long id);

    int insert(IssueCustomRelation record);

    int insertSelective(IssueCustomRelation record);

    List<IssueCustomRelation> selectByExampleWithBLOBs(IssueCustomRelationExample example);

    List<IssueCustomRelation> selectByExample(IssueCustomRelationExample example);

    IssueCustomRelation selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") IssueCustomRelation record, @Param("example") IssueCustomRelationExample example);

    int updateByExampleWithBLOBs(@Param("record") IssueCustomRelation record, @Param("example") IssueCustomRelationExample example);

    int updateByExample(@Param("record") IssueCustomRelation record, @Param("example") IssueCustomRelationExample example);

    int updateByPrimaryKeySelective(IssueCustomRelation record);

    int updateByPrimaryKeyWithBLOBs(IssueCustomRelation record);

    int updateByPrimaryKey(IssueCustomRelation record);

    int deleteByProjectIdAndIssueType(@Param("projectId")Long projectId,@Param("issueType")Byte issueType);

    List<Long> getAppliedByissueType(@Param("projectId")Long projectId,@Param("issueType")Byte issueType);

}