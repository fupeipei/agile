package com.yusys.agile.issue.service;

import com.yusys.agile.issue.domain.IssueCustomField;
import com.yusys.agile.issue.dto.IssueCustomFieldDTO;

import java.util.List;

public interface IssueCustomFieldService {

    /**
     *
     * @Date: 18:06
     * @Description: 获取工作项相关的自定义字段列表
     * @Param: * @param issueId
     * @Return: java.util.List<com.yusys.agile.issue.dto.IssueCustomFieldDTO>
     */
    List<IssueCustomFieldDTO> listCustomField(Long issueId,Byte issueType,Long projectId);

    /**
     *
     * @Date: 18:06
     * @Description: 批量新增自定义字段
     * @Param: * @param fields
     * @Return: int
     */
    int createBatch(List<IssueCustomField> fields);

    /**
     *
     * @Date: 18:06
     * @Description: 批量删除工作项自定义字段值
     * @Param: * @param fieldIds
    * @param issueId
     * @Return: void
     */
    void deleteInFieldIdList4Issus(List<Long> fieldIds, Long issueId);

    void deleteCustomFileByIssueId(Long issueId);

    /**
     * 功能描述: 修改自定义字段
     *     
     * @date 2020/7/21
     * @param fieldsAfterEdit
     * @return void
     */
    void editCustomFields(List<IssueCustomField> fieldsAfterEdit);

    void deleteCustomFileByIssueCustomRelationId(Long issueCustomRelationId);


    /**
      *功能描述 查询当前项目的所有自定义字段数据值
      *   
      * @date 2020/8/10
      * @param projectId
      * @return java.util.List<com.yusys.agile.issue.dto.IssueCustomFieldDTO>
     */

    List<IssueCustomField> selectIssueIdByProjectId(Long projectId);
}
