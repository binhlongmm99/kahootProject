package db;

import java.sql.ResultSet;
import java.sql.SQLException;

import db.ConnectionUtils;

public class DbQuery {

	public boolean isUsernameExist(ConnectionUtils myConnection, String name)
			throws SQLException, ClassNotFoundException {
		String sql = "SELECT * FROM REGISTRATION WHERE USERNAME = " + name;
		ResultSet resultSet = myConnection.executeStaResultSet(sql);
		if (resultSet.next()) {
			System.out.println("User name exists");
			return true;
		}
		return false;
	}

	public boolean isPasswordCorrect(ConnectionUtils myConnection, String name, String password)
			throws SQLException, ClassNotFoundException {
		String sql = "SELECT * FROM REGISTRATION WHERE USERNAME = " + name + " AND PASSWORD = " + password;
		ResultSet resultSet = myConnection.executeStaResultSet(sql);
		if (resultSet.next()) {
			return true;
		}
		return false;

	}

	public boolean isRoomExist(ConnectionUtils myConnection, int roomId) throws SQLException, ClassNotFoundException {
		String sql = "SELECT * FROM ROOM WHERE ROOM_ID = " + roomId;
		ResultSet resultSet = myConnection.executeStaResultSet(sql);
		if (resultSet.next()) {
			System.out.println("Room ID exists");
			return true;
		}
		return false;
	}

	public void createRoomDb(ConnectionUtils myConnection, String host, String password)
			throws SQLException, ClassNotFoundException {
		String sql = "INSERT INTO ROOM WHERE HOST = " + host + "AND PASSWORD = " + password;
		myConnection.executeStaResultSet(sql);
	}

	public void createUsernameDb(ConnectionUtils myConnection, String name, String password)
			throws SQLException, ClassNotFoundException {
		String sql = "INSERT INTO REGISTRATION WHERE USERNAME = " + name + "AND PASSWORD = " + password;
		myConnection.executeStaResultSet(sql);
	}

	public void createQuestionDb(ConnectionUtils myConnection, String question, String optionA, String optionB,
			String optionC, String optionD) throws SQLException, ClassNotFoundException {
		String sql = "Insert into Question WHERE question = " + question + " AND choice1 = " + optionA
				+ " AND choice2 = " + optionB + " AND choice3 = " + optionC + " AND choice4 = " + optionD;
		myConnection.executeStaResultSet(sql);
	}

//	public static void main(String[] args) throws SQLException, ClassNotFoundException {
//        try {
//            ConnectionUtils myConnection = new ConnectionUtils();           
//        } catch (Exception e) {
//            throw new SQLException("Error: " + e.getMessage() + ".My query");
//        }
//    }
	
}
