package com.hr;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Task_EmpQuery {
    
    // --- Task S1: Query table "emp" (using column index and column name) ---
    public static void task1_QueryEmpIndexAndName() {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        String sql = "SELECT ID, FIRST_NAME, LAST_NAME FROM EMP"; // ID is empno, FIRST_NAME/LAST_NAME are ename
        System.out.println("\n--- Task S1: Query EMP (Index and Name) ---");
        try {
            conn = DbConnection.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            System.out.printf("%-10s%-15s%-15s\n", "EMPNO", "ENAME_IDX", "ENAME_NAME");
            System.out.println("----------------------------------------------");

            while (rs.next()) {
                int empno = rs.getInt(1); // Column 1: ID
                String ename_idx = rs.getString(2); // Column 2: FIRST_NAME (used as ENAME)
                String ename_name = rs.getString("LAST_NAME"); // Column Name: LAST_NAME
                
                System.out.printf("%-10d%-15s%-15s\n", empno, ename_idx, ename_name);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (stmt != null) stmt.close(); } catch (SQLException e) { e.printStackTrace(); }
            DbConnection.closeConnection(conn);
        }
    }

    // --- Task S2: Modify program to display rows where salary is between 1000 and 2000 ---
    // NOTE: Our EMP table only has ID, LAST_NAME, FIRST_NAME, DEPT_ID. 
    // I will switch to using MY_EMPLOYEE table which has SALARY.
    public static void task2_QueryBySalaryRange() {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        
        // Using MY_EMPLOYEE table (created in Task_DML.java) which has SALARY and COMM
        String sql = "SELECT EMPLOYEE_ID, FIRST_NAME, SALARY, COMMISSION_PCT FROM MY_EMPLOYEE WHERE SALARY > 7000 AND SALARY < 13000";

        System.out.println("\n--- Task S2: Query MY_EMPLOYEE (Salary Range: 7000-13000) ---");
        try {
            conn = DbConnection.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            System.out.printf("%-10s%-20s%-15s%-10s\n", "EMPNO", "ENAME", "SAL", "COMM");
            System.out.println("-----------------------------------------------------");

            while (rs.next()) {
                System.out.printf("%-10d%-20s%-15.2f%-10s\n", 
                                rs.getInt("EMPLOYEE_ID"), 
                                rs.getString("FIRST_NAME"), 
                                rs.getDouble("SALARY"),
                                rs.getString("COMMISSION_PCT")); // COMMISSION_PCT is COMM
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (stmt != null) stmt.close(); } catch (SQLException e) { e.printStackTrace(); }
            DbConnection.closeConnection(conn);
        }
    }
}