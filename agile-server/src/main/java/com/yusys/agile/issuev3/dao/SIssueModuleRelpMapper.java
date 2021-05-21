package com.yusys.agile.issuev3.dao;

import com.yusys.agile.issuev3.domain.SIssueModuleRelp;
import com.yusys.agile.issuev3.domain.SIssueModuleRelpExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SIssueModuleRelpMapper {
    long countByExample(SIssueModuleRelpExample example);

    int deleteByExample(SIssueModuleRelpExample example);

    int deleteByPrimaryKey(Long relationId);

    int insert(SIssueModuleRelp record);

    int insertSelective(SIssueModuleRelp record);

    List<SIssueModuleRelp> selectByExample(SIssueModuleRelpExample example);

    SIssueModuleRelp selectByPrimaryKey(Long relationId);

    int updateByExampleSelective(@Param("record") SIssueModuleRelp record, @Param("example") SIssueModuleRelpExample example);

    int updateByExample(@Param("record") SIssueModuleRelp record, @Param("example") SIssueModuleRelpExample example);

    int updateByPrimaryKeySelective(SIssueModuleRelp record);

    int updateByPrimaryKey(SIssueModuleRelp record);
}