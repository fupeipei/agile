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
 * @ClassName BusinessJobHandler
 * @Description TODO
 * @Date 2021/2/13 13:52
 * @Version 1.0
 */
@Component
@JobHandler(ScheduleConstant.TaskHandler.BUSINESS_OVERDUE)
public class BusinessJobHandler extends IJobHandler {
    private static final Logger log = LoggerFactory.getLogger(BusinessJobHandler.class);

    @Autowired
    private MailSendUtil mailSendUtil;

    @Override
    public ReturnT<String> execute(String s) throws Exception {
        log.info("事务看板卡片超期定时任务触发Start，定时任务名称：{}", ScheduleConstant.TaskHandler.BUSINESS_OVERDUE);
        byte mailType = MailSwitchEnum.OVERDUE.getMailType();
        mailSendUtil.sendBusinessOverDueMail(mailType);
        log.info("事务看板卡片超期定时任务触发End");
        return ReturnT.SUCCESS;
    }
}
