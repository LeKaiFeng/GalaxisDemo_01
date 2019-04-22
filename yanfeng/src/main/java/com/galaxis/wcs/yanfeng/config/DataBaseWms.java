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

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = {"com.galaxis.wcs.yanfeng.database.wms.mapper"}
        , sqlSessionFactoryRef = "sqlSessionFactory3")
public class DataBaseWms {

    @Autowired
    @Qualifier("wms")
    private DataSource ds3;

    @Bean(name = "wms")
    @Qualifier("wms")
    @ConfigurationProperties(prefix = "datasource.wms")
    public DataSource primaryDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory3() throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(ds3);
        return factoryBean.getObject();
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplate3() throws Exception {
        SqlSessionTemplate template = new SqlSessionTemplate(sqlSessionFactory3());
        return template;
    }

    // @Bean("transactionManagerWms")
    // public PlatformTransactionManager transactionManager() {
    //     return new DataSourceTransactionManager(ds3);
    // }

}
