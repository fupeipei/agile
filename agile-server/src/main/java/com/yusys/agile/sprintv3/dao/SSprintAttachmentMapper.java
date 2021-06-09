package com.yusys.agile.sprintv3.dao;

import com.yusys.agile.sprintv3.domain.SSprintAttachment;
import com.yusys.agile.sprintv3.domain.SSprintAttachmentExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SSprintAttachmentMapper {
    long countByExample(SSprintAttachmentExample example);

    int deleteByExample(SSprintAttachmentExample example);

    int deleteByPrimaryKey(Long attachmentId);

    int insert(SSprintAttachment record);

    int insertSelective(SSprintAttachment record);

    List<SSprintAttachment> selectByExample(SSprintAttachmentExample example);

    SSprintAttachment selectByPrimaryKey(Long attachmentId);

    int updateByExampleSelective(@Param("record") SSprintAttachment record, @Param("example") SSprintAttachmentExample example);

    int updateByExample(@Param("record") SSprintAttachment record, @Param("example") SSprintAttachmentExample example);

    int updateByPrimaryKeySelective(SSprintAttachment record);

    int updateByPrimaryKey(SSprintAttachment record);
}