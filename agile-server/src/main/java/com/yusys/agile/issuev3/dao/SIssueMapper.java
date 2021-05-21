package com.yusys.agile.issuev3.dao;

import com.yusys.agile.issuev3.domain.SIssue;
import com.yusys.agile.issuev3.domain.SIssueExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SIssueMapper {
    long countByExample(SIssueExample example);

    int deleteByExample(SIssueExample example);

    int deleteByPrimaryKey(Long issueId);

    int insert(SIssue record);

    int insertSelective(SIssue record);

    List<SIssue> selectByExample(SIssueExample example);

    SIssue selectByPrimaryKey(Long issueId);

    int updateByExampleSelective(@Param("record") SIssue record, @Param("example") SIssueExample example);

    int updateByExample(@Param("record") SIssue record, @Param("example") SIssueExample example);

    int updateByPrimaryKeySelective(SIssue record);

    int updateByPrimaryKey(SIssue record);
}