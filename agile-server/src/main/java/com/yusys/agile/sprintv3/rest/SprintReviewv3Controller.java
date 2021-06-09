package com.yusys.agile.sprintv3.rest;

import com.google.common.base.Preconditions;
import com.yusys.agile.sprint.dto.SprintReviewDTO;
import com.yusys.agile.sprintv3.service.SprintReviewv3Service;
import com.yusys.portal.model.common.dto.ControllerResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * @Author maxp2
 * @Date: 2021/6/8
 */
@RestController
@RequestMapping("/sprint/review")
public class SprintReviewv3Controller {
    @Resource
    private SprintReviewv3Service sprintReviewv3Service;

    /**
     * @Author maxp2
     * @Date 2021/6/8
     * @Description 新增迭代回顾信息
     * @param sprintReviewDTO
     * @Return com.yusys.portal.model.common.dto.ControllerResponse
     */
    @PostMapping("/createSprintReview")
    public ControllerResponse createSprintReview(@RequestBody SprintReviewDTO sprintReviewDTO) {
        Preconditions.checkArgument(sprintReviewDTO.getReviewDesc().length() <= 200, "迭代回顾描述过长，不能大于200");
        int i = sprintReviewv3Service.createSprintReview(sprintReviewDTO);
        if (i == 0) {
            return ControllerResponse.fail("新增迭代回顾信息失败!");
        }
        return ControllerResponse.success("新增迭代回顾信息成功!");
    }

    /**
     * @Author maxp2
     * @Date 2021/6/8
     * @Description 根据迭代id获取迭代回顾信息
     * @param sprintId
     * @Return com.yusys.portal.model.common.dto.ControllerResponse
     */
    @GetMapping("/listSprintReview/{sprintId}")
    public ControllerResponse getSprintReviewList(@PathVariable Long sprintId) {
        return ControllerResponse.success(sprintReviewv3Service.getSprintReviewList(sprintId));
    }

    /**
     * @Author maxp2
     * @Date 2021/6/8
     * @Description 编辑迭代回顾信息
     * @param sprintReviewDTO
     * @Return com.yusys.portal.model.common.dto.ControllerResponse
     */
    @PostMapping("/updateSprintReview")
    public ControllerResponse editSprintReview(@RequestBody SprintReviewDTO sprintReviewDTO) {
        Preconditions.checkArgument(sprintReviewDTO.getReviewDesc().length() <= 200, "迭代回顾描述过长，不能大于200");
        int i = sprintReviewv3Service.editSprintReview(sprintReviewDTO);
        if (i == 0) {
            ControllerResponse.fail("编辑迭代回顾信息失败！");
        }
        return ControllerResponse.success("编辑迭代回顾信息成功！");
    }

    /**
     * @Author maxp2
     * @Date 2021/6/8
     * @Description 删除迭代回顾信息
     * @param reviewId
     * @Return com.yusys.portal.model.common.dto.ControllerResponse
     */
    @DeleteMapping("/deleteSprintReview/{reviewId}")
    public ControllerResponse deleteSprintReview(@PathVariable Long reviewId) {
        int i = sprintReviewv3Service.deleteSprintReview(reviewId);
        if (i == 0) {
            ControllerResponse.fail("删除迭代回顾信息失败！");
        }
        return ControllerResponse.success("删除迭代回顾信息成功！");
    }

    /**
     * @Author maxp2
     * @Date 2021/6/8
     * @Description 迭代回顾附件信息上传及保存
     * @param file
     * @param sprintId
     * @Return com.yusys.portal.model.common.dto.ControllerResponse
     */
    @PostMapping("/uploadAttachment/{sprintId}")
    public ControllerResponse uploadAttachment(@RequestParam("file") MultipartFile file, @PathVariable Long sprintId) {
        return ControllerResponse.success(sprintReviewv3Service.uploadAttachment(file, sprintId));
    }

    /**
     * @Author maxp2
     * @Date 2021/6/8
     * @Description 获取迭代回顾附件信息
     * @param sprintId
     * @Return com.yusys.portal.model.common.dto.ControllerResponse
     */
    @GetMapping("/listSprintAttachment/{sprintId}")
    public ControllerResponse getAttachmentList(@PathVariable Long sprintId) {
        return ControllerResponse.success(sprintReviewv3Service.getSprintAttachmentList(sprintId));
    }

    /**
     * @Author maxp2
     * @Date 2021/6/8
     * @Description 通过附件id删除附件
     * @param attachmentId
     * @Return com.yusys.portal.model.common.dto.ControllerResponse
     */
    @DeleteMapping("/deleteAttachment/{attachmentId}")
    public ControllerResponse deleteAttachment(@PathVariable Long attachmentId) {
        int i = sprintReviewv3Service.deleteAttachment(attachmentId);
        if (i == 0) {
            ControllerResponse.fail("附件删除失败！");
        }
        return ControllerResponse.success("附件删除成功！");
    }
}
