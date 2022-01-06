package com.epam.esm.config;

import com.epam.esm.exception.DaoException;
import com.zaxxer.hikari.HikariConfig;
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
public class DatabaseConfiguration {
    private static final String DATABASE_PROPERTY_FILE_PATH = "/database.properties";
    private static final String DATABASE_DRIVER_CLASS_NAME = "spring.datasource.driver-class-name";
    private static final String DATABASE_URL = "spring.datasource.url";
    private static final String DATABASE_USERNAME = "spring.datasource.username";
    private static final String DATABASE_PASSWORD = "spring.datasource.password";
    private static final String DATABASE_POOL_MAX_SIZE = "spring.datasource.poolMaxSize";
    private static final String CREATE_DATABASE_SCRIPT = "sql/script.sql";
    private static final String FILL_DATABASE_WITH_DATA_SCRIPT = "sql/data.sql";

    @Profile("prod")
    @Bean
    public DataSource dataSource() {
        HikariConfig config = new HikariConfig();
        Properties properties = new Properties();
        try {
            properties.load(DatabaseConfiguration.class.getResourceAsStream(DATABASE_PROPERTY_FILE_PATH));
            config.setDriverClassName(properties.getProperty(DATABASE_DRIVER_CLASS_NAME));
            config.setJdbcUrl(properties.getProperty(DATABASE_URL));
            config.setUsername(properties.getProperty(DATABASE_USERNAME));
            config.setPassword(properties.getProperty(DATABASE_PASSWORD));
            config.setMaximumPoolSize(Integer.parseInt(properties.getProperty(DATABASE_POOL_MAX_SIZE)));
        } catch (IOException e) {
            throw new DaoException("1", "Database connection error");
        }
        return new HikariDataSource(config);
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
