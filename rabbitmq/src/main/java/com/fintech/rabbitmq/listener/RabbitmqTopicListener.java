package com.fintech.rabbitmq.listener;

import com.amqp.spring.boot.starter.autoconfigure.AmqpContentUtils;
import com.amqp.spring.boot.starter.autoconfigure.AmqpMessageDispatch;
import com.fintech.rabbitmq.service.impl.MQServiceStrategy;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Arrays;

/**
 * @ClassName RabbitmqTopicListener
 * @Date 2020/9/23 14:19
 * @Auther wangyongyong
 * @Version 1.0
 * @Description rabbitmq topic 模式监听队列，接受消息 , 监听器唯一. AmqpMessageDispatch 只能存在一个实现
 */
@Component
public class RabbitmqTopicListener implements AmqpMessageDispatch, InitializingBean
{

    private static final Logger logger = LoggerFactory.getLogger(RabbitmqTopicListener.class);

    @Resource

    private MQServiceStrategy mqServiceStrategy;

    @Override
    public void doDispatch(Message message, Channel channel)
    {
        String queueName = message.getMessageProperties().getConsumerQueue();
        logger.info("RECEIVE QUEUE 【" + queueName + "】 MESSAGE:" + Arrays.toString(message.getBody()));
        mqServiceStrategy.receivedMQMessage(channel, message);
    }

    @Override
    public void afterPropertiesSet() throws Exception
    {
        AmqpContentUtils.initAmqpMessageDispatch(AmqpMessageDispatch.class, this);
    }
}
