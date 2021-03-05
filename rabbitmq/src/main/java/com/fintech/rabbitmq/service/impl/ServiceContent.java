package com.fintech.rabbitmq.service.impl;

import com.fintech.rabbitmq.bean.MQMessage;
import com.fintech.rabbitmq.constants.RabbitMQConstants;
import com.fintech.rabbitmq.service.AsAbstractService;
import com.fintech.rabbitmq.service.MQService;
import com.fintech.rabbitmq.service.MQServiceCallBack;
import com.fintech.rabbitmq.service.SupportMQMessageService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @ClassName ServiceContent
 * @Date 2020/2/17 15:46
 * @Auther wangyongyong
 * @Version 1.0
 * @Description TODO
 */
public class ServiceContent
{

    private static final String SUCCESS_STATUS = "✔";
    private static final String FAILURE_STATUS = "✘";
    public static final String OVERRIDE_STATUS = "O";

    /**
     * 消费类数据库主键
     */
    private Integer id;

    /**
     * 消费类类型 ：1 流程配置实例消费类 2 操作码配置消费类
     *
     * @see RabbitMQConstants#IMPL_CLAZZ_FLOW_INS
     * @see RabbitMQConstants#IMPL_CLAZZ_CODE_CONF
     */
    private Integer type;

    /**
     * 消费类排序
     */
    private Integer sort;

    /**
     * 消费类标识
     *
     * @see ServiceType
     */
    private Integer flag;

    /**
     * 消费类入参
     */
    private String input;

    /**
     * 消费类出参
     */
    private String output;

    /**
     * 消费类执行状态
     */
    private String status;

    /**
     * 消费类类名
     */
    private String clazz;

    /**
     * 开始时间
     */
    private Long beginTime = 0L;

    /**
     * 结束时间
     */
    private Long endTime = 0L;

    private SupportMQMessageService messageService;

    public void setId(Integer id)
    {
        this.id = id;
    }

    public void setType(Integer type)
    {
        this.type = type;
    }

    public Integer getSort()
    {
        return sort;
    }

    public void setSort(Integer sort)
    {
        this.sort = sort;
    }

    public void setFlag(Integer flag)
    {
        this.flag = flag;
    }

    public Integer getFlag()
    {
        return flag;
    }

    public void setMessageService(SupportMQMessageService messageService)
    {
        this.messageService = messageService;
    }

    public String getInput()
    {
        return input;
    }

    public String getOutput()
    {
        return output;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getStatus()
    {
        return status;
    }

    public String getClazz()
    {
        return clazz;
    }

    public Long getBeginTime()
    {
        return beginTime;
    }

    public Long getEndTime()
    {
        return endTime;
    }

    public enum ServiceType
    {
        前置, 正常, 后置;

        public static ServiceType valueOf(Integer integer)
        {
            switch (integer)
            {
                case -1:
                    return ServiceType.前置;
                case 0:
                    return ServiceType.正常;
                case 1:
                    return ServiceType.后置;
                default:
                    break;
            }
            return null;
        }
    }

    private static final Logger logger = LoggerFactory.getLogger(ServiceContent.class);

    private MQService<String> service;

    private ServiceContent()
    {

    }

    public static ServiceContent newInstance(MQService<String> service)
    {
        return newInstance(service, null);
    }

    public static ServiceContent newInstance(MQService<String> service, String input)
    {
        ServiceContent content = new ServiceContent();
        content.service = service;
        content.clazz = service.getImplClass().getName();
        content.input = input;
        return content;
    }

    public void doService(MQMessage message, MQServiceCallBack serviceCallBack)
    {
        beginTime = System.currentTimeMillis();
        String clazzType = (type == RabbitMQConstants.IMPL_CLAZZ_FLOW_INS ? "FLOW_INS" : (type == RabbitMQConstants.IMPL_CLAZZ_CODE_CONF ? "CODE_CONF" : "NONE"));
        try
        {
            if (StringUtils.isEmpty(input))
            {
                input = message.getDataId();
            }
            message.setDataId(input);
            logger.info("BEGIN ==========> CALL SERVICE 【" + ServiceType.valueOf(flag) + "】【" + clazzType + "】【" + clazz + "】INPUT:[" + input + "]");
            AsAbstractService abstractService = (AsAbstractService) service;
            if (abstractService.supportDoService(message.getDataId()))
            {
                abstractService.doService(input, serviceCallBack);
                output = message.getDataId();
                status = SUCCESS_STATUS;
            }
        }
        catch (Exception e)
        {
            logger.error(e.getMessage(), e);
            messageService.logMQMessageActionException(id, type, clazz, message, e);
            status = FAILURE_STATUS;
        }
        finally
        {
            endTime = System.currentTimeMillis();
            int executeStatus = SUCCESS_STATUS.equals(status) ? 1 : -1;// 执行成功，状态1 ，执行失败，状态 -1 ，所有消费类的状态都应该是 1
            id = messageService.updateActionInsDataId(id, type, message, output, executeStatus);
            logger.info("END =============> CALL SERVICE 【" + ServiceType.valueOf(flag) + "】【" + clazzType + "】【" + clazz + "】OUTPUT:[" + output + "]");
        }
    }
}
