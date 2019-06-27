
////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2018. 沈阳东睿科技有限公司.版权所有.
// SHENYANG NEURAY TECHNOLOGY CO.,LTD. All Rights Reserved
////////////////////////////////////////////////////////////////////////////////

package com.neuray.wp.config;

import org.beetl.sql.core.Interceptor;
import org.beetl.sql.core.NameConversion;
import org.beetl.sql.core.annotatoin.Table;
import org.beetl.sql.core.db.MySqlStyle;
import org.beetl.sql.core.db.OracleStyle;
import org.beetl.sql.core.kit.StringKit;
import org.beetl.sql.ext.DebugInterceptor;
import org.beetl.sql.ext.spring4.BeetlSqlDataSource;
import org.beetl.sql.ext.spring4.BeetlSqlScannerConfigurer;
import org.beetl.sql.ext.spring4.SqlManagerFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SqlManageConfig {
    @Bean
    public SqlManagerFactoryBean sqlManagerFactoryBean(@Qualifier("beetlSqlDataSource") BeetlSqlDataSource beetlSqlDataSource) {
        SqlManagerFactoryBean sqlManagerFactoryBean = new SqlManagerFactoryBean();
        sqlManagerFactoryBean.setCs(beetlSqlDataSource);
        sqlManagerFactoryBean.setDbStyle(new MySqlStyle());
        sqlManagerFactoryBean.setNc(new DelSuffixConversion());
        sqlManagerFactoryBean.setInterceptors(new Interceptor[]{
                new DebugInterceptor()
        });
        return sqlManagerFactoryBean;
    }

    @Bean(name = "beetlSqlScannerConfigurer")
    public BeetlSqlScannerConfigurer getBeetlSqlScannerConfigurer() {
        BeetlSqlScannerConfigurer conf = new BeetlSqlScannerConfigurer();
        conf.setBasePackage("com.neuray.wp.dao");
        conf.setDaoSuffix("Dao");
        conf.setSqlManagerFactoryBeanName("sqlManagerFactoryBean");
        return conf;
    }

    public class DelSuffixConversion extends NameConversion {
        @Override
        public String getTableName(Class<?> aClass) {
            Table table = (Table) aClass.getAnnotation(Table.class);
            return table != null ? table.name() : StringKit.enCodeUnderlined(aClass.getSimpleName());

        }

        @Override
        public String getClassName(String tableName) {
            int index = tableName.lastIndexOf("_T");
            if (tableName.length() - 2 == index) {
                tableName = tableName.substring(0, index);
            }
            String temp = StringKit.deCodeUnderlined(tableName.toLowerCase());
            return StringKit.toUpperCaseFirstOne(temp);
        }

        @Override
        public String getColName(Class<?> aClass, String s) {
            return StringKit.enCodeUnderlined(s);
        }

        @Override
        public String getPropertyName(Class<?> aClass, String s) {
            return StringKit.deCodeUnderlined(s.toLowerCase());
        }
    }
}
