package com.yusys.agile.review.service;

import com.yusys.agile.review.dto.ReviewDTO;
import com.yusys.agile.review.dto.ReviewRecordDTO;
import com.yusys.agile.review.dto.ReviewSetDTO;
import com.yusys.agile.review.dto.StoryCheckResultDTO;

import java.util.List;

/**
 * 评审service
 *
 *     
 * @create 2020-09-08 09:55
 */
public interface ReviewService {
    /**
     * 功能描述: 创建评审
     *
     * @param reviewDTO
     * @return void
     *     
     * @date 2020/9/8
     */
    void addReview(ReviewDTO reviewDTO);

    /**
     * 功能描述: 取消评审
     *
     * @param reviewId
     * @return void
     *     
     * @date 2020/9/8
     */
    void cancelReview(Long reviewId, Long operatorId);

    /**
     * 功能描述:进行评审
     *
     * @param reviewRecordDTO
     * @return void
     *     
     * @date 2020/9/8
     */
    void executeReview(ReviewRecordDTO reviewRecordDTO, Long operatorId);

    /**
     * 功能描述: 评审列表
     *
     * @param issueId
     * @return java.util.List<com.yusys.agile.review.dto.ReviewDTO>
     *     
     * @date 2020/9/8
     */
    List<ReviewDTO> listReview(Long issueId);

    /**
     * 功能描述: 查询review信息
     *
     * @param reviewId
     * @return com.yusys.agile.review.dto.ReviewDTO
     *     
     * @date 2020/9/9
     */
    ReviewDTO getReview(Long reviewId);

    /**
     * 功能描述: 判断故事是否允许加入到迭代中
     *
     * @param storyId
     * @return java.lang.Boolean
     *     
     * @date 2020/9/9
     */
    StoryCheckResultDTO allowStoryInSprint(Long storyId, Long projectId);

    /**
     * 功能描述: 获取项目下某类型工作项的评审设置
     *     
     * @date 2020/9/11
     * @param projectId
     * @param issueType
     * @return com.yusys.agile.review.dto.ReviewSetDTO
     */
    ReviewSetDTO getReviewSetDTO(Long projectId, Byte issueType);

    /**
     * 功能描述: 根据评审id获取工作项集合
     * @param reviewIds
     * @return java.util.List<Long>
     *
     * @date 2020/12/1
     */
    List<Long> listByListReviewId(List<Long> reviewIds,String tenantCode);
}
