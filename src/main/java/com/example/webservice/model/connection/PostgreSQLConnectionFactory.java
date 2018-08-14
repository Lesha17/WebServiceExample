package com.example.webservice.model.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Implementation of {@link ConnectionFactory} for Postgre database.
 */
public class PostgreSQLConnectionFactory implements ConnectionFactory {

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Connection getConnection() throws SQLException {
        return getConnection(null, null);
    }

    public Connection getConnection(String username, String password) throws SQLException {
        return DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/examplewebservicedb", username, password);
    }
}
