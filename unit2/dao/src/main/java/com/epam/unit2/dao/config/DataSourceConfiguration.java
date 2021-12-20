package com.epam.unit2.dao.config;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfiguration {

    @Bean
    public DataSource dataSource() {
        ApplicationContext context = new ClassPathXmlApplicationContext("datasource-config.xml");
        return (DataSource) context.getBean("dataSource");
    }
}
