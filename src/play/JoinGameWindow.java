package play;

import java.io.IOException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
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
		shell.setSize(1350, 700);
		GridLayout layout = new GridLayout();
		layout.makeColumnsEqualWidth = true;
		layout.numColumns = 3;
		shell.setLayout(layout);
		
		shell.setText("Join game");
		
		new Label(shell, SWT.NULL);

		Label lblUser = new Label(shell, SWT.NONE);
		lblUser.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.BOLD));
		lblUser.setAlignment(SWT.CENTER);
		//lblUser.setBounds(257, 44, 228, 46);
		lblUser.setText("User: " + clientName);
		GridData data = new GridData(GridData.HORIZONTAL_ALIGN_CENTER);
		data.widthHint = 400;
		data.heightHint = 200;
		lblUser.setLayoutData(data);
		
		new Label(shell, SWT.NULL);
		
		Button btnCreateRoom = new Button(shell, SWT.NONE);
		btnCreateRoom.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.NORMAL));
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
		//btnCreateRoom.setBounds(278, 44, 204, 47);
		btnCreateRoom.setText("Create room");
		data = new GridData(GridData.HORIZONTAL_ALIGN_CENTER);
		data.widthHint = 400;
		data.heightHint = 100;
		data.horizontalSpan = 3;
		btnCreateRoom.setLayoutData(data);
		
		Button btnChooseRoom = new Button(shell, SWT.NONE);
		btnChooseRoom.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.NORMAL));
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
		//btnChooseRoom.setBounds(278, 140, 204, 47);
		btnChooseRoom.setText("Choose room");
		data = new GridData(GridData.HORIZONTAL_ALIGN_CENTER);
		data.widthHint = 400;
		data.heightHint = 100;
		data.horizontalSpan = 3;
		btnChooseRoom.setLayoutData(data);

	}


}
