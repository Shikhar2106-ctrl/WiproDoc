package com.hr;

public class MainApplication {

    public static void main(String[] args) {
        
        // --- SECTION 1: JDBC Introduction & Connection (Task M1, S2) ---
        Task_DDL.testConnection(); // Includes connection test and implicitly Task S2 (Class.forName commented)

        // --- SECTION 2: DDL/Setup Tasks (DEPT & EMP Table Setup) ---
        Task_DDL.setupTablesAndData();
        
        // --- SECTION 3: Executing Query & Processing Results (EMP/ENAME) ---
        Task_EmpQuery.task1_QueryEmpIndexAndName();
        Task_EmpQuery.task2_QueryBySalaryRange();

        // --- SECTION 4: DML Tasks (MY_EMPLOYEE Table) ---
        Task_DML.runAllDMLTasks();

        // --- SECTION 5: CallableStatement & Transactions (Net Salary) ---
        // NOTE: Requires the GET_NET_SALARY Stored Procedure to be created in the DB.
        Task_CallableStatement.runNetSalaryCalculation();

        // --- SECTION 6: Student DAO Pattern (PreparedStatement) ---
        // The main method for Student DAO is designed to run from the command line.
        // For demonstration purposes inside this main(), we'll pass an array of arguments manually.
        System.out.println("\n\n--- Student DAO Demonstrations (Task 1-5) ---");

        // Example 1: Insert (Task 2)
        String[] insertArgs = {"1", "101", "Ajit", "IV", "28-Nov-2001", "4000"};
        Task_StudentDAO.processStudentCalls(insertArgs);

        // Example 2: Insert another
        String[] insertArgs2 = {"1", "102", "Bala", "X", "15-Jan-2000", "5500.50"};
        Task_StudentDAO.processStudentCalls(insertArgs2);

        // Example 3: Modify Fees (Task 4)
        String[] modifyArgs = {"3", "101", "4500"};
        Task_StudentDAO.processStudentCalls(modifyArgs);
        
        // Example 4: Display specific record (Task 5)
        String[] displayOneArgs = {"4", "101"};
        Task_StudentDAO.processStudentCalls(displayOneArgs);

        // Example 5: Display all records (Task 5)
        String[] displayAllArgs = {"4"};
        Task_StudentDAO.processStudentCalls(displayAllArgs);

        // Example 6: Delete a record (Task 3) - will also log
        String[] deleteArgs = {"2", "102"};
        Task_StudentDAO.processStudentCalls(deleteArgs);
        
        System.out.println("\nAll tasks completed.");
    }
}