package com.epam.unit2.dao.config;

import javax.sql.DataSource;

public class DataSourceConfig {
    public static final DataSource dataSource = new DataSourceConfiguration().dataSource();

}
