package com.fintech.rabbitmq.service;

import com.fintech.rabbitmq.service.impl.ServiceContent;

import java.io.IOException;

/**
 * @ClassName MQServiceCallBack
 * @Date 2020/1/2 19:01
 * @Auther wangyongyong
 * @Version 1.0
 * @Description TODO
 */
public interface MQServiceCallBack
{
    /**
     * mq 消息处理完成确认 - 消费
     */
    void basicAck() throws IOException;

    /**
     * mq 消息处理完成确认 - 不消费
     */
    void basicNAck() throws IOException;

    /**
     * 设置行为数据 id
     *
     * @param dataId 数据 Id
     */
    void setActionDataId(String dataId);

    /**
     * 启动一个流程实例
     *
     * @param businessId 业务 Id
     * @param basicId    基础业务 Id
     */
    void launchFlowIns(String businessId, String basicId);

    /**
     * 设置添加处理人
     *
     * @param processId 处理人Id
     */
    void setProcessId(Integer processId);

    /**
     * 设置移除处理人
     *
     * @param processId 处理人Id
     */
    void removeProcessId(Integer processId);

    /**
     * 获取消息Id
     *
     * @return
     */
    Integer getMessageId();

    /**
     * 获取产品 Id
     *
     * @return
     */
    Integer getProductId();

    /**
     * 获取用户 Id
     *
     * @return
     */
    Integer getUserId();

    /**
     * 获取企业 id
     *
     * @return
     */
    Integer getCpyId();

    /**
     * 设置代办业务处理类，获取业务Id,设置入参
     *
     * @param input 入参
     */
    void setTODOWorkInput(String input);

    /**
     * 加入消费类执行上下文
     *
     * @param content 消费类执行上下文
     */
    void putServiceContent(ServiceContent content);

}
