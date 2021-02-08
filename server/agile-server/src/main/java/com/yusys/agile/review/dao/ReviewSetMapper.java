package com.yusys.agile.review.dao;

import com.yusys.agile.review.domain.ReviewSet;
import com.yusys.agile.review.domain.ReviewSetExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ReviewSetMapper {
    long countByExample(ReviewSetExample example);

    int deleteByExample(ReviewSetExample example);

    int deleteByPrimaryKey(Long id);

    int insert(ReviewSet record);

    int insertSelective(ReviewSet record);

    List<ReviewSet> selectByExample(ReviewSetExample example);

    ReviewSet selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") ReviewSet record, @Param("example") ReviewSetExample example);

    int updateByExample(@Param("record") ReviewSet record, @Param("example") ReviewSetExample example);

    int updateByPrimaryKeySelective(ReviewSet record);

    int updateByPrimaryKey(ReviewSet record);
}