////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2018. 沈阳东睿科技有限公司.版权所有.
// SHENYANG NEURAY TECHNOLOGY CO.,LTD. All Rights Reserved
////////////////////////////////////////////////////////////////////////////////

package com.neuray.wp.config;

import com.zaxxer.hikari.HikariDataSource;
import org.beetl.sql.ext.spring4.BeetlSqlDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    @Bean(name = "dataSource")
    public DataSource datasource(Environment env) {
        HikariDataSource ds = new HikariDataSource();
        ds.setJdbcUrl(env.getProperty("spring.datasource.url"));
        ds.setUsername(env.getProperty("spring.datasource.username"));
        ds.setPassword(env.getProperty("spring.datasource.password"));
        ds.setDriverClassName(env.getProperty("spring.datasource.driver-class-name"));
        return ds;
    }

    @Bean
    public BeetlSqlDataSource beetlSqlDataSource(@Qualifier("dataSource") DataSource dataSource) {
        BeetlSqlDataSource source = new BeetlSqlDataSource();
        source.setMasterSource(dataSource);
        return source;
    }
}
