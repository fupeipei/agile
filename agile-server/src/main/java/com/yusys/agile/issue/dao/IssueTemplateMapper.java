package com.yusys.agile.issue.dao;

import com.yusys.agile.issue.domain.IssueTemplate;
import com.yusys.agile.issue.domain.IssueTemplateExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IssueTemplateMapper {
    long countByExample(IssueTemplateExample example);

    int deleteByExample(IssueTemplateExample example);

    int deleteByPrimaryKey(Long issueTemplateId);

    int insert(IssueTemplate record);

    int insertSelective(IssueTemplate record);

    List<IssueTemplate> selectByExampleWithBLOBs(IssueTemplateExample example);

    List<IssueTemplate> selectByExample(IssueTemplateExample example);

    IssueTemplate selectByPrimaryKey(Long issueTemplateId);

    int updateByExampleSelective(@Param("record") IssueTemplate record, @Param("example") IssueTemplateExample example);

    int updateByExampleWithBLOBs(@Param("record") IssueTemplate record, @Param("example") IssueTemplateExample example);

    int updateByExample(@Param("record") IssueTemplate record, @Param("example") IssueTemplateExample example);

    int updateByPrimaryKeySelective(IssueTemplate record);

    int updateByPrimaryKeyWithBLOBs(IssueTemplate record);

    int updateByPrimaryKey(IssueTemplate record);

    IssueTemplate getTemplateByProjectAndType(@Param("systemId") Long systemId, @Param("issueType") Byte issueType);

    /**
     * 更新需求自定义关系v3通过场id
     *
     * @param fieldId   字段id
     * @param fieldName 字段名
     */
    void updateIssueCustomRelationByFieldId(@Param("fieldId") Long fieldId, @Param("fieldName") String fieldName);
}