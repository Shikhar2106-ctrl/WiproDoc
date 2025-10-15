package com.hr;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Task_DDL { // Reusing for the simple connection test

    // Task M1: Write a Java program that establishes a connection...
    public static void testConnection() {
        // Connection details are in DbConnection.java
        String DB_URL = "jdbc:oracle:thin:@localhost:1521:ORCL";
        String USER = "hr";
        String PASS = "hr";
        
        System.out.println("\n--- Task M1: Connection Test ---");
        try {
            // Task S2: Exclude Class.forName ("Driver auto-registration is assumed")
            // Class.forName("oracle.jdbc.driver.OracleDriver"); // Commented out for S2
            
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Connection Established Successfully!");
            
            // Close the connection
            if (conn != null) conn.close();
            
        } catch (SQLException e) {
            System.err.println("Connection could not be established.");
            System.err.println("Description of the exception: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("An unexpected error occurred: " + e.getMessage());
        }
    }
    private static final String DDL_DEPT = 
        "CREATE TABLE DEPT (" +
        "Dept_ID NUMBER(7) PRIMARY KEY, " +
        "Dept_Name VARCHAR2(20) " +
        ")";

    // Task S3: Create EMP table
    private static final String DDL_EMP = 
        "CREATE TABLE EMP (" +
        "ID NUMBER(7) PRIMARY KEY, " +
        "LAST_NAME VARCHAR2(25) NOT NULL, " +
        "FIRST_NAME VARCHAR2(25), " +
        "DEPT_ID NUMBER(7), " +
        "FOREIGN KEY (DEPT_ID) REFERENCES DEPT(Dept_ID) " +
        ")";
    
    // Initial DML for EMP table (Task S3 - Insert 101,Sam,Sundar,10)
    private static final String DML_EMP_INITIAL = 
        "INSERT INTO EMP (ID, LAST_NAME, FIRST_NAME, DEPT_ID) VALUES (101, 'Sundar', 'Sam', 10)";
    
    // Task M2: Populate DEPT table data (using multiple INSERTs and correcting errors)
    private static final String[] DML_DEPT_DATA = {
        "INSERT INTO DEPT (Dept_ID, Dept_Name) SELECT DEPARTMENT_ID, DEPARTMENT_NAME FROM HR.DEPARTMENTS WHERE DEPARTMENT_ID IS NOT NULL", // Populate from existing DEPARTMENTS
        "INSERT INTO DEPT (Dept_ID, Dept_Name) VALUES (20, 'TT')",      // Correcting 'Insert dept Id 10 and Name Accounts' to 20/TT
        "INSERT INTO DEPT (Dept_ID, Dept_Name) VALUES (30, 'Accounts')" // Correcting 'Insert AI as ID and Accounts' to 30/Accounts
    };
    
    public static void setupTablesAndData() {
        Connection conn = null;
        Statement stmt = null;
        try {
            conn = DbConnection.getConnection();
            stmt = conn.createStatement();
            
            System.out.println("\n--- DDL/Setup Tasks (S1, S3, M2) ---");

            // Cleanup (Optional but helpful for re-running)
            try { stmt.executeUpdate("DROP TABLE EMP"); stmt.executeUpdate("DROP TABLE DEPT"); } catch (SQLException ignored) {}
            
            // Task S1: Create DEPT table
            stmt.executeUpdate(DDL_DEPT);
            System.out.println("✅ DEPT table created.");
            
            // Task M2: Populate DEPT table (Bulk and corrections)
            for (String sql : DML_DEPT_DATA) {
                stmt.executeUpdate(sql);
            }
            System.out.println("✅ DEPT table populated.");

            // Task S3: Create EMP table
            stmt.executeUpdate(DDL_EMP);
            System.out.println("✅ EMP table created.");
            
            // Task S3 (DML): Insert initial record
            stmt.executeUpdate(DML_EMP_INITIAL);
            System.out.println("✅ Initial record inserted into EMP.");
            
            conn.commit(); // Commit all DDL/DML changes
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { if (stmt != null) stmt.close(); } catch (SQLException e) { e.printStackTrace(); }
            DbConnection.closeConnection(conn);
        }
    }
}