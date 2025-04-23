package com.hexaware.sis.dao;

import com.hexaware.sis.entity.Course;
import com.hexaware.sis.exceptions.CourseNotFoundException;
import com.hexaware.sis.util.DbUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseDaoImpl implements ICourseDao {

	@Override
	public int addCourse(Course course) {
		int count = 0;
		try (Connection conn = DbUtil.getDBConnection()) {
			String sql = "INSERT INTO Course (course_id, course_name, credits, teacher_id) VALUES (?, ?, ?, ?)";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, course.getCourseId());
			ps.setString(2, course.getCourseName());
			ps.setInt(3, course.getCredits());
			ps.setInt(4, course.getTeacherId());

			count = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public int updateCourse(Course course) {
		int count = 0;
		try (Connection conn = DbUtil.getDBConnection()) {
			String sql = "UPDATE Course SET course_name = ?, credits = ?, teacher_id = ? WHERE course_id = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, course.getCourseName());
			ps.setInt(2, course.getCredits());
			ps.setInt(3, course.getTeacherId());
			ps.setInt(4, course.getCourseId());

			count = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public int deleteCourse(int courseId) {
		int count = 0;
		try (Connection conn = DbUtil.getDBConnection()) {
			String sql = "DELETE FROM Course WHERE course_id = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, courseId);
			count = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public Course getCourseById(int courseId) throws CourseNotFoundException {
		Course course = null;
		try (Connection conn = DbUtil.getDBConnection()) {
			String sql = "SELECT * FROM Course WHERE course_id = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, courseId);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				String courseName = rs.getString("course_name");
				int credits = rs.getInt("credits");
				int teacherId = rs.getInt("teacher_id");

				course = new Course(courseId, courseName, credits, teacherId);
			} else {
				throw new CourseNotFoundException("Course with ID " + courseId + " not found.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return course;
	}

	@Override
	public List<Course> getAllCourses() {
		List<Course> courses = new ArrayList<>();
		try (Connection conn = DbUtil.getDBConnection()) {
			String sql = "SELECT * FROM Course";
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				int id = rs.getInt("course_id");
				String name = rs.getString("course_name");
				int credits = rs.getInt("credits");
				int teacherId = rs.getInt("teacher_id");

				courses.add(new Course(id, name, credits, teacherId));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return courses;
	}

	@Override
	public List<Course> getCoursesByTeacherId(int teacherId) {
		List<Course> courses = new ArrayList<>();
		try (Connection conn = DbUtil.getDBConnection()) {
			String sql = "SELECT * FROM Course WHERE teacher_id = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, teacherId);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				int id = rs.getInt("course_id");
				String name = rs.getString("course_name");
				int credits = rs.getInt("credits");

				courses.add(new Course(id, name, credits, teacherId));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return courses;
	}
}
