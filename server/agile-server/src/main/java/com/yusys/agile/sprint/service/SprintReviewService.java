package com.yusys.agile.sprint.service;

import com.yusys.agile.sprint.domain.SprintAttachment;
import com.yusys.agile.sprint.dto.SprintAttachmentDTO;
import com.yusys.agile.sprint.dto.SprintReviewDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 *    maxp
 * @Date 2020/5/25
 */
public interface SprintReviewService {
    /**
     * @param sprintReviewDTO
     *    maxp
     * @Date 2020/5/25
     * @Description 新增迭代回顾信息
     * @Return int
     */
    int createSprintReview(SprintReviewDTO sprintReviewDTO);

    /**
     * @param sprintId
     *    maxp
     * @Date 2020/5/25
     * @Description 获取迭代回顾信息列表
     * @Return java.util.List<com.yusys.agile.sprint.dto.SprintReviewDTO>
     */
    List<SprintReviewDTO> getSprintReviewList(Long sprintId);

    /**
     * @param sprintReviewDTO
     *    maxp
     * @Date 2020/5/25
     * @Description 编辑迭代回顾信息
     * @Return int
     */
    int editSprintReview(SprintReviewDTO sprintReviewDTO);

    /**
     * @param reviewId
     *    maxp
     * @Date 2020/5/25
     * @Description 删除迭代回顾信息
     * @Return int
     */
    int deleteSprintReview(Long reviewId);

    /**
     * @param file
     * @param sprintId
     *    maxp
     * @Date 2020/5/26
     * @Description 迭代回顾附件信息上传及保存
     * @Return int
     */
    SprintAttachment uploadAttachment(MultipartFile file, Long sprintId);

    /**
     *    maxp
     * @Date 2020/5/26
     * @Description 通过迭代id删除迭代附件
     * @param sprintId
     * @Return void
     */
    void deleteAttachmentBySprintId(Long sprintId);

    /**
     * @param sprintId
     *    maxp
     * @Date 2020/5/26
     * @Description 获取迭代回顾附件信息
     * @Return java.util.List<com.yusys.agile.sprint.dto.SprintAttachmentDTO>
     */
    List<SprintAttachmentDTO> getSprintAttachmentList(Long sprintId);

    /**
     *    maxp
     * @Date 2020/5/26
     * @Description 通过迭代id删除迭代回顾信息
     * @param sprintId
     * @Return void
     */
    void deleteSprintReviewBySprintId(Long sprintId);

    /**
     *    maxp
     * @Date 2020/5/27
     * @Description 通过附件id删除附件
     * @param attachmentId
     * @Return int
     */
    int deleteAttachment(Long attachmentId);
}
