package com.fintech.rabbitmq.service.impl;

import com.alibaba.fastjson.JSON;
import com.amqp.spring.boot.starter.autoconfigure.AmqpProperties;
import com.fintech.common.bean.SyncQueue;
import com.fintech.rabbitmq.bean.MQMessage;
import com.fintech.rabbitmq.bean.MQMessageEvent;
import com.fintech.rabbitmq.bean.MQMessageSendEvent;
import com.fintech.rabbitmq.entity.OperateCode;
import com.fintech.rabbitmq.constants.MQOperateEnum;
import com.fintech.rabbitmq.constants.RabbitMQConstants;
import com.fintech.rabbitmq.service.MQMessageEventService;
import com.fintech.rabbitmq.service.SupportClientMQMessageService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * @ClassName MQMessageSendEventServiceImpl
 * @Date 2020/2/17 15:00
 * @Auther wangyongyong
 * @Version 1.0
 * @Description TODO
 */
@EnableScheduling
@Service
public class MQMessageSendEventServiceImpl implements MQMessageEventService, RabbitTemplate.ConfirmCallback
{
    @Autowired
    private SupportClientMQMessageService messageService;

    @Autowired
    private AmqpTemplate template;

    @Resource
    private AmqpProperties properties;

    private static SyncQueue<MQMessageCache> queue = new SyncQueue<>();

    private static final Logger logger = LoggerFactory.getLogger(MQMessageSendEventServiceImpl.class);

    @PostConstruct
    public void init()
    {
//        if (template instanceof RabbitTemplate)
//        {
//            ((RabbitTemplate) template).setConfirmCallback(this);
//        }
    }

    /**
     * 补偿机制，发送 mq 消息 , 5 秒扫描一次
     */
    @Scheduled(cron = "0/5 * * * * ?")
    public void compensationSendMQMessage()
    {
        MQMessageCache cache = null;
        while ((cache = queue.poll()) != null)
        {
            logger.info("BEGIN ========> COMPENSATION MECHANISM SEND MQ MESSAGE 【" + cache.toString() + "】 START!");
            sendMQMessage(cache.getExchange(), cache.getRoutingKey(), cache.getMessage());
            logger.info("END ========> COMPENSATION MECHANISM SEND MQ MESSAGE 【" + cache.toString() + "】 END!");
        }
    }

    /**
     * 发送 mq 消息
     *
     * @param exchange   mq 交换机
     * @param routingKey 路由 key 值
     * @param message    mq 消息
     */
    private void sendMQMessage(String exchange, String routingKey, MQMessage message)
    {
        try
        {
            template.convertAndSend(exchange, routingKey, JSON.toJSONString(message));
            logger.info("【SUCCESS】【" + MQOperateEnum.codeOf(message.getOperateId()) + "】 SEND MQ MESSAGE 【" + exchange + "】【" + routingKey + "】:【" + JSON.toJSONString(message) + "】 SUCCESSFUL!");
        }
        catch (Throwable ex)
        {
            logger.error("【FAILURE】 SEND MQ MESSAGE 【" + exchange + "】【" + routingKey + "】:【" + JSON.toJSONString(message) + "】UNSUCCESSFUL , REASON :【" + ex.getMessage() + "】", ex);
            MQMessageCache cache = new MQMessageCache();
            cache.setExchange(exchange);
            cache.setRoutingKey(routingKey);
            cache.setMessage(message);
            queue.offer(cache);
            logger.info("【COMPENSATION】 SEND MQ MESSAGE TO COMPENSATION MECHANISM : 【" + cache.toString() + "】!");
        }
    }

    @Override
    public boolean supportMQMessageEvent(MQMessageEvent event)
    {
        return event instanceof MQMessageSendEvent;
    }

    @Override
    public void executeEventService(MQMessageEvent event)
    {
        MQMessage message = event.getMessage();
        // 1、查询操作码，存在发送 mq ,不存在不发送 mq
        if (StringUtils.isEmpty(message.getOperateId()))
        {
            logger.info("OPERATE CODE IS NULL,PLEASE SET 【op_code】 VALUE ON HTTP HEADER !");
            return;
        }
        OperateCode operateCode = messageService.supportOperateCode(message.getOperateId());
        if (null != operateCode)
        {
            String exchange = "";
            String routingKey = operateCode.getModule();
            switch (operateCode.getType())
            {
                case RabbitMQConstants.EXCHANGE_P:
                    exchange = RabbitMQConstants.EVENT_EXCHANGE_NAME;
                    break;
                case RabbitMQConstants.EXCHANGE_C:
                    exchange = RabbitMQConstants.MESSAGE_EXCHANGE_NAME;
                    break;
                default:
                    break;
            }
            if (StringUtils.isNotEmpty(exchange) && StringUtils.isNotEmpty(routingKey))
            {
                routingKey = StringUtils.isNotEmpty(properties.getQueuePrefix()) ? (properties.getQueuePrefix() + "." + routingKey) : routingKey;
                logger.info("BEGIN ========> RECEIVE WAITING SEND MQ MESSAGE :【" + message.toString() + "】START!");
                messageService.logMQMessage(exchange, routingKey, message);
                // 2、发送消息到 mq 消息队列
                sendMQMessage(exchange, routingKey, message);
                logger.info("END ========> RECEIVE WAITING SEND MQ MESSAGE :【" + message.toString() + "】END!");
            }
            else
            {
                logger.info("OPERATE CODE 【" + message.getOperateId() + "】IS NOT SUPPORTED , PLEASE CHECK YOUR CONFIG!");
            }
        }
    }

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause)
    {
        Message message = correlationData.getReturnedMessage();
        if (ack)
        {
            logger.info("成功发送 mq 消息【" + correlationData.getId() + "】回调确认：【" + (message != null ? new String(message.getBody()) : StringUtils.EMPTY) + "】");
        }
        else
        {
            logger.info("失败发送 mq 消息【" + correlationData.getId() + "】回调确认：【" + (message != null ? new String(message.getBody()) : StringUtils.EMPTY) + "】，原因：【" + cause + "】");
        }
    }

    private class MQMessageCache
    {
        private String exchange;

        private String routingKey;

        private MQMessage message;

        public String getExchange()
        {
            return exchange;
        }

        public void setExchange(String exchange)
        {
            this.exchange = exchange;
        }

        public String getRoutingKey()
        {
            return routingKey;
        }

        public void setRoutingKey(String routingKey)
        {
            this.routingKey = routingKey;
        }

        public MQMessage getMessage()
        {
            return message;
        }

        public void setMessage(MQMessage message)
        {
            this.message = message;
        }

        @Override
        public String toString()
        {
            return "MQMessageCache{" +
                    "exchange='" + exchange + '\'' +
                    ", routingKey='" + routingKey + '\'' +
                    ", message=" + message +
                    '}';
        }
    }
}
