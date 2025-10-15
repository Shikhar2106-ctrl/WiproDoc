package com.hr;

import java.sql.*;
import java.util.Scanner;

public class Task_RestrictingSorting {
    
    // --- Helper for simple SELECT queries (Tasks 1-9, 12-14) ---
    private static void executeSimpleQuery(String taskNum, String sql) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            conn = DbConnection.getConnection();
            stmt = conn.createStatement();
            
            System.out.println("\n--- Task " + taskNum + " (SQL: " + sql + ") ---");
            rs = stmt.executeQuery(sql);
            
            // Print results dynamically
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();
            
            // Print Header
            for (int i = 1; i <= columnCount; i++) {
                System.out.printf("%-20s", rsmd.getColumnLabel(i));
            }
            System.out.println("\n---------------------------------------------------------");
            
            // Print Data
            while (rs.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    System.out.printf("%-20s", rs.getString(i));
                }
                System.out.println();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (stmt != null) stmt.close(); } catch (SQLException e) { e.printStackTrace(); }
            DbConnection.closeConnection(conn);
        }
    }
    
    // --- Task Implementations ---

    public static void runAllTasks() {
        // Task 1: Salary > $12,000
        executeSimpleQuery("1", "SELECT LAST_NAME, SALARY FROM EMPLOYEES WHERE SALARY > 12000");

        // Task 2: Employee ID 176
        executeSimpleQuery("2", "SELECT LAST_NAME, DEPARTMENT_ID FROM EMPLOYEES WHERE EMPLOYEE_ID = 176");

        // Task 3: Salary NOT BETWEEN $5,000 and $12,000
        executeSimpleQuery("3", "SELECT LAST_NAME, SALARY FROM EMPLOYEES WHERE SALARY NOT BETWEEN 5000 AND 12000");
        
        // Task 4: Last Name IN ('Matos', 'Taylor') ORDER BY HIRE_DATE
        executeSimpleQuery("4", "SELECT LAST_NAME, JOB_ID, HIRE_DATE FROM EMPLOYEES WHERE LAST_NAME IN ('Matos', 'Taylor') ORDER BY HIRE_DATE ASC");

        // Task 5: Department 20 or 50, ORDER BY LAST_NAME
        executeSimpleQuery("5", "SELECT LAST_NAME, DEPARTMENT_ID FROM EMPLOYEES WHERE DEPARTMENT_ID IN (20, 50) ORDER BY LAST_NAME ASC");
        
        // Task 6: Custom Labels and combined WHERE
        executeSimpleQuery("6", "SELECT LAST_NAME AS \"Employee\", SALARY AS \"Monthly Salary\" FROM EMPLOYEES WHERE (SALARY BETWEEN 5000 AND 12000) AND (DEPARTMENT_ID IN (20, 50))");
        
        // Task 7: Hired in 1994 (Oracle TO_CHAR)
        executeSimpleQuery("7", "SELECT LAST_NAME, HIRE_DATE FROM EMPLOYEES WHERE TO_CHAR(HIRE_DATE, 'YYYY') = '1994'");
        
        // Task 8: Employees without a Manager (JOIN)
        executeSimpleQuery("8", "SELECT E.LAST_NAME, J.JOB_TITLE FROM EMPLOYEES E JOIN JOBS J ON E.JOB_ID = J.JOB_ID WHERE E.MANAGER_ID IS NULL");

        // Task 9: Commission earners, ORDER BY numeric position (Salary, Commission DESC)
        executeSimpleQuery("9", "SELECT LAST_NAME, SALARY, COMMISSION_PCT FROM EMPLOYEES WHERE COMMISSION_PCT IS NOT NULL ORDER BY 2 DESC, 3 DESC");

        // Task 10: Prompts for Salary Value (PreparedStatement)
        task10_PromptForSalary();

        // Task 11: Prompts for Manager ID and Sort Column (PreparedStatement and Dynamic Sort)
        task11_PromptForManagerAndSort();

        // Task 12: Last Name with 'a' as the third letter (LIKE)
        executeSimpleQuery("12", "SELECT LAST_NAME FROM EMPLOYEES WHERE LAST_NAME LIKE '__a%'");

        // Task 13: Last Name containing 'a' AND 'e' (LIKE and AND)
        executeSimpleQuery("13", "SELECT LAST_NAME FROM EMPLOYEES WHERE LAST_NAME LIKE '%a%' AND LAST_NAME LIKE '%e%'");

        // Task 14: JOB_ID IN ('SA_REP', 'ST_CLERK') AND SALARY NOT IN (...)
        executeSimpleQuery("14", "SELECT LAST_NAME, JOB_ID, SALARY FROM EMPLOYEES WHERE JOB_ID IN ('SA_REP', 'ST_CLERK') AND SALARY NOT IN (2500, 3500, 7000)");
    }
    
    // Task 10: Use PreparedStatement for user input
    public static void task10_PromptForSalary() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.print("\n--- Task 10: Enter Salary_value (e.g., 12000): ");
            double salaryValue = scanner.nextDouble();
            
            conn = DbConnection.getConnection();
            String sql = "SELECT LAST_NAME, SALARY FROM EMPLOYEES WHERE SALARY > ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setDouble(1, salaryValue); // Set the bind variable
            
            System.out.println("SQL: " + sql.replace("?", String.valueOf(salaryValue)));

            rs = pstmt.executeQuery();
            
            System.out.printf("%-20s%-20s\n", "LAST_NAME", "SALARY");
            System.out.println("----------------------------------------");
            while (rs.next()) {
                System.out.printf("%-20s%-20.2f\n", rs.getString("LAST_NAME"), rs.getDouble("SALARY"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (java.util.InputMismatchException e) {
            System.err.println("Invalid input. Please enter a number for salary.");
        } finally {
            // NOTE: Scanner should not be closed here if System.in is still used. 
            // I'll leave it open for simplicity in this example.
            try { if (rs != null) rs.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (pstmt != null) pstmt.close(); } catch (SQLException e) { e.printStackTrace(); }
            DbConnection.closeConnection(conn);
        }
    }
    
    // Task 11: Dynamic SQL for sorting based on user input (Manager ID + Sort Column)
    public static void task11_PromptForManagerAndSort() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        Statement stmt = null; // Used for dynamic SQL (ORDER BY column)
        ResultSet rs = null;
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("\n--- Task 11: Enter manager_id (e.g., 103): ");
        int managerId = 0;
        try {
            managerId = scanner.nextInt();
        } catch (java.util.InputMismatchException e) {
            System.err.println("Invalid input. Please enter a number for manager ID.");
            return;
        }
        
        System.out.print("Enter column to sort by (e.g., last_name, salary): ");
        // Consume the remaining newline after nextInt()
        scanner.nextLine(); 
        String sortColumn = scanner.nextLine().toUpperCase().trim();
        
        // Basic validation for column name to prevent SQL Injection for ORDER BY
        if (!sortColumn.matches("[A-Z_]+")) {
             System.err.println("Invalid sort column name provided.");
             return;
        }

        try {
            conn = DbConnection.getConnection();
            
            // Note: ORDER BY column name cannot be a bind variable ('?') in most JDBC implementations. 
            // We must use string concatenation/dynamic SQL for the ORDER BY clause.
            String baseSql = "SELECT EMPLOYEE_ID, LAST_NAME, SALARY, DEPARTMENT_ID FROM EMPLOYEES WHERE MANAGER_ID = " + managerId + " ORDER BY " + sortColumn;
            
            System.out.println("SQL: " + baseSql);
            
            // Since we concatenated the Manager ID and Sort Column, we can use a simple Statement
            // or concatenate the Manager ID as well. Using Statement here for simplicity with dynamic sort.
            stmt = conn.createStatement();
            rs = stmt.executeQuery(baseSql);
            
            System.out.printf("%-15s%-20s%-15s%-15s\n", "EMP_ID", "LAST_NAME", "SALARY", "DEPT_ID");
            System.out.println("-----------------------------------------------------------------");
            while (rs.next()) {
                System.out.printf("%-15d%-20s%-15.2f%-15d\n", 
                                rs.getInt("EMPLOYEE_ID"), 
                                rs.getString("LAST_NAME"), 
                                rs.getDouble("SALARY"),
                                rs.getInt("DEPARTMENT_ID"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (pstmt != null) pstmt.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (stmt != null) stmt.close(); } catch (SQLException e) { e.printStackTrace(); }
            DbConnection.closeConnection(conn);
        }
    }
}