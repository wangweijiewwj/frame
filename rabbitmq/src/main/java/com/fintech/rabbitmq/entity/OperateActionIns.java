package com.fintech.rabbitmq.entity;

/**
 * 操作码行为实例表
 */
public class OperateActionIns
{
    /**
     * id
     */
    private Integer id;

    /**
     * 消息Id
     */
    private Integer msgId;

    /**
     * 操作Id
     */
    private Integer opId;

    /**
     * 编码
     */
    private String code;

    /**
     * 数据Id
     */
    private String dataId;

    /**
     * 消费类执行出参
     */
    private String output;

    /**
     * 实现类
     */
    private String clazz;

    /**
     * 任务标识
     */
    private Integer flag;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 产品Id
     */
    private Integer productId;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 处理人
     */
    private Integer processId;

    /**
     * 行为描述
     */
    private String desc;

    /**
     * 处理时间
     */
    private Long processTime;

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public Integer getMsgId()
    {
        return msgId;
    }

    public void setMsgId(Integer msgId)
    {
        this.msgId = msgId;
    }

    public Integer getOpId()
    {
        return opId;
    }

    public void setOpId(Integer opId)
    {
        this.opId = opId;
    }

    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code == null ? null : code.trim();
    }

    public String getDataId()
    {
        return dataId;
    }

    public void setDataId(String dataId)
    {
        this.dataId = dataId == null ? null : dataId.trim();
    }

    public String getOutput()
    {
        return output;
    }

    public void setOutput(String output)
    {
        this.output = output == null ? null : output.trim();
    }

    public String getClazz()
    {
        return clazz;
    }

    public void setClazz(String clazz)
    {
        this.clazz = clazz == null ? null : clazz.trim();
    }

    public Integer getFlag()
    {
        return flag;
    }

    public void setFlag(Integer flag)
    {
        this.flag = flag;
    }

    public Integer getSort()
    {
        return sort;
    }

    public void setSort(Integer sort)
    {
        this.sort = sort;
    }

    public Integer getProductId()
    {
        return productId;
    }

    public void setProductId(Integer productId)
    {
        this.productId = productId;
    }

    public Integer getStatus()
    {
        return status;
    }

    public void setStatus(Integer status)
    {
        this.status = status;
    }

    public Integer getProcessId()
    {
        return processId;
    }

    public void setProcessId(Integer processId)
    {
        this.processId = processId;
    }

    public String getDesc()
    {
        return desc;
    }

    public void setDesc(String desc)
    {
        this.desc = desc == null ? null : desc.trim();
    }

    public Long getProcessTime()
    {
        return processTime;
    }

    public void setProcessTime(Long processTime)
    {
        this.processTime = processTime;
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", msgId=").append(msgId);
        sb.append(", opId=").append(opId);
        sb.append(", code=").append(code);
        sb.append(", dataId=").append(dataId);
        sb.append(", output=").append(output);
        sb.append(", clazz=").append(clazz);
        sb.append(", flag=").append(flag);
        sb.append(", sort=").append(sort);
        sb.append(", productId=").append(productId);
        sb.append(", status=").append(status);
        sb.append(", processId=").append(processId);
        sb.append(", desc=").append(desc);
        sb.append(", processTime=").append(processTime);
        sb.append("]");
        return sb.toString();
    }
}
