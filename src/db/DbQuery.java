package db;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.sql.PreparedStatement;

import db.ConnectionUtils;

public class DbQuery {

	// ACCOUNT
	public boolean isUsernameExist(ConnectionUtils myConnection, String name)
			throws SQLException, ClassNotFoundException {
		String sql = "SELECT * FROM ACCOUNT WHERE USERNAME = '" + name + "'";
		ResultSet resultSet = myConnection.executeResultSetSt(sql);
		if (resultSet.next()) {
			System.out.println("User name exists");
			return true;
		}
		return false;
	}

	public boolean isPasswordCorrect(ConnectionUtils myConnection, String name, String password)
			throws SQLException, ClassNotFoundException {
		String sql = "SELECT * FROM ACCOUNT WHERE USERNAME = '" + name + "' AND PASSWORD = '" + password + "'";
		ResultSet resultSet = myConnection.executeResultSetSt(sql);
		if (resultSet.next()) {
			System.out.println("Password incorrect");
			return true;
		}
		return false;
	}

	public boolean createAccount(ConnectionUtils myConnection, String username, String password)
			throws SQLException, ClassNotFoundException {
		String sql = "INSERT INTO ACCOUNT(username, password) VALUES ('" + username + "', '" + password + "')";
		if (myConnection.executeUpdateSt(sql) > 0)
			return true;
		return false;
	}

	// ROOM
	public boolean isRoomExist(ConnectionUtils myConnection, String roomName)
			throws SQLException, ClassNotFoundException {
		String sql = "SELECT * FROM ROOM WHERE ROOM_NAME = '" + roomName + "'";
		ResultSet resultSet = myConnection.executeResultSetSt(sql);
		if (resultSet.next()) {
			System.out.println("Room ID exists");
			return true;
		}
		return false;
	}

	public boolean createRoom(ConnectionUtils myConnection, String roomName, String password, int hostId)
			throws SQLException, ClassNotFoundException {
		String sql = "INSERT INTO ROOM (room_name, password, host_id) VALUES ('" + roomName + "', '" + password + "', '"
				+ hostId + "')";
		if (myConnection.executeUpdateSt(sql) > 0)
			return true;
		return false;
	}

	public boolean setRoomTopic(ConnectionUtils myConnection, int roomId, int topicId)
			throws SQLException, ClassNotFoundException {
		String sql = "UPDATE room SET topic_id = " + topicId + "WHERE room_id = " + roomId;
		if (myConnection.executeUpdateSt(sql) > 0)
			return true;
		return false;
	}

	public static ResultSet getRoomList(ConnectionUtils myConnection) throws SQLException, ClassNotFoundException {
		String sql = "SELECT * FROM ROOM";
//		ResultSet rs = myConnection.executeResultSetSt(sql);
//		while (rs.next()) {
//	          String roomName = rs.getString("room_name");
//	          int roomHostId = rs.getInt("host_id");
//	          Boolean password;
//	          if (rs.getString("password") == null) password = false;
//	          else password = true;
//	          System.out.println("--------------------");
//	          System.out.println("Room Name:" + roomName);
//	          System.out.println("Room Host:" + roomHostId);
//	          System.out.println("Password:" + password.toString());
//	     }
//		return rs;
		return myConnection.executeResultSetSt(sql);
	}
	
	public boolean deleteRoom(ConnectionUtils myConnection, int roomId)
			throws SQLException, ClassNotFoundException {
		String sql = "DELETE room WHERE room_id = " + roomId;
		if (myConnection.executeUpdateSt(sql) > 0)
			return true;
		return false;
	}
	

	// TOPIC
	public boolean createTopic(ConnectionUtils myConnection, String topicName, int ownerId)
			throws SQLException, ClassNotFoundException {
		String sql = "INSERT INTO TOPIC (topic_name, owner_id) VALUES ('" + topicName + "', '" + ownerId + "')";
		if (myConnection.executeUpdateSt(sql) > 0)
			return true;
		return false;
	}

	public ResultSet getTopic(ConnectionUtils myConnection, String topicName)
			throws SQLException, ClassNotFoundException {
		String sql = "SELECT * FROM TOPIC WHERE topic_name = " + topicName;
		return myConnection.executeResultSetSt(sql);
	}

	
	// QUESTION
	public boolean createQuestion(ConnectionUtils myConnection, String question, String optionA, String optionB,
			String optionC, String optionD, String answer, int topicId) throws SQLException, ClassNotFoundException {
		String sql = "INSERT INTO Question (question, option1, option2, option3, option4, answer, topic_id)"
				+ "VALUES( ?, ?, ?, ?, ?, ?, ?)";
		try {
			PreparedStatement pst = myConnection.getMyConnection().prepareStatement(sql);
			pst.setString(1, question);
			pst.setString(2, optionA);
			pst.setString(3, optionB);
			pst.setString(4, optionC);
			pst.setString(5, optionD);
			pst.setString(6, answer);
			pst.setInt(7, topicId);
			if (pst.executeUpdate() > 0) {
				return true;
			} else
				return false;
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage() + ".My query");
			return false;
		}

	}

	public ResultSet getQuestionListWithTopic(ConnectionUtils myConnection, int topicId)
			throws SQLException, ClassNotFoundException {
		String sql = "SELECT * FROM QUESTION WHERE topic_id = " + topicId;
//		ResultSet rs = myConnection.executeResultSetSt(sql);
//		while (rs.next()) {
//			String question = rs.getString("question");
//			String option[] = null;
//			for (int i = 1; i <= 4; i++) {
//				option[i] = rs.getString("option" + i);
//			}
//			String answer = rs.getString("correct_answer");
//
//			System.out.println("--------------------");
//			System.out.println("Question:" + question);
//			for (int i = 1; i <= 4; i++) {
//				System.out.println("Option" + i + ": " + option[i]);
//			}
//			System.out.println("Answer:" + answer);
//		}
//		return rs;
		return myConnection.executeResultSetSt(sql);
	}

	
	// ANSWER
	public boolean createPlayerAnswer(ConnectionUtils myConnection, int quesID, int roomId, int playerId, String choice)
			throws SQLException, ClassNotFoundException {
		String sql = "INSERT INTO ANSWER (ques_id, room_id, player_id, choice, score)" + "VALUES( ?, ?, ?, ?, ?)";
		try {
			PreparedStatement pst = myConnection.getMyConnection().prepareStatement(sql);
			pst.setInt(1, quesID);
			pst.setInt(2, roomId);
			pst.setInt(3, playerId);
			pst.setString(4, choice);
			pst.setInt(5, 0);
			if (pst.executeUpdate() > 0) {
				return true;
			} else
				return false;
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage() + ".My query");
			return false;
		}
	}

	public boolean updatePlayerScore(ConnectionUtils myConnection, int quesId, int roomId, int score)
			throws SQLException, ClassNotFoundException {
		String sql = "SELECT * FROM QUESTION WHERE ques_id = " + quesId;
		ResultSet rs = myConnection.executeResultSetSt(sql);
		
		String answer = rs.getString("correct_answer");
		String sql1 = "UPDATE ANSWER SET score = " + 10 + "WHERE ques_id= " + quesId + "AND room_id = " + roomId
				+ "AND answer = " + answer;
		if (myConnection.executeUpdateSt(sql1) > 0)
			return true;
		return false;
	}
	
	public ResultSet getRoomScore(ConnectionUtils myConnection, int roomId)
			throws SQLException, ClassNotFoundException {
		String sql = "SELECT player_id, SUM(Score) FROM ANSWER WHERE room_id = " + roomId + "GROUP BY player_id";
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