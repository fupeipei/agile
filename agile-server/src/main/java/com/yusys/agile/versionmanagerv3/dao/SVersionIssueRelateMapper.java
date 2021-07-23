package com.yusys.agile.versionmanagerv3.dao;

import com.yusys.agile.versionmanagerv3.domain.SVersionIssueRelate;
import com.yusys.agile.versionmanagerv3.domain.SVersionIssueRelateExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SVersionIssueRelateMapper {
    long countByExample(SVersionIssueRelateExample example);

    int deleteByExample(SVersionIssueRelateExample example);

    int deleteByPrimaryKey(Long relateId);

    int insert(SVersionIssueRelate record);

    int insertSelective(SVersionIssueRelate record);

    List<SVersionIssueRelate> selectByExample(SVersionIssueRelateExample example);

    SVersionIssueRelate selectByPrimaryKey(Long relateId);

    int updateByExampleSelective(@Param("record") SVersionIssueRelate record, @Param("example") SVersionIssueRelateExample example);

    int updateByExample(@Param("record") SVersionIssueRelate record, @Param("example") SVersionIssueRelateExample example);

    int updateByPrimaryKeySelective(SVersionIssueRelate record);

    int updateByPrimaryKey(SVersionIssueRelate record);
}