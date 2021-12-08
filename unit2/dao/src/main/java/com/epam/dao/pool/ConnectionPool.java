package com.epam.dao.pool;

import com.epam.dao.exception.DaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Component
public class ConnectionPool {

    @Autowired
    private DataSource dataSource;

    public Connection getConnection() throws DaoException {
        try {
            return dataSource.getConnection();
        }catch (SQLException e) {
            throw new DaoException("the connection was not lost", e);
        }
    }

    public void closeConnection(Connection connection) throws DaoException {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new DaoException("the connect was not closed", e);
        }

    }
}
