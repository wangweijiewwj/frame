package com.fintech.rabbitmq.service;

import com.fintech.rabbitmq.constants.MQOperateEnum;

/**
 * @ClassName MQOperateService
 * @Date 2020/2/27 15:27
 * @Auther wangyongyong
 * @Version 1.0
 * @Description TODO
 */
public interface IMQOperateService
{

    void doService(MQOperateEnum operate, String dataId, String businessId, String basicId, Integer kindId, String productId, Integer cpyId, Integer userId);

    void doService(MQOperateEnum operate, String dataId, String businessId, String basicId, Integer kindId, String productId, Integer userId);

    void doService(MQOperateEnum operate, String dataId, String businessId, String basicId, Integer kindId, String productId);

    void doService(MQOperateEnum operate, String dataId, String businessId, String basicId, Integer kindId);

    /**
     * 发送执行 Mq 方法
     *
     * @param operate    业务操作
     * @param dataId     数据 Id
     * @param businessId 业务 Id,没有传值 null
     * @param basicId    业务基础 Id,没有传值 null
     */
    void doService(MQOperateEnum operate, String dataId, String businessId, String basicId);

    void doService(MQOperateEnum operate, String dataId, String businessId);

    void doService(MQOperateEnum operate, String dataId);

    /**
     * 发送执行 Mq 方法
     *
     * @param dataId     数据 Id
     * @param businessId 业务 Id,没有传值 null
     * @param basicId    业务基础 Id,没有传值 null
     */
    void doService(String dataId, String businessId, String basicId);

    /**
     * 发送执行 Mq 方法
     *
     * @param dataId     数据 Id
     * @param businessId 业务 Id,没有传值 null
     * @param basicId    业务基础 Id,没有传值 null
     * @param kindId     小类 Id
     */
    void doService(String dataId, String businessId, String basicId, Integer kindId);

    void doService(String dataId, String businessId);

    void doService(String dataId, String businessId, Integer kindId);

    void doService(String dataId);

    void doService(String dataId, Integer kindId);
}
