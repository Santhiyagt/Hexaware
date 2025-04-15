package com.hexaware.sis.main;

import com.hexaware.sis.dao.EnrollmentDaoImpl;
import com.hexaware.sis.dao.IEnrollmentDao;
import com.hexaware.sis.entity.Enrollment;
import com.hexaware.sis.exceptions.InvalidEnrollmentDataException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class EnrollmentManagement {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		IEnrollmentDao enrollmentDao = new EnrollmentDaoImpl();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		int choice;
		do {
			System.out.println("\n--- Enrollment Management ---");
			System.out.println("1. Enroll Student");
			System.out.println("2. Update Enrollment");
			System.out.println("3. Delete Enrollment");
			System.out.println("4. Get Enrollment by ID");
			System.out.println("5. Get All Enrollments");
			System.out.println("6. Get Enrollments by Student ID");
			System.out.println("7. Get Enrollments by Course ID");
			System.out.println("0. Exit");
			System.out.print("Enter choice: ");
			choice = sc.nextInt();

			switch (choice) {
			case 1:
				try {
					System.out.print("Enter Enrollment ID: ");
					int eid = sc.nextInt();
					System.out.print("Enter Student ID: ");
					int sid = sc.nextInt();
					System.out.print("Enter Course ID: ");
					int cid = sc.nextInt();
					System.out.print("Enter Enrollment Date (yyyy-MM-dd): ");
					Date date = sdf.parse(sc.next());

					Enrollment newEnroll = new Enrollment(eid, sid, cid, date);
					int rows = enrollmentDao.enrollStudent(newEnroll);
					System.out.println(rows + " record(s) inserted.");
				} catch (ParseException e) {
					System.out.println("Invalid date format.");
				}
				break;

			case 2:
				try {
					System.out.print("Enter Enrollment ID to update: ");
					int eid = sc.nextInt();
					System.out.print("Enter new Student ID: ");
					int sid = sc.nextInt();
					System.out.print("Enter new Course ID: ");
					int cid = sc.nextInt();
					System.out.print("Enter new Enrollment Date (yyyy-MM-dd): ");
					Date date = sdf.parse(sc.next());

					Enrollment updated = new Enrollment(eid, sid, cid, date);
					int rows = enrollmentDao.updateEnrollment(updated);
					System.out.println(rows + " record(s) updated.");
				} catch (ParseException e) {
					System.out.println("Invalid date format.");
				}
				break;

			case 3:
				System.out.print("Enter Enrollment ID to delete: ");
				int delId = sc.nextInt();
				int deleted = enrollmentDao.deleteEnrollment(delId);
				System.out.println(deleted + " record(s) deleted.");
				break;

			case 4:
				System.out.print("Enter Enrollment ID to fetch: ");
				int fetchId = sc.nextInt();
				try {
					Enrollment enrollment = enrollmentDao.getEnrollmentById(fetchId);
					System.out.println(enrollment);
				} catch (InvalidEnrollmentDataException e) {
					System.out.println(e.getMessage());
				}
				break;

			case 5:
				List<Enrollment> all = enrollmentDao.getAllEnrollments();
				all.forEach(System.out::println);
				break;

			case 6:
				System.out.print("Enter Student ID: ");
				int studentId = sc.nextInt();
				List<Enrollment> byStudent = enrollmentDao.getEnrollmentsByStudentId(studentId);
				byStudent.forEach(System.out::println);
				break;

			case 7:
				System.out.print("Enter Course ID: ");
				int courseId = sc.nextInt();
				List<Enrollment> byCourse = enrollmentDao.getEnrollmentsByCourseId(courseId);
				byCourse.forEach(System.out::println);
				break;

			case 0:
				System.out.println("Exiting Enrollment Management...Thank You");
				break;

			default:
				System.out.println("Invalid choice! Try again");
			}
		} while (choice != 0);
		sc.close();
	}
}
