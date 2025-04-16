package com.hexaware.lms.service;

import java.util.List;

import com.hexaware.lms.dao.ILoanRepository;
import com.hexaware.lms.dao.ILoanRepositoryImpl;
import com.hexaware.lms.entity.Customer;
import com.hexaware.lms.entity.Loan;
import com.hexaware.lms.exception.InvalidLoanException;

public class ILoanServiceImpl implements ILoanService {

	private ILoanRepository repo = new ILoanRepositoryImpl();

	@Override
	public int applyLoan(Loan loan) {
		return repo.applyLoan(loan);
	}

	@Override
	public double calculateInterest(int loanId) throws InvalidLoanException {
		return repo.calculateInterest(loanId);
	}

	@Override
	public double calculateInterest(double principal, double rate, int tenure) {
		return repo.calculateInterest(principal, rate, tenure);
	}

	@Override
	public String loanStatus(int loanId) throws InvalidLoanException {
		return repo.loanStatus(loanId);
	}

	@Override
	public double calculateEMI(int loanId) throws InvalidLoanException {
		return repo.calculateEMI(loanId);
	}

	@Override
	public double calculateEMI(double principal, double annualRate, int tenure) {
		return repo.calculateEMI(principal, annualRate, tenure);
	}

	@Override
	public int loanRepayment(int loanId, double amount) throws InvalidLoanException {
		return repo.loanRepayment(loanId, amount);
	}

	@Override
	public List<Loan> getAllLoan() {
		return repo.getAllLoan();
	}

	@Override
	public Loan getLoanById(int loanId) throws InvalidLoanException {
		return repo.getLoanById(loanId);
	}

	@Override
	public Customer getCustomerById(int customerId) {

		return null;
	}
}
