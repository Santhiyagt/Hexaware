package com.hexaware.sis.dao;

import com.hexaware.sis.entity.Teacher;
import com.hexaware.sis.exceptions.TeacherNotFoundException;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TeacherDaoImpl implements ITeacherDao {

    @Override
    public int addTeacher(Teacher teacher) {
        int count = 0;
        try (Connection conn = DbUtil.getDBConnection()) {
            String sql = "INSERT INTO Teacher (teacher_id, first_name, last_name, email) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, teacher.getTeacherId());
            ps.setString(2, teacher.getFirstName());
            ps.setString(3, teacher.getLastName());
            ps.setString(4, teacher.getEmail());
            count = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    @Override
    public int updateTeacher(Teacher teacher) {
        int count = 0;
        try (Connection conn = DbUtil.getDBConnection()) {
            String sql = "UPDATE Teacher SET first_name = ?, last_name = ?, email = ? WHERE teacher_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, teacher.getFirstName());
            ps.setString(2, teacher.getLastName());
            ps.setString(3, teacher.getEmail());
            ps.setInt(4, teacher.getTeacherId());
            count = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    @Override
    public int deleteTeacher(int teacherId) {
        int count = 0;
        try (Connection conn = DbUtil.getDBConnection()) {
            String sql = "DELETE FROM Teacher WHERE teacher_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, teacherId);
            count = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    @Override
    public Teacher getTeacherById(int teacherId) throws TeacherNotFoundException {
        Teacher teacher = null;
        try (Connection conn = DbUtil.getDBConnection()) {
            String sql = "SELECT * FROM Teacher WHERE teacher_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, teacherId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String email = rs.getString("email");

                teacher = new Teacher(teacherId, firstName, lastName, email);
            } else {
                throw new TeacherNotFoundException("Teacher with ID " + teacherId + " not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return teacher;
    }

    @Override
    public List<Teacher> getAllTeachers() {
        List<Teacher> teachers = new ArrayList<>();
        try (Connection conn = DbUtil.getDBConnection()) {
            String sql = "SELECT * FROM Teacher";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("teacher_id");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String email = rs.getString("email");

                teachers.add(new Teacher(id, firstName, lastName, email));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return teachers;
    }
}

