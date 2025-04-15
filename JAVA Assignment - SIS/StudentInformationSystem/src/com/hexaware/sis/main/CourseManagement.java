package com.hexaware.sis.main;

import com.hexaware.sis.dao.CourseDaoImpl;
import com.hexaware.sis.entity.Course;
import com.hexaware.sis.exceptions.CourseNotFoundException;

import java.util.List;
import java.util.Scanner;

public class CourseManagement {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		CourseDaoImpl dao = new CourseDaoImpl();
		int choice;

		do {
			System.out.println("\n--- Course Management ---");
			System.out.println("1. Add Course");
			System.out.println("2. Update Course");
			System.out.println("3. Delete Course");
			System.out.println("4. View Course by ID");
			System.out.println("5. View All Courses");
			System.out.println("6. View Courses by Teacher ID");
			System.out.println("0. Exit");
			System.out.print("Enter your choice: ");
			choice = sc.nextInt();
			sc.nextLine();

			switch (choice) {
			case 1:
				System.out.print("Enter Course ID: ");
				int cid1 = sc.nextInt();
				sc.nextLine();

				System.out.print("Enter Course Name: ");
				String cname1 = sc.nextLine();

				System.out.print("Enter Credits: ");
				int credits1 = sc.nextInt();

				System.out.print("Enter Teacher ID: ");
				int tid1 = sc.nextInt();

				Course newCourse = new Course(cid1, cname1, credits1, tid1);
				int added = dao.addCourse(newCourse);
				System.out.println("Course Added: " + (added > 0));
				break;

			case 2:
				System.out.print("Enter Course ID to Update: ");
				int cid2 = sc.nextInt();
				sc.nextLine();

				System.out.print("Enter New Course Name: ");
				String cname2 = sc.nextLine();

				System.out.print("Enter New Credits: ");
				int credits2 = sc.nextInt();

				System.out.print("Enter New Teacher ID: ");
				int tid2 = sc.nextInt();

				Course updateCourse = new Course(cid2, cname2, credits2, tid2);
				int updated = dao.updateCourse(updateCourse);
				System.out.println("Course Updated: " + (updated > 0));
				break;

			case 3:
				System.out.print("Enter Course ID to Delete: ");
				int cid3 = sc.nextInt();
				int deleted = dao.deleteCourse(cid3);
				System.out.println("Course Deleted: " + (deleted > 0));
				break;

			case 4:
				System.out.print("Enter Course ID to View: ");
				int cid4 = sc.nextInt();
				try {
					Course course = dao.getCourseById(cid4);
					System.out.println(course);
				} catch (CourseNotFoundException e) {
					System.out.println(e.getMessage());
				}
				break;

			case 5:
				List<Course> allCourses = dao.getAllCourses();
				if (allCourses.isEmpty()) {
					System.out.println("No courses found.");
				} else {
					System.out.println("All Courses:");
					allCourses.forEach(System.out::println);
				}
				break;

			case 6:
				System.out.print("Enter Teacher ID: ");
				int tid6 = sc.nextInt();
				List<Course> teacherCourses = dao.getCoursesByTeacherId(tid6);
				if (teacherCourses.isEmpty()) {
					System.out.println("No courses found for Teacher ID: " + tid6);
				} else {
					System.out.println("Courses taught by Teacher " + tid6 + ":");
					teacherCourses.forEach(System.out::println);
				}
				break;

			case 0:
				System.out.println("Exiting Course Management...Thank You");
				break;

			default:
				System.out.println("Invalid choice! Try again");
			}

		} while (choice != 0);

		sc.close();
	}
}
