package com.hexaware.sis.dao;

import com.hexaware.sis.entity.Payment;
import com.hexaware.sis.util.DbUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PaymentDaoImpl implements IPaymentDao {

	@Override
	public int addPayment(Payment payment) {
		int count = 0;
		try (Connection conn = DbUtil.getDBConnection()) {
			String sql = "INSERT INTO Payment (payment_id, student_id, amount, payment_date) VALUES (?, ?, ?, ?)";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, payment.getPaymentId());
			ps.setInt(2, payment.getStudentId());
			ps.setDouble(3, payment.getAmount());
			ps.setDate(4, new java.sql.Date(payment.getPaymentDate().getTime()));

			count = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public int updatePayment(Payment payment) {
		int count = 0;
		try (Connection conn = DbUtil.getDBConnection()) {
			String sql = "UPDATE Payment SET student_id = ?, amount = ?, payment_date = ? WHERE payment_id = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, payment.getStudentId());
			ps.setDouble(2, payment.getAmount());
			ps.setDate(3, new java.sql.Date(payment.getPaymentDate().getTime()));
			ps.setInt(4, payment.getPaymentId());

			count = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public int deletePayment(int paymentId) {
		int count = 0;
		try (Connection conn = DbUtil.getDBConnection()) {
			String sql = "DELETE FROM Payment WHERE payment_id = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, paymentId);
			count = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public Payment getPaymentById(int paymentId) {
		Payment payment = null;
		try (Connection conn = DbUtil.getDBConnection()) {
			String sql = "SELECT * FROM Payment WHERE payment_id = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, paymentId);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				int studentId = rs.getInt("student_id");
				double amount = rs.getDouble("amount");
				Date date = rs.getDate("payment_date");

				payment = new Payment(paymentId, studentId, amount, date);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return payment;
	}

	@Override
	public List<Payment> getAllPayments() {
		List<Payment> payments = new ArrayList<>();
		try (Connection conn = DbUtil.getDBConnection()) {
			String sql = "SELECT * FROM Payment";
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				int paymentId = rs.getInt("payment_id");
				int studentId = rs.getInt("student_id");
				double amount = rs.getDouble("amount");
				Date date = rs.getDate("payment_date");

				payments.add(new Payment(paymentId, studentId, amount, date));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return payments;
	}

	@Override
	public List<Payment> getPaymentsByStudentId(int studentId) {
		List<Payment> payments = new ArrayList<>();
		try (Connection conn = DbUtil.getDBConnection()) {
			String sql = "SELECT * FROM Payment WHERE student_id = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, studentId);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				int paymentId = rs.getInt("payment_id");
				double amount = rs.getDouble("amount");
				Date date = rs.getDate("payment_date");

				payments.add(new Payment(paymentId, studentId, amount, date));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return payments;
	}
}
