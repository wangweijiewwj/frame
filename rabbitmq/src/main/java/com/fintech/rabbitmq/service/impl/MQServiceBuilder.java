package com.fintech.rabbitmq.service.impl;

import com.fintech.rabbitmq.service.MQService;

/**
 * @ClassName MQServiceBuilder
 * @Date 2020/10/9 13:34
 * @Auther wangyongyong
 * @Version 1.0
 * @Description TODO
 */
public class MQServiceBuilder
{
    public static void putMQService(MQService<String> mqService)
    {
        MQMessageReceivedEventServiceImpl.mqServiceMap.put(mqService.getImplClass().getName(), mqService);
    }
}
