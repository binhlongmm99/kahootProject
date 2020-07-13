package play;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wb.swt.SWTResourceManager;

import account.Login;
import client.Client;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import java.io.IOException;

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
		shell.setSize(1350, 700);
		shell.setText("Playing Kahoot");
		
		Composite composite = new Composite(shell, SWT.NONE);
		composite.setBounds(10, 0, 1314, 165);
		
		Label lblKahoot = new Label(composite, SWT.NONE);
		lblKahoot.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.BOLD));
		lblKahoot.setAlignment(SWT.CENTER);
		lblKahoot.setBounds(581, 21, 134, 39);
		lblKahoot.setText("Kahoot");
		
		Label lblHello = new Label(composite, SWT.NONE);
		lblHello.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.BOLD));
		lblHello.setAlignment(SWT.CENTER);
		lblHello.setBounds(511, 94, 285, 47);
		lblHello.setText("Hello, " + clientName);
		
		Composite composite_1 = new Composite(shell, SWT.NONE);
		composite_1.setBounds(10, 183, 1314, 468);
		
		Button btnCreateNewQuestion = new Button(composite_1, SWT.NONE);
		btnCreateNewQuestion.setFont(SWTResourceManager.getFont("Times New Roman", 12, SWT.NORMAL));
		btnCreateNewQuestion.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					for (Control kid : shell.getChildren()) {
				          kid.dispose();
				    }
					CreateTopicWindow createTopicWindow = new CreateTopicWindow();
					createTopicWindow.setShell(shell);
					createTopicWindow.setClientName(clientName);
					createTopicWindow.open(client);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		btnCreateNewQuestion.setBounds(556, 88, 199, 81);
		btnCreateNewQuestion.setText("Create new questions");
		
		Button btnJoinGame = new Button(composite_1, SWT.NONE);
		btnJoinGame.setFont(SWTResourceManager.getFont("Times New Roman", 12, SWT.NORMAL));
		btnJoinGame.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					for (Control kid : shell.getChildren()) {
				          kid.dispose();
				    }
					JoinGameWindow joinGameWindow = new JoinGameWindow();
					joinGameWindow.setClientName(clientName);
					joinGameWindow.setShell(shell);
					joinGameWindow.open(client);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		btnJoinGame.setBounds(556, 215, 199, 81);
		btnJoinGame.setText("Join game");
		
		Button btnLogout = new Button(composite_1, SWT.NONE);
		btnLogout.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				//Go to login window
//				try {
//					for (Control kid : shell.getChildren()) {
//				          kid.dispose();
//				    }
//					Client client = new Client();
//					Login window = new Login();
//					window.setShell(shell);
//					window.open(client);
//				} catch (IOException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
			}
		});
		btnLogout.setFont(SWTResourceManager.getFont("Times New Roman", 12, SWT.NORMAL));
		btnLogout.setBounds(556, 345, 199, 81);
		btnLogout.setText("Logout");
		
		

	}

}
