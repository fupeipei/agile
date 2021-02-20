package com.yusys.agile.issue.service.impl;

import com.yusys.agile.issue.dao.IssueRuleMapper;
import com.yusys.agile.issue.domain.IssueRule;
import com.yusys.agile.issue.domain.IssueRuleExample;
import com.yusys.agile.issue.enums.IssueTypeEnum;
import com.yusys.agile.issue.service.IssueRuleService;
import com.yusys.agile.issue.utils.IssueRuleFactory;
import com.yusys.portal.model.facade.dto.SecurityDTO;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @ClassName IssueRuleServiceImpl
 * @Description TODO
 *
 * @Date 2021/2/8 11:03
 * @Version 1.0
 */
@Service
public class IssueRuleServiceImpl implements IssueRuleService {
    @Autowired
    private IssueRuleMapper issueRuleMapper;
    @Autowired
    private IssueRuleFactory ruleFactory;

    @Override
    public List<IssueRule> getIssueRules(Byte category, SecurityDTO securityDTO) {
        Long projectId = securityDTO.getProjectId();
        IssueRuleExample ruleExample = new IssueRuleExample();
        ruleExample.createCriteria().andProjectIdEqualTo(projectId)
                                    .andCategoryEqualTo(category);
        List<IssueRule> issueRules = issueRuleMapper.selectByExample(ruleExample);
        if(CollectionUtils.isEmpty(issueRules)){
            if(IssueTypeEnum.TYPE_TASK.CODE.equals(category) || IssueTypeEnum.TYPE_FAULT.CODE.equals(category)){
                ruleFactory.initTaskAndFaultRules(category,projectId);
            }else{
                ruleFactory.initDemandRules(category,projectId);
            }
            issueRules = issueRuleMapper.selectByExample(ruleExample);
        }
        return issueRules;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void pushRules(IssueRule issueRule) {
        Long ruleId = issueRule.getRuleId();
        if(Optional.ofNullable(ruleId).isPresent()){
            issueRuleMapper.updateByPrimaryKey(issueRule);
        }else{
            issueRuleMapper.insert(issueRule);
        }
    }

}
