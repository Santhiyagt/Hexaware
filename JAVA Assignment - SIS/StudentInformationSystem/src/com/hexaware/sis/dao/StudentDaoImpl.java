package com.hexaware.sis.dao;

import com.hexaware.sis.entity.Student;
import com.hexaware.sis.exceptions.StudentNotFoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDaoImpl implements IStudentDao {

	@Override
	public int addStudent(Student student) {
		int count = 0;
		try {
			Connection conn = DbUtil.getDBConnection();
			String insert = "INSERT INTO Student (student_id, first_name, last_name, date_of_birth, email, phone_number) VALUES (?, ?, ?, ?, ?, ?)";
			PreparedStatement pstmt = conn.prepareStatement(insert);
			pstmt.setInt(1, student.getStudentId());
			pstmt.setString(2, student.getFirstName());
			pstmt.setString(3, student.getLastName());
			pstmt.setDate(4, new Date(student.getDateOfBirth().getTime()));
			pstmt.setString(5, student.getEmail());
			pstmt.setString(6, student.getPhoneNumber());

			count = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public int updateStudent(Student student) {
		int count = 0;
		try {
			Connection conn = DbUtil.getDBConnection();
			String update = "UPDATE Student SET first_name = ?, last_name = ?, date_of_birth = ?, email = ?, phone_number = ? WHERE student_id = ?";
			PreparedStatement pstmt = conn.prepareStatement(update);
			pstmt.setString(1, student.getFirstName());
			pstmt.setString(2, student.getLastName());
			pstmt.setDate(3, new Date(student.getDateOfBirth().getTime()));
			pstmt.setString(4, student.getEmail());
			pstmt.setString(5, student.getPhoneNumber());
			pstmt.setInt(6, student.getStudentId());

			count = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public int deleteStudent(int studentId) {
		int count = 0;
		try {
			Connection conn = DbUtil.getDBConnection();
			String delete = "DELETE FROM Student WHERE student_id = ?";
			PreparedStatement pstmt = conn.prepareStatement(delete);
			pstmt.setInt(1, studentId);
			count = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public List<Student> getAllStudents() {
		List<Student> students = new ArrayList<>();
		try {
			Connection conn = DbUtil.getDBConnection();
			String select = "SELECT * FROM Student";
			PreparedStatement pstmt = conn.prepareStatement(select);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				int studentId = rs.getInt("student_id");
				String firstName = rs.getString("first_name");
				String lastName = rs.getString("last_name");
				Date dob = rs.getDate("date_of_birth");
				String email = rs.getString("email");
				String phoneNumber = rs.getString("phone_number");

				Student student = new Student(studentId, firstName, lastName, dob, email, phoneNumber);
				students.add(student);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return students;
	}

	@Override
	public Student getStudentById(int studentId) throws StudentNotFoundException {
		Student student = null;
		try {
			Connection conn = DbUtil.getDBConnection();
			String select = "SELECT * FROM Student WHERE student_id = ?";
			PreparedStatement pstmt = conn.prepareStatement(select);
			pstmt.setInt(1, studentId);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				String firstName = rs.getString("first_name");
				String lastName = rs.getString("last_name");
				Date dob = rs.getDate("date_of_birth");
				String email = rs.getString("email");
				String phoneNumber = rs.getString("phone_number");

				student = new Student(studentId, firstName, lastName, dob, email, phoneNumber);
			} else {
				throw new StudentNotFoundException("Student with ID " + studentId + " not found.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return student;
	}

	@Override
	public List<Student> getStudentsByCourse(String courseName) {
		List<Student> students = new ArrayList<>();
		try {
			Connection conn = DbUtil.getDBConnection();
			String select = "SELECT s.* FROM Student s JOIN Enrollment e ON s.student_id = e.student_id JOIN Course c ON e.course_id = c.course_id WHERE c.course_name = ?";
			PreparedStatement pstmt = conn.prepareStatement(select);
			pstmt.setString(1, courseName);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				int studentId = rs.getInt("student_id");
				String firstName = rs.getString("first_name");
				String lastName = rs.getString("last_name");
				Date dob = rs.getDate("date_of_birth");
				String email = rs.getString("email");
				String phoneNumber = rs.getString("phone_number");

				Student student = new Student(studentId, firstName, lastName, dob, email, phoneNumber);
				students.add(student);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return students;
	}
}
