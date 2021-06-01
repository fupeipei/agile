package com.yusys.agile.scheduletask;

import com.yusys.agile.AgileApplication;
import com.yusys.agile.issue.enums.IssueTypeEnum;
import com.yusys.agile.issue.service.IssueRuleService;
import com.yusys.agile.issue.utils.IssueRuleFactory;
import com.yusys.portal.model.facade.dto.SecurityDTO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = {AgileApplication.class})
public class IssueRuleRunnerTest {

    @Autowired
    private IssueRuleService ruleService;
    @Autowired
    private IssueRuleFactory ruleFactory;


    @Test
    public void testRunFault() {
        Byte category = IssueTypeEnum.TYPE_FAULT.CODE;
        SecurityDTO securityDTO = new SecurityDTO();
        securityDTO.setUserId(9999L);
        securityDTO.setProjectId(718135708161531904L);
        securityDTO.setUserName("admin");
        ruleService.getIssueRules(category, securityDTO);
        Assert.assertTrue("testRunFault成功", true);
    }

    @Test
    public void testRunTask() {

        Byte category = IssueTypeEnum.TYPE_TASK.CODE;
        SecurityDTO securityDTO = new SecurityDTO();
        securityDTO.setUserId(9999L);
        securityDTO.setProjectId(718135708161531904L);
        securityDTO.setUserName("admin");
        ruleService.getIssueRules(category, securityDTO);
        Assert.assertTrue("testRunTask成功", true);
    }

    @Test
    public void testRunEpic() {

        Byte category = IssueTypeEnum.TYPE_FEATURE.CODE;
        SecurityDTO securityDTO = new SecurityDTO();
        securityDTO.setUserId(9999L);
        securityDTO.setProjectId(718135708161531904L);
        securityDTO.setUserName("admin");
        ruleService.getIssueRules(category, securityDTO);
        Assert.assertTrue("testRunEpic成功", true);
    }

    @Test
    public void testAddStageInstace() {
        Long firstStageId = 3L;
        Long secondStageId = 195L;
        Long projectId = 718135708161531904L;
        ruleFactory.addStageIdToIssueRule(firstStageId, secondStageId, projectId);
        Assert.assertTrue("testAddStageInstace成功", true);
    }

    @Test
    public void testDelStageIdToIssueRule() {
        Long firstStageId = 2L;
        Long secondStageId = 999L;
        Long projectId = 718135708161531904L;
        ruleFactory.delStageIdToIssueRule(firstStageId, secondStageId, projectId);
        Assert.assertTrue("testDelStageIdToIssueRule成功", true);
    }

}