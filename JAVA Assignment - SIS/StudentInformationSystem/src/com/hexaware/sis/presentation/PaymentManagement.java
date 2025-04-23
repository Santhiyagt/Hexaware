package com.hexaware.sis.presentation;

import com.hexaware.sis.dao.IPaymentDao;
import com.hexaware.sis.dao.PaymentDaoImpl;
import com.hexaware.sis.entity.Payment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class PaymentManagement {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		IPaymentDao dao = new PaymentDaoImpl();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		int choice;
		do {
			System.out.println("\n--- Payment Management ---");
			System.out.println("1. Add Payment");
			System.out.println("2. Update Payment");
			System.out.println("3. Delete Payment");
			System.out.println("4. View Payment By Payment ID");
			System.out.println("5. View All Payments");
			System.out.println("6. View Payments By Student ID");
			System.out.println("0. Exit");
			System.out.print("Enter your choice: ");
			choice = sc.nextInt();

			switch (choice) {
			case 1:
				System.out.print("Enter Payment ID: ");
				int addPid = sc.nextInt();
				System.out.print("Enter Student ID: ");
				int addSid = sc.nextInt();
				System.out.print("Enter Amount: ");
				double amount = sc.nextDouble();
				sc.nextLine();
				System.out.print("Enter Payment Date (yyyy-MM-dd): ");
				String dateStr = sc.nextLine();

				try {
					Date payDate = sdf.parse(dateStr);
					Payment newPayment = new Payment(addPid, addSid, amount, payDate);
					int result = dao.addPayment(newPayment);
					System.out.println(result > 0 ? "Payment added successfully." : "Failed to add payment.");
				} catch (ParseException e) {
					System.out.println("Invalid date format.");
				}
				break;

			case 2:
				System.out.print("Enter Payment ID to update: ");
				int upPid = sc.nextInt();
				System.out.print("Enter New Student ID: ");
				int upSid = sc.nextInt();
				System.out.print("Enter New Amount: ");
				double upAmount = sc.nextDouble();
				sc.nextLine();
				System.out.print("Enter New Payment Date (yyyy-MM-dd): ");
				String upDateStr = sc.nextLine();

				try {
					Date upDate = sdf.parse(upDateStr);
					Payment updated = new Payment(upPid, upSid, upAmount, upDate);
					int result = dao.updatePayment(updated);
					System.out.println(result > 0 ? "Payment updated successfully." : "Failed to update payment.");
				} catch (ParseException e) {
					System.out.println("Invalid date format.");
				}
				break;

			case 3:
				System.out.print("Enter Payment ID to delete: ");
				int delPid = sc.nextInt();
				int delResult = dao.deletePayment(delPid);
				System.out.println(delResult > 0 ? "Payment deleted successfully." : "Payment not found.");
				break;

			case 4:
				System.out.print("Enter Payment ID to view: ");
				int viewPid = sc.nextInt();
				Payment p = dao.getPaymentById(viewPid);
				if (p != null) {
					System.out.println(p);
				} else {
					System.out.println("Payment not found.");
				}
				break;

			case 5:
				List<Payment> allPayments = dao.getAllPayments();
				System.out.println("\nAll Payments:");
				for (Payment pay : allPayments) {
					System.out.println(pay);
				}
				break;

			case 6:
				System.out.print("Enter Student ID to view payments: ");
				int sid = sc.nextInt();
				List<Payment> studentPayments = dao.getPaymentsByStudentId(sid);
				System.out.println("\nPayments for Student ID " + sid + ":");
				for (Payment pay : studentPayments) {
					System.out.println(pay);
				}
				break;

			case 0:
				System.out.println("Exiting Payment Management...Thank You");
				break;

			default:
				System.out.println("Invalid choice! Try again.");
			}

		} while (choice != 0);

		sc.close();
	}
}
