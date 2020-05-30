package server;
import java.io.*; 
import java.util.*;

import db.ConnectionUtils;

import java.net.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClientHandler implements Runnable  
{ 
	Scanner scn = new Scanner(System.in); 
	private String name; 
	private String roomId;
	final DataInputStream dis; 
	final DataOutputStream dos; 
	boolean isGameStarted = false;
	Socket s; 
	boolean isloggedin; 
	boolean isHost = false;
	ConnectionUtils myConnection;

	// constructor 
	public ClientHandler(Socket s, String name, 
			DataInputStream dis, DataOutputStream dos, ConnectionUtils myConnection) { 
		this.dis = dis; 
		this.dos = dos; 
		this.name = name; 
		this.s = s; 
		this.myConnection = myConnection;

		//        this.isloggedin=true; 
	} 

	@Override
	public void run() { 
		String received; 
		int count = 0;


		while (true)  
		{
			try {

				received = dis.readUTF();
				String[] parts = received.split("-");
				if (!received.contains("WH"))
					System.out.println(received); 
				switch(parts[0]) {
				case "GS":
					getScore(received);break;
				case "CSc":
					createScore(received);break;
				case "US":
					updateScore(received);break;
				case "CT": 
					createTopic(received);break;
				case "CS":
					closeSocket();break;
				case "CN":
					createName(received);break;
				case "LI":
					login(received);break;
				case "CR":
					createRoom(received);break;
				case "CQ":
					createQuestion(received);break;
				case "CO":
					chooseOption(received);break;
				case "SG":
					
					for (ClientHandler mc : Server.ar) {
						if (mc.getRoomId().equals(this.roomId) && mc.isHost == false)
							mc.startGame();

					}
					break;
				case "JR":
					joinRoom(received);break;
				case "ER":
					exitRoom(received);break;
				case "GQ":
					getQuestion(received);break;
				case "RL":
					try {
						getRoomList();
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}break;
				case "TL": 
					getTopicList(received);break;
				case "WH":
					waitHost();break;
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				//				e.printStackTrace();
				this.isloggedin=false; 
				Iterator<ClientHandler> ite = Server.ar.iterator();
				while (ite.hasNext()) {
					if (ite.next().equals(this)) {
						if (this.isHost == true) {
							try {
								deleteRoom(this.roomId);
							} catch (ClassNotFoundException | SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
						ite.remove();
						System.out.println("Socket is closed");
					}
				} 
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		} 
	}

	private boolean deleteRoom(String roomId) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		String sql = "DELETE FROM ROOM WHERE room_name = '" + roomId + "'";
		if (myConnection.executeUpdateSt(sql) > 0) {
			
			return true;
		}
		System.out.println("Room deleted");	
		return false;
	}

	private void getScore(String received) throws ClassNotFoundException, IOException, SQLException {
		// TODO Auto-generated method stub
		String[] parts = received.split("-");
		dos.writeUTF(getScoreMsg(parts[1]));
	}

	private String getScoreMsg(String room) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		String mess = getScoreDb(room);
		System.out.println(mess);
		return mess;
	}

	private String getScoreDb(String room) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		String sql = "SELECT USERNAME, SCORE FROM SCORE, ACCOUNT "
				+ "WHERE ROOM_ID = '" + getRoomId(room) + "' AND PLAYER_ID = ACC_ID" ;
		ResultSet rs = myConnection.executeResultSetSt(sql);
		String mess = "GS-";
		while (rs.next()) {
			mess += (rs.getString("username") + "-");
			mess += (rs.getString("score") + "-");
		}
		return mess;
	}

	private void createScore(String received) throws NumberFormatException, ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		String[] parts = received.split("-");
		createScoreDb(parts[1], parts[2], parts[3]);
	}

	private boolean createScoreDb(String client, String room, String score) throws NumberFormatException, ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		String sql = "INSERT INTO SCORE (SCORE, PLAYER_ID, ROOM_ID) "
				+ "VALUES (" + Integer.parseInt(score)+ ", " + getOwnerId(client) + ", " + getRoomId(room) + ")" ;
		if (myConnection.executeUpdateSt(sql) > 0)
			return true;
		return false;
	}

	private void updateScore(String received) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		String[] parts = received.split("-");
		updataScoreDb(parts[1], parts[2], parts[3]);
	}

	private boolean updataScoreDb(String client, String room, String score) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		String sql = "UPDATE SCORE SET score = " + Integer.parseInt(score)+ " "
				+ "WHERE player_id = " + getOwnerId(client) + " AND room_id = " + getRoomId(room);
		if (myConnection.executeUpdateSt(sql) > 0)
			return true;
		return false;
	}

	private void getQuestion(String received) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		try {
			dos.writeUTF(getQuestionMsg(received));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private String getQuestionMsg(String received) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		String[] parts = received.split("-");

		String mess = getQuestionDb(parts[1]);
		System.out.println(mess);
		return mess;
	}

	private String getQuestionDb(String roomId) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		String sql = "SELECT * FROM ROOM WHERE ROOM_NAME = '" + roomId + "'";
		ResultSet rs = myConnection.executeResultSetSt(sql);
		int topicId = 0;
		if (rs.next()) {
			topicId = rs.getInt("topic_id"); 
		}
		String mess = "GQ-";
		String sql1 = "SELECT * FROM QUESTION WHERE topic_id = " + topicId;
		ResultSet rs1 = myConnection.executeResultSetSt(sql1);
		while (rs1.next()) {
			mess += (rs1.getString("question") + "-");
			String option[] = null;
			for (int i = 1; i <= 4; i++) {
				mess += (rs1.getString("option" + i) + "-");
			}
			mess += (rs1.getString("correct_answer") + "-");

		}
		return mess;

	}

	private void getTopicList(String received) {
		// TODO Auto-generated method stub
		try {
			System.out.println("Getting topic list...");
			dos.writeUTF(getTopicListMsg(received));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private String getTopicListMsg(String received) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		String[] parts = received.split("-");
		Vector<String> topicList = getTopicListDb(parts[1]);
		String mess = "TL-";
		for (String topic : topicList) {
			mess += topic + "-";
		}
//		System.out.println(mess);
		return mess;
	}

	private Vector<String> getTopicListDb(String owner) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		String sql = "SELECT * FROM TOPIC WHERE OWNER_ID = '" + getOwnerId(owner) + "'";
		Vector<String> topicList = new Vector<String>();
		ResultSet rs = myConnection.executeResultSetSt(sql);
		while (rs.next()) {
			String topicName = rs.getString("topic_name");
			topicList.add(topicName);
		}

		return topicList;
	}

	private void createTopic(String received)  {
		//dos.writeUTF(createTopicMsg());
		String[] parts = received.split("-");
		try {
			createTopicDb(parts[1], parts[2]);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private String createTopicMsg() {
		// TODO Auto-generated method stub
		return "CT-Topic is created";
	}

	private boolean createTopicDb(String owner, String topicName) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		String sql = "INSERT INTO TOPIC (OWNER_ID, TOPIC_NAME) VALUES ('" + getOwnerId(owner) + "', '" + topicName + "')";
		if (myConnection.executeUpdateSt(sql) > 0) 
			return true;
		return false;
	}



	private void closeSocket() {
		// TODO Auto-generated method stub
		this.isloggedin=false; 
		try {
			this.s.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

	}

	public void waitHost() {
		// TODO Auto-generated method stub
		if (!isGameStarted) {

			try {
				dos.writeUTF(waitHostMsg());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}


	public void getRoomList() throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		try {
			dos.writeUTF(getRoomListMsg());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private String getRoomListMsg() throws ClassNotFoundException, SQLException {
		Vector<String> roomList = getRoomListDb();
		String mess = "RL-";
		for (String room : roomList) {
			mess += room + "-";
			System.out.println(room);
		}
		return mess;
	}

	private Vector<String> getRoomListDb() throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		String sql = "SELECT * FROM ROOM";
		Vector<String> roomList = new Vector<String>();
		ResultSet rs = myConnection.executeResultSetSt(sql);
		while (rs.next()) {
			String roomId = rs.getString("room_name");
			System.out.println(roomId);
			roomList.add(roomId);
		}

		return roomList;
	}

	public void login(String msg) {
		// TODO Auto-generated method stub
		try {
			String[] parts = msg.split("-");
			dos.writeUTF(loginMsg(parts[1], parts[2]));
			setName(parts[1]);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private String loginMsg(String name, String password) {
		String mess = null;
		try {
			if (!isNameExist(name)) {
				mess = "NO-" + "Account not exist"; 
			}
			else if (!isPasswordCorrect(name, password)) {
				mess = "NO-" + "Password not correct";
			}
			else {
				mess = "OK-" + "Logged in";
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mess;
	}

	public void exitRoom(String msg) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		deleteRoom(this.roomId);
	}

	public void joinRoom(String msg) {
		// TODO Auto-generated method stub
		try {
			this.isHost = false;
			String[] parts = msg.split("-");
			dos.writeUTF(joinRoomMsg(parts[1], parts[2]));
			setRoomId(parts[1]);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private String joinRoomMsg(String roomId, String name) {
		String mess = null;
		try {
			if (!isRoomExist(roomId)) {
				mess = "NO-" + "Room not exist"; 
			}
			else {
				mess = "OK-" + "Room joined";
				this.roomId = roomId;
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mess;
		// TODO Auto-generated method stub

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void startGame() {
		// TODO Auto-generated method stub
		try {
			dos.writeUTF(startGameMsg());
			isGameStarted = true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void chooseOption(String msg) {
		// TODO Auto-generated method stub

	}

	public void createQuestion(String msg) {	
		// TODO Auto-generated method stub
		try {
			String[] parts = msg.split("-");
			dos.writeUTF(createQuestionMsg());
			// update to database
			try {
				createQuestionDb(parts[1], parts[2], parts[3], parts[4], parts[5], parts[6], parts[7]);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}


	public boolean createQuestionDb(String topic ,String question, String optionA, String optionB,
			String optionC, String optionD, String answer) throws SQLException, ClassNotFoundException {
		String sql = "INSERT INTO QUESTION (TOPIC_ID, QUESTION, OPTION1, OPTION2, OPTION3, OPTION4, CORRECT_ANSWER) VALUES (?, ?, ?, ?, ?, ?, ?)";
		try {
			PreparedStatement pst = myConnection.getMyConnection().prepareStatement(sql);
			pst.setInt(1, getTopicId(topic));
			pst.setString(2, question);
			pst.setString(3, optionA);
			pst.setString(4, optionB);
			pst.setString(5, optionC);
			pst.setString(6, optionD);
			pst.setString(7, answer);
			if (pst.executeUpdate() > 0) {
				return true;
			} else
				return false;
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage() + ".My query");
			return false;
		}

	}

	public void createRoom(String msg) {
		// TODO Auto-generated method stub
		try {
			String[] parts = msg.split("-");
			isHost = true;
			dos.writeUTF(createRoomMsg(parts[1]));
			// update to database
			try {
				createRoomDb(parts[1], parts[2]);
				this.roomId = parts[1];
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private String createRoomMsg(String roomId) {
		String mess = null;
		try {
			if (!isRoomExist(roomId)) {
				mess = "OK-" + "Room created"; 
			}
			else {
				mess = "NO-" + "Room exist";
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mess;
		// TODO Auto-generated method stub

	}

	public boolean createRoomDb(String roomName, String topic)
			throws SQLException, ClassNotFoundException {
		String sql = "INSERT INTO ROOM (ROOM_NAME, TOPIC_ID) VALUES ( '" + roomName + "', '" 
				+ getTopicId(topic) + "')" ;
		if (myConnection.executeUpdateSt(sql) > 0) 
			return true;
		return false;
	}

	public void createName(String msg) {
		// TODO Auto-generated method stub
		try {
			String[] parts = msg.split("-");
			dos.writeUTF(createNameMsg(parts[1]));
			setName(parts[1]);
			//update to database
			try {
				createNameDb(parts[1], parts[2]);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}



		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	} 


	public boolean createNameDb(String name, String password)
			throws SQLException, ClassNotFoundException {
		String sql = "INSERT INTO ACCOUNT (USERNAME, PASSWORD) VALUES ('" + name + "', '" + password + "')" ;
		if (myConnection.executeUpdateSt(sql) > 0) 
			return true;
		return false;
	}

	//////////////////////////////////////////////////////////




	private String exitRoomMsg() {
		return name;
		// TODO Auto-generated method stub

	}

	private String waitHostMsg() {
		// TODO Auto-generated method stub
		String mess = "NO-Waiting for host";
		return mess;
	}

	private String startGameMsg() {
		String mess = "GS-Game started";
		return mess;
		// TODO Auto-generated method stub

	}

	private String createQuestionMsg() {
		String mess = "OK-question created";
		return mess;
		// TODO Auto-generated method stub

	}

	private String createNameMsg(String name) {
		String mess = null;
		try {
			if (!isNameExist(name)) {
				mess = "OK-" + "Name created"; 
			}
			else {
				mess = "NO-" + "Name exist";
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mess;
		// TODO Auto-generated method stub

	}


	public boolean isNameExist(String name)
			throws SQLException, ClassNotFoundException {
		String sql = "SELECT * FROM ACCOUNT WHERE USERNAME = '" + name + "'";
		ResultSet resultSet = myConnection.executeResultSetSt(sql);
		if (resultSet.next()) {
			System.out.println("User name exists");
			return true;
		}
		return false;
	}

	public boolean isPasswordCorrect(String name, String password)
			throws SQLException, ClassNotFoundException {
		String sql = "SELECT * FROM ACCOUNT WHERE USERNAME = '" + name + "' AND PASSWORD = '" + password + "'";
		ResultSet resultSet = myConnection.executeResultSetSt(sql);
		if (resultSet.next()) {
			System.out.println("Password correct");
			return true;
		}
		return false;

	}

	public boolean isRoomExist(String roomName) throws SQLException, ClassNotFoundException {
		String sql = "SELECT * FROM ROOM WHERE ROOM_NAME = '" + roomName + "'";
		ResultSet resultSet = myConnection.executeResultSetSt(sql);
		if (resultSet.next()) {
			System.out.println("Room ID exists");
			return true;
		}
		return false;
	}

	private int getOwnerId(String owner) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		String sql = "SELECT * FROM ACCOUNT WHERE USERNAME = '" + owner +"'";
		ResultSet rs = myConnection.executeResultSetSt(sql);
		if (rs.next()) {
			int ownerId = rs.getInt("acc_id");
			return ownerId;
		}
		return 0;
	}

	public int getRoomId(String room) throws ClassNotFoundException, SQLException {
		String sql = "SELECT * FROM ROOM WHERE ROOM_NAME = '" + room + "'";
		ResultSet rs = myConnection.executeResultSetSt(sql);
		int room_id = 0;
		if (rs.next()) {
			room_id = rs.getInt("room_id"); 
		}
		return room_id;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}


	public int getTopicId(String topic) throws ClassNotFoundException, SQLException {
		String sql = "SELECT * FROM TOPIC WHERE topic_name = '" + topic +"'";
		ResultSet rs = myConnection.executeResultSetSt(sql);
		if (rs.next()) {
			int topicId = rs.getInt("topic_id");
			return topicId;
		}
		return 0;
	}

	public int getHostId(String host) throws ClassNotFoundException, SQLException {
		String sql = "SELECT REG_ID FROM ACCOUNT WHERE USERNAME = '" + host +"'";
		ResultSet resultSet = myConnection.executeResultSetSt(sql);
		if (resultSet.next())
			return resultSet.getInt("reg_id");
		return 0;

	}

	public String getRoomId() {
		return roomId;
	}
}
