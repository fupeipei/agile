package com.yusys.agile.issue.dao;

import com.yusys.agile.issue.domain.IssueAcceptance;
import com.yusys.agile.issue.domain.IssueAcceptanceExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface IssueAcceptanceMapper {
    long countByExample(IssueAcceptanceExample example);

    int deleteByExample(IssueAcceptanceExample example);

    int deleteByPrimaryKey(Long acceptanceId);

    int insert(IssueAcceptance record);

    int insertSelective(IssueAcceptance record);

    List<IssueAcceptance> selectByExampleWithBLOBs(IssueAcceptanceExample example);

    List<IssueAcceptance> selectByExample(IssueAcceptanceExample example);

    IssueAcceptance selectByPrimaryKey(Long acceptanceId);

    int updateByExampleSelective(@Param("record") IssueAcceptance record, @Param("example") IssueAcceptanceExample example);

    int updateByExampleWithBLOBs(@Param("record") IssueAcceptance record, @Param("example") IssueAcceptanceExample example);

    int updateByExample(@Param("record") IssueAcceptance record, @Param("example") IssueAcceptanceExample example);

    int updateByPrimaryKeySelective(IssueAcceptance record);

    int updateByPrimaryKeyWithBLOBs(IssueAcceptance record);

    int updateByPrimaryKey(IssueAcceptance record);
}