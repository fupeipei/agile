package com.yusys.agile.issuev3.dao;

import com.yusys.agile.issuev3.domain.SIssueTemplate;
import com.yusys.agile.issuev3.domain.SIssueTemplateExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SIssueTemplateMapper {
    long countByExample(SIssueTemplateExample example);

    int deleteByExample(SIssueTemplateExample example);

    int deleteByPrimaryKey(Long issueTemplateId);

    int insert(SIssueTemplate record);

    int insertSelective(SIssueTemplate record);

    List<SIssueTemplate> selectByExampleWithBLOBs(SIssueTemplateExample example);

    List<SIssueTemplate> selectByExample(SIssueTemplateExample example);

    SIssueTemplate selectByPrimaryKey(Long issueTemplateId);

    int updateByExampleSelective(@Param("record") SIssueTemplate record, @Param("example") SIssueTemplateExample example);

    int updateByExampleWithBLOBs(@Param("record") SIssueTemplate record, @Param("example") SIssueTemplateExample example);

    int updateByExample(@Param("record") SIssueTemplate record, @Param("example") SIssueTemplateExample example);

    int updateByPrimaryKeySelective(SIssueTemplate record);

    int updateByPrimaryKeyWithBLOBs(SIssueTemplate record);

    int updateByPrimaryKey(SIssueTemplate record);
}