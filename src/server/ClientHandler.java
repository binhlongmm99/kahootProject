package server;
import java.io.*; 
import java.util.*; 
import java.net.*;

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

	// constructor 
	public ClientHandler(Socket s, String name, 
			DataInputStream dis, DataOutputStream dos) { 
		this.dis = dis; 
		this.dos = dos; 
		this.name = name; 
		this.s = s; 

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
					getRoomList();break;
				case "WH":
					waitHost();break;
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

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






public void getRoomList() {
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
		createQuestionDb(parts[1], parts[2], parts[3], parts[4], parts[5], parts[6]);


	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

}

public void createQuestionDb(String question, String optionA, String optionB, String optionC, String optionD, String answer) {
	// TODO Auto-generated method stub

}

public void createRoom(String msg) {
	// TODO Auto-generated method stub
	try {
		String[] parts = msg.split("-");
		dos.writeUTF(createRoomMsg(parts[1]));
		// update to database
		createRoomDb(parts[1], parts[2]);


	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}

public void createRoomDb(String roomId, String host) {
	// TODO Auto-generated method stub

}

public void createName(String msg) {
	// TODO Auto-generated method stub
	try {
		String[] parts = msg.split("-");
		dos.writeUTF(createNameMsg(parts[1]));
		setName(parts[1]);
		//update to database
		createNameDb(parts[1], parts[2]);



	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
} 

private void createNameDb(String name, String password) {
	// TODO Auto-generated method stub

}

//////////////////////////////////////////////////////////
private String getRoomListMsg() {
	String[] roomList = getRoomListDb();
	String mess = "OK-";
	for (String room : roomList) {
		mess += room + "-";
		System.out.println(room);
	}
	return mess;
}

private String[] getRoomListDb() {
	// TODO Auto-generated method stub
	String[] roomList = {"123"};
	return roomList;
}

private String loginMsg(String name, String password) {
	String mess;
	if (!isNameExist(name)) {
		mess = "NO-" + "Account not exist"; 
	}
	else if (!isPasswordCorrect(name, password)) {
		mess = "NO-" + "Password not correct";
	}
	else {
		mess = "OK-" + "Logged in";
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
	String mess;
	if (!isRoomExist(roomId)) {
		mess = "NO-" + "Room not exist"; 
	}
	else {
		mess = "OK-" + "Room joined";
		if (isHost(roomId, name)) {
			mess += "-Host";
		}
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
	String mess;
	if (!isRoomExist(roomId)) {
		mess = "OK-" + "Room created"; 
	}
	else {
		mess = "NO-" + "Room exist";
	}
	return mess;
	// TODO Auto-generated method stub

}

private boolean isRoomExist(String roomId) {
	// TODO Auto-generated method stub
	//check from database


	return true;
}

private String createNameMsg(String name) {
	String mess;
	if (!isNameExist(name)) {
		mess = "OK-" + "Name created"; 
	}
	else {
		mess = "NO-" + "Name exist";
	}
	return mess;
	// TODO Auto-generated method stub

}

private boolean isNameExist(String name) {
	// TODO Auto-generated method stub
	//check from database


	return true;
}

private boolean isPasswordCorrect(String name, String password) {
	//check from database


	return true;
}

private boolean isHost(String roomId, String clientName) {
	return true;	
}
} 
