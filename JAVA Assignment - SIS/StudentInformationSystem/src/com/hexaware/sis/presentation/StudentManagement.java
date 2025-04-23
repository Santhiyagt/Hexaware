package com.hexaware.sis.presentation;

import com.hexaware.sis.dao.IStudentDao;
import com.hexaware.sis.dao.StudentDaoImpl;
import com.hexaware.sis.entity.Student;
import com.hexaware.sis.exceptions.StudentNotFoundException;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class StudentManagement {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		IStudentDao dao = new StudentDaoImpl();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		while (true) {
			System.out.println("\n--- Student Management ---");
			System.out.println("1. Add Student");
			System.out.println("2. Update Student");
			System.out.println("3. Delete Student");
			System.out.println("4. Get Student by ID");
			System.out.println("5. List All Students");
			System.out.println("6. Get Students by Course Name");
			System.out.println("7. Exit");
			System.out.print("Enter your choice: ");
			int choice = sc.nextInt();
			sc.nextLine();

			try {
				switch (choice) {
				case 1:
					System.out.print("Enter Student ID: ");
					int studentId = sc.nextInt();
					sc.nextLine();
					System.out.print("Enter First Name: ");
					String firstName = sc.nextLine();
					System.out.print("Enter Last Name: ");
					String lastName = sc.nextLine();
					System.out.print("Enter Date of Birth (yyyy-MM-dd): ");
					String dobStr = sc.nextLine();
					Date dob = sdf.parse(dobStr);
					System.out.print("Enter Email: ");
					String email = sc.nextLine();
					System.out.print("Enter Phone Number: ");
					String phone = sc.nextLine();

					Student student = new Student(studentId, firstName, lastName, dob, email, phone);
					int addResult = dao.addStudent(student);
					System.out.println(addResult > 0 ? "Student added successfully." : "Failed to add student.");
					break;

				case 2:
					System.out.print("Enter Student ID to update: ");
					int updateId = sc.nextInt();
					sc.nextLine();
					System.out.print("Enter New First Name: ");
					String newFirstName = sc.nextLine();
					System.out.print("Enter New Last Name: ");
					String newLastName = sc.nextLine();
					System.out.print("Enter New Date of Birth (yyyy-MM-dd): ");
					String newDobStr = sc.nextLine();
					Date newDob = sdf.parse(newDobStr);
					System.out.print("Enter New Email: ");
					String newEmail = sc.nextLine();
					System.out.print("Enter New Phone Number: ");
					String newPhone = sc.nextLine();

					Student updatedStudent = new Student(updateId, newFirstName, newLastName, newDob, newEmail,
							newPhone);
					int updateResult = dao.updateStudent(updatedStudent);
					System.out
							.println(updateResult > 0 ? "Student updated successfully." : "Failed to update student.");
					break;

				case 3:
					System.out.print("Enter Student ID to delete: ");
					int deleteId = sc.nextInt();
					int deleteResult = dao.deleteStudent(deleteId);
					System.out.println(deleteResult > 0 ? "Student deleted." : "Student not found.");
					break;

				case 4:
					System.out.print("Enter Student ID to view: ");
					int searchId = sc.nextInt();
					Student foundStudent = dao.getStudentById(searchId);
					System.out.println(foundStudent);
					break;

				case 5:
					List<Student> allStudents = dao.getAllStudents();
					if (allStudents.isEmpty()) {
						System.out.println("No students found.");
					} else {
						allStudents.forEach(System.out::println);
					}
					break;

				case 6:
					System.out.print("Enter Course Name: ");
					String courseName = sc.nextLine();
					List<Student> studentsByCourse = dao.getStudentsByCourse(courseName);
					if (studentsByCourse.isEmpty()) {
						System.out.println("No students found for the course.");
					} else {
						studentsByCourse.forEach(System.out::println);
					}
					break;

				case 7:
					System.out.println("Exiting Student Management...Thank You");
					sc.close();
					System.exit(0);
					break;

				default:
					System.out.println("Invalid choice! Try again");
					break;
				}
			} catch (StudentNotFoundException e) {
				System.out.println(e.getMessage());
			} catch (Exception e) {
				System.out.println("Error: " + e.getMessage());
			}
		}
	}
}
