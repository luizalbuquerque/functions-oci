package org.example.config;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {

    private final ConfigLoader configLoader;
    private Connection connection;

    public DatabaseManager(ConfigLoader configLoader) {
        this.configLoader = configLoader;
    }

    public void connect() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            return;
        }

        String dbUrl = configLoader.getProperty("database.url");
        String dbUser = configLoader.getProperty("database.user");
        String dbPassword = configLoader.getProperty("database.password");

        connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }

    public Connection getConnection() {
        return connection;
    }

    public void close() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}
