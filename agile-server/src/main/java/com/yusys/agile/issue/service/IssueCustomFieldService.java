package com.yusys.agile.issue.service;

import com.yusys.agile.issue.domain.SIssueCustomField;
import com.yusys.agile.issue.dto.IssueCustomFieldDTO;

import java.util.List;

public interface IssueCustomFieldService {

    /**
     * @Date: 18:06
     * @Description: 获取工作项相关的自定义字段列表
     * @Param: * @param issueId
     * @Return: java.util.List<com.yusys.agile.issue.dto.IssueCustomFieldDTO>
     */
    List<IssueCustomFieldDTO> listCustomField(Long systemId,Long issueId, Byte issueType);

    /**
     * @Date: 18:06
     * @Description: 批量新增自定义字段
     * @Param: * @param fields
     * @Return: int
     */
    int createBatch(List<SIssueCustomField> fields);

    /**
     * @param issueId
     * @Date: 18:06
     * @Description: 批量删除工作项自定义字段值
     * @Param: * @param fieldIds
     * @Return: void
     */
    void deleteInFieldIdList4Issus(List<Long> fieldIds, Long issueId);

    void deleteCustomFileByIssueId(Long issueId);

    /**
     * 功能描述: 修改自定义字段
     *
     * @param fieldsAfterEdit
     * @return void
     * @date 2021/2/21
     */
    void editCustomFields(List<SIssueCustomField> fieldsAfterEdit);

    void deleteCustomFileByIssueCustomRelationId(Long issueCustomRelationId);


    /**
     * 功能描述 查询当前租户的所有自定义字段数据值
     *
     * @param tenantCode
     * @return java.util.List<com.yusys.agile.issue.dto.IssueCustomFieldDTO>
     * @date 2020/8/10
     */

    List<SIssueCustomField> selectIssueIdByTenantCode(String tenantCode);

    /**
     * 恢复逻辑删除后的数据
     * @author zhaofeng
     * @date 2021/6/11 13:07
     * @param issueCustomRelationId
     */
    void recoveryCustomFileByIssueCustomRelationId(Long issueCustomRelationId);

    /**
     * @Date: 18:06
     * @Description: 根据工作项Id获取工作项相关的自定义字段列表
     * @Param: * @param issueId
     * @Return: java.util.List<com.yusys.agile.issue.dto.IssueCustomFieldDTO>
     * @return
     */
    List<IssueCustomFieldDTO> listCustomFieldByIssueId(Long issueId);
}
