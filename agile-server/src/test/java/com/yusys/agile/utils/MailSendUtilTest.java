package com.yusys.agile.utils;


import com.yusys.agile.AgileApplication;
import com.yusys.agile.constant.NumberConstant;
import com.yusys.agile.issue.dao.IssueMapper;
import com.yusys.agile.issue.domain.Issue;
import com.yusys.agile.issue.utils.IssueUpRegularFactory;
import com.yusys.agile.noticesettings.MailSwitchEnum;
import com.yusys.portal.model.facade.dto.SecurityDTO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {AgileApplication.class})
public class MailSendUtilTest {

    @Autowired
    private MailSendUtil mailSendUtil;
    @Autowired
    private IssueMapper issueMapper;
    @Autowired
    private IssueUpRegularFactory regularFactory;

    @Test
    public void sendMail() {
        Long issueId = 10680L;
        Issue issue = issueMapper.selectByPrimaryKey(issueId);

        String content = "admin 修改了内容";
        SecurityDTO securityDTO = new SecurityDTO();
        securityDTO.setUserId(9999L);
        securityDTO.setUserName("admin");
        securityDTO.setProjectId(issue.getProjectId());
        mailSendUtil.sendMailContent(issue, NumberConstant.ZERO, securityDTO);
        Assert.assertTrue("sendMail成功", true);
    }

    @Test
    public void sendOverDueMail() {
        byte mailType = MailSwitchEnum.OVERDUE.getMailType();
        mailType = MailSwitchEnum.OVERTIME.getMailType();
        mailSendUtil.sendOverDueMail(mailType);
        Assert.assertTrue("sendOverDueMail成功", true);
    }

    @Test
    public void testSendBusinessOverDueMail() {
        byte mailType = MailSwitchEnum.OVERDUE.getMailType();
        mailSendUtil.sendBusinessOverDueMail(mailType);
        Assert.assertTrue("testSendBusinessOverDueMail成功", true);
    }

    @Test
    public void testComm() {
        Long issueId = 507234L;
        regularFactory.commonIssueUpRegular(issueId);
        Assert.assertTrue("testComm成功", true);
    }
}