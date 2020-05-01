package play;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;

import java.io.IOException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;

import client.Client;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;

public class CreateRoomWindow {

	protected Shell shell;
	private String clientName;
	private Text roomTxt;
	
	public void setShell(Shell shell) {
		this.shell = shell;
	}
	
	public void setClientName(String name) {
		this.clientName = name;
	}

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			CreateRoomWindow window = new CreateRoomWindow();
			window.setClientName("abcd");
			//window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open(Client client) {
		Display display = Display.getDefault();
		createContents(display, client);
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents(Display display, Client client) {
		if(shell == null) shell = new Shell();
		shell.setSize(450, 300);
		shell.setText("Create new room");
		
		Label lblUser = new Label(shell, SWT.NONE);
		lblUser.setAlignment(SWT.CENTER);
		lblUser.setBounds(153, 10, 122, 15);
		lblUser.setText("User: " + clientName);
		
		Label lblEnterRoom = new Label(shell, SWT.NONE);
		lblEnterRoom.setBounds(44, 87, 107, 15);
		lblEnterRoom.setText("Enter room number:");
		
		roomTxt = new Text(shell, SWT.BORDER);
		roomTxt.setBounds(182, 84, 76, 21);
		
		Color redColor = new Color(display, 255, 0, 0);
		
		Label lblErrorTxt = new Label(shell, SWT.NONE);
		lblErrorTxt.setBounds(157, 122, 177, 15);
		lblErrorTxt.setText("");
		lblErrorTxt.setForeground(redColor);
		
		Button btnNext = new Button(shell, SWT.NONE);
		btnNext.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String room = roomTxt.getText();
				String sRep = null;
				if(!isRoomValid(room)) {
					lblErrorTxt.setText("Invalid room number! Please try again");
				}else {
					//Send room information to server
					try {
						client.dos.writeUTF(client.createRoomMsg(room, clientName));
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					try {
						sRep = client.dis.readUTF();
						System.out.println(sRep);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					if(client.isRoomExist(sRep)) {
						lblErrorTxt.setText("Room is existed! Please try another");
					} else {
						lblErrorTxt.setText("");
						//shell.close();
						try {
							for (Control kid : shell.getChildren()) {
						          kid.dispose();
						    }
							CreateQuestionWindow window = new CreateQuestionWindow();
							window.setShell(shell);
							window.setClientName(clientName);
							window.setRoom(room);
							window.open(client);
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}
				}
					 
			}
		});
		btnNext.setBounds(273, 201, 75, 25);
		btnNext.setText("Next");
		
	}
	
	private boolean isRoomValid(String room) {
		if(room == null || room.isBlank() || room.isEmpty()) return false;
		try {
			int roomNo = Integer.parseInt(room);
		} catch(NumberFormatException nfe) {
			return false;
		}
		return true;
	}
	
	private boolean isRoomExist(String room) {
		//Check if room is existed
		//ENTER CODE HERE 
		return false;
	}

}
