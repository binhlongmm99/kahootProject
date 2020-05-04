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
				System.out.println(received); 
				String[] parts = received.split("-");
				switch(parts[0]) {
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
					startGame();
					for (ClientHandler mc : Server.ar) {
						mc.dos.writeUTF(mc.startGameMsg());
						mc.isGameStarted = true;
					}
					break;
				case "JR":
					joinRoom(received);break;
				case "ER":
					exitRoom(received);break;
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
				case "WH":
					waitHost();break;
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
//				e.printStackTrace();
				this.isloggedin=false; 
                try {
					this.s.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} 
			}

		} 
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

public void exitRoom(String msg) {
	// TODO Auto-generated method stub

}

public void joinRoom(String msg) {
	// TODO Auto-generated method stub
	try {
		String[] parts = msg.split("-");
		dos.writeUTF(joinRoomMsg(parts[1], parts[2]));
		setRoomId(parts[1]);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}

public String getName() {
	return name;
}

public void setName(String name) {
	this.name = name;
}

public String getRoomId() {
	return roomId;
}

public void setRoomId(String roomId) {
	this.roomId = roomId;
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
			createQuestionDb(parts[1], parts[2], parts[3], parts[4], parts[5], parts[6]);
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

//public void createQuestionDb(String question, String optionA, String optionB, String optionC, String optionD, String answer) {
//	// TODO Auto-generated method stub
//
//}

public boolean createQuestionDb(String question, String optionA, String optionB,
		String optionC, String optionD, String answer) throws SQLException, ClassNotFoundException {
	String sql = "INSERT INTO QUESTION (QUESTION, CHOICE1, CHOICE2, CHOICE3, CHOICE4, CORRECT_ANSWER) VALUES (?, ?, ?, ?, ?, ?)";
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

public void createRoom(String msg) {
	// TODO Auto-generated method stub
	try {
		String[] parts = msg.split("-");
		dos.writeUTF(createRoomMsg(parts[1]));
		// update to database
		try {
			createRoomDb(parts[1], parts[2]);
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

//public void createRoomDb(String roomId, String host) {
//	// TODO Auto-generated method stub
//
//}

public int getHostId(String host) throws ClassNotFoundException, SQLException {
	String sql = "SELECT REG_ID FROM REGISTRATION WHERE USERNAME = '" + host +"'";
	ResultSet resultSet = myConnection.executeResultSetSt(sql);
	if (resultSet.next())
		return resultSet.getInt("reg_id");
	return 0;

}

public boolean createRoomDb(String roomName, String host)
		throws SQLException, ClassNotFoundException {
	String sql = "INSERT INTO ROOM (ROOM_NAME, HOST) VALUES ( '" + roomName + "', '" 
		+ getHostId(host) + "')" ;
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

//private void createNameDb(String name, String password) {
//	// TODO Auto-generated method stub
//
//}

public boolean createNameDb(String name, String password)
		throws SQLException, ClassNotFoundException {
	String sql = "INSERT INTO REGISTRATION (USERNAME, PASSWORD) VALUES ('" + name + "', '" + password + "')" ;
	if (myConnection.executeUpdateSt(sql) > 0) 
		return true;
	return false;
}

//////////////////////////////////////////////////////////
private String getRoomListMsg() throws ClassNotFoundException, SQLException {
	Vector<String> roomList = getRoomListDb();
	String mess = "OK-";
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

private String exitRoomMsg() {
	return name;
	// TODO Auto-generated method stub

}

private String waitHostMsg() {
	// TODO Auto-generated method stub
	String mess = "NO-Waiting for host";
	return mess;
}

private String joinRoomMsg(String roomId, String name) {
	String mess = null;
	try {
		if (!isRoomExist(roomId)) {
			mess = "NO-" + "Room not exist"; 
		}
		else {
			mess = "OK-" + "Room joined";
			if (isHost(roomId, name)) {
				mess += "-Host";
			}
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

private String startGameMsg() {
	String mess = "OK-Game started";
	return mess;
	// TODO Auto-generated method stub

}

private String chooseOptionMsg() {
	return name;
	// TODO Auto-generated method stub

}

private String createQuestionMsg() {
	String mess = "OK-question created";
	return mess;
	// TODO Auto-generated method stub

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

//private boolean isRoomExist(String roomId) {
//	// TODO Auto-generated method stub
//	//check from database
//
//
//	return true;
//}

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

//private boolean isNameExist(String name) {
//	// TODO Auto-generated method stub
//	//check from database
//
//
//	return true;
//}

public boolean isNameExist(String name)
		throws SQLException, ClassNotFoundException {
	String sql = "SELECT * FROM REGISTRATION WHERE USERNAME = '" + name + "'";
	ResultSet resultSet = myConnection.executeResultSetSt(sql);
	if (resultSet.next()) {
		System.out.println("User name exists");
		return true;
	}
	return false;
}

//private boolean isPasswordCorrect(String name, String password) {
//	//check from database
//
//
//	return true;
//}
public boolean isPasswordCorrect(String name, String password)
		throws SQLException, ClassNotFoundException {
	String sql = "SELECT * FROM REGISTRATION WHERE USERNAME = '" + name + "' AND PASSWORD = '" + password + "'";
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

private boolean isHost(String roomId, String clientName) throws ClassNotFoundException, SQLException {
	String sql = "SELECT * FROM REGISTRATION, ROOM WHERE ROOM_NAME = '" + roomId 
			+ "' AND HOST = (SELECT R.REG_ID FROM REGISTRATION R WHERE R.USERNAME = '" + clientName + "')";
	ResultSet resultSet = myConnection.executeResultSetSt(sql);
	if (resultSet.next()) {
		System.out.println("Hosting");
		return true;
	}
	return false;	
}
} 
