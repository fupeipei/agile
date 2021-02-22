package com.yusys.agile.issue.dao;

import com.yusys.agile.issue.domain.IssueSystemRelp;
import com.yusys.agile.issue.domain.IssueSystemRelpExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IssueSystemRelpMapper {
    long countByExample(IssueSystemRelpExample example);

    int deleteByExample(IssueSystemRelpExample example);

    int deleteByPrimaryKey(Long relationId);

    int insert(IssueSystemRelp record);

    int insertSelective(IssueSystemRelp record);

    List<IssueSystemRelp> selectByExample(IssueSystemRelpExample example);

    IssueSystemRelp selectByPrimaryKey(Long relationId);

    int updateByExampleSelective(@Param("record") IssueSystemRelp record, @Param("example") IssueSystemRelpExample example);

    int updateByExample(@Param("record") IssueSystemRelp record, @Param("example") IssueSystemRelpExample example);

    int updateByPrimaryKeySelective(IssueSystemRelp record);

    int updateByPrimaryKey(IssueSystemRelp record);

    /**
     * @Date: 2021/2/13 11:07
     * @Description: 批量插入
     * @Param: * @param systemIds
     * @Return: void
     */
    void batchInsert(@Param("systemIds") List<IssueSystemRelp> systemIds, @Param("issueId") Long issueId);

    void deleteByIssueId(Long issueId);
}