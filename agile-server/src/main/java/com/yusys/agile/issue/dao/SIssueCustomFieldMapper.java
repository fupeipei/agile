package com.yusys.agile.issue.dao;

import com.yusys.agile.issue.domain.SIssueCustomField;
import com.yusys.agile.issue.domain.SIssueCustomFieldExample;
import com.yusys.agile.issue.dto.IssueCustomFieldDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SIssueCustomFieldMapper {
    long countByExample(SIssueCustomFieldExample example);

    int deleteByExample(SIssueCustomFieldExample example);

    int deleteByPrimaryKey(Long extendId);

    int insert(SIssueCustomField record);

    int insertSelective(SIssueCustomField record);

    List<SIssueCustomField> selectByExample(SIssueCustomFieldExample example);

    SIssueCustomField selectByPrimaryKey(Long extendId);

    int updateByExampleSelective(@Param("record") SIssueCustomField record, @Param("example") SIssueCustomFieldExample example);

    int updateByExample(@Param("record") SIssueCustomField record, @Param("example") SIssueCustomFieldExample example);

    int updateByPrimaryKeySelective(SIssueCustomField record);

    int updateByPrimaryKey(SIssueCustomField record);

    /**
     * @Date: 18:10
     * @Description: 根据工作项ID查询自定义字段信息
     * @Param: * @param issueId
     * @Return: java.util.List<com.yusys.agile.issue.dto.IssueCustomFieldDTO>
     */
    List<IssueCustomFieldDTO> listCustomField(Long issueId);

    /**
     * @Date: 18:10
     * @Description: 批量新增工作项信息
     * @Param: * @param fields
     * @Return: int
     */
    int createBatch(@Param("fields") List<SIssueCustomField> fields);

    /**
     * @param issueId
     * @Date: 18:10
     * @Description: 批量删除工作项自定义字段值
     * @Param: * @param fieldIds
     * @Return: void
     */
    void deleteInFieldIdList4Issus(@Param("fieldIds") List<Long> fieldIds, @Param("issueId") Long issueId);

    void deleteCustomFileByIssueId(Long issueId);

    /**
     * 功能描述: 修改自定义字段明细值是允许为空
     *
     * @param record
     * @param example
     * @return int
     * @date 2021/2/22
     */
    int updateByExampleSelectiveWithNull(@Param("record") SIssueCustomField record, @Param("example") SIssueCustomFieldExample example);

    void deleteInFieldIdListByFieldIds(@Param("fieldId") Long fieldId);
}