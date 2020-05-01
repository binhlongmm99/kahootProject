package play;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import client.Client;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import java.io.IOException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;

public class JoinRoomWindow {

	protected Shell shell;
	private String clientName;
	
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
			JoinRoomWindow window = new JoinRoomWindow();
			//window.open(client);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open(Client client) {
		Display display = Display.getDefault();
		String sRep = null;
		//Send request to server to get roomlist
		List roomList;
		try {
			client.dos.writeUTF(client.getRoomListMsg());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			sRep = client.dis.readUTF();
			System.out.println(sRep);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String[] parts = sRep.split("-");
		
		createContents(display,client, parts);
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
	protected void createContents(Display display,Client client, String[] parts) {
		if(shell == null) shell = new Shell();
		shell.setSize(450, 300);
		shell.setText("Join room");
		
		Composite composite = new Composite(shell, SWT.NONE);
		composite.setBounds(0, 0, 424, 64);
		
		Label lblHello = new Label(composite, SWT.NONE);
		lblHello.setBounds(170, 23, 55, 15);
		lblHello.setText("Hello, " + clientName);
		
		Composite composite_1 = new Composite(shell, SWT.NONE);
		composite_1.setBounds(0, 71, 424, 157);
		
		Label lblChooseRoom = new Label(composite_1, SWT.NONE);
		lblChooseRoom.setBounds(21, 10, 122, 15);
		lblChooseRoom.setText("Choose room: ");
		
		List list = new List(composite_1, SWT.BORDER | SWT.SINGLE | SWT.V_SCROLL);
		list.setBounds(106, 6, 46, 137);
		
		//CODE HERE
		//Connect to DB to get all available rooms and add to list
		//Sample code: 
		//roomList = getAvailableRooms();
		//for(String room: roomList)
		//	list.add(room);	
		
		for (int i = 1; i < parts.length; i++) {
			list.add(parts[i]);
			System.out.println(parts[i]);
		}
		
		Color red = new Color(display, 255, 0, 0);
		Label lblNewLabel = new Label(composite_1, SWT.NONE);
		lblNewLabel.setBounds(176, 32, 122, 15);
		lblNewLabel.setText("");
		lblNewLabel.setForeground(red);
		
		Button btnJoin = new Button(shell, SWT.NONE);
		btnJoin.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String sRep = null;
				int index = list.getSelectionIndex();
				if(index == -1) {
					lblNewLabel.setText("Please choose room!");
				} else {
					lblNewLabel.setText("");
					String room = list.getItem(index);
					//Check if client is host or not	
					try {
						client.dos.writeUTF(client.joinRoomMsg(room, clientName));
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					try {
						sRep = client.dis.readUTF();
						System.out.println(sRep);
						if (client.isHost(sRep)) {
							//Host window here
							try {
								for (Control kid : shell.getChildren()) {
							          kid.dispose();
							    }
								StartWindow window = new StartWindow();
								window.setShell(shell);
								window.setClientName(clientName);
								window.setRoom(room);
								window.open(client);
							} catch (Exception ex) {
								ex.printStackTrace();
							}
						}
						else {
							//Player window
							try {
								for (Control kid : shell.getChildren()) {
							          kid.dispose();
							    }
								WaitWindow window = new WaitWindow();
								window.setShell(shell);
								window.setClientName(clientName);
								window.setRoom(room);
								window.open(client);

							} catch (Exception ex) {
								ex.printStackTrace();
							}
						}
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
//					shell.close();

				}
			}
		});
		btnJoin.setBounds(248, 234, 75, 25);
		btnJoin.setText("Join");
		
		Button btnExit = new Button(shell, SWT.NONE);
		btnExit.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					for (Control kid : shell.getChildren()) {
				         kid.dispose();
				    }
					//String loginMsg = loginMsg(name, password);
					ClientWindow clientWindow = new ClientWindow();
					clientWindow.setShell(shell);
					clientWindow.setClientName(clientName);
					clientWindow.open(client);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		btnExit.setBounds(329, 234, 75, 25);
		btnExit.setText("Exit");

	}
}
