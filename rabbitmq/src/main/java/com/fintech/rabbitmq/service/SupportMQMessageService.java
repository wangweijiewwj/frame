package com.fintech.rabbitmq.service;

import com.fintech.rabbitmq.bean.MQMessage;
import com.fintech.rabbitmq.bean.NodeActionBO;
import com.fintech.rabbitmq.entity.OperateCode;

import java.util.List;

/**
 * @ClassName SupportMQMessageService
 * @Date 2020/2/17 13:39
 * @Auther wangyongyong
 * @Version 1.0
 * @Description TODO
 */
public interface SupportMQMessageService extends SupportClientMQMessageService
{

    List<NodeActionBO> queryNodeActions(String code, String productId);

    /**
     * 查询操作码节点行为消费类列表
     *
     * @param code      操作码
     * @param flowInsId 实例 Id
     * @return
     */
    List<NodeActionBO> queryNodeActions(String code, Integer flowInsId);

    List<NodeActionBO> queryNodeActions(String code, Integer flowInsId, String productId);

    /**
     * 查询操作码下一个操作码的前置行为列表
     *
     * @param code      操作码
     * @param flowInsId 实例 Id
     * @param message   mq 消息
     * @return
     */
    List<NodeActionBO> queryNextBeforeNodeActions(String code, Integer flowInsId, MQMessage message);

    /**
     * 更新消息消费完毕
     *
     * @param id
     * @param deliveryTag
     */
    void updateLogMQMessage(Integer id, long deliveryTag);

    /**
     * 设置获取 流程实例 Id
     *
     * @param operateCode
     * @param message
     */
    void resetFlowInsId(OperateCode operateCode, MQMessage message);

    /**
     * 记录 mq 节点行为执行异常
     *
     * @param id
     * @param type
     * @param name
     * @param message
     * @param e
     */
    void logMQMessageActionException(Integer id, Integer type, String name, MQMessage message, Exception e);

    void logFlowLogs(MQMessage message, long startTime, long endTime);

    /**
     * 更新行为实现入参执行
     *
     * @param id      ActionIns 实例Id
     * @param type
     * @param message mq 消息
     * @param output
     */
    Integer updateActionInsDataId(Integer id, Integer type, MQMessage message, String output, Integer executeStatus);
}
