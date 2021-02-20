package com.yusys.agile.issue.dao;

import com.yusys.agile.issue.domain.IssueSyncFailRecord;
import com.yusys.agile.issue.domain.IssueSyncFailRecordExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IssueSyncFailRecordMapper {
    long countByExample(IssueSyncFailRecordExample example);

    int deleteByExample(IssueSyncFailRecordExample example);

    int deleteByPrimaryKey(Long recordId);

    int insert(IssueSyncFailRecord record);

    int insertSelective(IssueSyncFailRecord record);

    List<IssueSyncFailRecord> selectByExample(IssueSyncFailRecordExample example);

    IssueSyncFailRecord selectByPrimaryKey(Long recordId);

    int updateByExampleSelective(@Param("record") IssueSyncFailRecord record, @Param("example") IssueSyncFailRecordExample example);

    int updateByExample(@Param("record") IssueSyncFailRecord record, @Param("example") IssueSyncFailRecordExample example);

    int updateByPrimaryKeySelective(IssueSyncFailRecord record);

    int updateByPrimaryKey(IssueSyncFailRecord record);

    List<IssueSyncFailRecord> getIssueSyncFailRecordDataList(@Param("startIndex") int startIndex, @Param("pageSize") int pageSize);
}