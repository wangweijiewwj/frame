package com.fintech.rabbitmq.spring.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.fintech.rabbitmq.util.MybatisBeanNameGenerator;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * @ClassName MybatisConfig
 * @Date 2019/12/26 10:55
 * @Auther wangyongyong
 * @Version 1.0
 * @Description mybatis 数据源配置
 */
@Configuration("rabbitmq-config")
@MapperScan(basePackages = MybatisConfig.PACKAGES, sqlSessionTemplateRef = MybatisConfig.SQL_SESSION_TEMPLATE_NAME,nameGenerator = MybatisBeanNameGenerator.class)
public class MybatisConfig
{

    public static final String TRANSACTION_NAME = "rabbitmqTransactionManager";

    protected static final String PACKAGES = "com.fintech.rabbitmq.mapper";

    protected static final String DATA_SOURCE_PROPERTIES = "rabbitmqDataSourceProperties";

    protected static final String DATA_SOURCE_NAME = "rabbitmqDataSource";

    protected static final String SQL_SESSION_FACTORY_NAME = "rabbitmqSqlSessionFactory";

    protected static final String SQL_SESSION_TEMPLATE_NAME = "rabbitmqSessionTemplate";

    protected static final String MAPPER_LOCATIONS = "classpath*:mappers/rabbitmq/*.xml";

    protected static final String DATA_SOURCE_PREFIX = "spring.rabbitmq.datasource";

    @Bean(DATA_SOURCE_PROPERTIES)
    @ConditionalOnMissingBean(name = DATA_SOURCE_PROPERTIES)
    @ConfigurationProperties(prefix = DATA_SOURCE_PREFIX)
    public DataSourceProperties dataSourceProperties()
    {
        return new DataSourceProperties();
    }

    @Bean(DATA_SOURCE_NAME)
    @ConditionalOnMissingBean(name = DATA_SOURCE_NAME)
    public DataSource dataSource(@Qualifier(DATA_SOURCE_PROPERTIES) DataSourceProperties dataSourceProperties) throws SQLException
    {
        DataSource dataSource = dataSourceProperties.initializeDataSourceBuilder().build();
        DruidDataSource druidDataSource = (DruidDataSource) dataSource;
        druidDataSource.setFilters("stat,wall");
        return dataSource;
    }

    @Bean(TRANSACTION_NAME)
    @ConditionalOnMissingBean(name = TRANSACTION_NAME)
    public DataSourceTransactionManager transactionManager(@Qualifier(DATA_SOURCE_NAME) DataSource dataSource)
    {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(SQL_SESSION_FACTORY_NAME)
    @ConditionalOnMissingBean(name = SQL_SESSION_FACTORY_NAME)
    public SqlSessionFactory sqlSessionFactory(@Qualifier(DATA_SOURCE_NAME) DataSource dataSource) throws Exception
    {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        bean.setMapperLocations(resolver.getResources(MAPPER_LOCATIONS));
        bean.setVfs(SpringBootVFS.class);
        return bean.getObject();
    }

    @Bean(SQL_SESSION_TEMPLATE_NAME)
    @ConditionalOnMissingBean(name = SQL_SESSION_TEMPLATE_NAME)
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier(SQL_SESSION_FACTORY_NAME) SqlSessionFactory sqlSessionFactory)
    {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
