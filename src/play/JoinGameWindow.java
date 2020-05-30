package play;

import java.io.IOException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wb.swt.SWTResourceManager;

import client.Client;

public class JoinGameWindow {

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
			JoinGameWindow window = new JoinGameWindow();
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
		createContents(client);
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
	protected void createContents(Client client) {
		if(shell == null) shell = new Shell();
		shell.setSize(780, 480);
		shell.setText("Join game");
		
		Composite composite = new Composite(shell, SWT.NONE);
		composite.setBounds(10, 10, 744, 127);
		
		Label lblUser = new Label(composite, SWT.NONE);
		lblUser.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.BOLD));
		lblUser.setAlignment(SWT.CENTER);
		lblUser.setBounds(257, 44, 228, 46);
		lblUser.setText("User: " + clientName);
		
		Composite composite_1 = new Composite(shell, SWT.NONE);
		composite_1.setBounds(10, 159, 744, 272);
		
		Button btnCreateRoom = new Button(composite_1, SWT.NONE);
		btnCreateRoom.setFont(SWTResourceManager.getFont("Times New Roman", 12, SWT.NORMAL));
		btnCreateRoom.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					for (Control kid : shell.getChildren()) {
				         kid.dispose();
				    }
					//String loginMsg = loginMsg(name, password);
					CreateRoomWindow createRoomWindow = new CreateRoomWindow();
					createRoomWindow.setShell(shell);
					createRoomWindow.setClientName(clientName);
					createRoomWindow.open(client);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		btnCreateRoom.setBounds(278, 44, 204, 47);
		btnCreateRoom.setText("Create room");
		
		Button btnChooseRoom = new Button(composite_1, SWT.NONE);
		btnChooseRoom.setFont(SWTResourceManager.getFont("Times New Roman", 12, SWT.NORMAL));
		btnChooseRoom.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					for (Control kid : shell.getChildren()) {
				         kid.dispose();
				    }
					//String loginMsg = loginMsg(name, password);
					JoinRoomWindow joinRoomWindow = new JoinRoomWindow();
					joinRoomWindow.setShell(shell);
					joinRoomWindow.setClientName(clientName);
					joinRoomWindow.open(client);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		btnChooseRoom.setBounds(278, 140, 204, 47);
		btnChooseRoom.setText("Choose room");
		
		Button btnBack = new Button(composite_1, SWT.NONE);
		btnBack.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					for (Control kid : shell.getChildren()) {
				         kid.dispose();
				    }
					System.out.println(client.dis.readUTF());
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
		
		btnBack.setFont(SWTResourceManager.getFont("Times New Roman", 12, SWT.NORMAL));
		btnBack.setBounds(278, 185, 204, 39);
		btnBack.setText("Back");

	}


}
