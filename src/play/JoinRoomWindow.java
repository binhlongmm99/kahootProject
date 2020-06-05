package play;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
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
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;

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
		shell.setSize(1350, 700);
		GridLayout layout = new GridLayout();
		layout.makeColumnsEqualWidth = true;
		layout.numColumns = 3;
		shell.setLayout(layout);
		
		shell.setText("Join room");
		
		new Label(shell, SWT.NULL);

		Label lblHello = new Label(shell, SWT.NONE);
		lblHello.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.BOLD));
		lblHello.setAlignment(SWT.CENTER);
		//lblHello.setBounds(267, 25, 193, 39);
		lblHello.setText("Hello, " + clientName);
		GridData data = new GridData(GridData.HORIZONTAL_ALIGN_CENTER);
		data.widthHint = 400;
		data.heightHint = 200;
		lblHello.setLayoutData(data);
		
		new Label(shell, SWT.NULL);

		Label lblEnterRoom = new Label(shell, SWT.NONE);
		lblEnterRoom.setFont(SWTResourceManager.getFont("Times New Roman", 12, SWT.NORMAL));
		//lblEnterRoom.setBounds(41, 53, 153, 47);
		lblEnterRoom.setText("Enter room: ");
		
		Text text = new Text(shell, SWT.BORDER);
		text.setFont(SWTResourceManager.getFont("Times New Roman", 12, SWT.NORMAL));
		//text.setBounds(200, 53, 115, 29);
		data = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
		data.heightHint = 50;
		//data.horizontalSpan = 2;
		text.setLayoutData(data);

		//CODE HERE
		//Connect to DB to get all available rooms and add to list
		//Sample code: 
		//roomList = getAvailableRooms();
		//for(String room: roomList)
		//	list.add(room);	

//		for (int i = 1; i < parts.length; i++) {
//			list.add(parts[i]);
//			System.out.println(parts[i]);
//		}

		Color red = new Color(display, 255, 0, 0);
		Label lblNewLabel = new Label(shell, SWT.NONE);
		lblNewLabel.setFont(SWTResourceManager.getFont("Times New Roman", 12, SWT.NORMAL));
		//lblNewLabel.setBounds(41, 122, 223, 29);
		lblNewLabel.setText("");
		lblNewLabel.setForeground(red);
		data = new GridData(GridData.HORIZONTAL_ALIGN_CENTER);
		data.widthHint = 400;
		data.heightHint = 50;
		data.horizontalSpan = 3;
		lblNewLabel.setLayoutData(data);
		
		Button btnExit = new Button(shell, SWT.CENTER);
		data = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		data.heightHint = 42;
		data.widthHint = 94;
		btnExit.setLayoutData(data);
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
		//btnExit.setBounds(589, 365, 118, 48);
		btnExit.setText("Exit");

		Button btnJoin = new Button(shell, SWT.NONE);
		btnJoin.setFont(SWTResourceManager.getFont("Times New Roman", 12, SWT.NORMAL));
		btnJoin.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String sRep = null;
				String room = text.getText().replaceAll("\\s++", "");
				if(room == null || room == "") {
					lblNewLabel.setText("Please enter room!");
				} else if(!existRoom(room, parts)){
					lblNewLabel.setText("Room is not exist. Enter again!");
					text.setText("");
				} else {
					lblNewLabel.setText("");
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
		//btnJoin.setBounds(444, 365, 118, 48);
		btnJoin.setText("Join");
		data = new GridData(GridData.HORIZONTAL_ALIGN_END);
		data.widthHint = 122;
		data.heightHint = 50;
		data.horizontalSpan = 2;
		btnJoin.setLayoutData(data);

	}
	
	private boolean existRoom(String room, String[] parts) {
		for(int i=1; i<parts.length; i++) {
			if(parts[i].compareTo(room) == 0) {
				return true;
			}
		}
		return false;
	}
}