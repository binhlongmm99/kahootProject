package play;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import client.Client;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class ClientWindow {

	protected Shell shell;
	private String clientName;
	
	public void setShell(Shell shell) {
		this.shell = shell;
	}
	
	public void setClientName(String name) {
		clientName = name;
	}
	
	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			ClientWindow window = new ClientWindow();
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
		shell.setSize(450, 315);
		shell.setText("Playing Kahoot");
		
		Composite composite = new Composite(shell, SWT.NONE);
		composite.setBounds(0, 0, 424, 109);
		
		Label lblKahoot = new Label(composite, SWT.NONE);
		lblKahoot.setAlignment(SWT.CENTER);
		lblKahoot.setBounds(184, 32, 55, 15);
		lblKahoot.setText("Kahoot");
		
		Label lblHello = new Label(composite, SWT.NONE);
		lblHello.setAlignment(SWT.CENTER);
		lblHello.setBounds(157, 63, 106, 15);
		lblHello.setText("Hello, " + clientName);
		
		Composite composite_1 = new Composite(shell, SWT.NONE);
		composite_1.setBounds(0, 108, 424, 168);
		
		Button btnCreateNewRoom = new Button(composite_1, SWT.NONE);
		btnCreateNewRoom.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					for (Control kid : shell.getChildren()) {
				          kid.dispose();
				    }
					CreateRoomWindow createRoomWindow = new CreateRoomWindow();
					createRoomWindow.setShell(shell);
					createRoomWindow.setClientName(clientName);
					createRoomWindow.open(client);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		btnCreateNewRoom.setBounds(143, 49, 131, 25);
		btnCreateNewRoom.setText("Create new room");
		
		Button btnJoinRoom = new Button(composite_1, SWT.NONE);
		btnJoinRoom.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					for (Control kid : shell.getChildren()) {
				          kid.dispose();
				    }
					JoinRoomWindow joinRoomWindow = new JoinRoomWindow();
					joinRoomWindow.setClientName(clientName);
					joinRoomWindow.setShell(shell);
					joinRoomWindow.open(client);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		btnJoinRoom.setBounds(143, 80, 131, 25);
		btnJoinRoom.setText("Join room");
		
		Label lblChooseYourSelection = new Label(composite_1, SWT.NONE);
		lblChooseYourSelection.setAlignment(SWT.CENTER);
		lblChooseYourSelection.setBounds(146, 10, 139, 15);
		lblChooseYourSelection.setText("Choose your selection:");

	}

}
