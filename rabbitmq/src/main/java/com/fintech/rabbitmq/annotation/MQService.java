package com.fintech.rabbitmq.annotation;

import com.fintech.rabbitmq.constants.MQOperateEnum;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * @ClassName MQService
 * @Date 2020/1/11 13:40
 * @Auther wangyongyong
 * @Version 1.0
 * @Description TODO
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface MQService
{
    String value() default "";

    /**
     * 参数描述
     *
     * @return
     */
    String paramDesc() default "";

    /**
     * 业务名称
     *
     * @return
     */
    String name() default "";

    /**
     * 业务描述
     *
     * @return
     */
    String description() default "";

    /**
     * 操作码
     *
     * @return
     */
    MQOperateEnum operate() default MQOperateEnum.NONE;

    /**
     * 类型 0-正常 -1 前置 1-后置
     *
     * @return
     */
    int flag() default 0;

    /**
     * 排序 数字越小越先执行
     * @return
     */
    int sort() default -1;

}
