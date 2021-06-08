package com.yusys.agile.sprintv3.dao;

import com.yusys.agile.sprintv3.domain.SSprintReview;
import com.yusys.agile.sprintv3.domain.SSprintReviewExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SSprintReviewMapper {
    long countByExample(SSprintReviewExample example);

    int deleteByExample(SSprintReviewExample example);

    int deleteByPrimaryKey(Long reviewId);

    int insert(SSprintReview record);

    int insertSelective(SSprintReview record);

    List<SSprintReview> selectByExample(SSprintReviewExample example);

    SSprintReview selectByPrimaryKey(Long reviewId);

    int updateByExampleSelective(@Param("record") SSprintReview record, @Param("example") SSprintReviewExample example);

    int updateByExample(@Param("record") SSprintReview record, @Param("example") SSprintReviewExample example);

    int updateByPrimaryKeySelective(SSprintReview record);

    int updateByPrimaryKey(SSprintReview record);
}