package com.yusys.agile.review.service;

import com.yusys.agile.review.dto.ReviewSetDTO;

public interface ReviewSetService {

    /**
     * @descripiton 查询评审设置信息
     *  
     * @date 2020/09/09
     * @param projectId
     * @param issueType
     * @return
     */
    ReviewSetDTO getReviewSetInfo(Long projectId, Byte issueType);

    /**
     * @descripiton 编辑评审设置信息
     *  
     * @date 2020/09/09
     * @param reviewSetDTO
     * @return
     */
    int editReviewSetInfo(ReviewSetDTO reviewSetDTO);
}
