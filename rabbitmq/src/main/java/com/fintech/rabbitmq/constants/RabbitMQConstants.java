package com.fintech.rabbitmq.constants;

/**
 * @ClassName RabbitMQConstants
 * @Date 2019/12/24 17:32
 * @Auther wangyongyong
 * @Version 1.0
 * @Description TODO
 */
public class RabbitMQConstants
{

    /**
     * 前置实现类
     */
    public static final Integer IMPL_CLAZZ_TYPE_BEFORE = -1;

    /**
     * 正常实现类
     */
    public static final Integer IMPL_CLAZZ_TYPE_COMMON = 0;

    /**
     * 后置实现类
     */
    public static final Integer IMPL_CLAZZ_TYPE_AFTER = 1;

    /**
     * 流程实例消费类
     */
    public static final Integer IMPL_CLAZZ_FLOW_INS = 1;

    /**
     * 操作码配置消费类
     */
    public static final Integer IMPL_CLAZZ_CODE_CONF = 2;

    /**
     * 代办实现类
     */
    public static final Integer IMPL_CLAZZ_TYPE_TODO = 2;

    // region 初始化参数

    public static final String ROUTE_KEY_A = "A";
    public static final String ROUTE_KEY_R = "R";
    public static final String ROUTE_KEY_G = "G";
    public static final String ROUTE_KEY_T = "T";
    public static final String ROUTE_KEY_S = "S";
    public static final String ROUTE_KEY_F = "F";
    public static final String ROUTE_KEY_C = "C";
    public static final String ROUTE_KEY_P = "P";
    public static final String ROUTE_KEY_O = "O";
    public static final String ROUTE_KEY_I = "I";

    public static final String EXCHANGE_P = "P";
    public static final String EXCHANGE_C = "C";

    public static final String EVENT_EXCHANGE_NAME = "auv.event.exchange";

    public static final String MESSAGE_EXCHANGE_NAME = "auv.message.exchange";

    public static final String ACCESS_QUEUE = "access.queue";

    public static final String RESOURCE_QUEUE = "resource.queue";

    public static final String GOODS_QUEUE = "goods.queue";

    public static final String TRANSPORT_QUEUE = "transport.queue";

    public static final String SETTLEMENT_QUEUE = "settlement.queue";

    public static final String FINANCE_QUEUE = "finance.queue";

    public static final String RISK_QUEUE = "risk.queue";

    public static final String REPORT_QUEUE = "report.queue";

    public static final String CONFIGURE_QUEUE = "configure.queue";

    public static final String INVITATION_QUEUE = "invitation.queue";

    // endregion 初始化参数

    // region 操作码常量

    //region PA
    public static final String OP_PA000 = "PA000";


}
