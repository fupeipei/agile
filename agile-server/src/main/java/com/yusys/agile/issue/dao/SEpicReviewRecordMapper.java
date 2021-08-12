package com.yusys.agile.issue.dao;

import com.yusys.agile.issue.domain.SEpicReviewRecord;
import com.yusys.agile.issue.domain.SEpicReviewRecordExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SEpicReviewRecordMapper {
    long countByExample(SEpicReviewRecordExample example);

    int deleteByExample(SEpicReviewRecordExample example);

    int deleteByPrimaryKey(Long recordId);

    int insert(SEpicReviewRecord record);

    int insertSelective(SEpicReviewRecord record);

    List<SEpicReviewRecord> selectByExample(SEpicReviewRecordExample example);

    SEpicReviewRecord selectByPrimaryKey(Long recordId);

    int updateByExampleSelective(@Param("record") SEpicReviewRecord record, @Param("example") SEpicReviewRecordExample example);

    int updateByExample(@Param("record") SEpicReviewRecord record, @Param("example") SEpicReviewRecordExample example);

    int updateByPrimaryKeySelective(SEpicReviewRecord record);

    int updateByPrimaryKey(SEpicReviewRecord record);

    void removeEpicReviewRecord(Long recordId);
}