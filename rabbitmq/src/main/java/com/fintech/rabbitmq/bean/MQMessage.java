package com.fintech.rabbitmq.bean;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName MQMessage
 * @Date 2020/1/9 9:08
 * @Auther wangyongyong
 * @Version 1.0
 * @Description TODO
 */
public class MQMessage implements Serializable
{

    /**
     * 消息 Id
     */
    @JSONField(name = "a")
    private Integer id;

    /**
     * 操作码 Id
     */
    @JSONField(name = "b")
    private String operateId;

    /**
     * 数据 Id
     */
    @JSONField(name = "c")
    private String dataId;

    /**
     * 原始报文
     */
    @JSONField(deserialize = false, serialize = false)
    private String finalDataId;

    /**
     * 流程实例 Id
     */
    @JSONField(name = "d")
    private Integer flowInsId;

    /**
     * 业务 Id
     */
    @JSONField(name = "e")
    private String businessId;

    /**
     * 基础业务 Id
     */
    @JSONField(name = "f")
    private String basicId;

    /**
     * 用户 id
     */
    @JSONField(name = "g")
    private Integer userId;

    /**
     * 处理人列表
     */
    @JSONField(serialize = false)
    private List<Integer> processIds;

    /**
     * 是否存在下一个节点
     */
    @JSONField(serialize = false)
    private boolean existsNextNode = false;

    /**
     * 下一个操作码列表
     */
    @JSONField(name = "h")
    private List<String> nextOperateIds;

    /**
     * 产品 Id
     */
    @JSONField(name = "i")
    private String productId = "1000";

    /**
     * 流程 Id
     */
    @JSONField(name = "j")
    private Integer flowId;

    /**
     * 企业Id
     */
    @JSONField(name = "k")
    private Integer cpyId;

    /**
     * 小类 id
     */
    @JSONField(name = "l")
    private Integer kindId;

    /**
     * 代办业务处理入参
     */
    @JSONField(serialize = false)
    private String toDoWorkInput;

    /**
     * 是否新的流程实例
     */
    @JSONField(serialize = false)
    private Boolean newFlowIns = false;

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getOperateId()
    {
        return operateId;
    }

    public void setOperateId(String operateId)
    {
        this.operateId = operateId;
    }

    public String getDataId()
    {
        return dataId;
    }

    public void setDataId(String dataId)
    {
        if (null == finalDataId && null != dataId)
        {
            this.finalDataId = dataId;
        }
        this.dataId = dataId;
    }

    public String getFinalDataId()
    {
        return finalDataId;
    }

    public Integer getFlowInsId()
    {
        return flowInsId;
    }

    public void setFlowInsId(Integer flowInsId)
    {
        this.flowInsId = flowInsId;
    }

    public String getBusinessId()
    {
        return businessId;
    }

    public void setBusinessId(String businessId)
    {
        this.businessId = businessId;
    }

    public String getBasicId()
    {
        return basicId;
    }

    public void setBasicId(String basicId)
    {
        this.basicId = basicId;
    }

    public Integer getUserId()
    {
        return userId;
    }

    public void setUserId(Integer userId)
    {
        this.userId = userId;
    }

    public List<Integer> getProcessIds()
    {
        return processIds;
    }

    public void setProcessIds(List<Integer> processIds)
    {
        this.processIds = processIds;
    }

    public boolean isExistsNextNode()
    {
        return existsNextNode;
    }

    public void setExistsNextNode(boolean existsNextNode)
    {
        this.existsNextNode = existsNextNode;
    }

    public List<String> getNextOperateIds()
    {
        return nextOperateIds;
    }

    public void setNextOperateIds(List<String> nextOperateIds)
    {
        this.nextOperateIds = nextOperateIds;
    }

    public String getProductId()
    {
        return productId;
    }

    public void setProductId(String productId)
    {
        this.productId = productId;
    }

    public Integer getFlowId()
    {
        return flowId;
    }

    public void setFlowId(Integer flowId)
    {
        this.flowId = flowId;
    }

    public Integer getCpyId()
    {
        return cpyId;
    }

    public void setCpyId(Integer cpyId)
    {
        this.cpyId = cpyId;
    }

    public Integer getKindId()
    {
        return kindId;
    }

    public void setKindId(Integer kindId)
    {
        this.kindId = kindId;
    }

    public String getToDoWorkInput()
    {
        return toDoWorkInput;
    }

    public void setToDoWorkInput(String toDoWorkInput)
    {
        this.toDoWorkInput = toDoWorkInput;
    }


    public Boolean getNewFlowIns()
    {
        return newFlowIns;
    }

    public void setNewFlowIns(Boolean newFlowIns)
    {
        this.newFlowIns = newFlowIns;
    }

    @Override
    public String toString()
    {
        return "MQMessage{" +
                "id=" + id +
                ", operateId='" + operateId + '\'' +
                ", dataId='" + dataId + '\'' +
                ", finalDataId='" + finalDataId + '\'' +
                ", flowInsId=" + flowInsId +
                ", businessId='" + businessId + '\'' +
                ", basicId='" + basicId + '\'' +
                ", userId=" + userId +
                ", processIds=" + processIds +
                ", existsNextNode=" + existsNextNode +
                ", nextOperateIds=" + nextOperateIds +
                ", productId='" + productId + '\'' +
                ", flowId=" + flowId +
                ", cpyId=" + cpyId +
                ", kindId=" + kindId +
                ", toDoWorkInput='" + toDoWorkInput + '\'' +
                ", newFlowIns=" + newFlowIns +
                '}';
    }
}
