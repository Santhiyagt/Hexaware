package com.hexaware.sis.main;

import java.sql.Connection;
import com.hexaware.sis.dao.DbUtil;

public class TestDb {
	public static void main(String[] args) {
		try {
			Connection conn = DbUtil.getDBConnection();
			if (conn != null) {
				System.out.println("Database connected successfully!");
			} else {
				System.out.println("Connection failed.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
