package com.yusys.agile.review.dao;

import com.yusys.agile.review.domain.ReviewRecord;
import com.yusys.agile.review.domain.ReviewRecordExample;
import java.util.List;

import com.yusys.agile.review.dto.ReviewRecordDTO;
import org.apache.ibatis.annotations.Param;

public interface ReviewRecordMapper {
    long countByExample(ReviewRecordExample example);

    int deleteByExample(ReviewRecordExample example);

    int deleteByPrimaryKey(Long recordId);

    int insert(ReviewRecord record);

    int insertSelective(ReviewRecord record);

    List<ReviewRecord> selectByExample(ReviewRecordExample example);

    ReviewRecord selectByPrimaryKey(Long recordId);

    int updateByExampleSelective(@Param("record") ReviewRecord record, @Param("example") ReviewRecordExample example);

    int updateByExample(@Param("record") ReviewRecord record, @Param("example") ReviewRecordExample example);

    int updateByPrimaryKeySelective(ReviewRecord record);

    int updateByPrimaryKey(ReviewRecord record);

    /**
     * 功能描述: 查询返回dto
     *
     * @date 2021/3/9
     * @param example
     * @return java.util.List<com.yusys.agile.review.dto.ReviewRecordDTO>
     */
    List<ReviewRecordDTO> selectDTOByExample(ReviewRecordExample example);
}