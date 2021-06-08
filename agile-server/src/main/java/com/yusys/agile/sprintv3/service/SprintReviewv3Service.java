package com.yusys.agile.sprintv3.service;

import com.yusys.agile.sprint.dto.SprintAttachmentDTO;
import com.yusys.agile.sprint.dto.SprintReviewDTO;
import com.yusys.agile.sprintv3.domain.SSprintAttachment;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @Author maxp2
 * @Date: 2021/6/8
 */
public interface SprintReviewv3Service {

    /**
     * @Author maxp2
     * @Date 2021/6/8
     * @Description 新增迭代回顾信息
     * @param sprintReviewDTO
     * @Return int
     */
    int createSprintReview(SprintReviewDTO sprintReviewDTO);

    /**
     * @Author maxp2
     * @Date 2021/6/8
     * @Description 获取迭代回顾信息列表
     * @param sprintId
     * @Return java.util.List<com.yusys.agile.sprint.dto.SprintReviewDTO>
     */
    List<SprintReviewDTO> getSprintReviewList(Long sprintId);

    /**
     * @Author maxp2
     * @Date 2021/6/8
     * @Description 编辑迭代回顾信息
     * @param sprintReviewDTO
     * @Return int
     */
    int editSprintReview(SprintReviewDTO sprintReviewDTO);

    /**
     * @Author maxp2
     * @Date 2021/6/8
     * @Description 删除迭代回顾信息
     * @param reviewId
     * @Return int
     */
    int deleteSprintReview(Long reviewId);

    /**
     * @Author maxp2
     * @Date 2021/6/8
     * @Description 迭代回顾附件信息上传及保存
     * @param file
     * @param sprintId
     * @Return com.yusys.agile.sprintv3.domain.SSprintAttachment
     */
    SSprintAttachment uploadAttachment(MultipartFile file, Long sprintId);

    /**
     * @Author maxp2
     * @Date 2021/6/8
     * @Description 通过迭代id删除迭代附件
     * @param sprintId
     * @Return void
     */
    void deleteAttachmentBySprintId(Long sprintId);

    /**
     * @Author maxp2
     * @Date 2021/6/8
     * @Description 获取迭代回顾附件信息
     * @param sprintId
     * @Return java.util.List<com.yusys.agile.sprint.dto.SprintAttachmentDTO>
     */
    List<SprintAttachmentDTO> getSprintAttachmentList(Long sprintId);

    /**
     * @Author maxp2
     * @Date 2021/6/8
     * @Description 通过迭代id删除迭代回顾信息
     * @param sprintId
     * @Return void
     */
    void deleteSprintReviewBySprintId(Long sprintId);

    /**
     * @Author maxp2
     * @Date 2021/6/8
     * @Description 通过附件id删除附件
     * @param attachmentId
     * @Return int
     */
    int deleteAttachment(Long attachmentId);
}
