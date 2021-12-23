package com.epam.esm.config;

import org.junit.jupiter.api.TestInstance;

import javax.sql.DataSource;

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class DataSourceConfig {
    public static final DataSource dataSource = new DatabaseConfiguration().embeddedDataSource();

}
