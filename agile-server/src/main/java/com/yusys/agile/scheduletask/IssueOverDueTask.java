package com.yusys.agile.scheduletask;

import com.yusys.agile.constant.ScheduleConstant;
import com.yusys.agile.noticesettings.MailSwitchEnum;
import com.yusys.agile.utils.MailSendUtil;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Description 超期工作项邮件发送
 * @Class IssueOverDueTask
 */
@Component
@JobHandler(ScheduleConstant.TaskHandler.ISSUE_OVERDUE)
public class IssueOverDueTask extends IJobHandler {

    private static final Logger log = LoggerFactory.getLogger(IssueOverDueTask.class);

    @Autowired
    private MailSendUtil mailSendUtil;
    @Override
    public ReturnT<String> execute(String s) throws Exception {
        log.info("工作项超期定时任务触发Start，定时任务名称：{}",ScheduleConstant.TaskHandler.ISSUE_OVERDUE);
        byte mailType = MailSwitchEnum.OVERDUE.getMailType();
        mailSendUtil.sendOverDueMail(mailType);
        log.info("工作项超期定时任务触发End");
        return ReturnT.SUCCESS;
    }
}
