package com.yusys.agile.issuev3.dao;

import com.yusys.agile.issuev3.domain.SIssueRichtext;
import com.yusys.agile.issuev3.domain.SIssueRichtextExample;
import com.yusys.agile.issuev3.domain.SIssueRichtextWithBLOBs;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SIssueRichtextMapper {
    long countByExample(SIssueRichtextExample example);

    int deleteByExample(SIssueRichtextExample example);

    int deleteByPrimaryKey(Long id);

    int insert(SIssueRichtextWithBLOBs record);

    int insertSelective(SIssueRichtextWithBLOBs record);

    List<SIssueRichtextWithBLOBs> selectByExampleWithBLOBs(SIssueRichtextExample example);

    List<SIssueRichtext> selectByExample(SIssueRichtextExample example);

    SIssueRichtextWithBLOBs selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") SIssueRichtextWithBLOBs record, @Param("example") SIssueRichtextExample example);

    int updateByExampleWithBLOBs(@Param("record") SIssueRichtextWithBLOBs record, @Param("example") SIssueRichtextExample example);

    int updateByExample(@Param("record") SIssueRichtext record, @Param("example") SIssueRichtextExample example);

    int updateByPrimaryKeySelective(SIssueRichtextWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(SIssueRichtextWithBLOBs record);

    int updateByPrimaryKey(SIssueRichtext record);
}