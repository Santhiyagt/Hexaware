package com.hexaware.sis.dao;

import com.hexaware.sis.entity.Enrollment;
import com.hexaware.sis.exceptions.InvalidEnrollmentDataException;
import java.util.List;

public interface IEnrollmentDao {
    int enrollStudent(Enrollment enrollment);
    int updateEnrollment(Enrollment enrollment);
    int deleteEnrollment(int enrollmentId);
    Enrollment getEnrollmentById(int enrollmentId) throws InvalidEnrollmentDataException;
    List<Enrollment> getAllEnrollments();
    List<Enrollment> getEnrollmentsByStudentId(int studentId);
    List<Enrollment> getEnrollmentsByCourseId(int courseId);
}

