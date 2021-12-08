package com.epam.dao.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:/database.properties")
public class Config {

    @Value("${db.driverClassName}")
    private String driverClassName;
    @Value("${db.url}")
    private String url;
    @Value("${db.username}")
    private String userName;
    @Value("${db.password}")
    private String pass;
    @Value("${db.maxOpenPreparedStatements}")
    private int maxOpenPreparedStatements;
    @Value("${db.autoReconnect}")
    private String autoReconnect;
    @Value("${db.characterEncoding}")
    private String characterEncoding;
    @Value("${db.initialSize}")
    private int initialSize;
    @Value("${db.serverTimezone}")
    private String serverTimeZone;
    @Value("${db.useUnicode}")
    private String useUnicode;

}
