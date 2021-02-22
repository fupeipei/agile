package com.yusys.agile.issue.dao;

import com.yusys.agile.issue.domain.IssueFilterRelatedChecked;
import com.yusys.agile.issue.domain.IssueFilterRelatedCheckedExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IssueFilterRelatedCheckedMapper {
    long countByExample(IssueFilterRelatedCheckedExample example);

    int deleteByExample(IssueFilterRelatedCheckedExample example);

    int deleteByPrimaryKey(Long id);

    int insert(IssueFilterRelatedChecked record);

    int insertSelective(IssueFilterRelatedChecked record);

    List<IssueFilterRelatedChecked> selectByExample(IssueFilterRelatedCheckedExample example);

    IssueFilterRelatedChecked selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") IssueFilterRelatedChecked record, @Param("example") IssueFilterRelatedCheckedExample example);

    int updateByExample(@Param("record") IssueFilterRelatedChecked record, @Param("example") IssueFilterRelatedCheckedExample example);

    int updateByPrimaryKeySelective(IssueFilterRelatedChecked record);

    int updateByPrimaryKey(IssueFilterRelatedChecked record);
}