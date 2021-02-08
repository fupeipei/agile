package com.yusys.agile.review.rest;

import com.yusys.agile.review.dto.ReviewSetDTO;
import com.yusys.agile.review.service.ReviewSetService;
import com.yusys.portal.model.common.dto.ControllerResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RequestMapping("/review")
@RestController
public class ReviewSetController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReviewSetController.class);

    @Resource
    private ReviewSetService reviewSetService;

    /**
     * @description 查询评审设置信息
     *  
     * @date 2020/09/09
     * @param projectId
     * @param issueType
     * @return
     */
    @GetMapping("/getReviewSetInfo/{issueType}")
    public ControllerResponse getReviewSetInfo(@RequestHeader Long projectId, @PathVariable Byte issueType) {
        try {
            return ControllerResponse.success(reviewSetService.getReviewSetInfo(projectId, issueType));
        } catch (Exception e) {
            LOGGER.error("查询评论设置信息异常,异常信息:{}", e.getMessage());
            return ControllerResponse.fail("查询评论设置信息异常");
        }
    }

    /**
     * @description 编辑评审设置信息
     * @param reviewSetDTO
     * @return
     */
    @PostMapping("/editReviewSetInfo")
    public ControllerResponse editReviewSetInfo(@RequestBody ReviewSetDTO reviewSetDTO) {
        try {
            int count = reviewSetService.editReviewSetInfo(reviewSetDTO);
            if (count > 0) {
                return ControllerResponse.success("编辑评审设置信息成功");
            }
            return ControllerResponse.fail("编辑评审设置信息失败");
        } catch (Exception e) {
            LOGGER.error("编辑评审设置信息异常,异常信息:{}", e.getMessage());
            return ControllerResponse.fail("编辑评审设置信息异常");
        }
    }
}
