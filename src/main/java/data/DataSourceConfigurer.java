package data;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DataSourceConfigurer {

    @Bean(name = "masterDataSource")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.druid.master")
    public DataSource masterDataSource() {
        return DruidDataSourceBuilder.create().build();
    }

    @Bean(name = "slaverDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.druid.slaver")
    public DataSource slaverDataSource() {
        return DruidDataSourceBuilder.create().build();
    }

    @Bean(name = "dynamicDataSource")
    @Autowired
    public DataSource dynamicDataSource(@Qualifier("masterDataSource") DataSource masterDataSource, @Qualifier("slaverDataSource")DataSource slaverDataSource) {
        DynamicRoutingDataSource dynamicRoutingDataSource = new DynamicRoutingDataSource();

        Map<Object, Object> dataSourceMap = new HashMap<>();
        dataSourceMap.put(DataSourceKey.masterDataSource.name(), masterDataSource);
        dataSourceMap.put(DataSourceKey.slaverDataSource.name(), slaverDataSource);

        dynamicRoutingDataSource.setTargetDataSources(dataSourceMap);
        dynamicRoutingDataSource.setDefaultTargetDataSource(masterDataSource);

        return dynamicRoutingDataSource;
    }

    @Bean(name = "sqlSessionFactory")
    @Autowired
    public SqlSessionFactory sqlSessionFactoryBean(@Qualifier("dynamicDataSource") DataSource dynamicDataSource) throws Exception {
        DefaultResourceLoader defaultResourceLoader = new DefaultResourceLoader();

        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dynamicDataSource);
        sqlSessionFactoryBean.setConfigLocation(defaultResourceLoader.getResource("classpath:mybatis-config.xml"));

        return sqlSessionFactoryBean.getObject();
    }

    @Autowired
    @Bean
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("sqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Autowired
    @Bean
    public DataSourceTransactionManager txManager(@Qualifier("dynamicDataSource") DataSource dynamicDataSource) {
        DataSourceTransactionManager txManager = new DataSourceTransactionManager();
        txManager.setDataSource(dynamicDataSource);
        return txManager;
    }
}