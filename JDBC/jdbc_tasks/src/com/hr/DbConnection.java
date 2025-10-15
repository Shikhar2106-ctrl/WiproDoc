package com.hr;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
    // Task 1: Connection details
    private static final String DB_URL = "jdbc:oracle:thin:@localhost:1521:ORCL";
    private static final String USER = "hr";
    private static final String PASS = "hr";

    // Static block to register the JDBC driver
    static {
        try {
            // For older JDKs, this is necessary. Modern JDBC 4.0+ auto-registers.
            // Class.forName("oracle.jdbc.driver.OracleDriver"); 
            System.out.println("Oracle JDBC Driver registered.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Establishes and returns a database connection.
     * @return Connection object
     * @throws SQLException if a database access error occurs
     */
    public static Connection getConnection() throws SQLException {
        System.out.println("Connecting to database...");
        // Ensure you have the ojdbcX.jar in your classpath
        Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
        System.out.println("Connection established successfully!");
        return conn;
    }

    /**
     * Closes the database connection.
     * @param conn The Connection object to close.
     */
    public static void closeConnection(Connection conn) {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
                System.out.println("Connection closed.");
            }
        } catch (SQLException e) {
            System.err.println("Error closing connection: " + e.getMessage());
        }
    }
}