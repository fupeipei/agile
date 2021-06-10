package com.yusys.agile.issue.service;

import com.yusys.agile.customfield.dto.CustomFieldDTO;
import com.yusys.agile.issue.domain.SIssueCustomRelation;
import com.yusys.agile.issue.domain.IssueCustomRelationList;
import com.yusys.portal.model.facade.dto.SecurityDTO;

import java.util.List;

public interface IssueCustomRelationService {


    List<SIssueCustomRelation> getIssueCustomRelations(Long projectId, Byte issueType);
    /**
     * 删除关联关系
     * @author zhaofeng
     * @date 2021/6/9 15:19
     * @param id
     */
    void deleteIssueCustomRelation(Long id);

    void saveIssueCustomRelation(SecurityDTO securityDTO, IssueCustomRelationList idList);

    /**
     * 查询系统和工作项类型查询，当系统id为空时，只按类型查询
     * @author zhaofeng
     * @date 2021/6/3 15:14
     * @param systemId
     * @param issueType
     */
    List<CustomFieldDTO> getCustomFieldList(Long systemId, Byte issueType);

    void deleteIssueCustomRelationByFieldId(Long fieldId);

    List<CustomFieldDTO>  getUnApplied(Long systemId, Byte issueType, String fieldName, Integer pageSize, Integer pageNum);
}
