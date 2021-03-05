package com.fintech.rabbitmq.bean;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;

/**
 * @ClassName MQMessageEvent
 * @Date 2020/2/17 13:47
 * @Auther wangyongyong
 * @Version 1.0
 * @Description TODO
 */
public class MQMessageReceiveEvent extends MQMessageEvent
{

    private Channel channel;

    private Message mqMessage;

    /**
     * Create a new {@code ApplicationEvent}.
     *
     * @param source the object on which the event initially occurred or with
     *               which the event is associated (never {@code null})
     */
    public MQMessageReceiveEvent(Object source, MQMessage message, Channel channel, Message mqMessage)
    {
        super(source, message);
        this.channel = channel;
        this.mqMessage = mqMessage;
    }

    public Channel getChannel()
    {
        return channel;
    }

    public Message getMqMessage()
    {
        return mqMessage;
    }

}
