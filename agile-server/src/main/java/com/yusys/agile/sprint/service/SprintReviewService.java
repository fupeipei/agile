package com.yusys.agile.sprint.service;

import com.yusys.agile.sprint.domain.SprintAttachment;
import com.yusys.agile.sprint.dto.SprintAttachmentDTO;
import com.yusys.agile.sprint.dto.SprintReviewDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @Date 2021/2/25
 */
public interface SprintReviewService {
    /**
     * @param sprintReviewDTO
     * @Date 2021/2/25
     * @Description 新增迭代回顾信息
     * @Return int
     */
    int createSprintReview(SprintReviewDTO sprintReviewDTO);

    /**
     * @param sprintId
     * @Date 2021/2/25
     * @Description 获取迭代回顾信息列表
     * @Return java.util.List<com.yusys.agile.sprint.dto.SprintReviewDTO>
     */
    List<SprintReviewDTO> getSprintReviewList(Long sprintId);

    /**
     * @param sprintReviewDTO
     * @Date 2021/2/25
     * @Description 编辑迭代回顾信息
     * @Return int
     */
    int editSprintReview(SprintReviewDTO sprintReviewDTO);

    /**
     * @param reviewId
     * @Date 2021/2/25
     * @Description 删除迭代回顾信息
     * @Return int
     */
    int deleteSprintReview(Long reviewId);

    /**
     * @param file
     * @param sprintId
     * @Date 2021/2/26
     * @Description 迭代回顾附件信息上传及保存
     * @Return int
     */
    SprintAttachment uploadAttachment(MultipartFile file, Long sprintId);

    /**
     * @param sprintId
     * @Date 2021/2/26
     * @Description 通过迭代id删除迭代附件
     * @Return void
     */
    void deleteAttachmentBySprintId(Long sprintId);

    /**
     * @param sprintId
     * @Date 2021/2/26
     * @Description 获取迭代回顾附件信息
     * @Return java.util.List<com.yusys.agile.sprint.dto.SprintAttachmentDTO>
     */
    List<SprintAttachmentDTO> getSprintAttachmentList(Long sprintId);

    /**
     * @param sprintId
     * @Date 2021/2/26
     * @Description 通过迭代id删除迭代回顾信息
     * @Return void
     */
    void deleteSprintReviewBySprintId(Long sprintId);

    /**
     * @param attachmentId
     * @Date 2021/2/27
     * @Description 通过附件id删除附件
     * @Return int
     */
    int deleteAttachment(Long attachmentId);
}
