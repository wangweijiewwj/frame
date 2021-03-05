package com.fintech.rabbitmq.entity;

public class OperateActionConf
{
    /**
     * 行为Id
     */
    private Integer id;

    /**
     * 操作Id
     */
    private Integer opId;

    /**
     * 编码
     */
    private String code;

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
     * 行为描述
     */
    private String desc;

    /**
     * 创建时间
     */
    private Long createTime;

    /**
     * 创建人
     */
    private Integer creator;

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
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
        this.code = code;
    }

    public String getClazz()
    {
        return clazz;
    }

    public void setClazz(String clazz)
    {
        this.clazz = clazz;
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

    public String getDesc()
    {
        return desc;
    }

    public void setDesc(String desc)
    {
        this.desc = desc;
    }

    public Long getCreateTime()
    {
        return createTime;
    }

    public void setCreateTime(Long createTime)
    {
        this.createTime = createTime;
    }

    public Integer getCreator()
    {
        return creator;
    }

    public void setCreator(Integer creator)
    {
        this.creator = creator;
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", opId=").append(opId);
        sb.append(", code=").append(code);
        sb.append(", clazz=").append(clazz);
        sb.append(", flag=").append(flag);
        sb.append(", sort=").append(sort);
        sb.append(", productId=").append(productId);
        sb.append(", status=").append(status);
        sb.append(", desc=").append(desc);
        sb.append(", createTime=").append(createTime);
        sb.append(", creator=").append(creator);
        sb.append("]");
        return sb.toString();
    }
}
