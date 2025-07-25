package com.example.yuch_nug.config;


import com.example.yuch_nug.mapper.LoginMapper;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = "com.example.yuch_nug.mapper")
@EnableTransactionManagement
public class databaseConfig {

    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
        sqlSessionFactory.setDataSource(dataSource);
        sqlSessionFactory.setTypeAliasesPackage("com.example.yuch_nug.dto");

        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        // 모든 XML 매퍼 파일을 읽게 변경
        sqlSessionFactory.setMapperLocations(resolver.getResources("classpath:mapper/*.xml"));

        return sqlSessionFactory.getObject();
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) throws Exception{
        final SqlSessionTemplate sqlSessionTemplate = new SqlSessionTemplate(sqlSessionFactory);
        return sqlSessionTemplate;
    }

    @Bean
    public LoginMapper loginMapper(SqlSessionTemplate sqlSessionTemplate){
        return sqlSessionTemplate.getMapper(LoginMapper.class);
    }


}
