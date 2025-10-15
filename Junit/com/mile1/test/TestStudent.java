package com.mile1.test;

import com.mile1.bean.Student;
import com.mile1.exception.*;
import com.mile1.service.*;

public class TestStudent {

    static Student data[] = new Student[10];

    static {
        data[0] = new Student("Sekar", new int[]{35, 35, 35});
        data[1] = new Student(null, new int[]{11, 22, 33});
        data[2] = null;
        data[3] = new Student("Manoj", null);
        data[4] = new Student("Rita", new int[]{90, 95, 93});
        data[5] = new Student("Tom", new int[]{40, 45, 48});
        data[6] = new Student("Jerry", new int[]{20, 25, 30});
        data[7] = new Student("Mona", new int[]{75, 80, 70});
        data[8] = new Student(null, null);
        data[9] = null;
    }

    public static void main(String[] args) {
        StudentReport report = new StudentReport();
        StudentService service = new StudentService();

        System.out.println("Grade Calculation for Valid Objects:");
        for (int i = 0; i < data.length; i++) {
            try {
                if (report.validate(data[i]).equals("VALID"))
                    System.out.println("Grade of student " + data[i].getName() + ": " + report.findGrade(data[i]));
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        System.out.println("\nCounting Nulls:");
        System.out.println("Number of Null Marks Array: " + service.findNumberOfNullMarksArray(data));
        System.out.println("Number of Null Names: " + service.findNumberOfNullNames(data));
        System.out.println("Number of Null Objects: " + service.findNumberOfNullObjects(data));
    }
}




// com/mile1/bean/Student.java
// com/mile1/exception/NullStudentException.java
// com/mile1/exception/NullNameException.java
// com/mile1/exception/NullMarksArrayException.java
// com/mile1/service/StudentReport.java
// com/mile1/service/StudentService.java
// com/mile1/test/TestStudent.java


// Grade Calculation for Valid Objects:
// Grade of student Sekar: D
// Grade of student Rita: A
// Grade of student Tom: D
// Grade of student Jerry: F
// Grade of student Mona: B

// Counting Nulls:
// Number of Null Marks Array: 2
// Number of Null Names: 2
// Number of Null Objects: 2

