package com.fintech.rabbitmq.constants;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName MQOperateEnum
 * @Date 2020/2/27 15:21
 * @Auther wangyongyong
 * @Version 1.0
 * @Description TODO
 */
public enum MQOperateEnum
{
    NONE("none", "NONE"),

    PLAN_CREATE_GOODS_ORDER("中天计划单接收", "PI008"),

    UPLOAD_LOAD_RECEIPT("上传装货磅单", "PT016"),

    UPLOAD_UNLOAD_RECEIPT("上传卸货磅单", "PT018"),

    LOAD_SIGN("装货签到", "PT015"),

    UNLOAD_SIGN("卸货签到", "PT017"),

    COMPANY_REGISTER("企业注册", "PA006"),

    DRIVER_ADD("添加司机", "PR011"),

    USERS_ADD("新增人员", "PR006"),

    USERS_UPDATE("修改人员", "PR007");

    private final static Map<String, MQOperateEnum> onlyReadCodeName = new HashMap<>();

    MQOperateEnum(String name, String code)
    {
        this.name = name;
        this.code = code;
    }

    static
    {
        for (MQOperateEnum operateEnum : MQOperateEnum.values())
        {
            onlyReadCodeName.put(operateEnum.getCode(), operateEnum);
        }
    }

    private String name;

    private String code;

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

    /**
     * 根据操作码获取操作码名称含义
     *
     * @param code
     * @return
     */
    public static String codeOf(String code)
    {
        return onlyReadCodeName.get(code).name;
    }

    /**
     * 根据操作码获取操作码名称含义
     *
     * @param code
     * @return
     */
    public static MQOperateEnum codeEnumOf(String code)
    {
        return onlyReadCodeName.get(code);
    }

}
