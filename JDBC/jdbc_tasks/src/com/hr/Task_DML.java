package com.hr;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class Task_DML {

    public static void runAllDMLTasks() {
        Connection conn = null;
        Statement stmt = null;
        PreparedStatement pstmt = null;

        try {
            conn = DbConnection.getConnection();
            conn.setAutoCommit(false); // Start transaction
            
            System.out.println("\n--- DML Tasks ---");

            // --- Task 1/2 (Create MY_EMPLOYEE table - DML, not DDL) ---
            String createMyEmp = "CREATE TABLE MY_EMPLOYEE AS SELECT EMPLOYEE_ID, FIRST_NAME, LAST_NAME, DEPARTMENT_ID, SALARY FROM HR.EMPLOYEES WHERE 1=2";
            stmt = conn.createStatement();
            try { stmt.executeUpdate("DROP TABLE MY_EMPLOYEE"); } catch (SQLException ignored) {}
            stmt.executeUpdate(createMyEmp);
            System.out.println("✅ Task 1/2: MY_EMPLOYEE table created.");

            // --- Task 3: Insert without listing columns (Requires values for ALL columns) ---
            String insert3 = "INSERT INTO MY_EMPLOYEE VALUES (201, 'Michael', 'Hartstein', 20, 13000)";
            stmt.executeUpdate(insert3);
            System.out.println("✅ Task 3: Insert (201, 13000) - full insert.");

            // --- Task 4: Insert without listing columns, salary undetermined (NULL) ---
            // Assumes table column is nullable. We insert explicitly for salary to be NULL.
            String insert4 = "INSERT INTO MY_EMPLOYEE VALUES (202, 'Pat', 'Fay', 20, NULL)";
            stmt.executeUpdate(insert4);
            System.out.println("✅ Task 4: Insert (202, NULL) - salary value undetermined.");

            // --- Task 5: Insert listing columns, avoiding salary (NULL) ---
            String insert5 = "INSERT INTO MY_EMPLOYEE (EMPLOYEE_ID, FIRST_NAME, LAST_NAME, DEPARTMENT_ID) VALUES (203, 'Susan', 'Mavris', 40)";
            stmt.executeUpdate(insert5);
            System.out.println("✅ Task 5: Insert (203, NULL) - avoided salary column.");
            
            // --- Task 6: Insert multiple records using PreparedStatement (Recommended) ---
            String insert6 = "INSERT INTO MY_EMPLOYEE (EMPLOYEE_ID, FIRST_NAME, LAST_NAME, DEPARTMENT_ID, SALARY) VALUES (?, ?, ?, ?, ?)";
            pstmt = conn.prepareStatement(insert6);

            Object[][] records = {
                {205, "Shelley", "Higgins", 110, 12000.0}, {100, "Steven", "King", 90, 24000.0},
                {101, "Neena", "Kochhar", 90, 17000.0}, {102, "Lex", "De Haan", 90, 17000.0},
                {111, "Ismael", "Sciarra", 100, 7700.0}, {112, "Jose Manuel", "Urman", 100, 7800.0},
                {204, "Hermann", "Baer", 70, 10000.0}
            };
            
            for (Object[] rec : records) {
                pstmt.setInt(1, (int) rec[0]);
                pstmt.setString(2, (String) rec[1]);
                pstmt.setString(3, (String) rec[2]);
                pstmt.setInt(4, (int) rec[3]);
                pstmt.setDouble(5, (double) rec[4]);
                pstmt.addBatch();
            }
            pstmt.executeBatch();
            System.out.println("✅ Task 6: Multiple records inserted using batch.");
            
            // --- Task 7: Increase salary by 10% for dept 90 ---
            String update7 = "UPDATE MY_EMPLOYEE SET SALARY = SALARY * 1.10 WHERE DEPARTMENT_ID = 90";
            stmt.executeUpdate(update7);
            System.out.println("✅ Task 7: Salaries in Dept 90 increased by 10%.");
            
            // --- Task 8: Update Last_name of emp 202 to 'Higgins' ---
            String update8 = "UPDATE MY_EMPLOYEE SET LAST_NAME = 'Higgins' WHERE EMPLOYEE_ID = 202";
            stmt.executeUpdate(update8);
            System.out.println("✅ Task 8: Employee 202's last name updated to Higgins.");

            // --- Task 9: Delete employees whose name has char seq of 'man' (case insensitive) ---
            String delete9 = "DELETE FROM MY_EMPLOYEE WHERE UPPER(FIRST_NAME) LIKE '%MAN%' OR UPPER(LAST_NAME) LIKE '%MAN%'";
            int deletedRows = stmt.executeUpdate(delete9);
            System.out.println("✅ Task 9: Employees with 'man' in name deleted. Rows affected: " + deletedRows);

            conn.commit();
        } catch (SQLException e) {
            System.err.println("DML Task failed. Rolling back.");
            try { if (conn != null) conn.rollback(); } catch (SQLException rollbackEx) { rollbackEx.printStackTrace(); }
            e.printStackTrace();
        } finally {
            try { if (pstmt != null) pstmt.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (stmt != null) stmt.close(); } catch (SQLException e) { e.printStackTrace(); }
            DbConnection.closeConnection(conn);
        }
    }
}