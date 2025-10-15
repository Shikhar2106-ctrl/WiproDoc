package com.mile1.service;

import com.mile1.bean.Student;
import com.mile1.exception.NullNameException;
import com.mile1.exception.NullMarksArrayException;
import com.mile1.exception.NullStudentException;

public class StudentReport {

    public String findGrade(Student studentObject) {
        int[] marks = studentObject.getMarks();
        int sum = 0;

        for (int mark : marks) {
            if (mark < 35)
                return "F";
            sum += mark;
        }

        int average = sum / marks.length;

        if (average <= 100 && average >= 80)
            return "A";
        else if (average >= 65)
            return "B";
        else if (average >= 50)
            return "C";
        else if (average >= 35)
            return "D";
        else
            return "F";
    }

    public String validate(Student studentObject)
            throws NullNameException, NullMarksArrayException, NullStudentException {

        if (studentObject == null)
            throw new NullStudentException();
        if (studentObject.getName() == null)
            throw new NullNameException();
        if (studentObject.getMarks() == null)
            throw new NullMarksArrayException();

        return "VALID";
    }
}
