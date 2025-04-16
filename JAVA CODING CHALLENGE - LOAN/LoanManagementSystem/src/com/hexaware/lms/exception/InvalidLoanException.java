package com.hexaware.lms.exception;

public class InvalidLoanException extends Exception {

	private static final long serialVersionUID = 1L;

	public InvalidLoanException() {
		super("Invalid loan details provided.");
	}

	public InvalidLoanException(String message) {
		super(message);
	}
}
