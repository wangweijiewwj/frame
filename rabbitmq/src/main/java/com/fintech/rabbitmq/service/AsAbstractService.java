package com.fintech.rabbitmq.service;

import com.fintech.rabbitmq.service.impl.MQServiceBuilder;
import com.fintech.rabbitmq.service.impl.ServiceContent;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * @ClassName AsAbstractService<I>
 * @Date 2019/12/20 14:29
 * @Auther wangyongyong
 * @Version 1.0
 * @Description TODO
 */
public abstract class AsAbstractService implements MQService<String>, InitializingBean
{

    private static Logger logger = LoggerFactory.getLogger(AsAbstractService.class);

    private Class<String> inputClass;

    private Class implClass;

    /**
     * 设置下一个执行类的数据 Id
     *
     * @param dataId
     */
    protected void setNextActionDataId(String dataId)
    {
        logger.info("set next action dataId:" + dataId);
        MQServiceCallBack mqServiceCallBack = threadLocal.get();
        if (null != mqServiceCallBack)
        {
            mqServiceCallBack.setActionDataId(dataId);
            logger.info("set next action dataId [" + dataId + "] successful!");
        }
    }

    private Class<?> getActualTypeArgument(int i)
    {
        Class<?> entityClass = Object.class;
        Type genericSuperclass = implClass.getGenericSuperclass();
        if (genericSuperclass instanceof ParameterizedType)
        {
            Type[] actualTypeArguments = ((ParameterizedType) genericSuperclass).getActualTypeArguments();
            if (actualTypeArguments != null && actualTypeArguments.length > i)
            {
                entityClass = (Class<?>) actualTypeArguments[i];
            }
        }
        return entityClass;
    }

    @Override
    public Class getImplClass()
    {
        return this.implClass;
    }

    private static ThreadLocal<MQServiceCallBack> threadLocal = new ThreadLocal<>();

    @Override
    public boolean supportDoService(String input)
    {
        return true;
    }

    public void doService(String input, MQServiceCallBack callBack) throws Exception
    {
        threadLocal.set(callBack);
        this.doService(input);
        threadLocal.remove();
    }

    /**
     * 执行 业务逻辑
     *
     * @param input 入参
     * @return O 出参
     * @throws Exception 业务执行异常
     */
    public abstract void doService(String input) throws Exception;

    @Override
    public void afterPropertiesSet() throws Exception
    {
        this.implClass = this.getClass();
        this.inputClass = (Class<String>) this.getActualTypeArgument(0);
        MQServiceBuilder.putMQService(this);
    }

    @Override
    public Class<String> getInputClass()
    {
        return this.inputClass;
    }

    /**
     * 确认消息完成
     */
    public void basicAck() throws IOException
    {
        MQServiceCallBack mqServiceCallBack = threadLocal.get();
        if (null != mqServiceCallBack)
        {
            mqServiceCallBack.basicAck();
        }
    }

    /**
     * 确认消息未完成
     */
    public void basicNAck() throws IOException
    {
        MQServiceCallBack mqServiceCallBack = threadLocal.get();
        if (null != mqServiceCallBack)
        {
            mqServiceCallBack.basicNAck();
        }
    }

    /**
     * 启动一个流程实例
     *
     * @param businessId 业务 Id
     * @param basicId    基础业务 Id
     */
    public void launchFlowIns(String businessId, String basicId)
    {
        logger.info("BEGION ============> launch flow ins businessId :【" + businessId + "】 basicId : 【" + basicId + "】");
        MQServiceCallBack mqServiceCallBack = threadLocal.get();
        if (null != mqServiceCallBack)
        {
            mqServiceCallBack.launchFlowIns(businessId, basicId);
            logger.info("END ============> launch flow ins businessId :【" + businessId + "】 basicId : 【" + basicId + "】");
        }
    }

    /**
     * 添加处理人 Id
     *
     * @param processId 处理人Id
     */
    public void addProcessId(Integer processId)
    {
        logger.info("BEGION ============> add processId 【" + processId + "】");
        MQServiceCallBack mqServiceCallBack = threadLocal.get();
        if (null != mqServiceCallBack)
        {
            mqServiceCallBack.setProcessId(processId);
            logger.info("END ============> add processId 【" + processId + "】");
        }
    }

    /**
     * 添加处理人 Id
     *
     * @param processIds 处理人Id 集合
     */
    public void addProcessId(List<Integer> processIds)
    {
        if (CollectionUtils.isNotEmpty(processIds))
        {
            processIds.forEach(this::addProcessId);
        }
    }

    /**
     * 移除处理人 Id
     *
     * @param processId 处理人 Id
     */
    public void removeProcessId(Integer processId)
    {
        logger.info("BEGION ============> remove processId 【" + processId + "】");
        MQServiceCallBack mqServiceCallBack = threadLocal.get();
        if (null != mqServiceCallBack)
        {
            mqServiceCallBack.removeProcessId(processId);
            logger.info("END ============> remove processId 【" + processId + "】");
        }
    }

    /**
     * 获取头部产品 Id
     *
     * @return
     */
    public static Integer getProductId()
    {
        MQServiceCallBack mqServiceCallBack = threadLocal.get();
        if (null != mqServiceCallBack)
        {
            return mqServiceCallBack.getProductId();
        }
        return null;
    }

    /**
     * 获取消费端消息ID
     *
     * @return
     */
    public Integer getMessageId()
    {
        MQServiceCallBack mqServiceCallBack = threadLocal.get();
        if (null != mqServiceCallBack)
        {
            return mqServiceCallBack.getMessageId();
        }
        return null;
    }

    /**
     * 获取当前用户 Id
     *
     * @return
     */
    public static Integer getUserId()
    {
        MQServiceCallBack mqServiceCallBack = threadLocal.get();
        if (null != mqServiceCallBack)
        {
            return mqServiceCallBack.getUserId();
        }
        return null;
    }

    /**
     * 获取当前用户所属企业 Id
     *
     * @return
     */
    public static Integer getCpyId()
    {
        MQServiceCallBack mqServiceCallBack = threadLocal.get();
        if (null != mqServiceCallBack)
        {
            return mqServiceCallBack.getCpyId();
        }
        return null;
    }

    /**
     * 设置代办业务处理类入参
     *
     * @param input 入参
     */
    public void setTODOWorkInput(String input)
    {
        MQServiceCallBack mqServiceCallBack = threadLocal.get();
        if (null != mqServiceCallBack)
        {
            mqServiceCallBack.setTODOWorkInput(input);
        }
    }

    /**
     * 加入正常消费类执行完毕之后的后续执行
     *
     * @param content
     */
    public void putServiceContent(ServiceContent content)
    {
        MQServiceCallBack mqServiceCallBack = threadLocal.get();
        if (null != mqServiceCallBack)
        {
            mqServiceCallBack.putServiceContent(content);
        }
    }

}
