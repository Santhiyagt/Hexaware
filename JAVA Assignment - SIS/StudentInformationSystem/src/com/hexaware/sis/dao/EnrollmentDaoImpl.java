package com.hexaware.sis.dao;

import com.hexaware.sis.entity.Enrollment;
import com.hexaware.sis.exceptions.InvalidEnrollmentDataException;
import com.hexaware.sis.util.DbUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EnrollmentDaoImpl implements IEnrollmentDao {

	@Override
	public int enrollStudent(Enrollment enrollment) {
		int count = 0;
		try (Connection conn = DbUtil.getDBConnection()) {
			String sql = "INSERT INTO Enrollment (enrollment_id, student_id, course_id, enrollment_date) VALUES (?, ?, ?, ?)";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, enrollment.getEnrollmentId());
			ps.setInt(2, enrollment.getStudentId());
			ps.setInt(3, enrollment.getCourseId());
			ps.setDate(4, new java.sql.Date(enrollment.getEnrollmentDate().getTime()));

			count = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public int updateEnrollment(Enrollment enrollment) {
		int count = 0;
		try (Connection conn = DbUtil.getDBConnection()) {
			String sql = "UPDATE Enrollment SET student_id = ?, course_id = ?, enrollment_date = ? WHERE enrollment_id = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setFloat(1, enrollment.getStudentId());
			ps.setInt(2, enrollment.getCourseId());
			ps.setDate(3, new java.sql.Date(enrollment.getEnrollmentDate().getTime()));
			ps.setInt(4, enrollment.getEnrollmentId());

			count = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public int deleteEnrollment(int enrollmentId) {
		int count = 0;
		try (Connection conn = DbUtil.getDBConnection()) {
			String sql = "DELETE FROM Enrollment WHERE enrollment_id = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, enrollmentId);
			count = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public Enrollment getEnrollmentById(int enrollmentId) throws InvalidEnrollmentDataException {
		Enrollment enrollment = null;
		try (Connection conn = DbUtil.getDBConnection()) {
			String sql = "SELECT * FROM Enrollment WHERE enrollment_id = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, enrollmentId);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				int studentId = rs.getInt("student_id");
				int courseId = rs.getInt("course_id");
				Date enrollmentDate = rs.getDate("enrollment_date");

				enrollment = new Enrollment(enrollmentId, studentId, courseId, enrollmentDate);
			} else {
				throw new InvalidEnrollmentDataException("Enrollment with ID " + enrollmentId + " not found.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return enrollment;
	}

	@Override
	public List<Enrollment> getAllEnrollments() {
		List<Enrollment> enrollments = new ArrayList<>();
		try (Connection conn = DbUtil.getDBConnection()) {
			String sql = "SELECT * FROM Enrollment";
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				int id = rs.getInt("enrollment_id");
				int sid = rs.getInt("student_id");
				int cid = rs.getInt("course_id");
				Date edate = rs.getDate("enrollment_date");

				enrollments.add(new Enrollment(id, sid, cid, edate));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return enrollments;
	}

	@Override
	public List<Enrollment> getEnrollmentsByStudentId(int studentId) {
		List<Enrollment> enrollments = new ArrayList<>();
		try (Connection conn = DbUtil.getDBConnection()) {
			String sql = "SELECT * FROM Enrollment WHERE student_id = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, studentId);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				int id = rs.getInt("enrollment_id");
				int cid = rs.getInt("course_id");
				Date edate = rs.getDate("enrollment_date");

				enrollments.add(new Enrollment(id, studentId, cid, edate));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return enrollments;
	}

	@Override
	public List<Enrollment> getEnrollmentsByCourseId(int courseId) {
		List<Enrollment> enrollments = new ArrayList<>();
		try (Connection conn = DbUtil.getDBConnection()) {
			String sql = "SELECT * FROM Enrollment WHERE course_id = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, courseId);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				int id = rs.getInt("enrollment_id");
				int sid = rs.getInt("student_id");
				Date edate = rs.getDate("enrollment_date");

				enrollments.add(new Enrollment(id, sid, courseId, edate));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return enrollments;
	}
}
