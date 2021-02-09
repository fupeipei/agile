package com.yusys.agile.headerfield.service;


import com.yusys.agile.headerfield.domain.HeaderField;
import com.yusys.agile.issue.domain.IssueCustomField;
import com.yusys.agile.issue.domain.IssueHistoryRecord;
import com.yusys.agile.issue.dto.IssueCustomFieldDTO;
import com.yusys.portal.model.facade.dto.SecurityDTO;

import java.util.List;
import java.util.Map;

/**
 * :
 *
 * @Date: 2020/4/13
 */
public interface HeaderFieldService {

    List<HeaderField> queryAllHeaderFields(SecurityDTO securityDTO, Byte category, Byte isFilter);

    /**
     * 需求编码：task_200409_174881
     * 描述: 查询项目字段id集合
     */
    List<HeaderField> getFieldIdList(Long projectId, Byte code, Byte code1);

    /**
     * 需求编码：task_200409_174881
     * 描述: 组织工作项自定义字段历史数据
     */
    List<IssueHistoryRecord> generateHistory(List<IssueCustomField> newFieldList, List<IssueCustomFieldDTO> oldFieldList, Byte issueType, Long issueId, Long projectId);

    /**
     * 功能描述  根据key获取列头数据
     *
     * @param updateList
     * @return java.util.List<com.yusys.agile.headerfield.domain.HeaderField>
     * @date 2020/4/15
     */
    List<HeaderField> getAllHeaderField(List<Long> updateList);


    /**
     * 功能描述  初始化列头数据,高级搜索条件
     *
     * @param securityDTO
     * @param category    * @param isFilter
     * @return java.util.Map
     * @date 2020/4/16
     */
    Map queryHeaderFields(SecurityDTO securityDTO, Byte category, Byte isFilter);

    Integer deleteCustomFieldByFieldId(Long fieldId);

    Integer saveCustomFieldByFieldId(Long projectId, Long fieldId, Byte issueType);


    /**
     * 功能描述  列头下的ContentList
     *
     * @param
     * @return java.util.List<com.yusys.agile.headerfield.domain.HeaderField>
     * @date 2020/4/15
     */
    Map getAllHeaderFieldContNotNull();


    /**
     * 功能描述  当前项目的列头信息
     *
     * @param projectId
     * @return java.util.List<com.yusys.agile.headerfield.domain.HeaderField>
     * @date 2020/4/15
     */
    List<HeaderField> getAllHeaderFieldByProjectId(Long projectId);


}
