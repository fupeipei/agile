package com.yusys.agile.issue.service;

import com.yusys.agile.issue.domain.IssueTemplate;
import com.yusys.portal.model.facade.dto.SecurityDTO;

import java.util.Map;

/**
 * :
 *
 * @Date: 2021/2/31
 * @Description: TODO
 */
public interface IssueTemplateService {
    /**
     * @author hanhe
     * @param systemId
     */
    void initIssueTemplate(Long systemId);

    Map query(Byte issueType, SecurityDTO securityDTO);

    Integer editIssueCustomRelation(SecurityDTO securityDTO, IssueTemplate issueTemplate);

    /**
     * 功能描述 根据项目id和工作项类型获取具体模板
     *
     * @param projectId
     * @param issueType
     * @return com.yusys.agile.issue.domain.IssueTemplate
     * @date 2021/2/20
     */
    IssueTemplate getTemplateByProjectAndType(Long projectId, Byte issueType);
}
