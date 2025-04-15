package com.hexaware.sis.dao;


import com.hexaware.sis.entity.Course;
import com.hexaware.sis.exceptions.CourseNotFoundException;

import java.util.List;

public interface ICourseDao {
    int addCourse(Course course);
    int updateCourse(Course course);
    int deleteCourse(int courseId);
    Course getCourseById(int courseId) throws CourseNotFoundException;
    List<Course> getAllCourses();
    List<Course> getCoursesByTeacherId(int teacherId);
}

