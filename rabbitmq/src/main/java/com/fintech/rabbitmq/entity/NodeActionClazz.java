package com.fintech.rabbitmq.entity;

public class NodeActionClazz
{
    /**
     * 主键
     */
    private Integer id;

    /**
     * 名称
     */
    private String name;

    /**
     * 实现类
     */
    private String clazz;

    /**
     * 行为描述
     */
    private String desc;

    /**
     * 入参描述
     */
    private String paramDesc;

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getClazz()
    {
        return clazz;
    }

    public void setClazz(String clazz)
    {
        this.clazz = clazz;
    }

    public String getDesc()
    {
        return desc;
    }

    public void setDesc(String desc)
    {
        this.desc = desc;
    }

    public String getParamDesc()
    {
        return paramDesc;
    }

    public void setParamDesc(String paramDesc)
    {
        this.paramDesc = paramDesc;
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", clazz=").append(clazz);
        sb.append(", desc=").append(desc);
        sb.append(", paramDesc=").append(paramDesc);
        sb.append("]");
        return sb.toString();
    }
}
