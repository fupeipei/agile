package com.yusys.agile.issue.service;

import com.yusys.agile.customfield.dto.CustomFieldDTO;
import com.yusys.agile.issue.domain.SIssueCustomRelation;
import com.yusys.agile.issue.domain.IssueCustomRelationList;
import com.yusys.portal.model.facade.dto.SecurityDTO;

import java.util.List;

public interface IssueCustomRelationService {


    List<SIssueCustomRelation> getIssueCustomRelations(Long projectId, Byte issueType);

    void deleteIssueCustomRelation(Long id);

    void saveIssueCustomRelation(SecurityDTO securityDTO, IssueCustomRelationList idList);
    /**
     * 查询未被应用的自定义字段，当系统id为空时，查询系统外的
     * @author zhaofeng
     * @date 2021/6/3 16:20
     * @param systemId
     * @param issueType
     * @param fieldName
     */
    List<SIssueCustomRelation> getUnApplied(Long systemId, Byte issueType, String fieldName);
    /**
     * 查询系统和工作项类型查询，当系统id为空时，只按类型查询
     * @author zhaofeng
     * @date 2021/6/3 15:14
     * @param systemId
     * @param issueType
     */
    List<CustomFieldDTO> getCustomFieldList(Long systemId, Byte issueType);

    void deleteIssueCustomRelationByFieldId(Long fieldId);

}
