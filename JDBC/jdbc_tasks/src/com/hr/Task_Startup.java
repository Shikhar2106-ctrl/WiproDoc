package com.hr;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class Task_Startup {

    public static void viewEmployeeTableStructureAndData() {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            conn = DbConnection.getConnection();
            stmt = conn.createStatement();
            
            // Task 2 Part 1: View Employee Table Structure (via Metadata)
            System.out.println("\n--- Task 2: EMPLOYEES Table Structure ---");
            // Execute a query and inspect the metadata to get column structure
            rs = stmt.executeQuery("SELECT * FROM EMPLOYEES WHERE 1 = 0"); // Get metadata without data
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                System.out.printf("Column %d: %s (%s)\n", i, rsmd.getColumnName(i), rsmd.getColumnTypeName(i));
            }
            rs.close(); // Close the first ResultSet
            
            // Task 2 Part 2: View Employee Table Data
            System.out.println("\n--- Task 2: EMPLOYEES Table Data (First 5 records) ---");
            rs = stmt.executeQuery("SELECT * FROM EMPLOYEES WHERE ROWNUM <= 5"); 
            while (rs.next()) {
                System.out.printf("ID: %d, Name: %s %s, Salary: %.2f\n", 
                                rs.getInt("EMPLOYEE_ID"), 
                                rs.getString("FIRST_NAME"), 
                                rs.getString("LAST_NAME"), 
                                rs.getDouble("SALARY"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close resources in reverse order of creation
            try { if (rs != null) rs.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (stmt != null) stmt.close(); } catch (SQLException e) { e.printStackTrace(); }
            DbConnection.closeConnection(conn);
        }
    }
}