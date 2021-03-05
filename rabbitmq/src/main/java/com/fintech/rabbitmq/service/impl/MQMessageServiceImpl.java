package com.fintech.rabbitmq.service.impl;

import com.alibaba.fastjson.JSON;
import com.fintech.rabbitmq.bean.*;
import com.fintech.rabbitmq.constants.MQOperateEnum;
import com.fintech.rabbitmq.constants.RabbitMQConstants;
import com.fintech.rabbitmq.entity.*;
import com.fintech.rabbitmq.mapper.*;
import com.fintech.rabbitmq.service.MQService;
import com.fintech.rabbitmq.service.SupportMQMessageService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @ClassName MQMessageServiceImpl
 * @Date 2020/2/17 17:01
 * @Auther wangyongyong
 * @Version 1.0
 * @Description TODO
 */
@Service
public class MQMessageServiceImpl implements SupportMQMessageService, InitializingBean
{

    private static final Logger logger = LoggerFactory.getLogger(MQMessageServiceImpl.class);

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private OperateCodeConfMapper operateCodeConfMapper;

    @Autowired
    private OperateActionConfMapper operateActionConfMapper;

    @Autowired
    private MqMessageLogMapper mqMessageLogMapper;

    @Autowired
    private NodeActionClazzMapper nodeActionClazzMapper;

    @Autowired
    private ActionInsExceptionMapper actionInsExceptionMapper;

    @Autowired
    private OperateActionInsMapper operateActionInsMapper;

    @Override
    public OperateCode supportOperateCode(String operateId)
    {
        OperateCodeConf operateCodeConf = operateCodeConfMapper.selectOneByCode(operateId);
        if (null != operateCodeConf)
        {
            OperateCode code = new OperateCode();
            BeanUtils.copyProperties(operateCodeConf, code);
            return code;
        }
        return null;
    }

    @Override
    public void logMQMessage(String exchange, String routingKey, MQMessage message)
    {
        MqMessageLog log = new MqMessageLog();
        log.setConsume(0);
        log.setExchange(exchange);
        log.setRoutingKey(routingKey);
        log.setpTime(new Date().getTime());
        mqMessageLogMapper.insertSelective(log);
        message.setId(log.getId());
        log.setMessage(JSON.toJSONString(message));
        mqMessageLogMapper.updateByPrimaryKeySelective(log);
    }

    @Override
    public List<NodeActionBO> queryNodeActions(String code, String productId)
    {
        List<NodeActionBO> nodeActionBOS = null;
        List<OperateActionConf> operateActionConfs = operateActionConfMapper.selectAllByCodeAndProductId(code, productId);
        if (CollectionUtils.isNotEmpty(operateActionConfs))
        {
            nodeActionBOS = new ArrayList<>();
            NodeActionBO nodeActionBO = null;
            for (OperateActionConf operateActionConf : operateActionConfs)
            {
                nodeActionBO = new NodeActionBO();
                BeanUtils.copyProperties(operateActionConf, nodeActionBO);
                nodeActionBO.setType(RabbitMQConstants.IMPL_CLAZZ_CODE_CONF);
                nodeActionBOS.add(nodeActionBO);
            }
        }
        return nodeActionBOS;
    }

    @Override
    public List<NodeActionBO> queryNodeActions(String code, Integer flowInsId)
    {
        return null;
    }

    @Override
    public List<NodeActionBO> queryNodeActions(String code, Integer flowInsId, String productId)
    {
        return null;
    }

    @Override
    public List<NodeActionBO> queryNextBeforeNodeActions(String code, Integer flowInsId, MQMessage message)
    {
        return null;
    }

    @Override
    public void updateLogMQMessage(Integer id, long deliveryTag)
    {
        MqMessageLog log = mqMessageLogMapper.selectByPrimaryKey(id);
        if (null != log)
        {
            log.setConsume(1);
            log.setcTime(new Date().getTime());
            log.setDeliveryTag(deliveryTag);
            mqMessageLogMapper.updateByPrimaryKeySelective(log);
        }
    }

    @Override
    public void resetFlowInsId(OperateCode operateCode, MQMessage message)
    {

    }

    @Override
    public void logMQMessageActionException(Integer id, Integer type, String name, MQMessage message, Exception e)
    {
        ActionInsException exception = new ActionInsException();
        exception.setAcId(id);
        exception.setAcName(name);
        exception.setType(type);
        exception.setMsgId(message.getId());
        exception.setExTime(System.currentTimeMillis());
        if (StringUtils.isNotEmpty(ExceptionUtils.getStackTrace(e)))
        {
            exception.setException("【" + message.getId() + "】 ==> " + ExceptionUtils.getStackTrace(e));
        }
        else
        {
            exception.setException("【" + message.getId() + "】 ==> " + ExceptionUtils.getMessage(e));
        }
        actionInsExceptionMapper.insertSelective(exception);
    }

    @Override
    public void logFlowLogs(MQMessage message, long startTime, long endTime)
    {
        if (null == message.getFlowInsId())
        {
            return;
        }
    }

    @Override
    public Integer updateActionInsDataId(Integer id, Integer type, MQMessage message, String output, Integer executeStatus)
    {
        if (type == RabbitMQConstants.IMPL_CLAZZ_CODE_CONF)
        {
            OperateActionConf operateActionConf = operateActionConfMapper.selectByPrimaryKey(id);
            if (null != operateActionConf)
            {
                OperateActionIns operateActionIns = new OperateActionIns();
                BeanUtils.copyProperties(operateActionConf, operateActionIns);
                operateActionIns.setDataId(message.getDataId());
                operateActionIns.setOutput(output);
                operateActionIns.setProcessId(message.getUserId());
                operateActionIns.setProcessTime(new Date().getTime());
                operateActionIns.setMsgId(message.getId());
                operateActionIns.setStatus(executeStatus);
                operateActionInsMapper.insertSelective(operateActionIns);
                id = operateActionIns.getId();
            }
        }
        else if (type == RabbitMQConstants.IMPL_CLAZZ_FLOW_INS)
        {

        }
        return id;
    }

    @Override
    public void afterPropertiesSet() throws Exception
    {
        Map<String, MQService> stringMQServiceMap = this.applicationContext.getBeansOfType(MQService.class);
        List<NodeActionClazz> clazzes = new ArrayList<>();
        stringMQServiceMap.forEach((key, value) ->
        {
            if (null != value && null != value.getImplClass())
            {
                com.fintech.rabbitmq.annotation.MQService annotation = AopUtils.getTargetClass(value).getAnnotation(com.fintech.rabbitmq.annotation.MQService.class);
                NodeActionClazz actionClazz = new NodeActionClazz();
                if (null != annotation)
                {
                    actionClazz.setName(annotation.name());
                    actionClazz.setClazz(value.getImplClass().getName());
                    actionClazz.setDesc(annotation.description());
                    actionClazz.setParamDesc(annotation.paramDesc());
                    MQOperateEnum operate = annotation.operate();
                    if (!operate.equals(MQOperateEnum.NONE))
                    {
                        OperateCodeConf conf = operateCodeConfMapper.selectOneByCode(operate.getCode());
                        if (null != conf)
                        {
                            OperateActionConf actionConf = new OperateActionConf();
                            actionConf.setOpId(conf.getId());
                            actionConf.setCode(conf.getCode());
                            actionConf.setClazz(actionClazz.getClazz());
                            actionConf.setFlag(annotation.flag());
                            actionConf.setSort(annotation.sort());
                            actionConf.setStatus(1);
                            actionConf.setDesc(conf.getName());
                            actionConf.setCreator(0);
                            actionConf.setCreateTime(new Date().getTime());
                            if (CollectionUtils.isEmpty(operateActionConfMapper.selectDistinctClazzByCodeAndClazz(actionConf.getCode(), actionConf.getClazz())))
                            {
                                operateActionConfMapper.insertSelective(actionConf);
                            }
                        }
                    }
                }
                else
                {
                    actionClazz.setName("");
                    actionClazz.setClazz(value.getImplClass().getName());
                    actionClazz.setDesc("");
                    actionClazz.setParamDesc("");
                }
                clazzes.add(actionClazz);
            }
            else
            {
                logger.error("======================>" + key + " is not init!");
            }
        });
        //nodeActionClazzMapper.batchInsertSelective(clazzes);
    }
}
