package com.hexaware.sis.presentation;

import com.hexaware.sis.dao.ITeacherDao;
import com.hexaware.sis.dao.TeacherDaoImpl;
import com.hexaware.sis.entity.Teacher;
import com.hexaware.sis.exceptions.TeacherNotFoundException;

import java.util.List;
import java.util.Scanner;

public class TeacherManagement {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		ITeacherDao dao = new TeacherDaoImpl();

		while (true) {
			System.out.println("\n--- Teacher Management ---");
			System.out.println("1. Add Teacher");
			System.out.println("2. Update Teacher");
			System.out.println("3. Delete Teacher");
			System.out.println("4. Get Teacher by ID");
			System.out.println("5. List All Teachers");
			System.out.println("6. Exit");
			System.out.print("Enter your choice: ");
			int choice = sc.nextInt();
			sc.nextLine();

			switch (choice) {
			case 1:
				System.out.print("Enter Teacher ID: ");
				int id = sc.nextInt();
				sc.nextLine();
				System.out.print("Enter First Name: ");
				String firstName = sc.nextLine();
				System.out.print("Enter Last Name: ");
				String lastName = sc.nextLine();
				System.out.print("Enter Email: ");
				String email = sc.nextLine();

				Teacher t = new Teacher(id, firstName, lastName, email);
				int result = dao.addTeacher(t);
				System.out.println(result > 0 ? "Teacher added successfully." : "Failed to add teacher.");
				break;

			case 2:
				System.out.print("Enter Teacher ID to update: ");
				int upId = sc.nextInt();
				sc.nextLine();
				System.out.print("Enter New First Name: ");
				String newFirstName = sc.nextLine();
				System.out.print("Enter New Last Name: ");
				String newLastName = sc.nextLine();
				System.out.print("Enter New Email: ");
				String newEmail = sc.nextLine();

				Teacher updated = new Teacher(upId, newFirstName, newLastName, newEmail);
				int upResult = dao.updateTeacher(updated);
				System.out.println(upResult > 0 ? "Teacher updated successfully." : "Failed to update teacher.");
				break;

			case 3:
				System.out.print("Enter Teacher ID to delete: ");
				int delId = sc.nextInt();
				int delResult = dao.deleteTeacher(delId);
				System.out.println(delResult > 0 ? "Teacher deleted." : "Teacher not found.");
				break;

			case 4:
				System.out.print("Enter Teacher ID to view: ");
				int searchId = sc.nextInt();
				try {
					Teacher found = dao.getTeacherById(searchId);
					System.out.println(found);
				} catch (TeacherNotFoundException e) {
					System.out.println(e.getMessage());
				}
				break;

			case 5:
				List<Teacher> all = dao.getAllTeachers();
				if (all.isEmpty()) {
					System.out.println("No teachers found.");
				} else {
					all.forEach(System.out::println);
				}
				break;

			case 6:
				System.out.println("Exiting Teacher Management...Thank You");
				sc.close();
				System.exit(0);

			default:
				System.out.println("Invalid choice! Try again");
			}
		}
	}
}
