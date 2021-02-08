package com.yusys.agile.issue.rest;

import com.yusys.agile.issue.domain.IssueRule;
import com.yusys.agile.issue.service.IssueRuleService;
import com.yusys.portal.model.common.dto.ControllerResponse;
import com.yusys.portal.model.facade.dto.SecurityDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName IssueRuleController
 * @Description 工作项阶段状态流转规则
 *
 * @Date 2020/7/8 10:58
 * @Version 1.0
 */
@RestController
@RequestMapping("/issue/rules")
public class IssueRuleController {

    @Autowired
    private IssueRuleService issueRuleService;

    /**
     * 获取工作项阶段状态流转规则列表数据
     * @param securityDTO
     * @return
     */
    @GetMapping("")
    public ControllerResponse getIssueRules(@RequestParam("category") Byte category, SecurityDTO securityDTO){
        List<IssueRule> list = issueRuleService.getIssueRules(category,securityDTO);
        return  ControllerResponse.success(list);
    }

    /**
     * 更新工作项阶段状态流转规则
     * @param issueRule
     * @param securityDTO
     * @return
     */
    @PostMapping("/push")
    public ControllerResponse pushRules(@RequestBody IssueRule issueRule, SecurityDTO securityDTO){
        issueRule.setProjectId(securityDTO.getProjectId());
        issueRule.setTenantCode(securityDTO.getTenantCode());
        issueRuleService.pushRules(issueRule);
        return ControllerResponse.success("编辑工作项流转规则成功");
    }

}
