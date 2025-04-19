package com.hexaware.fms.util;

import java.io.IOException;
import java.util.Properties;

public class DbPropertyUtil {

    public static Properties getConnectionProperties(String filename) {
        Properties props = new Properties();
        try {
            // Load the file from classpath (like from /resources)
            props.load(DbPropertyUtil.class.getClassLoader().getResourceAsStream(filename));
        } catch (IOException | NullPointerException e) {
            System.err.println("Error reading property file: " + e.getMessage());
        }
        return props;
    }
}
