package com.hexaware.fms.presentation;


import com.hexaware.fms.util.DBConnUtil;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionTest {

    public static void main(String[] args) {
        try {
            // Attempt to get a connection using the properties file
            Connection connection = DBConnUtil.getConnection("db.properties");

            // Check if the connection has been established
            if (connection != null) {
                System.out.println("Database connection established successfully!");
                // Always close your connection when done
                connection.close();
            } else {
                System.out.println("Failed to establish the database connection.");
            }
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
        }
    }
}
