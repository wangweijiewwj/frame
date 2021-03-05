package com.fintech.rabbitmq.bean;

/**
 * @ClassName NodeActionBO
 * @Date 2020/2/17 15:22
 * @Auther wangyongyong
 * @Version 1.0
 * @Description TODO
 */
public class NodeActionBO
{
    /**
     * id
     */
    private Integer id;

    /**
     * 节点编码
     */
    private String code;

    /**
     * 节点名称
     */
    private String name;

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
     * 行为描述
     */
    private String desc;

    /**
     * 类型
     */
    private Integer type;

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
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

    public String getDesc()
    {
        return desc;
    }

    public void setDesc(String desc)
    {
        this.desc = desc;
    }

    public Integer getType()
    {
        return type;
    }

    public void setType(Integer type)
    {
        this.type = type;
    }

    @Override
    public String toString()
    {
        return "NodeActionBO{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", clazz='" + clazz + '\'' +
                ", flag=" + flag +
                ", sort=" + sort +
                ", desc='" + desc + '\'' +
                ", type=" + type +
                '}';
    }
}
