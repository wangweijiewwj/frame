package com.fintech.rabbitmq.bean;

import org.springframework.context.ApplicationEvent;

/**
 * @ClassName MQMessageEvent
 * @Date 2020/2/17 14:42
 * @Auther wangyongyong
 * @Version 1.0
 * @Description TODO
 */
public class MQMessageEvent extends ApplicationEvent
{

    private MQMessage message;

    /**
     * Create a new {@code ApplicationEvent}.
     *
     * @param source the object on which the event initially occurred or with
     *               which the event is associated (never {@code null})
     */
    public MQMessageEvent(Object source, MQMessage message)
    {
        super(source);
        this.message = message;
    }

    public MQMessage getMessage()
    {
        return message;
    }
}
