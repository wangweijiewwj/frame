package com.fintech.rabbitmq.util;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;
import org.springframework.context.annotation.ScannedGenericBeanDefinition;

/**
 * @ClassName MybatisBeanNameGenerator
 * @Date 2020/4/2 9:21
 * @Auther wangyongyong
 * @Version 1.0
 * @Description TODO
 */
public class MybatisBeanNameGenerator extends AnnotationBeanNameGenerator
{

    private static final String BEAN_NAME_ALIAS = "org.apache.ibatis.annotations.Mapper";

    @Override
    protected String buildDefaultBeanName(BeanDefinition definition)
    {
        if (definition instanceof ScannedGenericBeanDefinition &&
                ((ScannedGenericBeanDefinition) definition).getMetadata().hasAnnotation(BEAN_NAME_ALIAS))
        {
            return definition.getBeanClassName();
        }
        return super.buildDefaultBeanName(definition);
    }
}
