package com.hr;

import java.sql.*;
import java.text.SimpleDateFormat;

public class Task_StudentDAO {
    
    // --- Task 1 (S1) - Main Control Method (Uses command line args) ---
    public static void processStudentCalls(String[] args) {
        if (args.length == 0) {
            System.err.println("Usage: java com.hr.MainApplication <option> [args...]");
            System.err.println("Options: 1=Insert, 2=Delete, 3=Modify, 4=Display");
            return;
        }

        try {
            int option = Integer.parseInt(args[0]);
            switch (option) {
                case 1: // Insert (Rollno, Name, Standard, DOB, Fees)
                    // Example: 1 101 "Ajit" "IV" "28-Nov-2001" 4000
                    if (args.length < 6) throw new IllegalArgumentException("Insert requires all 5 arguments.");
                    int rollno_i = Integer.parseInt(args[1]);
                    String name_i = args[2].toUpperCase(); // Max 20 chars, uppercase
                    String standard_i = args[3].toUpperCase(); // Roman I to X
                    String dob_i = args[4];
                    double fees_i = Double.parseDouble(args[5]);
                    instantiate(rollno_i, name_i, standard_i, dob_i, fees_i);
                    break;
                case 2: // Delete (Rollno)
                    // Example: 2 101
                    if (args.length < 2) throw new IllegalArgumentException("Delete requires Rollno.");
                    int rollno_d = Integer.parseInt(args[1]);
                    delete(rollno_d);
                    break;
                case 3: // Modify (Rollno, New Fees)
                    // Example: 3 101 4500
                    if (args.length < 3) throw new IllegalArgumentException("Modify requires Rollno and new fees.");
                    int rollno_m = Integer.parseInt(args[1]);
                    double fees_m = Double.parseDouble(args[2]);
                    modify(rollno_m, fees_m);
                    break;
                case 4: // Display (Optional Rollno) - Task 5
                    // Example: 4 101 (specific) or 4 (all)
                    if (args.length > 1) { // Specific student
                        display(Integer.parseInt(args[1])); 
                    } else { // All students
                        display(-1); 
                    }
                    break;
                default:
                    System.err.println("Invalid option.");
            }
        } catch (Exception e) {
            System.err.println("Error processing student call: " + e.getMessage());
        }
    }

    // Task 2: Inserting a record (instantiate)
    public static void instantiate(int rollno, String name, String standard, String dob, double fees) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql = "INSERT INTO STUDENT VALUES (?, ?, ?, ?, ?)";

        try {
            conn = DbConnection.getConnection();
            pstmt = conn.prepareStatement(sql);
            
            // Format Date
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
            Date sqlDate = new Date(sdf.parse(dob).getTime());

            // Set Parameters
            pstmt.setInt(1, rollno);
            pstmt.setString(2, name);
            pstmt.setString(3, standard);
            pstmt.setDate(4, sqlDate);
            pstmt.setDouble(5, fees);

            int rows = pstmt.executeUpdate();
            System.out.println("✅ Task 2: Insert successful. " + rows + " row(s) added.");
        } catch (Exception e) {
            System.err.println("Insert failed: " + e.getMessage());
        } finally {
            try { if (pstmt != null) pstmt.close(); } catch (SQLException e) { e.printStackTrace(); }
            DbConnection.closeConnection(conn);
        }
    }

    // Task 3: Deleting a Student's record (delete)
    public static void delete(int rollno) {
        Connection conn = null;
        PreparedStatement pstmt_select = null;
        PreparedStatement pstmt_log = null;
        PreparedStatement pstmt_delete = null;
        ResultSet rs = null;

        String sql_select = "SELECT Rollno, StudentName, Standard FROM STUDENT WHERE Rollno = ?";
        String sql_log = "INSERT INTO StudentLog (Rollno, StudentName, Standard, Leaving_Date) VALUES (?, ?, ?, SYSDATE)";
        String sql_delete = "DELETE FROM STUDENT WHERE Rollno = ?";

        try {
            conn = DbConnection.getConnection();
            conn.setAutoCommit(false); // Transaction start

            // 1. Get student data
            pstmt_select = conn.prepareStatement(sql_select);
            pstmt_select.setInt(1, rollno);
            rs = pstmt_select.executeQuery();
            if (rs.next()) {
                // 2. Insert into StudentLog
                pstmt_log = conn.prepareStatement(sql_log);
                pstmt_log.setInt(1, rs.getInt("Rollno"));
                pstmt_log.setString(2, rs.getString("StudentName"));
                pstmt_log.setString(3, rs.getString("Standard"));
                pstmt_log.executeUpdate();
                
                // 3. Delete from STUDENT
                pstmt_delete = conn.prepareStatement(sql_delete);
                pstmt_delete.setInt(1, rollno);
                int rows = pstmt_delete.executeUpdate();

                conn.commit();
                System.out.println("✅ Task 3: Delete successful. " + rows + " row(s) deleted and logged.");
            } else {
                System.out.println("Rollno " + rollno + " not found.");
            }
        } catch (SQLException e) {
            System.err.println("Delete failed. Rolling back: " + e.getMessage());
            try { if (conn != null) conn.rollback(); } catch (SQLException rollbackEx) { rollbackEx.printStackTrace(); }
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (pstmt_select != null) pstmt_select.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (pstmt_log != null) pstmt_log.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (pstmt_delete != null) pstmt_delete.close(); } catch (SQLException e) { e.printStackTrace(); }
            DbConnection.closeConnection(conn);
        }
    }

    // Task 4: Modification of Student record (modify)
    public static void modify(int rollno, double newFees) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql = "UPDATE STUDENT SET Fees = ? WHERE Rollno = ?";

        try {
            conn = DbConnection.getConnection();
            pstmt = conn.prepareStatement(sql);
            
            pstmt.setDouble(1, newFees);
            pstmt.setInt(2, rollno);

            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                System.out.println("✅ Task 4: Modification successful. Fees updated to " + newFees + " for Rollno " + rollno);
            } else {
                System.out.println("Rollno " + rollno + " not found. No modification performed.");
            }
        } catch (SQLException e) {
            System.err.println("Modify failed: " + e.getMessage());
        } finally {
            try { if (pstmt != null) pstmt.close(); } catch (SQLException e) { e.printStackTrace(); }
            DbConnection.closeConnection(conn);
        }
    }

    // Task 5: Display Student details (display)
    public static void display(int rollno) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM STUDENT";
        
        System.out.println("\n--- Task 5: Display Student Details ---");

        try {
            conn = DbConnection.getConnection();
            if (rollno != -1) {
                sql += " WHERE Rollno = ?";
                pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1, rollno);
                System.out.println("Displaying details for Rollno: " + rollno);
            } else {
                pstmt = conn.prepareStatement(sql);
                System.out.println("Displaying details for ALL students.");
            }

            rs = pstmt.executeQuery();

            System.out.printf("%-10s%-20s%-10s%-15s%-10s\n", "Rollno", "Name", "Std", "DOB", "Fees");
            System.out.println("----------------------------------------------------------");
            while (rs.next()) {
                System.out.printf("%-10d%-20s%-10s%-15s%-10.2f\n",
                                rs.getInt("Rollno"),
                                rs.getString("StudentName"),
                                rs.getString("Standard"),
                                rs.getDate("Date_Of_Birth"),
                                rs.getDouble("Fees"));
            }
            
        } catch (SQLException e) {
            System.err.println("Display failed: " + e.getMessage());
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (pstmt != null) pstmt.close(); } catch (SQLException e) { e.printStackTrace(); }
            DbConnection.closeConnection(conn);
        }
    }
}