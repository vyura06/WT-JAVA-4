package com.example.hotel.connection;

import com.example.hotel.exception.ConnectionPoolException;

import java.io.Closeable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ConnectionPool implements Closeable {
    private static final ConnectionPool instance = new ConnectionPool();
    private static final ConnectionProperties connectionProperties = ConnectionProperties.getInstance();
    private final BlockingQueue<ProxyConnection> connections;

    private ConnectionPool() {
        String driverName = connectionProperties.getDbDriver();
        if (driverName != null) {
            try {
                Class.forName(driverName);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException("Driver registration failed", e);
            }
        }
        // or static
        int poolSize = connectionProperties.getDbPoolSize();
        connections = new LinkedBlockingQueue<>(poolSize);

        for (int i = 0; i < poolSize; i++) {
            connections.add(ConnectionPool.createConnection());
        }
    }

    public static ConnectionPool getInstance() {
        return instance;
    }

    private void init() {

    }

    private static ProxyConnection createConnection() {
        Connection connection;
        try {
            connection = DriverManager.getConnection(
                    connectionProperties.getUrl(),
                    connectionProperties.getDbUser(),
                    connectionProperties.getDbPass());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return new ProxyConnection(connection);
    }

    public Connection takeConnection() throws InterruptedException {
        ProxyConnection connection;
        connection = connections.take();
        return connection;
    }

    void releaseConnection(ProxyConnection connection) {
        if (connection != null) {
            connections.add(connection);
        }
    }

    @Override
    public void close() {
        for (int i = 0; i < connectionProperties.getDbPoolSize(); i++) {
            try {
                ProxyConnection proxyConnection = connections.take();
                proxyConnection.realClose();
            } catch (SQLException | InterruptedException e) {
                throw new ConnectionPoolException(e);
            }
        }
    }
}
