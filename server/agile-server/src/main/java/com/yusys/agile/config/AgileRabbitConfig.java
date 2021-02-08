package com.yusys.agile.config;

import com.yusys.agile.consumer.constant.AgileConstant;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName RabbitConfig
 * @Description 消息配置
 * @Date 2020/2/1
 * @Version 1.0
 **/
@Configuration
public class AgileRabbitConfig {

    @Bean
    public Queue mailSendQueue() {
        return new Queue(AgileConstant.Queue.ISSUE_MAIL_SEND_QUEUE);
    }

    @Bean
    public Queue issueUpRegularQueue() {
        return new Queue(AgileConstant.Queue.ISSUE_UP_REGULAR_QUEUE);
    }

    //自动配置里，配置RabbitTemplate的时候会判断是否有自定义的MessageConvert，如果有则采用自定义的
    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

}
