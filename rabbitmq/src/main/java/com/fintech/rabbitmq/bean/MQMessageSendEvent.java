package com.fintech.rabbitmq.bean;

/**
 * @ClassName MQMessageEvent
 * @Date 2020/2/17 13:47
 * @Auther wangyongyong
 * @Version 1.0
 * @Description TODO
 */
public class MQMessageSendEvent extends MQMessageEvent
{

    /**
     * Create a new {@code ApplicationEvent}.
     *
     * @param source the object on which the event initially occurred or with
     *               which the event is associated (never {@code null})
     */
    public MQMessageSendEvent(Object source, MQMessage message)
    {
        super(source, message);
    }

}
