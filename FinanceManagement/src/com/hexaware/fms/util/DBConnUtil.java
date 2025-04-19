package com.hexaware.fms.util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnUtil {

    public static Connection getConnection(String propertyFile) throws SQLException {
        Properties props = getConnectionProperties(propertyFile);
        String url = props.getProperty("db.url");
        String user = props.getProperty("db.user");
        String pass = props.getProperty("db.password");

        return DriverManager.getConnection(url, user, pass);
    }

    private static Properties getConnectionProperties(String filename) {
        Properties props = new Properties();
        try {
            props.load(DBConnUtil.class.getClassLoader().getResourceAsStream(filename));
        } catch (IOException | NullPointerException e) {
            System.err.println("Error reading property file: " + e.getMessage());
        }
        return props;
    }
}
