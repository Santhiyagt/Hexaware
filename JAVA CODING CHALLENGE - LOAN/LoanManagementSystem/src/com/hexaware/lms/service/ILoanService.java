package com.hexaware.lms.service;

import java.util.List;

import com.hexaware.lms.entity.Customer;
import com.hexaware.lms.entity.Loan;
import com.hexaware.lms.exception.InvalidLoanException;

public interface ILoanService {

	int applyLoan(Loan loan);

	double calculateInterest(int loanId) throws InvalidLoanException;

	double calculateInterest(double principal, double rate, int tenure);

	String loanStatus(int loanId) throws InvalidLoanException;

	double calculateEMI(int loanId) throws InvalidLoanException;

	double calculateEMI(double principal, double annualRate, int tenure);

	int loanRepayment(int loanId, double amount) throws InvalidLoanException;

	List<Loan> getAllLoan();

	Loan getLoanById(int loanId) throws InvalidLoanException;

	Customer getCustomerById(int customerId);
}
