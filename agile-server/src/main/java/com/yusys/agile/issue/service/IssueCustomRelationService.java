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

    List<SIssueCustomRelation> getUnApplied(SecurityDTO securityDTO, Byte issueType, String fieldName);

    List<CustomFieldDTO> getCustomFieldDTO(Long projectId, Byte issueType);

    void deleteIssueCustomRelationByFieldId(Long fieldId);

}
