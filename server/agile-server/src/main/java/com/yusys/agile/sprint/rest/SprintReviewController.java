package com.yusys.agile.sprint.rest;

import com.yusys.portal.model.common.dto.ControllerResponse;
import com.google.common.base.Preconditions;
import com.yusys.agile.sprint.dto.SprintReviewDTO;
import com.yusys.agile.sprint.service.SprintReviewService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * @Date 2021/2/25
 */
@RestController
@RequestMapping("/sprint/review")
public class SprintReviewController {
    @Resource
    private SprintReviewService sprintReviewService;

    /**
     * @param sprintReviewDTO
     * @Date 2021/2/25
     * @Description 新增迭代回顾信息
     * @return com.yusys.portal.model.common.dto.ControllerResponse
     */
    @PostMapping("/createSprintReview")
    public ControllerResponse createSprintReview(@RequestBody SprintReviewDTO sprintReviewDTO) {
        Preconditions.checkArgument(sprintReviewDTO.getReviewDesc().length() <= 200, "迭代回顾描述过长，不能大于200");
        int i = sprintReviewService.createSprintReview(sprintReviewDTO);
        if (i == 0) {
            return ControllerResponse.fail("新增迭代回顾信息失败!");
        }
        return ControllerResponse.success("新增迭代回顾信息成功!");
    }

    /**
     * @param sprintId
     * @Date 2021/2/25
     * @Description 根据迭代id获取迭代回顾信息
     * @return com.yusys.portal.model.common.dto.ControllerResponse
     */
    @GetMapping("/listSprintReview/{sprintId}")
    public ControllerResponse getSprintReviewList(@PathVariable Long sprintId) {
        return ControllerResponse.success(sprintReviewService.getSprintReviewList(sprintId));
    }

    /**
     * @param sprintReviewDTO
     * @Date 2021/2/25
     * @Description 编辑迭代回顾信息
     * @return com.yusys.portal.model.common.dto.ControllerResponse
     */
    @PostMapping("/updateSprintReview")
    public ControllerResponse editSprintReview(@RequestBody SprintReviewDTO sprintReviewDTO) {
        Preconditions.checkArgument(sprintReviewDTO.getReviewDesc().length() <= 200, "迭代回顾描述过长，不能大于200");
        int i = sprintReviewService.editSprintReview(sprintReviewDTO);
        if (i == 0) {
            ControllerResponse.fail("编辑迭代回顾信息失败！");
        }
        return ControllerResponse.success("编辑迭代回顾信息成功！");
    }

    /**
     * @param reviewId
     * @Date 2021/2/25
     * @Description 删除迭代回顾信息
     * @return com.yusys.portal.model.common.dto.ControllerResponse
     */
    @DeleteMapping("/deleteSprintReview/{reviewId}")
    public ControllerResponse deleteSprintReview(@PathVariable Long reviewId) {
        int i = sprintReviewService.deleteSprintReview(reviewId);
        if (i == 0) {
            ControllerResponse.fail("删除迭代回顾信息失败！");
        }
        return ControllerResponse.success("删除迭代回顾信息成功！");
    }

    /**
     * @param file
     * @param sprintId
     * @Date 2021/2/26
     * @Description 迭代回顾附件信息上传及保存
     * @return com.yusys.portal.model.common.dto.ControllerResponse
     */
    @PostMapping("/uploadAttachment/{sprintId}")
    public ControllerResponse uploadAttachment(@RequestParam("file") MultipartFile file, @PathVariable Long sprintId) {
        return ControllerResponse.success(sprintReviewService.uploadAttachment(file, sprintId));
    }

    /**
     * @param sprintId
     * @Date 2021/2/26
     * @Description 获取迭代回顾附件信息
     * @return com.yusys.portal.model.common.dto.ControllerResponse
     */
    @GetMapping("/listSprintAttachment/{sprintId}")
    public ControllerResponse getAttachmentList(@PathVariable Long sprintId) {
        return ControllerResponse.success(sprintReviewService.getSprintAttachmentList(sprintId));
    }

    /**
     * @param attachmentId
     * @Date 2021/2/27
     * @Description 通过附件id删除附件
     * @return com.yusys.portal.model.common.dto.ControllerResponse
     */
    @DeleteMapping("/deleteAttachment/{attachmentId}")
    public ControllerResponse deleteAttachment(@PathVariable Long attachmentId) {
        int i = sprintReviewService.deleteAttachment(attachmentId);
        if (i == 0) {
            ControllerResponse.fail("附件删除失败！");
        }
        return ControllerResponse.success("附件删除成功！");
    }
}
