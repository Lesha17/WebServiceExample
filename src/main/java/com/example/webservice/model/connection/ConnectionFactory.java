package com.example.webservice.model.connection;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Provides a {@link Connection} instance.
 */
public interface ConnectionFactory {

    /**
     * Provides a connection instance. Returned instance must be closed after use.
     *
     * @return connection instance (not null)
     * @throws SQLException if failed to create connection due to SQL exception
     */
    public Connection getConnection() throws SQLException;
}
