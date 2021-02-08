package com.yusys.agile.consumer;


import com.yusys.agile.constant.EmailOperationTypeEnum;
import com.yusys.agile.consumer.constant.AgileConstant;
import com.yusys.agile.consumer.dto.IssueMailSendDto;
import com.yusys.agile.issue.domain.Issue;
import com.yusys.agile.issue.utils.IssueUpRegularFactory;
import com.yusys.agile.utils.MailSendUtil;
import com.yusys.portal.model.facade.dto.SecurityDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * @ClassName IssueConsumer
 * @Description 工作项相关消息队列消费者
 * @Date 2021/2/1
 * @Version 1.0
 */

@Component
public class IssueConsumer {

    private static final Logger log = LoggerFactory.getLogger(IssueConsumer.class);

    @Resource
    private IssueUpRegularFactory regularFactory;
    @Resource
    private MailSendUtil mailSendUtil;

    @Value("${issue.regular.flag}")
    private String flagRegular;

    @RabbitListener(queues = AgileConstant.Queue.ISSUE_UP_REGULAR_QUEUE)
    public void receiveRegular(Long issueId){

        if(Optional.ofNullable(issueId).isPresent() && Boolean.valueOf(flagRegular)){
            try {
                //延迟1000毫秒，保证拖拽向上规则状态修改正确，不可删除
                log.info("接收到工作项向上规整队列命令,工作项ID:{},issueId",issueId);
                regularFactory.commonIssueUpRegular(issueId);
            }catch (Exception e){
                log.error("工作项向上规则异常:{}",e);
            }
        }
    }

    @RabbitListener(queues = AgileConstant.Queue.ISSUE_MAIL_SEND_QUEUE)
    public void receive(IssueMailSendDto issueMailSendDto){
        if(Optional.ofNullable(issueMailSendDto).isPresent()){
            try {
                Issue issue = issueMailSendDto.getIssue();
                Integer operationType = issueMailSendDto.getOperationType();
                SecurityDTO securityDTO = issueMailSendDto.getSecurityDTO();
                log.info("接收到工作项邮件发送队列命令,工作项ID:{},操作类型:{}",issue.getIssueId(), EmailOperationTypeEnum.getName(operationType));
                mailSendUtil.sendMailContent(issue, operationType, securityDTO);
            }catch (Exception e){
                log.error("敏捷模块工作项邮件发送异常:{}",e);
            }
        }
    }
}
