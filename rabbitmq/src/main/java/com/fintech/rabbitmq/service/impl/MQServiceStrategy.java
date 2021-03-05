package com.fintech.rabbitmq.service.impl;

import com.alibaba.fastjson.JSON;
import com.fintech.common.constants.CommonConstants;
import com.fintech.rabbitmq.bean.MQMessage;
import com.fintech.rabbitmq.bean.MQMessageEvent;
import com.fintech.rabbitmq.bean.MQMessageReceiveEvent;
import com.fintech.rabbitmq.bean.MQMessageSendEvent;
import com.fintech.rabbitmq.service.MQMessageEventService;
import com.fintech.rabbitmq.util.HttpContentUtil;
import com.rabbitmq.client.Channel;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @ClassName MQServiceStrategy
 * @Date 2020/2/17 13:36
 * @Auther wangyongyong
 * @Version 1.0
 * @Description TODO
 */
@Component
public class MQServiceStrategy implements ApplicationListener<MQMessageEvent>
{

    private static final Logger logger = LoggerFactory.getLogger(MQServiceStrategy.class);

    private static final String SPLIT = ":";

    @Autowired
    private ApplicationContext context;

    /**
     * 发送 mq 消息
     *
     * @param message 消息对象
     */
    public void sendMQMessage(MQMessage message)
    {
        HttpContentUtil.printHeaders();
        if (StringUtils.isNotEmpty(HttpContentUtil.getOpCode()))
        {
            message.setOperateId(HttpContentUtil.getOpCode());
        }
        String opNCode = HttpContentUtil.getOpNCode();
        if (StringUtils.isNotEmpty(opNCode))
        {
            List<String> ops = Arrays.asList(opNCode.split(CommonConstants.SPLIT));
            int size = ops.size();
            for (int i = 0; i < size; i++)
            {
                String[] ss = ops.get(i).split(SPLIT);
                message.setFlowId(Integer.parseInt(ss[0]));
                ops.set(i, ss[1]);
            }
            message.setNextOperateIds(ops);
        }
        if (StringUtils.isNotEmpty(HttpContentUtil.getProductId()))
        {
            message.setProductId(HttpContentUtil.getProductId());
        }
        if (null != HttpContentUtil.getUserId())
        {
            message.setUserId(HttpContentUtil.getUserId());
        }
        if (null != HttpContentUtil.getCpyId())
        {
            message.setCpyId(HttpContentUtil.getCpyId());
        }
        context.publishEvent(new MQMessageSendEvent(this, message));
    }

    /**
     * 接受 mq 消息
     *
     * @param mqMessage 消息对象
     */
    public void receivedMQMessage(Channel channel, Message mqMessage)
    {
        MQMessage message = null;
        try
        {
            message = JSON.parseObject(mqMessage.getBody(), MQMessage.class);
            message.setDataId(message.getDataId());
        }
        catch (Exception ex)
        {
            logger.error("RECEIVE MQ MESSAGE BODY【" + Arrays.toString(mqMessage.getBody()) + "】PARSE JSON EXCEPTION ===> :", ex);
        }
        finally
        {
            context.publishEvent(new MQMessageReceiveEvent(this, message, channel, mqMessage));
        }
    }

    @Override
    public void onApplicationEvent(MQMessageEvent event)
    {
        Map<String, MQMessageEventService> mqMessageEventServiceMap = context.getBeansOfType(MQMessageEventService.class);
        mqMessageEventServiceMap.values().forEach(mqMessageEventService ->
        {
            if (mqMessageEventService.supportMQMessageEvent(event))
            {
                mqMessageEventService.executeEventService(event);
            }
        });
    }
}
