package com.epam.unit2.dao.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

@Configuration
public class DataSourceConfiguration {
    private static final String DATABASE_PROPERTY_FILE_PATH = "database.properties";
    private static final String DATABASE_DRIVER_CLASS_NAME = "spring.datasource.driver-class-name";
    private static final String DATABASE_URL = "spring.datasource.url";
    private static final String DATABASE_USERNAME = "db.username";
    private static final String DATABASE_PASSWORD = "db.password";
    private static final String DATABASE_POOL_MAX_SIZE = "db.initialSize";
    private static final String CREATE_DATABASE_SCRIPT = "classpath:sql/schema.sql";
    private static final String FILL_DATABASE_WITH_DATA_SCRIPT = "classpath:sql/data.sql";

    @Bean
    public DataSource dataSource() throws IOException {
        Properties properties = new Properties();
        properties.load(DataSourceConfiguration.class.getResourceAsStream(DATABASE_PROPERTY_FILE_PATH));
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName(properties.getProperty(DATABASE_DRIVER_CLASS_NAME));
        dataSource.setJdbcUrl(properties.getProperty(DATABASE_URL));
        dataSource.setUsername(properties.getProperty(DATABASE_USERNAME));
        dataSource.setPassword(properties.getProperty(DATABASE_PASSWORD));
        dataSource.setMaximumPoolSize(Integer.parseInt(properties.getProperty(DATABASE_POOL_MAX_SIZE)));
        return dataSource;
    }
    @Profile("dev")
    @Bean
    public DataSource embeddedDataSource() {
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript(CREATE_DATABASE_SCRIPT)
                .addScript(FILL_DATABASE_WITH_DATA_SCRIPT)
                .build();
    }
}
