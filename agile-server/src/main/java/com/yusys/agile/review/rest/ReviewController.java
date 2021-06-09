package com.yusys.agile.review.rest;

import com.yusys.agile.review.dto.ReviewDTO;
import com.yusys.agile.review.dto.ReviewRecordDTO;
import com.yusys.agile.review.service.ReviewService;
import com.yusys.portal.model.common.dto.ControllerResponse;
import com.yusys.portal.model.facade.dto.SecurityDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 评审controller
 *
 * @create 2020-09-08 09:51
 */
@RestController
@RequestMapping("/review")
public class ReviewController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReviewController.class);

    @Autowired
    private ReviewService reviewService;

    /**
     * 功能描述: 新增评审
     *
     * @param reviewDTO
     * @return com.yusys.portal.model.common.dto.ControllerResponse
     * @date 2021/3/8
     */
    @PostMapping("/addReview")
    public ControllerResponse addReview(@RequestBody ReviewDTO reviewDTO) {

        reviewService.addReview(reviewDTO);
        return ControllerResponse.success();
    }

    /**
     * 功能描述: 取消评审
     * @return com.yusys.portal.model.common.dto.ControllerResponse
     * @date 2021/3/8
     */
    @GetMapping("/cancelReview/{reviewId}")
    public ControllerResponse cancelReview(@PathVariable("reviewId") Long reviewId,SecurityDTO securityDTO) {

        reviewService.cancelReview(reviewId, securityDTO.getUserId());
        return ControllerResponse.success();
    }

    /**
     * 功能描述: 进行评审
     *
     * @return com.yusys.portal.model.common.dto.ControllerResponse
     * @date 2021/3/8
     */
    @PostMapping("/executeReview")
    public ControllerResponse executeReview(@RequestBody ReviewRecordDTO reviewRecordDTO,
                                            SecurityDTO securityDTO) {
        reviewService.executeReview(reviewRecordDTO, securityDTO.getUserId());
        return ControllerResponse.success();
    }


    /**
     * 功能描述: 查询评审的列表
     *
     * @param issueId
     * @return com.yusys.portal.model.common.dto.ControllerResponse
     * @date 2021/3/9
     */
    @GetMapping("/listReview/{issueId}")
    public ControllerResponse listReview(@PathVariable("issueId") Long issueId) {
        return ControllerResponse.success(reviewService.listReview(issueId));
    }

    /**
     * 功能描述:查询评审信息
     *
     * @return com.yusys.portal.model.common.dto.ControllerResponse
     * @date 2021/3/9
     */
    @GetMapping("/getReview/{reviewId}")
    public ControllerResponse getReview(@PathVariable("reviewId") Long reviewId) {
        return ControllerResponse.success(reviewService.getReview(reviewId));
    }


    /**
     * 功能描述: 判断是否可以将故事加进迭代
     *
     * @param storyId
     * @return com.yusys.portal.model.common.dto.ControllerResponse
     * @date 2021/3/10
     */
    @GetMapping("/allowStoryInSprint/{storyId}")
    public ControllerResponse allowStoryInSprint(@PathVariable("storyId") Long storyId) {
        return ControllerResponse.success(reviewService.allowStoryInSprint(storyId));
    }


}