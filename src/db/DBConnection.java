package db;

import config.DatabaseConfig;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Singleton class to manage database connection using JDBC.
 */
public class DBConnection {

    private static Connection connection = null;

    private DBConnection() {
        // Prevent instantiation
    }

    /**
     * Obtains the singleton database connection.
     * Starts a new one if it is closed or null.
     */
    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                // Ensure the driver is loaded
                Class.forName("com.mysql.cj.jdbc.Driver");
                
                // Establish connection
                connection = DriverManager.getConnection(
                    DatabaseConfig.URL, 
                    DatabaseConfig.USERNAME, 
                    DatabaseConfig.PASSWORD
                );
            }
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC Driver not found. Ensure the JAR is added to the build path.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Database connection failed. Please check MySQL server and credentials.");
            e.printStackTrace();
        }
        return connection;
    }

    /**
     * Closes the active connection if it exists.
     */
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
