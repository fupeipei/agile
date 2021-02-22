package com.yusys.agile.sprint.dao;

import com.yusys.agile.sprint.domain.SprintAttachment;
import com.yusys.agile.sprint.domain.SprintAttachmentExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface SprintAttachmentMapper {
    long countByExample(SprintAttachmentExample example);

    int deleteByExample(SprintAttachmentExample example);

    int deleteByPrimaryKey(Long attachmentId);

    int insert(SprintAttachment record);

    int insertSelective(SprintAttachment record);

    List<SprintAttachment> selectByExample(SprintAttachmentExample example);

    SprintAttachment selectByPrimaryKey(Long attachmentId);

    int updateByExampleSelective(@Param("record") SprintAttachment record, @Param("example") SprintAttachmentExample example);

    int updateByExample(@Param("record") SprintAttachment record, @Param("example") SprintAttachmentExample example);

    int updateByPrimaryKeySelective(SprintAttachment record);

    int updateByPrimaryKey(SprintAttachment record);

    int createBatch(@Param("attachments") List<SprintAttachment> attachments);

    int deleteInIds(@Param("ids") List<Long> ids);

    void deleteBySprintId(Long sprintId);
}