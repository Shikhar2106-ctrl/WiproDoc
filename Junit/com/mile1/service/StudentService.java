
package com.mile1.service;

import com.mile1.bean.Student;

public class StudentService {

    public int findNumberOfNullMarksArray(Student[] data) {
        int count = 0;
        for (Student s : data) {
            if (s != null && s.getMarks() == null)
                count++;
        }
        return count;
    }

    public int findNumberOfNullNames(Student[] data) {
        int count = 0;
        for (Student s : data) {
            if (s != null && s.getName() == null)
                count++;
        }
        return count;
    }

    public int findNumberOfNullObjects(Student[] data) {
        int count = 0;
        for (Student s : data) {
            if (s == null)
                count++;
        }
        return count;
    }
}
