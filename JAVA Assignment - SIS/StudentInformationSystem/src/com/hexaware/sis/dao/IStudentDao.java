package com.hexaware.sis.dao;

import com.hexaware.sis.entity.Student;
import com.hexaware.sis.exceptions.StudentNotFoundException;
import java.util.List;

public interface IStudentDao {

    int addStudent(Student student);

    int updateStudent(Student student);

    int deleteStudent(int studentId);

    List<Student> getAllStudents();

    Student getStudentById(int studentId) throws StudentNotFoundException;

    List<Student> getStudentsByCourse(String courseName);
}


