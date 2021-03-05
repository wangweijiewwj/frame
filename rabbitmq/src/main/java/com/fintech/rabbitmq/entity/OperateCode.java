package com.fintech.rabbitmq.entity;

/**
 * @ClassName OperateCode
 * @Date 2020/1/9 11:59
 * @Auther wangyongyong
 * @Version 1.0
 * @Description TODO
 */
public class OperateCode
{
    /**
     * 名称
     */
    private String name;

    /**
     * 编码
     */
    private String code;

    /**
     * 类型
     */
    private String type;

    /**
     * 模块
     */
    private String module;

    /**
     * 是否新的流程实例
     */
    private boolean isNewFlowIns=false;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getModule()
    {
        return module;
    }

    public void setModule(String module)
    {
        this.module = module;
    }

    public boolean isNewFlowIns()
    {
        return isNewFlowIns;
    }

    public void setNewFlowIns(boolean newFlowIns)
    {
        isNewFlowIns = newFlowIns;
    }
}
