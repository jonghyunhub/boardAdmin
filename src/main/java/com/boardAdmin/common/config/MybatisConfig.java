package com.boardAdmin.common.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = "com.boardAdmin.mapper")
public class MybatisConfig {

    public SqlSessionFactory sqlSessionFactory(DataSource dataSource)throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);

        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sessionFactory.setMapperLocations(resolver.getResource("classpath:mapper/*.xml"));

        Resource myBatisConfig = new PathMatchingResourcePatternResolver().getResource("classpath:mapper/*.xml");
        sessionFactory.setConfigLocation(myBatisConfig);

        return sessionFactory.getObject();
    }
}
