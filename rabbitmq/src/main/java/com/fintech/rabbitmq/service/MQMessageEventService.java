package com.fintech.rabbitmq.service;

import com.fintech.rabbitmq.bean.MQMessageEvent;

/**
 * @ClassName MQMessageEventService
 * @Date 2020/2/17 14:49
 * @Auther wangyongyong
 * @Version 1.0
 * @Description TODO
 */
public interface MQMessageEventService
{

    boolean supportMQMessageEvent(MQMessageEvent event);

    void executeEventService(MQMessageEvent event);

}
