package com.geek;

/**
 * @author itguoy
 * @date 2021-10-24 14:56
 */
public class StudentService {

    private StudentProperties studentProperties;

    public StudentProperties getStudentProperties() {
        return studentProperties;
    }

    public void setStudentProperties(StudentProperties studentProperties) {
        this.studentProperties = studentProperties;
    }

    public String getStudent() {
        Student student = new Student(studentProperties.getId(), studentProperties.getName());
        return student.toString();
    }
}
