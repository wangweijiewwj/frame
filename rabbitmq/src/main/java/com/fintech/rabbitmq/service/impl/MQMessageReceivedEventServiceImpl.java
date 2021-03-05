package com.fintech.rabbitmq.service.impl;

import com.fintech.common.util.CharsUtil;
import com.fintech.rabbitmq.bean.MQMessage;
import com.fintech.rabbitmq.bean.MQMessageEvent;
import com.fintech.rabbitmq.bean.MQMessageReceiveEvent;
import com.fintech.rabbitmq.bean.NodeActionBO;
import com.fintech.rabbitmq.constants.MQOperateEnum;
import com.fintech.rabbitmq.constants.RabbitMQConstants;
import com.fintech.rabbitmq.entity.OperateCode;
import com.fintech.rabbitmq.service.MQMessageEventService;
import com.fintech.rabbitmq.service.MQService;
import com.fintech.rabbitmq.service.MQServiceCallBack;
import com.fintech.rabbitmq.service.SupportMQMessageService;
import com.rabbitmq.client.Channel;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @ClassName MQMessageReceivedEventServiceImpl
 * @Date 2020/2/17 15:03
 * @Auther wangyongyong
 * @Version 1.0
 * @Description TODO
 */
@Service
public class MQMessageReceivedEventServiceImpl implements MQMessageEventService, InitializingBean, RabbitTemplate.ReturnCallback
{
    private static final Logger logger = LoggerFactory.getLogger(MQMessageReceivedEventServiceImpl.class);

    protected static Map<String, MQService<String>> mqServiceMap = new HashMap<>();

    @Autowired
    private ApplicationContext context;

    @Autowired(required = false)
    private SupportMQMessageService messageService;

    @Autowired
    private AmqpTemplate template;

    @PostConstruct
    public void init()
    {
//        if (template instanceof RabbitTemplate)
//        {
//            ((RabbitTemplate) template).setReturnCallback(this);
//        }
    }

    @Override
    public boolean supportMQMessageEvent(MQMessageEvent event)
    {
        return event instanceof MQMessageReceiveEvent;
    }

    private void executeEventAction(OperateCode operateCode, MQMessage message, MQServiceCallBack serviceCallBack, List<ServiceContent> serviceContents)
    {
        if (CollectionUtils.isEmpty(message.getNextOperateIds()))
        {
            message.setNextOperateIds(new ArrayList<>());
        }
        //messageService.resetFlowInsId(operateCode, message);
        // 2、查询流程操作码关联的行为列表
        //List<NodeActionBO> nodeActionBOS = messageService.queryNodeActions(operateCode.getCode(), message.getFlowInsId(), message.getProductId());
        List<NodeActionBO> nodeActionBOS = new ArrayList<>();// 不走流程实现
        // 3、执行操作码关联的类型是正常消费类代码
        if (CollectionUtils.isEmpty(nodeActionBOS))
        {
            logger.info("OPERATE CODE 【" + operateCode.getCode() + "】HAS NO FLOW NODE INSTANCE , WILL RETURN CONFIG NODE ACTION LIST!");
            // 4、查询操作码关联的行为列表
            nodeActionBOS = messageService.queryNodeActions(operateCode.getCode(), message.getProductId());
        }
        if (CollectionUtils.isNotEmpty(nodeActionBOS))
        {
            nodeActionBOS = nodeActionBOS.stream().filter(nodeActionBO ->
                    nodeActionBO.getFlag() == RabbitMQConstants.IMPL_CLAZZ_TYPE_COMMON
                            || nodeActionBO.getFlag() == RabbitMQConstants.IMPL_CLAZZ_TYPE_AFTER
                            || nodeActionBO.getFlag() == RabbitMQConstants.IMPL_CLAZZ_TYPE_TODO).collect(Collectors.toList()
            );
        }
        initServiceContents(serviceContents, nodeActionBOS);
        executeServiceContents(serviceContents, message, RabbitMQConstants.IMPL_CLAZZ_TYPE_COMMON, serviceCallBack);
        executeServiceContents(serviceContents, message, RabbitMQConstants.IMPL_CLAZZ_TYPE_AFTER, serviceCallBack);
        // List<ServiceContent> nextServiceContents = new ArrayList<>();
        // 5、查询操作码下一个操作码节点前置消费类列表
//        List<NodeActionBO> nextActions = messageService.queryNextBeforeNodeActions(operateCode.getCode(), message.getFlowInsId(), message);
//        if (CollectionUtils.isNotEmpty(nextActions))
//        {
//            initServiceContents(nextServiceContents, nextActions);
//            logger.info("OPERATE CODE 【" + operateCode.getCode() + "】==================> " + "【" + nextActions.get(0).getCode() + "】");
//        }
//        executeServiceContents(nextServiceContents, message, RabbitMQConstants.IMPL_CLAZZ_TYPE_BEFORE, serviceCallBack);
//        if (CollectionUtils.isNotEmpty(nextServiceContents))
//        {
//            serviceContents.addAll(nextServiceContents);
//        }

    }

    /**
     * mq 消息 ack
     *
     * @param channel
     * @param message
     * @return
     * @throws IOException
     */
    private long mqAck(Channel channel, Message message) throws IOException
    {
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        channel.basicAck(deliveryTag, false);
        return deliveryTag;
    }

    @Override
    public void executeEventService(MQMessageEvent event)
    {
        MQMessage message = event.getMessage();
        MQMessageReceiveEvent receiveEvent = (MQMessageReceiveEvent) event;
        MQServiceCallBack serviceCallBack = null;
        long startTime = new Date().getTime();
        try
        {
            if (null != message)
            {
                message.setProcessIds(new ArrayList<>());
                logger.info("BEGIN ========> RECEIVE MQ MESSAGE ：【" + message.toString() + "】 START!");
                // 1、查询操作码，存在发送 mq ,不存在不发送 mq
                if (StringUtils.isEmpty(message.getOperateId()))
                {
                    logger.info("OPERATE CODE IS NULL,PLEASE SET 【op_code】 VALUE ON HTTP HEADER !");
                    return;
                }
                // 1、查询操作码是否存在
                OperateCode operateCode = messageService.supportOperateCode(message.getOperateId());
                if (null != operateCode)
                {
                    logger.info("OPERATE CODE 【" + message.getOperateId() + "】【" + MQOperateEnum.codeOf(message.getOperateId()) + "】 PROCESS START！");
                    final List<ServiceContent> contents = new ArrayList<>();
                    final List<ServiceContent> singleContents = new ArrayList<>();
                    serviceCallBack = new MQServiceCallBack()
                    {
                        @Override
                        public void basicAck() throws IOException
                        {
                            if (null != receiveEvent.getChannel())
                            {
                                long deliveryTag = mqAck(((MQMessageReceiveEvent) event).getChannel(), ((MQMessageReceiveEvent) event).getMqMessage());
                                if (null != message.getId() && message.getId() > 0)
                                {
                                    messageService.updateLogMQMessage(message.getId(), deliveryTag);
                                }
                            }
                        }

                        @Override
                        public void basicNAck() throws IOException
                        {
                            if (null != receiveEvent.getChannel())
                            {
                                long deliveryTag = ((MQMessageReceiveEvent) event).getMqMessage().getMessageProperties().getDeliveryTag();
                                receiveEvent.getChannel().basicReject(deliveryTag, true);
                            }
                        }

                        @Override
                        public void setActionDataId(String dataId)
                        {
                            message.setDataId(dataId);
                        }

                        @Override
                        public void launchFlowIns(String businessId, String basicId)
                        {
                            logger.info("OPERATE CODE 【" + operateCode.getCode() + "】 LAUNCH FLOW INSTANCE BUSINESS ID 【" + businessId + "】 BASIC ID 【" + basicId + "】！");
                            message.setBusinessId(businessId);
                            message.setBasicId(basicId);
                            messageService.resetFlowInsId(operateCode, message);
                            operateCode.setNewFlowIns(true);
                        }

                        @Override
                        public void setProcessId(Integer processId)
                        {
                            message.getProcessIds().add(processId);
                        }

                        @Override
                        public void removeProcessId(Integer processId)
                        {
                            message.getProcessIds().removeIf(integer -> integer.equals(processId));
                        }

                        @Override
                        public Integer getMessageId()
                        {
                            return message.getId();
                        }

                        @Override
                        public Integer getProductId()
                        {
                            return Integer.parseInt(message.getProductId());
                        }

                        @Override
                        public Integer getUserId()
                        {
                            return message.getUserId();
                        }

                        @Override
                        public Integer getCpyId()
                        {
                            return message.getCpyId();
                        }

                        @Override
                        public void setTODOWorkInput(String input)
                        {
                            message.setToDoWorkInput(input);
                        }

                        @Override
                        public void putServiceContent(ServiceContent content)
                        {
                            singleContents.add(content);
                        }
                    };
                    executeEventAction(operateCode, message, serviceCallBack, contents);
                    if (CollectionUtils.isNotEmpty(singleContents))
                    {
                        // 执行单步执行消费类
                        executeServiceContents(singleContents, message, serviceCallBack);
                        // 打印消费类执行日志
                        contents.addAll(singleContents);
                    }
                    logServiceContents(contents);

                    logger.info("OPERATE CODE 【" + message.getOperateId() + "】【" + MQOperateEnum.codeOf(message.getOperateId()) + "】 PROCESS END！");
                }
                else
                {
                    logger.info("OPERATE CODE 【" + message.getOperateId() + "】IS NOT SUPPORTED , PLEASE CHECK YOUR CONFIG!");
                }
                logger.info("END ========> RECEIVE MQ MESSAGE ：【" + message.toString() + "】 END!");
            }
        }
        catch (Throwable ex)
        {
            logger.error(ex.getMessage(), ex);
        }
        finally
        {
            try
            {
                long endTime = new Date().getTime();
                if (null != message)
                {
                    messageService.logFlowLogs(message, startTime, endTime);
                }
                if (null != serviceCallBack)
                {
                    serviceCallBack.basicAck();
                }
                else
                {
                    mqAck(((MQMessageReceiveEvent) event).getChannel(), ((MQMessageReceiveEvent) event).getMqMessage());
                }
            }
            catch (Exception e)
            {
                logger.error(e.getMessage(), e);
            }
        }
    }

    /**
     * 获取执行消费类
     *
     * @param serviceContents 包装消费类集合
     * @param nodeActionBOS   业务节点行为
     */
    private void initServiceContents(List<ServiceContent> serviceContents, List<NodeActionBO> nodeActionBOS)
    {
        if (CollectionUtils.isNotEmpty(nodeActionBOS))
        {
            nodeActionBOS.forEach(nodeActionBO ->
            {
                MQService<String> service = mqServiceMap.get(nodeActionBO.getClazz());
                if (null != service)
                {
                    ServiceContent serviceContent = ServiceContent.newInstance(service);
                    serviceContent.setId(nodeActionBO.getId());
                    serviceContent.setType(nodeActionBO.getType());
                    serviceContent.setFlag(nodeActionBO.getFlag());
                    serviceContent.setSort(nodeActionBO.getSort());
                    serviceContent.setMessageService(messageService);
                    String clazz = service.getImplClass().getSuperclass().getName();
                    for (ServiceContent content : serviceContents)
                    {
                        if (clazz.equals(content.getClazz()))
                        {
                            content.setStatus(ServiceContent.OVERRIDE_STATUS);
                        }
                    }
                    serviceContents.add(serviceContent);
                }
            });
        }
    }

    private void executeServiceContents(List<ServiceContent> serviceContents, MQMessage message, Integer clazzType, MQServiceCallBack serviceCallBack)
    {
        Stream<ServiceContent> contentStream = serviceContents.stream().filter(serviceContent -> serviceContent.getFlag().equals(clazzType)).sorted(Comparator.comparing(ServiceContent::getSort));
        Iterator<ServiceContent> iterable = contentStream.iterator();
        int i = 0;
        while (iterable.hasNext())
        {
            ServiceContent serviceContent = iterable.next();
            if (StringUtils.isEmpty(serviceContent.getStatus()))
            {
                serviceContent.doService(message, serviceCallBack);
                i++;
            }
        }
        logger.info("OPERATE CODE 【" + message.getOperateId() + "】【" + MQOperateEnum.codeOf(message.getOperateId()) + "】 CALL 【" + ServiceContent.ServiceType.valueOf(clazzType).name() + "】 SERVICE TOTAL SIZE :【" + i + "】！");
    }

    /**
     * 执行消费类
     *
     * @param serviceContents 包装消费类集合
     * @param message         MQ 消息报文
     * @param serviceCallBack 消费类执行中回调类
     */
    private void executeServiceContents(List<ServiceContent> serviceContents, MQMessage message, MQServiceCallBack serviceCallBack)
    {
        executeServiceContents(serviceContents, message, RabbitMQConstants.IMPL_CLAZZ_TYPE_COMMON, serviceCallBack);
        executeServiceContents(serviceContents, message, RabbitMQConstants.IMPL_CLAZZ_TYPE_AFTER, serviceCallBack);
        executeServiceContents(serviceContents, message, RabbitMQConstants.IMPL_CLAZZ_TYPE_BEFORE, serviceCallBack);
    }

    private void logServiceContents(List<ServiceContent> serviceContents)
    {
        // 打印消费类执行统计情况
        List<String> infos = new ArrayList<>();
        int length = 0;
        if (CollectionUtils.isNotEmpty(serviceContents))
        {
            long totalCost = 0L;
            for (ServiceContent serviceContent : serviceContents)
            {
                if (StringUtils.isNotEmpty(serviceContent.getStatus()))
                {
                    long cost = serviceContent.getEndTime() - serviceContent.getBeginTime();
                    totalCost += cost;
                    String info = "= BEGIN-TIME:【" + serviceContent.getBeginTime() + "】END-TIME:【" + serviceContent.getEndTime() + "】COST:【" + cost + "】【" + serviceContent.getStatus() + "】【" + serviceContent.getClazz() + "】INPUT:【" + serviceContent.getInput() + "】OUTPUT:【" + serviceContent.getOutput() + "】";
                    length = Math.max(info.getBytes().length, length);
                    infos.add(info);
                }
            }
            String genStrString = CharsUtil.genStrString((length + 1), '=');
            if (CollectionUtils.isNotEmpty(infos))
            {
                logger.info(genStrString);
                for (String info : infos)
                {
                    logger.info("=" + info);
                }
                logger.info("===> CALL SERVICE TOTAL COST:【" + totalCost + "】！");
                logger.info(genStrString);
            }
        }
    }

    @Override
    public final void afterPropertiesSet() throws Exception
    {
//        Map<String, MQService> serviceMap = context.getBeansOfType(MQService.class);
//        serviceMap.forEach((key, value) ->
//        {
//            mqServiceMap.put(value.getImplClass().getName(), value);
//        });
    }

    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey)
    {
        logger.info("【" + exchange + "】【" + routingKey + "】成功回调 mq 消息：【" + new String(message.getBody()) + "】回复状态：【" + replyCode + "】回复文本：【" + replyText + "】");
    }
}
