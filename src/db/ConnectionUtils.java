package db;

import java.sql.*;

public class ConnectionUtils {
	private static Connection conn = null;
	private static Statement statement = null;
	private static ResultSet resultSet = null;

	public static Connection getMyConnection() throws SQLException, ClassNotFoundException {
		try {
			conn = MySQLConnUtils.getMySQLConnection();
		} catch (Exception e) {
			throw new SQLException(e.getMessage());
		}
		return conn;
	}

	public Statement getStatement() throws SQLException, ClassNotFoundException {
		if (statement == null ? true : statement.isClosed()) {
			statement = getMyConnection().createStatement(resultSet.TYPE_SCROLL_INSENSITIVE,
					resultSet.CONCUR_READ_ONLY);
			System.out.println(statement.toString());
		}
		return statement;
	}

	public ResultSet executeStaResultSet(String query) throws SQLException, ClassNotFoundException {
		try {
			resultSet = getStatement().executeQuery(query);
		} catch (Exception e) {
			throw new SQLException("Error: " + e.getMessage() + ".My query" + query);
		}
		return resultSet;
	}

	public static void main(String[] args) throws SQLException, ClassNotFoundException {

		System.out.println("Get connection ... ");

		conn = ConnectionUtils.getMyConnection();
		System.out.println("Get connection " + conn);
		System.out.println("Done!");
	}

}