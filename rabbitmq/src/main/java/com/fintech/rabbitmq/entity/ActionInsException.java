package com.fintech.rabbitmq.entity;

/**
 * 行为（实现类）实例异常表
 */
public class ActionInsException
{
    /**
     * id
     */
    private Integer id;

    /**
     * 行为实例Id
     */
    private Integer acId;

    /**
     * 行为类名称
     */
    private String acName;

    /**
     * 类型 1-流程 2-操作
     */
    private Integer type;

    /**
     * 异常
     */
    private String exception;

    /**
     * 消息Id
     */
    private Integer msgId;

    /**
     * 异常时间
     */
    private Long exTime;

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public Integer getAcId()
    {
        return acId;
    }

    public void setAcId(Integer acId)
    {
        this.acId = acId;
    }

    public String getAcName()
    {
        return acName;
    }

    public void setAcName(String acName)
    {
        this.acName = acName == null ? null : acName.trim();
    }

    public Integer getType()
    {
        return type;
    }

    public void setType(Integer type)
    {
        this.type = type;
    }

    public String getException()
    {
        return exception;
    }

    public void setException(String exception)
    {
        this.exception = exception == null ? null : exception.trim();
    }

    public Integer getMsgId()
    {
        return msgId;
    }

    public void setMsgId(Integer msgId)
    {
        this.msgId = msgId;
    }

    public Long getExTime()
    {
        return exTime;
    }

    public void setExTime(Long exTime)
    {
        this.exTime = exTime;
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", acId=").append(acId);
        sb.append(", acName=").append(acName);
        sb.append(", type=").append(type);
        sb.append(", exception=").append(exception);
        sb.append(", msgId=").append(msgId);
        sb.append(", exTime=").append(exTime);
        sb.append("]");
        return sb.toString();
    }
}
