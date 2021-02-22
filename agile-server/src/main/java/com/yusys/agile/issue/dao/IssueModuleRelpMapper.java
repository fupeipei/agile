package com.yusys.agile.issue.dao;

import com.yusys.agile.issue.domain.IssueModuleRelp;
import com.yusys.agile.issue.domain.IssueModuleRelpExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IssueModuleRelpMapper {
    long countByExample(IssueModuleRelpExample example);

    int deleteByExample(IssueModuleRelpExample example);

    int deleteByPrimaryKey(Long relationId);

    int insert(IssueModuleRelp record);

    int insertSelective(IssueModuleRelp record);

    List<IssueModuleRelp> selectByExample(IssueModuleRelpExample example);

    IssueModuleRelp selectByPrimaryKey(Long relationId);

    int updateByExampleSelective(@Param("record") IssueModuleRelp record, @Param("example") IssueModuleRelpExample example);

    int updateByExample(@Param("record") IssueModuleRelp record, @Param("example") IssueModuleRelpExample example);

    int updateByPrimaryKeySelective(IssueModuleRelp record);

    int updateByPrimaryKey(IssueModuleRelp record);

    /**
     * @param issueId
     * @param moduleIds
     * @Date 2020-06-24
     * @Description 批量插入工作项和模块关联关系
     */
    void batchInsert(@Param("moduleIds") List<IssueModuleRelp> moduleIds, @Param("issueId") Long issueId);

    /**
     * @param issueId
     * @Date 2020-06-24
     * @Description 删除工作项相关模块信息
     */
    void deleteByIssueId(Long issueId);
}