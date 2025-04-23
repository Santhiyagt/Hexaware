package com.hexaware.sis.dao;

import com.hexaware.sis.entity.Teacher;
import com.hexaware.sis.exceptions.TeacherNotFoundException;
import java.util.List;

public interface ITeacherDao {
	int addTeacher(Teacher teacher);

	int updateTeacher(Teacher teacher);

	int deleteTeacher(int teacherId);

	Teacher getTeacherById(int teacherId) throws TeacherNotFoundException;

	List<Teacher> getAllTeachers();
}
