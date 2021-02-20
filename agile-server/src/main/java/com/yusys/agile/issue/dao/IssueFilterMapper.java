package com.yusys.agile.issue.dao;

import com.yusys.agile.issue.domain.IssueFilter;
import com.yusys.agile.issue.domain.IssueFilterExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IssueFilterMapper {
    long countByExample(IssueFilterExample example);

    int deleteByExample(IssueFilterExample example);

    int deleteByPrimaryKey(Long filterId);

    int insert(IssueFilter record);

    int insertSelective(IssueFilter record);

    List<IssueFilter> selectByExample(IssueFilterExample example);

    IssueFilter selectByPrimaryKey(Long filterId);

    int updateByExampleSelective(@Param("record") IssueFilter record, @Param("example") IssueFilterExample example);

    int updateByExample(@Param("record") IssueFilter record, @Param("example") IssueFilterExample example);

    int updateByPrimaryKeySelective(IssueFilter record);

    int updateByPrimaryKey(IssueFilter record);
}