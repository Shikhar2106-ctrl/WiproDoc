package com.hr;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.sql.Statement;

public class Task_CallableStatement {

    public static void runNetSalaryCalculation() {
        Connection conn = null;
        CallableStatement cstmt = null;
        Statement stmt = null;
        ResultSet rs = null;

        System.out.println("\n--- Task S1: CallableStatement (Net Salary) ---");
        System.out.printf("%-10s%-20s%-15s\n", "EMPNO", "ENAME", "NET SALARY");
        System.out.println("----------------------------------------------");

        String callSQL = "{call GET_NET_SALARY(?, ?)}";
        String selectSQL = "SELECT EMPLOYEE_ID, FIRST_NAME FROM MY_EMPLOYEE";
        
        try {
            conn = DbConnection.getConnection();
            cstmt = conn.prepareCall(callSQL);
            stmt = conn.createStatement();
            rs = stmt.executeQuery(selectSQL); // Get all employees

            while (rs.next()) {
                int empId = rs.getInt("EMPLOYEE_ID");
                String empName = rs.getString("FIRST_NAME");
                
                // 1. Set IN parameter
                cstmt.setInt(1, empId);
                
                // 2. Register OUT parameter
                cstmt.registerOutParameter(2, Types.NUMERIC);
                
                // 3. Execute
                cstmt.execute();
                
                // 4. Retrieve OUT parameter
                double netSalary = cstmt.getDouble(2);
                
                System.out.printf("%-10d%-20s%-15.2f\n", empId, empName, netSalary);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (stmt != null) stmt.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (cstmt != null) cstmt.close(); } catch (SQLException e) { e.printStackTrace(); }
            DbConnection.closeConnection(conn);
        }
    }
}