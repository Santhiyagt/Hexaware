package com.hexaware.lms.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.hexaware.lms.entity.Loan;
import com.hexaware.lms.exception.InvalidLoanException;
import com.hexaware.lms.util.DbUtil;

public class ILoanRepositoryImpl implements ILoanRepository {

	@Override
	public int applyLoan(Loan loan) {
		int status = 0;
		try (Connection con = DbUtil.getDBConnection()) {
			String sql = "INSERT INTO loan (customer_id, principal_amount, interest_rate, loan_term, loan_type, loan_status) VALUES (?, ?, ?, ?, ?, 'Pending')";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, loan.getCustomerId());
			ps.setDouble(2, loan.getPrincipalAmount());
			ps.setDouble(3, loan.getInterestRate());
			ps.setInt(4, loan.getLoanTerm());
			ps.setString(5, loan.getLoanType());

			status = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return status;
	}

	@Override
	public double calculateInterest(int loanId) throws InvalidLoanException {
		Loan loan = getLoanById(loanId);
		return calculateInterest(loan.getPrincipalAmount(), loan.getInterestRate(), loan.getLoanTerm());
	}

	@Override
	public double calculateInterest(double principal, double rate, int tenure) {
		return (principal * rate * tenure) / 12;
	}

	@Override
	public String loanStatus(int loanId) throws InvalidLoanException {
		String status = "";
		try (Connection con = DbUtil.getDBConnection()) {
			String sql = "SELECT c.credit_score FROM customer c JOIN loan l ON c.customer_id = l.customer_id WHERE l.loan_id = ?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, loanId);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				int creditScore = rs.getInt("credit_score");
				status = (creditScore > 650) ? "Approved" : "Rejected";

				// Update the loan status in the database
				String updateSql = "UPDATE loan SET loan_status = ? WHERE loan_id = ?";
				PreparedStatement updatePs = con.prepareStatement(updateSql);
				updatePs.setString(1, status);
				updatePs.setInt(2, loanId);
				updatePs.executeUpdate();
			} else {
				throw new InvalidLoanException("Loan or customer not found for ID: " + loanId);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return status;
	}

	@Override
	public double calculateEMI(int loanId) throws InvalidLoanException {
		Loan loan = getLoanById(loanId);
		return calculateEMI(loan.getPrincipalAmount(), loan.getInterestRate(), loan.getLoanTerm());
	}

	@Override
	public double calculateEMI(double principal, double annualRate, int tenure) {
		double monthlyRate = annualRate / 12 / 100;
		return (principal * monthlyRate * Math.pow(1 + monthlyRate, tenure)) / (Math.pow(1 + monthlyRate, tenure) - 1);
	}

	// Loan repayment
	@Override
	public int loanRepayment(int loanId, double amount) throws InvalidLoanException {
		double emi = calculateEMI(loanId);
		if (amount < emi) {
			System.out.println("Payment rejected, Amount less than one EMI.");
			return 0;
		}
		int emisPaid = (int) (amount / emi);
		System.out.println("Payment accepted. You paid " + emisPaid + " EMIs.");
		return emisPaid;
	}

	// Retrieve all loans
	@Override
	public List<Loan> getAllLoan() {
		List<Loan> loans = new ArrayList<>();
		try (Connection con = DbUtil.getDBConnection()) {
			String sql = "SELECT * FROM loan";
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Loan loan = new Loan(rs.getInt("loan_id"), rs.getInt("customer_id"), rs.getDouble("principal_amount"),
						rs.getDouble("interest_rate"), rs.getInt("loan_term"), rs.getString("loan_type"),
						rs.getString("loan_status"));
				loans.add(loan);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return loans;
	}

	@Override
	public Loan getLoanById(int loanId) throws InvalidLoanException {
		Loan loan = null;
		try (Connection con = DbUtil.getDBConnection()) {
			String sql = "SELECT * FROM loan WHERE loan_id = ?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, loanId);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				loan = new Loan(rs.getInt("loan_id"), rs.getInt("customer_id"), rs.getDouble("principal_amount"),
						rs.getDouble("interest_rate"), rs.getInt("loan_term"), rs.getString("loan_type"),
						rs.getString("loan_status"));
			} else {
				throw new InvalidLoanException("Loan ID " + loanId + " not found");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return loan;
	}
}
