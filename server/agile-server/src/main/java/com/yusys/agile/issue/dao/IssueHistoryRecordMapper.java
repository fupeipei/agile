package com.yusys.agile.issue.dao;

import com.yusys.agile.issue.domain.IssueHistoryRecord;
import com.yusys.agile.issue.domain.IssueHistoryRecordExample;
import com.yusys.agile.issue.dto.IssueHistoryRecordDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IssueHistoryRecordMapper {
    long countByExample(IssueHistoryRecordExample example);

    int deleteByExample(IssueHistoryRecordExample example);

    int deleteByPrimaryKey(Long recordId);

    int insert(IssueHistoryRecord record);

    int insertSelective(IssueHistoryRecord record);

    List<IssueHistoryRecord> selectByExample(IssueHistoryRecordExample example);

    List<IssueHistoryRecordDTO> selectByExampleDTO(IssueHistoryRecordExample example);

    IssueHistoryRecord selectByPrimaryKey(Long recordId);

    int updateByExampleSelective(@Param("record") IssueHistoryRecord record, @Param("example") IssueHistoryRecordExample example);

    int updateByExampleWithBLOBs(@Param("record") IssueHistoryRecord record, @Param("example") IssueHistoryRecordExample example);

    int updateByExample(@Param("record") IssueHistoryRecord record, @Param("example") IssueHistoryRecordExample example);

    int updateByPrimaryKeySelective(IssueHistoryRecord record);

    int updateByPrimaryKeyWithBLOBs(IssueHistoryRecord record);

    int updateByPrimaryKey(IssueHistoryRecord record);

    int deleteByTaskId(Long issueId);

    int createBatchHistory(@Param("history") List<IssueHistoryRecord> history);
}