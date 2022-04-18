package com.bruce.self.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.bruce.self.config.properties.DatasourceMaster;
import com.bruce.self.config.properties.DatasourceSlaver0;
import com.bruce.self.config.properties.DatasourceSlaver1;
import com.bruce.self.enums.DataSourceEnum;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author rcy
 * @version 1.0.0
 * @className: DataSourceConfig
 * @date 2022-04-12
 */
@Configuration
@MapperScan(basePackages = "com.bruce.self.mapper", sqlSessionTemplateRef = "sqlTemplate")
public class DataSourceConfig {

    /**
     * 主库
     */
    @Primary
    @Bean
    public DataSource master(DatasourceMaster master) {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(master.getUrl());
        dataSource.setUsername(master.getUsername());
        dataSource.setPassword(master.getPassword());
        dataSource.setDriverClassName(master.getDriverClassName());
        return dataSource;
    }

    /**
     * 从库
     */
    @Bean
    @ConditionalOnProperty(prefix = "spring.datasource", name = "slaver0")
    public DataSource slaver0(DatasourceSlaver0 slaver0) {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(slaver0.getUrl());
        dataSource.setUsername(slaver0.getUsername());
        dataSource.setPassword(slaver0.getPassword());
        dataSource.setDriverClassName(slaver0.getDriverClassName());
        return dataSource;
    }

    /**
     * 从库
     */
    @Bean
    @ConditionalOnProperty(prefix = "spring.datasource", name = "slaver1")
    public DataSource slaver1(DatasourceSlaver1 slaver1) {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(slaver1.getUrl());
        dataSource.setUsername(slaver1.getUsername());
        dataSource.setPassword(slaver1.getPassword());
        dataSource.setDriverClassName(slaver1.getDriverClassName());
        return dataSource;
    }

    /**
     * 实例化数据源路由
     */
    @Bean
    public DataSourceRouter dynamicDB(@Qualifier("master") DataSource masterDataSource,
                                      @Autowired(required = false) @Qualifier("slaver0") DataSource slave0DataSource,
                                      @Autowired(required = false) @Qualifier("slaver1") DataSource slave1DataSource) {
        DataSourceRouter dynamicDataSource = new DataSourceRouter();
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put(DataSourceEnum.MASTER.getDataSourceName(), masterDataSource);
        if (slave0DataSource != null) {
            targetDataSources.put(DataSourceEnum.SLAVE_0.getDataSourceName(), slave0DataSource);
        }
        if (slave1DataSource != null) {
            targetDataSources.put(DataSourceEnum.SLAVE_1.getDataSourceName(), slave1DataSource);
        }
        dynamicDataSource.setTargetDataSources(targetDataSources);
        dynamicDataSource.setDefaultTargetDataSource(masterDataSource);
        return dynamicDataSource;
    }

    /**
     * 配置sessionFactory
     *
     * @param dynamicDataSource
     * @return
     * @throws Exception
     */
    @Bean
    public SqlSessionFactory sessionFactory(@Qualifier("dynamicDB") DataSource dynamicDataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setMapperLocations(
                new PathMatchingResourcePatternResolver().getResources("classpath*:mapper/*Mapper.xml"));
        bean.setDataSource(dynamicDataSource);
        return bean.getObject();
    }

    /**
     * 创建sqlTemplate
     *
     * @param sqlSessionFactory
     * @return
     */
    @Bean
    public SqlSessionTemplate sqlTemplate(@Qualifier("sessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }


    /**
     * 事务配置
     *
     * @param dynamicDataSource
     * @return
     */
    @Bean(name = "dataSourceTx")
    public DataSourceTransactionManager dataSourceTransactionManager(@Qualifier("dynamicDB") DataSource dynamicDataSource) {
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
        dataSourceTransactionManager.setDataSource(dynamicDataSource);
        return dataSourceTransactionManager;
    }

//    /**
//     * 主库
//     */
//    @Bean
//    @ConfigurationProperties(prefix = "spring.datasource.master")
//    public DataSource master() {
//        return DruidDataSourceBuilder.create().build();
//    }
//
//    /**
//     * 从库
//     */
//    @Bean
//    @ConfigurationProperties(prefix = "spring.datasource.slave")
//    public DataSource slaver() {
//        return DruidDataSourceBuilder.create().build();
//    }
//
//
//    /**
//     * 实例化数据源路由
//     */
//    @Bean
//    public DataSourceRouter dynamicDB(@Qualifier("master") DataSource masterDataSource,
//                                      @Autowired(required = false) @Qualifier("slaver") DataSource slaveDataSource) {
//        DataSourceRouter dynamicDataSource = new DataSourceRouter();
//        Map<Object, Object> targetDataSources = new HashMap<>();
//        targetDataSources.put(DataSourceEnum.MASTER.getDataSourceName(), masterDataSource);
//        if (slaveDataSource != null) {
//            targetDataSources.put(DataSourceEnum.SLAVE_0.getDataSourceName(), slaveDataSource);
//        }
//        dynamicDataSource.setTargetDataSources(targetDataSources);
//        dynamicDataSource.setDefaultTargetDataSource(masterDataSource);
//        return dynamicDataSource;
//    }
//
//
//    /**
//     * 配置sessionFactory
//     * @param dynamicDataSource
//     * @return
//     * @throws Exception
//     */
//    @Bean
//    public SqlSessionFactory sessionFactory(@Qualifier("dynamicDB") DataSource dynamicDataSource) throws Exception {
//        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
//        bean.setMapperLocations(
//                new PathMatchingResourcePatternResolver().getResources("classpath*:mapper/*Mapper.xml"));
//        bean.setDataSource(dynamicDataSource);
//        return bean.getObject();
//    }
//
//
//    /**
//     * 创建sqlTemplate
//     * @param sqlSessionFactory
//     * @return
//     */
//    @Bean
//    public SqlSessionTemplate sqlTemplate(@Qualifier("sessionFactory") SqlSessionFactory sqlSessionFactory) {
//        return new SqlSessionTemplate(sqlSessionFactory);
//    }
//
//
//    /**
//     * 事务配置
//     *
//     * @param dynamicDataSource
//     * @return
//     */
//    @Bean(name = "dataSourceTx")
//    public DataSourceTransactionManager dataSourceTransactionManager(@Qualifier("dynamicDB") DataSource dynamicDataSource) {
//        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
//        dataSourceTransactionManager.setDataSource(dynamicDataSource);
//        return dataSourceTransactionManager;
//    }

}
