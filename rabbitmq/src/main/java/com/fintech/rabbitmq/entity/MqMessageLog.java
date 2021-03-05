package com.fintech.rabbitmq.entity;

public class MqMessageLog
{
    /**
     * 消息Id
     */
    private Integer id;

    /**
     * 交换机
     */
    private String exchange;

    /**
     * 路由Key
     */
    private String routingKey;

    /**
     * 消息
     */
    private String message;

    /**
     * 发送时间
     */
    private Long pTime;

    /**
     * 消费时间
     */
    private Long cTime;

    /**
     * 消费号
     */
    private Long deliveryTag;

    /**
     * 是否消费 0 - 没有消费 1- 消费
     */
    private Integer consume;

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getExchange()
    {
        return exchange;
    }

    public void setExchange(String exchange)
    {
        this.exchange = exchange;
    }

    public String getRoutingKey()
    {
        return routingKey;
    }

    public void setRoutingKey(String routingKey)
    {
        this.routingKey = routingKey;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public Long getpTime()
    {
        return pTime;
    }

    public void setpTime(Long pTime)
    {
        this.pTime = pTime;
    }

    public Long getcTime()
    {
        return cTime;
    }

    public void setcTime(Long cTime)
    {
        this.cTime = cTime;
    }

    public Long getDeliveryTag()
    {
        return deliveryTag;
    }

    public void setDeliveryTag(Long deliveryTag)
    {
        this.deliveryTag = deliveryTag;
    }

    public Integer getConsume()
    {
        return consume;
    }

    public void setConsume(Integer consume)
    {
        this.consume = consume;
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", exchange=").append(exchange);
        sb.append(", routingKey=").append(routingKey);
        sb.append(", message=").append(message);
        sb.append(", pTime=").append(pTime);
        sb.append(", cTime=").append(cTime);
        sb.append(", deliveryTag=").append(deliveryTag);
        sb.append(", consume=").append(consume);
        sb.append("]");
        return sb.toString();
    }
}
