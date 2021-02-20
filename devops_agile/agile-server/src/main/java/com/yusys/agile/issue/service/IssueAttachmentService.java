package com.yusys.agile.issue.service;

import com.yusys.agile.issue.domain.IssueAttachment;
import com.yusys.agile.issue.dto.IssueAttachmentDTO;

import java.util.List;

public interface IssueAttachmentService {

    /**
     *
     * @Date: 18:04
     * @Description: 获取工作项相关的附件列表
     * @Param: * @param issueId
     * @Return: java.util.List<com.yusys.agile.issue.dto.IssueAttachmentDTO>
     */
    List<IssueAttachmentDTO> listIssueAttachment(Long issueId);

    /**
     *
     * @Date: 18:05
     * @Description: 删除单个附件
     * @Param: * @param attachmentId
     * @Return: int
     */
    int deleteAttach(Long attachmentId);

    /**
     *
     * @Date: 18:05
     * @Description: 创建工作项附件
     * @Param: * @param newAttachment
     * @Return: int
     */
    int createBatchAttachment(List<IssueAttachment> newAttachment);

    /**
     *
     * @Date: 18:05
     * @Description: 删除工作项下所有附件
     * @Param: * @param issueId
     * @Return: void
     */
    void deleteAttachmentByIssueId(Long issueId);

    int updateByPrimaryKeySelective(IssueAttachment record);
}
