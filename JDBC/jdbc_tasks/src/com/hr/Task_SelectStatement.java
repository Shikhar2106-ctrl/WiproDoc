package com.hr;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class Task_SelectStatement {

    // Helper method to execute and print results for simple SELECT tasks
    private static void executeAndPrint(String taskName, String sql, String[] columnNames) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            conn = DbConnection.getConnection();
            stmt = conn.createStatement();
            
            System.out.println("\n--- Task " + taskName + " ---");
            System.out.println("SQL: " + sql);
            
            rs = stmt.executeQuery(sql);
            
            // Print Header
            StringBuilder header = new StringBuilder();
            for (String col : columnNames) {
                header.append(String.format("%-15s", col));
            }
            System.out.println(header.toString());
            System.out.println("---------------------------------------------------------");
            
            // Print Data
            while (rs.next()) {
                StringBuilder row = new StringBuilder();
                for (String col : columnNames) {
                    // Using getObject to handle various types easily
                    Object value = rs.getObject(col.trim()); 
                    row.append(String.format("%-15s", value != null ? value.toString() : "NULL"));
                }
                System.out.println(row.toString());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (stmt != null) stmt.close(); } catch (SQLException e) { e.printStackTrace(); }
            DbConnection.closeConnection(conn);
        }
    }
    
    // Task 1: Determine the structure of the DEPARTMENTS table and its contents.
    public static void task1_DepartmentsStructureAndData() {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = DbConnection.getConnection();
            stmt = conn.createStatement();

            System.out.println("\n--- Task 1: DEPARTMENTS Table Structure ---");
            rs = stmt.executeQuery("SELECT * FROM DEPARTMENTS WHERE 1 = 0");
            ResultSetMetaData rsmd = rs.getMetaData();
            for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                System.out.printf("Column %d: %s (%s)\n", i, rsmd.getColumnName(i), rsmd.getColumnTypeName(i));
            }
            rs.close(); 

            executeAndPrint("1 (Contents)", "SELECT * FROM DEPARTMENTS", 
                new String[]{"DEPARTMENT_ID", "DEPARTMENT_NAME", "MANAGER_ID", "LOCATION_ID"});

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (stmt != null) stmt.close(); } catch (SQLException e) { e.printStackTrace(); }
            DbConnection.closeConnection(conn);
        }
    }
    
    // Task 2: Display employee ID, last name, job ID, and hire date (aliased as STARTDATE).
    public static void task2_SelectWithAlias() {
        String sql = "SELECT EMPLOYEE_ID, LAST_NAME, JOB_ID, HIRE_DATE AS STARTDATE FROM EMPLOYEES";
        executeAndPrint("2", sql, new String[]{"EMPLOYEE_ID", "LAST_NAME", "JOB_ID", "STARTDATE"});
    }

    // Task 3: Display all unique job IDs.
    public static void task3_DistinctJobIds() {
        String sql = "SELECT DISTINCT JOB_ID FROM EMPLOYEES";
        executeAndPrint("3", sql, new String[]{"JOB_ID"});
    }

    // Task 4: Display employee data with custom column headings.
    public static void task4_CustomColumnHeadings() {
        // Note: Oracle uses double quotes for case-sensitive identifiers (like column aliases).
        // Since JDBC ResultSet can read the aliased name, we use the custom names in the column list.
        String sql = "SELECT EMPLOYEE_ID AS \"Emp #\", LAST_NAME AS \"Employee\", JOB_ID AS \"Job\", HIRE_DATE AS \"Hire Date\" FROM EMPLOYEES";
        executeAndPrint("4", sql, new String[]{"Emp #", "Employee", "Job", "Hire Date"});
    }
}