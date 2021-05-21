package com.yusys.agile.issuev3.dao;

import com.yusys.agile.issuev3.domain.SIssueAttachment;
import com.yusys.agile.issuev3.domain.SIssueAttachmentExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SIssueAttachmentMapper {
    long countByExample(SIssueAttachmentExample example);

    int deleteByExample(SIssueAttachmentExample example);

    int deleteByPrimaryKey(Long attachmentId);

    int insert(SIssueAttachment record);

    int insertSelective(SIssueAttachment record);

    List<SIssueAttachment> selectByExample(SIssueAttachmentExample example);

    SIssueAttachment selectByPrimaryKey(Long attachmentId);

    int updateByExampleSelective(@Param("record") SIssueAttachment record, @Param("example") SIssueAttachmentExample example);

    int updateByExample(@Param("record") SIssueAttachment record, @Param("example") SIssueAttachmentExample example);

    int updateByPrimaryKeySelective(SIssueAttachment record);

    int updateByPrimaryKey(SIssueAttachment record);
}