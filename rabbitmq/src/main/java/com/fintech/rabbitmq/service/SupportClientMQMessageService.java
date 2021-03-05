package com.fintech.rabbitmq.service;

import com.fintech.rabbitmq.bean.MQMessage;
import com.fintech.rabbitmq.entity.OperateCode;

/**
 * @ClassName SupportClientMQMessageService
 * @Date 2020/2/18 11:34
 * @Auther wangyongyong
 * @Version 1.0
 * @Description TODO
 */
public interface SupportClientMQMessageService
{
    /**
     * 检查操作码是否存在
     *
     * @param operateId 操作码
     * @return 操作码
     */
    OperateCode supportOperateCode(String operateId);

    /**
     * 记录 mq 消息发送记录
     *
     * @param exchange
     * @param routingKey
     * @param message
     */
    void logMQMessage(String exchange, String routingKey, MQMessage message);
}
