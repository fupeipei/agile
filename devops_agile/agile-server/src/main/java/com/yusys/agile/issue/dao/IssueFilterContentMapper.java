package com.yusys.agile.issue.dao;

import com.yusys.agile.issue.domain.IssueFilterContent;
import com.yusys.agile.issue.domain.IssueFilterContentExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IssueFilterContentMapper {
    long countByExample(IssueFilterContentExample example);

    int deleteByExample(IssueFilterContentExample example);

    int deleteByPrimaryKey(Long id);

    int insert(IssueFilterContent record);

    int insertSelective(IssueFilterContent record);

    List<IssueFilterContent> selectByExampleWithBLOBs(IssueFilterContentExample example);

    List<IssueFilterContent> selectByExample(IssueFilterContentExample example);

    IssueFilterContent selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") IssueFilterContent record, @Param("example") IssueFilterContentExample example);

    int updateByExampleWithBLOBs(@Param("record") IssueFilterContent record, @Param("example") IssueFilterContentExample example);

    int updateByExample(@Param("record") IssueFilterContent record, @Param("example") IssueFilterContentExample example);

    int updateByPrimaryKeySelective(IssueFilterContent record);

    int updateByPrimaryKeyWithBLOBs(IssueFilterContent record);

    int updateByPrimaryKey(IssueFilterContent record);
}