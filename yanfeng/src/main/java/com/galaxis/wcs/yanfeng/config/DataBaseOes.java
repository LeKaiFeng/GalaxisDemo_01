package com.galaxis.wcs.yanfeng.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@MapperScan(
        basePackages = {
                "com.galaxis.wcs.yanfeng.database.oes.mapper"
                , "com.galaxis.wcs.yanfeng.database.wcs.mapper"
        }, sqlSessionFactoryRef = "sqlSessionFactory1")
public class DataBaseOes {
    @Autowired
    @Qualifier("oes")
    private DataSource ds1;

    @Bean(name = "oes")
    @Qualifier("oes")
    @ConfigurationProperties(prefix = "datasource.oes")
    public DataSource primaryDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory1() throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(ds1);
        return factoryBean.getObject();
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplate1() throws Exception {
        SqlSessionTemplate template = new SqlSessionTemplate(sqlSessionFactory1());
        return template;
    }

    /**
     * 默认的事务管理器
     */
    @Bean("transactionManager")
    public PlatformTransactionManager transactionManager1() {
        return new DataSourceTransactionManager(ds1);
    }

}
