package com.yusys.agile.issue.dao;

import com.yusys.agile.issue.domain.IssueRichtext;
import com.yusys.agile.issue.domain.IssueRichtextExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IssueRichtextMapper {
    long countByExample(IssueRichtextExample example);

    int deleteByExample(IssueRichtextExample example);

    int deleteByPrimaryKey(Long id);

    int insert(IssueRichtext record);

    int insertSelective(IssueRichtext record);

    List<IssueRichtext> selectByExampleWithBLOBs(IssueRichtextExample example);

    List<IssueRichtext> selectByExample(IssueRichtextExample example);

    IssueRichtext selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") IssueRichtext record, @Param("example") IssueRichtextExample example);

    int updateByExampleWithBLOBs(@Param("record") IssueRichtext record, @Param("example") IssueRichtextExample example);

    int updateByExample(@Param("record") IssueRichtext record, @Param("example") IssueRichtextExample example);

    int updateByPrimaryKeySelective(IssueRichtext record);

    int updateByPrimaryKeyWithBLOBs(IssueRichtext record);

    int updateByPrimaryKey(IssueRichtext record);
}