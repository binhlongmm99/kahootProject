package db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;

import db.ConnectionUtils;

public class DbQuery {

	public boolean isUsernameExist(ConnectionUtils myConnection, String name)
			throws SQLException, ClassNotFoundException {
		String sql = "SELECT * FROM REGISTRATION WHERE USERNAME = '" + name + "'";
		ResultSet resultSet = myConnection.executeResultSetSt(sql);
		if (resultSet.next()) {
			System.out.println("User name exists");
			return true;
		}
		return false;
	}

	public boolean isPasswordCorrect(ConnectionUtils myConnection, String name, String password)
			throws SQLException, ClassNotFoundException {
		String sql = "SELECT * FROM REGISTRATION WHERE USERNAME = '" + name + "' AND PASSWORD = '" + password + "'";
		ResultSet resultSet = myConnection.executeResultSetSt(sql);
		if (resultSet.next()) {
			System.out.println("Password incorrect");
			return true;
		}
		return false;

	}

	public boolean isRoomExist(ConnectionUtils myConnection, String roomName) throws SQLException, ClassNotFoundException {
		String sql = "SELECT * FROM ROOM WHERE ROOM_NAME = '" + roomName + "'";
		ResultSet resultSet = myConnection.executeResultSetSt(sql);
		if (resultSet.next()) {
			System.out.println("Room ID exists");
			return true;
		}
		return false;
	}

	public boolean createRoomDb(ConnectionUtils myConnection, int hostId, int roomName, String password)
			throws SQLException, ClassNotFoundException {
		String sql = "INSERT INTO ROOM WHERE HOST = '" + hostId + "' AND roomName = '" + roomName +
				"' AND PASSWORD = '" + password + "'";
		if (myConnection.executeUpdateSt(sql) > 0) 
			return true;
		return false;
	}

	public boolean createUsernameDb(ConnectionUtils myConnection, String name, String password)
			throws SQLException, ClassNotFoundException {
		String sql = "INSERT INTO REGISTRATION WHERE USERNAME = '" + name + "' AND PASSWORD = '" + password + "'";
		if (myConnection.executeUpdateSt(sql) > 0) 
			return true;
		return false;
	}

	
	public boolean createQuestionDb(ConnectionUtils myConnection, String question, String optionA, String optionB,
			String optionC, String optionD, String answer) throws SQLException, ClassNotFoundException {
		String sql = "INSERT INTO Question WHERE question = ? AND choice1 = ? AND choice2 = ? AND choice3 = ?"
				+ "AND choice4 = ? AND answer = ?";
		try {
			PreparedStatement pst = myConnection.getMyConnection().prepareStatement(sql);
			pst.setString(1, question);
			pst.setString(2, optionA);
			pst.setString(3, optionB);
			pst.setString(4, optionC);
			pst.setString(5, optionD);
			pst.setString(6, answer);
			if (pst.executeUpdate() > 0) {
				return true;
			} else
				return false;
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage() + ".My query");
			return false;
		}

	}
	
	public static ResultSet getRoomList(ConnectionUtils myConnection) throws SQLException, ClassNotFoundException {
		String sql = "SELECT * FROM ROOM";	
//		ResultSet rs = myConnection.executeResultSetSt(sql);
//		while (rs.next()) {
//	          String roomName = rs.getString("room_name");
//	          String roomHost = rs.getString("host");
//	          Boolean password;
//	          if (rs.getString("password") == null) password = false;
//	          else password = true;
//	          System.out.println("--------------------");
//	          System.out.println("Room Name:" + roomName);
//	          System.out.println("Room Host:" + roomHost);
//	          System.out.println("Password:" + password.toString());
//	     }
//		return rs;
		return myConnection.executeResultSetSt(sql);
	}

	
	
	
//	public static void main(String[] args) throws SQLException, ClassNotFoundException {
//        try {
//            ConnectionUtils myConnection = new ConnectionUtils();           
//        } catch (Exception e) {
//            throw new SQLException("Error: " + e.getMessage() + ".My query");
//        }
//    }

}