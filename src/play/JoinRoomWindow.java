package play;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wb.swt.SWTResourceManager;

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
		shell.setSize(780, 480);
		shell.setText("Join room");

		Composite composite = new Composite(shell, SWT.NONE);
		composite.setBounds(10, 10, 744, 86);

		Label lblHello = new Label(composite, SWT.NONE);
		lblHello.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.BOLD));
		lblHello.setAlignment(SWT.CENTER);
		lblHello.setBounds(267, 25, 193, 39);
		lblHello.setText("Hello, " + clientName);

		Composite composite_1 = new Composite(shell, SWT.NONE);
		composite_1.setBounds(10, 117, 744, 230);

		Label lblChooseRoom = new Label(composite_1, SWT.NONE);
		lblChooseRoom.setFont(SWTResourceManager.getFont("Times New Roman", 12, SWT.NORMAL));
		lblChooseRoom.setBounds(41, 53, 153, 47);
		lblChooseRoom.setText("Choose room: ");

		List list = new List(composite_1, SWT.BORDER | SWT.SINGLE | SWT.V_SCROLL);
		list.setFont(SWTResourceManager.getFont("Times New Roman", 12, SWT.NORMAL));
		list.setBounds(200, 28, 128, 137);

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
		lblNewLabel.setFont(SWTResourceManager.getFont("Times New Roman", 12, SWT.NORMAL));
		lblNewLabel.setBounds(43, 178, 223, 29);
		lblNewLabel.setText("");
		lblNewLabel.setForeground(red);

		Button btnJoin = new Button(shell, SWT.NONE);
		btnJoin.setFont(SWTResourceManager.getFont("Times New Roman", 12, SWT.NORMAL));
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

						//Player window
						try {
							client.dos.writeUTF("WH");
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

					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		btnJoin.setBounds(444, 365, 118, 48);
		btnJoin.setText("Join");

		Button btnExit = new Button(shell, SWT.NONE);
		btnExit.setFont(SWTResourceManager.getFont("Times New Roman", 12, SWT.NORMAL));
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
		btnExit.setBounds(589, 365, 118, 48);
		btnExit.setText("Exit");

	}
}
