package org.example.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigLoader {

    private final Properties properties;

    public ConfigLoader() {
        this.properties = new Properties();
    }

    public void load() throws IOException {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            if (inputStream == null) {
                throw new IOException("File 'config.properties' not found in classpath");
            }
            properties.load(inputStream);
        }
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    public String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    public void displayDatabaseInfo() {
        String dbName = getProperty("database.name");
        String dbUser = getProperty("database.user");
        String dbPassword = getProperty("database.password");
        String dbUrl = getProperty("database.url");

        System.out.println("Database Name: " + dbName);
        System.out.println("Database User: " + dbUser);
        System.out.println("Database Pass: " + dbPassword);
        System.out.println("Database url: " + dbUrl);
    }
}
