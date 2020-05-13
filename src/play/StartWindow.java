package play;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import client.Client;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import java.io.IOException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class StartWindow {

	protected Shell shell;
	private String room;
	private String clientName;
	
	public void setShell(Shell shell) {
		this.shell = shell;
	}
	
	public void setClientName(String name) {
		this.clientName = name;
	}
	
	public void setRoom(String room) {
		this.room = room;
	}

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			StartWindow window = new StartWindow();
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
		shell.setSize(450, 300);
		shell.setText("Start your room");
		
		Composite composite = new Composite(shell, SWT.NONE);
		composite.setBounds(0, 0, 424, 104);
		
		Label lblHello = new Label(composite, SWT.NONE);
		lblHello.setAlignment(SWT.CENTER);
		lblHello.setBounds(105, 23, 173, 15);
		lblHello.setText("Hello, " + clientName);
		
		Label lblYouveJustJoined = new Label(composite, SWT.NONE);
		lblYouveJustJoined.setBounds(122, 63, 173, 15);
		lblYouveJustJoined.setText("You've just created room " + room);
		
		Composite composite_1 = new Composite(shell, SWT.NONE);
		composite_1.setBounds(0, 110, 424, 141);
		
		Button btnStart = new Button(composite_1, SWT.NONE);
		btnStart.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				//CODE HERE
				//Tell server to start room and get questions of given room
				try {
					client.dos.writeUTF(client.startGameMsg());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					System.out.println(client.dis.readUTF());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				Label lblYouveJustStarted = new Label(composite_1, SWT.NONE);
				lblYouveJustStarted.setAlignment(SWT.CENTER);
				lblYouveJustStarted.setBounds(98, 10, 250, 20);
				lblYouveJustStarted.setText("You've just started the room " + room + ". Click Exit");
				btnStart.dispose();
			}
		});
		btnStart.setBounds(155, 52, 75, 25);
		btnStart.setText("Start");
		
		Button btnExit = new Button(composite_1, SWT.NONE);
		btnExit.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					for (Control kid : shell.getChildren()) {
				         kid.dispose();
				    }
					//String loginMsg = loginMsg(name, password);
					LeaderboardWindow window = new LeaderboardWindow();
					window.setShell(shell);
					window.setClientName(clientName);
					window.open(client);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		btnExit.setBounds(155, 91, 75, 25);
		btnExit.setText("Exit");
	}
}
