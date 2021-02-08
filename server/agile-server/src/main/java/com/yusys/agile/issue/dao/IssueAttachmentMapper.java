package com.yusys.agile.issue.dao;

import com.yusys.agile.issue.domain.IssueAttachment;
import com.yusys.agile.issue.domain.IssueAttachmentExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IssueAttachmentMapper {
    long countByExample(IssueAttachmentExample example);

    int deleteByExample(IssueAttachmentExample example);

    int deleteByPrimaryKey(Long attachmentId);

    int insert(IssueAttachment record);

    int insertSelective(IssueAttachment record);

    List<IssueAttachment> selectByExample(IssueAttachmentExample example);

    IssueAttachment selectByPrimaryKey(Long attachmentId);

    int updateByExampleSelective(@Param("record") IssueAttachment record, @Param("example") IssueAttachmentExample example);

    int updateByExample(@Param("record") IssueAttachment record, @Param("example") IssueAttachmentExample example);

    int updateByPrimaryKeySelective(IssueAttachment record);

    int updateByPrimaryKey(IssueAttachment record);

    /**
     *
     * @Date: 18:10
     * @Description: 批量新增缺陷
     * @Param: * @param newAttachment
     * @Return: int
     */
    int createBatchAttachment(@Param("newAttachments") List<IssueAttachment> newAttachments);
}