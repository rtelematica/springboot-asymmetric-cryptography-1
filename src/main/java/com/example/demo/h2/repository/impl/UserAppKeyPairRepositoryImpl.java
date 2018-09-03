package com.example.demo.h2.repository.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.annotation.PostConstruct;

import com.example.demo.h2.repository.IUserAppKeyPairRepository;
import com.example.demo.h2.repository.entity.UserAppKeyPair;

//@Component
public abstract class UserAppKeyPairRepositoryImpl implements IUserAppKeyPairRepository {

	static final String JDBC_DRIVER = "org.h2.Driver";
	static final String DB_URL = "jdbc:h2:~/test";

	static final String USER = "sa";
	static final String PASS = "";

	@PostConstruct
	public void createTable() {
		Connection conn = null;
		Statement stmt = null;

		try {
			Class.forName(JDBC_DRIVER);

			System.out.println("Connecting to a selected database...");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			System.out.println("Connected database successfully...");

			System.out.println("Creating table in given database...");
			stmt = conn.createStatement();

			String sql = "CREATE TABLE IF NOT EXISTS USER_APP_KEY " + "(app_id VARCHAR(255) not NULL, "
					+ " public_key VARCHAR(MAX), " + " private_key VARCHAR(MAX)) ";

			stmt.executeUpdate(sql);
			System.out.println("Created table in given database...");

		} catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
		System.out.println("H2 in memory database Setup finished!");
	}

	public UserAppKeyPair findOne(String appId) {
		Connection conn = null;
		Statement stmt = null;

		UserAppKeyPair.UserAppKeyPairBuilder builder = UserAppKeyPair.builder();
		UserAppKeyPair userAppKeyPair = null;

		try {

			Class.forName(JDBC_DRIVER);

			System.out.println("Connecting to a selected database...");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			System.out.println("Connected database successfully...");

			System.out.println("Reading record from the table...");
			stmt = conn.createStatement();

			// if appid exist
			String sql = "SELECT * FROM USER_APP_KEY WHERE APP_ID='" + appId + "'";
			ResultSet resultSet = stmt.executeQuery(sql);

			if (resultSet.next()) {
				userAppKeyPair = builder.privateKey(resultSet.getString("PRIVATE_KEY"))
						.publicKey(resultSet.getString("PUBLIC_KEY")).appId(resultSet.getString("APP_ID")).build();

			}
			resultSet.close();
		} catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {

			}
		}

		return userAppKeyPair;
	}

	public UserAppKeyPair insert(UserAppKeyPair userAppKeyPair) {
		Connection conn = null;
		Statement stmt = null;

		try {
			Class.forName(JDBC_DRIVER);

			System.out.println("Connecting to a selected database...");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			System.out.println("Connected database successfully...");

			System.out.println("Inserting records into the table...");
			stmt = conn.createStatement();

			String sql = "SELECT APP_ID FROM USER_APP_KEY WHERE APP_ID='" + userAppKeyPair.getAppId() + "'";
			ResultSet resultSet = stmt.executeQuery(sql);
			if (!resultSet.isBeforeFirst()) {
				stmt.executeUpdate("INSERT INTO USER_APP_KEY " + "VALUES ('" + userAppKeyPair.getAppId() + "', '"
						+ userAppKeyPair.getPublicKey() + "', '" + userAppKeyPair.getPrivateKey() + "')");
				resultSet.close();
			} else {
				stmt.executeUpdate("UPDATE USER_APP_KEY SET PUBLIC_KEY='" + userAppKeyPair.getPublicKey()
						+ "', PRIVATE_KEY='" + userAppKeyPair.getPrivateKey() + "' WHERE APP_ID='"
						+ userAppKeyPair.getAppId() + "' ");
				resultSet.close();
			}
			System.out.println("Inserted records into the table...");

		} catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}

		return userAppKeyPair;
	}

	public UserAppKeyPair delete(String appId) {
		UserAppKeyPair userKeyPair = this.findOne(appId);
		if (userKeyPair != null)
			this.delete(userKeyPair);
		return userKeyPair;
	}

	public void delete(UserAppKeyPair userAppKeyPair) {
		// TODO: Delete UserAppKeyPair
		// continue here ...

		throw new UnsupportedOperationException("Not implemented yet.");
	}
}
