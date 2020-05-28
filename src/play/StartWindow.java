package play;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.wb.swt.SWTResourceManager;

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
		shell.setSize(780, 480);
		shell.setText("Start room");
		
		Composite composite = new Composite(shell, SWT.NONE);
		composite.setBounds(10, 10, 731, 119);
		
		Label lblHello = new Label(composite, SWT.NONE);
		lblHello.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.BOLD));
		lblHello.setAlignment(SWT.CENTER);
		lblHello.setBounds(253, 31, 219, 35);
		lblHello.setText("Hello, " + clientName);
		
		Label lblCreate = new Label(composite, SWT.NONE);
		lblCreate.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.BOLD));
		lblCreate.setAlignment(SWT.CENTER);
		lblCreate.setBounds(192, 72, 341, 41);
		lblCreate.setText("You've just created room " + room);
		
		Composite composite_1 = new Composite(shell, SWT.NONE);
		composite_1.setBounds(10, 146, 731, 285);
		
		Button btnExit = new Button(composite_1, SWT.NONE);
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
		btnExit.setBounds(484, 10, 131, 48);
		btnExit.setText("Exit");
		btnExit.setEnabled(false);
		
		Button btnStartRoom = new Button(composite_1, SWT.NONE);
		btnStartRoom.setFont(SWTResourceManager.getFont("Times New Roman", 12, SWT.NORMAL));
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
				scoreTable.setFont(SWTResourceManager.getFont("Times New Roman", 12, SWT.NORMAL));
				scoreTable.setBounds(208, 66, 325, 195);
				scoreTable.setHeaderVisible(true);
				scoreTable.setLinesVisible(true);
				
				TableColumn tblclmnPlayer = new TableColumn(scoreTable, SWT.CENTER);
				tblclmnPlayer.setWidth(159);
				tblclmnPlayer.setText("Player");
				
				TableColumn tblclmnScore = new TableColumn(scoreTable, SWT.CENTER);
				tblclmnScore.setWidth(159);
				tblclmnScore.setText("Score");
				
				//Enable button "Exit"
				btnExit.setEnabled(true);
				
				//Disable button "Start"
				btnStartRoom.setEnabled(false);
			}
		});
		btnStartRoom.setBounds(103, 10, 131, 40);
		btnStartRoom.setText("Start room");
	}
}
