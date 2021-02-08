package com.yusys.agile.issue.dao;

import com.yusys.agile.issue.domain.IssueRule;
import com.yusys.agile.issue.domain.IssueRuleExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IssueRuleMapper {
    long countByExample(IssueRuleExample example);

    int deleteByExample(IssueRuleExample example);

    int deleteByPrimaryKey(Long ruleId);

    int insert(IssueRule record);

    int insertSelective(IssueRule record);

    List<IssueRule> selectByExample(IssueRuleExample example);

    IssueRule selectByPrimaryKey(Long ruleId);

    int updateByExampleSelective(@Param("record") IssueRule record, @Param("example") IssueRuleExample example);

    int updateByExample(@Param("record") IssueRule record, @Param("example") IssueRuleExample example);

    int updateByPrimaryKeySelective(IssueRule record);

    int updateByPrimaryKey(IssueRule record);

    void batchInsert(@Param("rules") List<IssueRule> insertRules);
}