package play;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

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
		shell.setSize(450, 378);
		shell.setText("Start room");
		
		Composite composite = new Composite(shell, SWT.NONE);
		composite.setBounds(10, 0, 424, 64);
		
		Label lblHello = new Label(composite, SWT.NONE);
		lblHello.setAlignment(SWT.CENTER);
		lblHello.setBounds(155, 10, 83, 15);
		lblHello.setText("Hello, ");
		
		Label lblCreate = new Label(composite, SWT.NONE);
		lblCreate.setAlignment(SWT.CENTER);
		lblCreate.setBounds(96, 39, 204, 15);
		lblCreate.setText("You've just created room ");
		
		Composite composite_1 = new Composite(shell, SWT.NONE);
		composite_1.setBounds(10, 70, 414, 259);
		
		Button btnExit = new Button(composite_1, SWT.NONE);
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
		btnExit.setBounds(197, 10, 75, 25);
		btnExit.setText("Exit");
		btnExit.setEnabled(false);
		
		Button btnStartRoom = new Button(composite_1, SWT.NONE);
		btnStartRoom.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				//When click "Start", table is created
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
				
				Table scoreTable = new Table(composite_1, SWT.BORDER | SWT.HIDE_SELECTION | SWT.READ_ONLY);
				scoreTable.setBounds(89, 53, 213, 181);
				scoreTable.setHeaderVisible(true);
				scoreTable.setLinesVisible(true);
				
				TableColumn tblclmnPlayer = new TableColumn(scoreTable, SWT.CENTER);
				tblclmnPlayer.setWidth(100);
				tblclmnPlayer.setText("Player");
				
				TableColumn tblclmnScore = new TableColumn(scoreTable, SWT.CENTER);
				tblclmnScore.setWidth(100);
				tblclmnScore.setText("Score");
				
				//Enable button "Exit"
				btnExit.setEnabled(true);
				
				//Disable button "Start"
				btnStartRoom.setEnabled(false);
			}
		});
		btnStartRoom.setBounds(99, 10, 75, 25);
		btnStartRoom.setText("Start room");
	}
}
