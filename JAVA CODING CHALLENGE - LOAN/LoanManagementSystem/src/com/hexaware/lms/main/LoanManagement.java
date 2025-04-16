package com.hexaware.lms.main;

import java.util.Scanner;
import com.hexaware.lms.entity.*;
import com.hexaware.lms.dao.*;
import com.hexaware.lms.service.*;
import com.hexaware.lms.exception.*;

public class LoanManagement {

	public static void main(String[] args) throws InvalidLoanException {
		Scanner scanner = new Scanner(System.in);
		ILoanRepository loanRepository = new ILoanRepositoryImpl();
		ILoanService loanService = new ILoanServiceImpl();

		while (true) {
			// Displaying the main menu
			System.out.println("\n--- Loan Management System ---");
			System.out.println("1. Apply for a Loan");
			System.out.println("2. Get All Loans");
			System.out.println("3. Get Loan by ID");
			System.out.println("4. Repay Loan");
			System.out.println("5. Exit");
			System.out.print("Enter your choice: ");

			int choice = scanner.nextInt();
			scanner.nextLine();

			switch (choice) {
			case 1:
				System.out.print("Enter Customer ID: ");
				int customerId = scanner.nextInt();
				scanner.nextLine();
				System.out.print("Enter Loan Type (Home/Car): ");
				String loanType = scanner.nextLine();
				System.out.print("Enter Principal Amount: ");
				double principalAmount = scanner.nextDouble();
				System.out.print("Enter Interest Rate: ");
				double interestRate = scanner.nextDouble();
				System.out.print("Enter Loan Term (months): ");
				int loanTerm = scanner.nextInt();
				scanner.nextLine();

				Customer customer = loanService.getCustomerById(customerId);

				Loan loan = new Loan(0, customerId, principalAmount, interestRate, loanTerm, loanType, "Pending");

				loanService.applyLoan(loan);
				System.out.println("Loan application submitted successfully!");
				break;

			case 2:
				loanService.getAllLoan().forEach(System.out::println);
				break;

			case 3:
				System.out.print("Enter Loan ID: ");
				int loanId = scanner.nextInt();
				try {
					Loan loanDetails = loanService.getLoanById(loanId);
					System.out.println(loanDetails);
				} catch (InvalidLoanException e) {
					System.out.println("Error: " + e.getMessage());
				}
				break;

			case 4:
				System.out.print("Enter Loan ID: ");
				int repayLoanId = scanner.nextInt();
				System.out.print("Enter Repayment Amount: ");
				double repaymentAmount = scanner.nextDouble();
				loanService.loanRepayment(repayLoanId, repaymentAmount);
				break;

			case 5:
				System.out.println("Exiting the Loan Management System...");
				scanner.close();
				System.exit(0);

			default:
				System.out.println("Invalid choice! Please select a valid option.");
				break;
			}
		}
	}
}
