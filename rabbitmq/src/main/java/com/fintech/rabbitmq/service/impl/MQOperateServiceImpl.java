package com.fintech.rabbitmq.service.impl;

import com.fintech.rabbitmq.bean.MQMessage;
import com.fintech.rabbitmq.constants.MQOperateEnum;
import com.fintech.rabbitmq.service.IMQOperateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/**
 * @ClassName MQOperateServiceImpl
 * @Date 2020/2/27 15:29
 * @Auther wangyongyong
 * @Version 1.0
 * @Description TODO
 */
@Service
public class MQOperateServiceImpl implements IMQOperateService
{

    @Autowired
    private MQServiceStrategy serviceStrategy;

    @Override
    public void doService(MQOperateEnum operate, String dataId, String businessId, String basicId, Integer kindId, String productId, Integer cpyId, Integer userId)
    {
        MQMessage message = new MQMessage();
        if (null != operate)
        {
            message.setOperateId(operate.getCode());
        }
        message.setDataId(dataId);
        message.setBusinessId(businessId);
        message.setBasicId(basicId);
        message.setKindId(kindId);
        message.setProductId(productId);
        message.setCpyId(cpyId);
        message.setUserId(userId);
        if (TransactionSynchronizationManager.isActualTransactionActive())
        {
            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter()
            {
                @Override
                public void afterCommit()
                {
                    serviceStrategy.sendMQMessage(message);
                }
            });
        }
        else
        {
            serviceStrategy.sendMQMessage(message);
        }
    }

    @Override
    public void doService(MQOperateEnum operate, String dataId, String businessId, String basicId, Integer kindId, String productId, Integer userId)
    {
        doService(operate, dataId, businessId, basicId, kindId, productId, null, userId);
    }

    @Override
    public void doService(MQOperateEnum operate, String dataId, String businessId, String basicId, Integer kindId, String productId)
    {
        doService(operate, dataId, businessId, basicId, kindId, productId, null, null);
    }

    @Override
    public void doService(MQOperateEnum operate, String dataId, String businessId, String basicId, Integer kindId)
    {
        doService(operate, dataId, businessId, basicId, kindId, null, null, null);
    }

    @Override
    public void doService(MQOperateEnum operate, String dataId, String businessId, String basicId)
    {
        doService(operate, dataId, businessId, basicId, null);
    }

    @Override
    public void doService(MQOperateEnum operate, String dataId, String businessId)
    {
        doService(operate, dataId, businessId, null, null);
    }

    @Override
    public void doService(MQOperateEnum operate, String dataId)
    {
        doService(operate, dataId, null, null, null);
    }

    @Override
    public void doService(String dataId, String businessId, String basicId)
    {
        doService(null, dataId, businessId, basicId, null);
    }

    @Override
    public void doService(String dataId, String businessId, String basicId, Integer kindId)
    {
        doService(null, dataId, businessId, basicId, kindId);
    }

    @Override
    public void doService(String dataId, String businessId)
    {
        doService(null, dataId, businessId, null, null);
    }

    @Override
    public void doService(String dataId, String businessId, Integer kindId)
    {
        doService(null, dataId, businessId, null, kindId);
    }

    @Override
    public void doService(String dataId)
    {
        doService(null, dataId, null, null, null);
    }

    @Override
    public void doService(String dataId, Integer kindId)
    {
        doService(null, dataId, null, null, kindId);
    }
}
