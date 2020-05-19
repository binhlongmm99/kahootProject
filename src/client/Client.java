package client;

import java.io.*; 
import java.net.*; 
import java.util.Scanner; 

public class Client  
{ 
	final static int ServerPort = 1234; 
	private InetAddress ip;
	Socket s;
	public final DataInputStream dis; 
    public final DataOutputStream dos; 
    
	public Client() throws IOException
	{
		// getting localhost ip 
		this.ip = InetAddress.getByName("localhost"); 

		// establish the connection 
		this.s = new Socket(ip, ServerPort); 

		// obtaining input and out streams 
		this.dis = new DataInputStream(s.getInputStream()); 
		this.dos = new DataOutputStream(s.getOutputStream()); 





	} 
	
	public boolean isReadyToPlay(String msg) {
		if (msg.contains("started")){
			return true;
		}
		return false;
	}

	public boolean isNameExist(String msg) {
		if (msg.contains("Name created") || msg.contains("Account not exist")) {
			return false;
		}
		else {
			return true;
		}
	}

	public boolean isRoomExist(String msg) {
		if (msg.contains("Room created") || msg.contains("Room not exist")) {
			return false;
		}
		else {
			return true;
		}
	}
	public boolean isHost(String msg) {
		if (msg.contains("Host")) {
			return true;
		}
		else return false;
		
	}

	public boolean isPasswordCorrect(String msg) {
		if (msg.contains("Password not correct")) {
			return false;
		}
		else {
			return true;
		}	
	}

	public String exitRoomMsg() {
		// TODO Auto-generated method stub
		String mess = "ER";
		return mess;
	}

	public String joinRoomMsg(String roomId, String name) {
		// TODO Auto-generated method stub
		String mess = "JR-" + roomId + "-" + name;
		return mess;
	}

	public String startGameMsg() {
		// TODO Auto-generated method stub
		String mess = "SG";
		return mess;
	}

	public void chooseOptionMsg() {
		// TODO Auto-generated method stub

	}

	public String createQuestionMsg(String topic,String question, String optionA, String optionB, String optionC, String optionD, String answer) {
		// TODO Auto-generated method stub
		String mess = "CQ-" + topic + "-" + question + "-" + optionA + "-" + optionB + "-"+ optionC + "-"+ optionD + "-" + answer;
		return mess;
	}

	public String createRoomMsg(String roomId, String topic) {
		// TODO Auto-generated method stub
		String mess = "CR-" + roomId + "-" + topic;
		return mess;
	}

	public String loginMsg(String name, String password) {
		String mess = "LI-" + name + "-" + password;
		return mess;
	}

	public String createNameMsg(String name, String password) {
		// TODO Auto-generated method stub
		String mess = "CN-" + name + "-" + password;
		return mess;

	} 
	public String getRoomListMsg() {
		String mess = "RL-Get room list";
		return mess;
	}

	public String getQuestionListMsg(String roomId) {
		// TODO Auto-generated method stub
		String mess = "GQ-" + roomId;
		return mess;
	}


	public String updateScoreMsg(String clientName, String room, int score) {
		// TODO Auto-generated method stub
		String mess = "US-" + clientName + "-" + room + "-" + score;
		return mess;
	}

	public String createScoreMsg(String clientName, String room, int i) {
		// TODO Auto-generated method stub
		String mess = "CSc-" + clientName + "-" + room + "-" + i;
		return mess;
	}

	public String getScore(String room) {
		// TODO Auto-generated method stub
		String mess = "GS-" + room;
		return mess;
	}

	public String createTopicMsg(String clientName, String topic) {
		// TODO Auto-generated method stub
		String mess = "CT-" + clientName + "-" + topic; 
		return mess;
	}

	public String getTopicList(String clientName) {
		// TODO Auto-generated method stub
		return "TL-" + clientName;
	}
	
	
	
} 