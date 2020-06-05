package play;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.wb.swt.SWTResourceManager;

import client.Client;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;

public class StartWindow {

	protected Shell shell;
	private String room;
	private String clientName;
	private ArrayList<Player> playerList;
	
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
		shell.setSize(1350, 700);
		GridLayout layout = new GridLayout();
		layout.makeColumnsEqualWidth = true;
		layout.numColumns = 3;
		shell.setLayout(layout);
		
		shell.setText("Start room");
		
		new Label(shell, SWT.NULL);

		Label lblHello = new Label(shell, SWT.NONE);
		lblHello.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.BOLD));
		lblHello.setAlignment(SWT.CENTER);
		lblHello.setBounds(253, 31, 219, 35);
		lblHello.setText("\nHello, " + clientName + "\nYou've just created room " + room);
		GridData data = new GridData(GridData.HORIZONTAL_ALIGN_CENTER);
		data.widthHint = 400;
		data.heightHint = 100;
		lblHello.setLayoutData(data);
		
		new Label(shell, SWT.NULL);
		
		Table scoreTable = new Table(shell, SWT.BORDER | SWT.HIDE_SELECTION | SWT.READ_ONLY);
		scoreTable.setFont(SWTResourceManager.getFont("Times New Roman", 12, SWT.NORMAL));
		scoreTable.setBounds(208, 66, 325, 195);
		scoreTable.setHeaderVisible(true);
		scoreTable.setLinesVisible(true);
		
		TableColumn tblclmnPlayer = new TableColumn(scoreTable, SWT.CENTER);
		tblclmnPlayer.setWidth(200);
		tblclmnPlayer.setText("Player");
		
		TableColumn tblclmnScore = new TableColumn(scoreTable, SWT.CENTER);
		tblclmnScore.setWidth(200);
		tblclmnScore.setText("Score");
		
		data = new GridData(GridData.HORIZONTAL_ALIGN_CENTER);
		data.widthHint = 400;
		data.heightHint = 200;
		data.horizontalSpan = 3;
		//data.verticalSpan = 4;
		scoreTable.setLayoutData(data);
		scoreTable.setVisible(false);
		
		Button btnExit = new Button(shell, SWT.NONE);
		data = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		data.heightHint = 42;
		data.widthHint = 100;
		btnExit.setLayoutData(data);
		btnExit.setFont(SWTResourceManager.getFont("Times New Roman", 12, SWT.NORMAL));
		btnExit.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					client.dos.writeUTF(client.exitRoomMsg());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
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
		//btnExit.setBounds(484, 10, 131, 48);
		btnExit.setText("Exit");
		//btnExit.setEnabled(false);
		
		Button btnStartRoom = new Button(shell, SWT.NONE);
		btnStartRoom.setFont(SWTResourceManager.getFont("Times New Roman", 12, SWT.NORMAL));
		data = new GridData(GridData.HORIZONTAL_ALIGN_END);
		data.widthHint = 122;
		data.heightHint = 50;
		data.horizontalSpan = 2;
		btnStartRoom.setLayoutData(data);
		btnStartRoom.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				//When click "Start", table is created
				//Enable button "Exit"
				//btnExit.setEnabled(true);
				
				//Disable button "Start"
				btnStartRoom.setEnabled(false);
				
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
				
				playerList = getScoreFromServer(client);
				printPlayerScore(playerList, scoreTable);
				scoreTable.setVisible(true);				

			}
		});
		//btnStartRoom.setBounds(103, 10, 131, 40);
		btnStartRoom.setText("Start room");
	}
	
	private ArrayList<Player> getScoreFromServer(Client client) {
		String sRep = null;
		try {
			client.dos.writeUTF(client.getScore(room));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			sRep = client.dis.readUTF();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String[] parts = sRep.split("-");
		ArrayList<Player> playerList = new ArrayList<Player>();
		for (int i = 1; i < parts.length; i += 2) {
			Player p = new Player(parts[i], Integer.parseInt(parts[i+1]));
			playerList.add(p);
		}
		return playerList;
	}
	
	private void printPlayerScore(ArrayList<Player> pL, Table table) {
		//Clear old leaderboard data
		table.removeAll();;
		
		//Sort
		Collections.sort(pL, new Comparator<Player>() {
	        @Override
	        public int compare(Player p1, Player p2)
	        {
	            return  p1.getScore() - p2.getScore();
	        }
	    });
		
		//Get new leaderboard data
		for (int i = 0; i < pL.size(); i++) {
			System.out.println(pL.get(i).getPlayerName() + "------" + pL.get(i).getScore());
			TableItem item = new TableItem(table, SWT.NONE);
			item.setText(0, pL.get(i).getPlayerName());
			item.setText(1, pL.get(i).getScore() + "");
		}
	}
}
