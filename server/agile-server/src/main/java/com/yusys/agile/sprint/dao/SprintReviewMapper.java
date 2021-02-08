package com.yusys.agile.sprint.dao;

import com.yusys.agile.sprint.domain.SprintReview;
import com.yusys.agile.sprint.domain.SprintReviewExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SprintReviewMapper {
    long countByExample(SprintReviewExample example);

    int deleteByExample(SprintReviewExample example);

    int deleteByPrimaryKey(Long reviewId);

    int insert(SprintReview record);

    int insertSelective(SprintReview record);

    List<SprintReview> selectByExample(SprintReviewExample example);

    SprintReview selectByPrimaryKey(Long reviewId);

    int updateByExampleSelective(@Param("record") SprintReview record, @Param("example") SprintReviewExample example);

    int updateByExample(@Param("record") SprintReview record, @Param("example") SprintReviewExample example);

    int updateByPrimaryKeySelective(SprintReview record);

    int updateByPrimaryKey(SprintReview record);
}