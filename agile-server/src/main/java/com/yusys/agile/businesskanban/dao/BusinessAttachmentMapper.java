package com.yusys.agile.businesskanban.dao;

import com.yusys.agile.businesskanban.domain.BusinessAttachment;
import com.yusys.agile.businesskanban.domain.BusinessAttachmentExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface BusinessAttachmentMapper {
    long countByExample(BusinessAttachmentExample example);

    int deleteByExample(BusinessAttachmentExample example);

    int deleteByPrimaryKey(Long attachmentId);

    int insert(BusinessAttachment record);

    int insertSelective(BusinessAttachment record);

    List<BusinessAttachment> selectByExample(BusinessAttachmentExample example);

    BusinessAttachment selectByPrimaryKey(Long attachmentId);

    int updateByExampleSelective(@Param("record") BusinessAttachment record, @Param("example") BusinessAttachmentExample example);

    int updateByExample(@Param("record") BusinessAttachment record, @Param("example") BusinessAttachmentExample example);

    int updateByPrimaryKeySelective(BusinessAttachment record);

    int updateByPrimaryKey(BusinessAttachment record);

    int batchCreate(@Param("collection") List<BusinessAttachment> attachments);
}