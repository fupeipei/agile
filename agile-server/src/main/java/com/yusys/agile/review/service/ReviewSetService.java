package com.yusys.agile.review.service;

import com.yusys.agile.review.dto.ReviewSetDTO;

public interface ReviewSetService {

    /**
     * @param issueType
     * @return
     * @descripiton 查询评审设置信息
     * @date 2020/09/09
     */
    ReviewSetDTO getReviewSetInfo( Byte issueType);

    /**
     * @param reviewSetDTO
     * @return
     * @descripiton 编辑评审设置信息
     * @date 2020/09/09
     */
    int editReviewSetInfo(ReviewSetDTO reviewSetDTO);
}
