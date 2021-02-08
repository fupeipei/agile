package com.yusys.agile.versionmanager.dao;

import com.yusys.agile.versionmanager.domain.VersionIssueRelate;
import com.yusys.agile.versionmanager.domain.VersionIssueRelateExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface VersionIssueRelateMapper {
    long countByExample(VersionIssueRelateExample example);

    int deleteByExample(VersionIssueRelateExample example);

    int deleteByPrimaryKey(Long relateId);

    int insert(VersionIssueRelate record);

    int insertSelective(VersionIssueRelate record);

    List<VersionIssueRelate> selectByExample(VersionIssueRelateExample example);

    VersionIssueRelate selectByPrimaryKey(Long relateId);

    int updateByExampleSelective(@Param("record") VersionIssueRelate record, @Param("example") VersionIssueRelateExample example);

    int updateByExample(@Param("record") VersionIssueRelate record, @Param("example") VersionIssueRelateExample example);

    int updateByPrimaryKeySelective(VersionIssueRelate record);

    int updateByPrimaryKey(VersionIssueRelate record);
//根据工作项Id查询版本与计划的信息
    VersionIssueRelate selectVersionIssueRelateByIssueId(Long issueId);
}