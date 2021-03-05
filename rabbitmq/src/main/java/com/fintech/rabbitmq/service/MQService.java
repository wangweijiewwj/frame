package com.fintech.rabbitmq.service;

/**
 * @ClassName MQService
 * @Date 2019/12/20 13:55
 * @Auther wangyongyong
 * @Version 1.0
 * @Description 定义执行消息业务
 */
public interface MQService<I>
{

    /**
     * 是否支持执行业务 doService 方法
     *
     * @param input 入参
     * @return 是否执行
     */
    boolean supportDoService(I input);

    /**
     * 执行 业务逻辑
     *
     * @param input 入参
     * @return O 出参
     * @throws Exception 业务执行异常
     */
    void doService(I input) throws Exception;

    /**
     * 获取实现类 Class
     *
     * @return class 类型
     */
    Class getImplClass();

    /**
     * 获取入参 Class 类型
     *
     * @return class 类型
     */
    Class<I> getInputClass();

}
