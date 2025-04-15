package com.hexaware.sis.dao;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DbUtil {

	public static Connection getDBConnection() throws SQLException {

		FileReader fr = null;
		Properties prop = null;
		try {
			fr = new FileReader("src/db.properties");

			prop = new Properties();

			prop.load(fr);
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}

		String url = prop.getProperty("url");

		String username = prop.getProperty("username");

		String password = prop.getProperty("password");

		DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());

		Connection conn = DriverManager.getConnection(url, username, password);

		return conn;

	}

}
