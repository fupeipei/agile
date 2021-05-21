package com.yusys.agile.issuev3.dao;

import com.yusys.agile.issuev3.domain.SIssueHistoryRecord;
import com.yusys.agile.issuev3.domain.SIssueHistoryRecordExample;
import com.yusys.agile.issuev3.domain.SIssueHistoryRecordWithBLOBs;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SIssueHistoryRecordMapper {
    long countByExample(SIssueHistoryRecordExample example);

    int deleteByExample(SIssueHistoryRecordExample example);

    int deleteByPrimaryKey(Long recordId);

    int insert(SIssueHistoryRecordWithBLOBs record);

    int insertSelective(SIssueHistoryRecordWithBLOBs record);

    List<SIssueHistoryRecordWithBLOBs> selectByExampleWithBLOBs(SIssueHistoryRecordExample example);

    List<SIssueHistoryRecord> selectByExample(SIssueHistoryRecordExample example);

    SIssueHistoryRecordWithBLOBs selectByPrimaryKey(Long recordId);

    int updateByExampleSelective(@Param("record") SIssueHistoryRecordWithBLOBs record, @Param("example") SIssueHistoryRecordExample example);

    int updateByExampleWithBLOBs(@Param("record") SIssueHistoryRecordWithBLOBs record, @Param("example") SIssueHistoryRecordExample example);

    int updateByExample(@Param("record") SIssueHistoryRecord record, @Param("example") SIssueHistoryRecordExample example);

    int updateByPrimaryKeySelective(SIssueHistoryRecordWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(SIssueHistoryRecordWithBLOBs record);

    int updateByPrimaryKey(SIssueHistoryRecord record);
}