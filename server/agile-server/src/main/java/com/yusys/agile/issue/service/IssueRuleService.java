package com.yusys.agile.issue.service;

import com.yusys.agile.issue.domain.IssueRule;
import com.yusys.portal.model.facade.dto.SecurityDTO;

import java.util.List;

/**
 * @ClassName IssueRuleService
 * @Description TODO
 *
 * @Date 2020/7/8 11:03
 * @Version 1.0
 */
public interface IssueRuleService {

    /**
     * 获取工作项阶段状态流转规则列表数据
     * @param securityDTO
     * @return
     */
    List<IssueRule> getIssueRules(Byte category, SecurityDTO securityDTO);

    /**
     * 更新工作项阶段状态流转规则
     * @param issueRule
     * @return
     */
    void pushRules(IssueRule issueRule);
}
