package com.backend.ServiceImpl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.backend.Entity.DatabaseRequest;

@Service
public class DatabaseService {

	private static final String MYSQL_URL = "jdbc:mysql://162.240.158.75:3306/";
	private static final String SOURCE_DB = "fuma_test1";

	public boolean createDatabase(DatabaseRequest request) {
		String dbName = request.getDbName();
		String dbUsername = request.getDbUsername();
		String dbPassword = request.getDbPassword();

		Connection rootConnection = null;
		try {
			// 1. Load MySQL JDBC Driver
			Class.forName("com.mysql.cj.jdbc.Driver");

			// 2. Create root connection (no specific database)
			rootConnection = DriverManager.getConnection(MYSQL_URL, dbUsername, dbPassword);

			// 3. Create the new database
			try (Statement statement = rootConnection.createStatement()) {
				statement.executeUpdate("CREATE DATABASE IF NOT EXISTS " + dbName);
			}

			// 4. Copy only table structures from erp_solution to new database
			return copyDatabaseStructure(dbUsername, dbPassword, dbName);

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			if (rootConnection != null) {
				try {
					rootConnection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private boolean copyDatabaseStructure(String username, String password, String targetDbName) {
		Connection sourceConn = null;
		Connection targetConn = null;
		try {
			// 1. Connect to source and target databases
			sourceConn = DriverManager.getConnection(MYSQL_URL + SOURCE_DB, username, password);
			targetConn = DriverManager.getConnection(MYSQL_URL + targetDbName, username, password);

			// 2. Disable foreign key checks temporarily
			try (Statement stmt = targetConn.createStatement()) {
				stmt.execute("SET FOREIGN_KEY_CHECKS=0");
			}

			// 3. Get all table names from source database
			List<String> tables = getTableNames(sourceConn);

			// 4. Copy each table structure without data
			for (String table : tables) {
				if (!copyTableStructure(sourceConn, targetConn, table)) {
					return false;
				}
			}

			// 5. Re-enable foreign key checks
			try (Statement stmt = targetConn.createStatement()) {
				stmt.execute("SET FOREIGN_KEY_CHECKS=1");
			}

			return true;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				if (sourceConn != null)
					sourceConn.close();
				if (targetConn != null)
					targetConn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	private List<String> getTableNames(Connection conn) throws SQLException {
		List<String> tables = new ArrayList<>();
		try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery("SHOW TABLES")) {
			while (rs.next()) {
				tables.add(rs.getString(1));
			}
		}
		return tables;
	}

	private boolean copyTableStructure(Connection source, Connection target, String tableName) {
		try (Statement stmt = source.createStatement();
				ResultSet rs = stmt.executeQuery("SHOW CREATE TABLE " + tableName)) {

			if (rs.next()) {
				String createSql = rs.getString(2);
				// Execute in target database
				try (Statement targetStmt = target.createStatement()) {
					targetStmt.executeUpdate(createSql);
					return true;
				}
			}
			return false;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
}