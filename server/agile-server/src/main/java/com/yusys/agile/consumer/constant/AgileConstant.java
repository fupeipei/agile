package com.yusys.agile.consumer.constant;

/**
 * @ClassName AgileConstant
 * @Description 敏捷模块消息队列
 * @Date 2020/2/1
 * @Version 1.0
 */
public class AgileConstant {
    /** rabbitmq队列 **/
    public interface Queue {
        /** 邮件发送 **/
        String ISSUE_MAIL_SEND_QUEUE = "issue_mail_send_queue";

        /** 工作项向上规则消息队列 **/
        String ISSUE_UP_REGULAR_QUEUE = "issue_up_regular_queue";

    }
}
