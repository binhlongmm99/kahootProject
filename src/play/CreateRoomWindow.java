package play;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;

import java.io.IOException;
import java.util.Random;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

import client.Client;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;

public class CreateRoomWindow {

	protected Shell shell;
	private String clientName;
	private Text roomTxt;
	
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
			CreateRoomWindow window = new CreateRoomWindow();
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
		String sRep = null;
		List topicList;
		try {
			client.dos.writeUTF(client.getTopicList(clientName));
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
		String[] parts = sRep.split("--");
		createContents(display, client, parts);
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
	protected void createContents(Display display, Client client, String[] parts) {
		if(shell == null) shell = new Shell();
		shell.setSize(1350, 700);
		shell.setText("Create room");
		
		Composite composite = new Composite(shell, SWT.NONE);
		composite.setBounds(10, 10, 1314, 131);
		
		Label lblUser = new Label(composite, SWT.NONE);
		lblUser.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.BOLD));
		lblUser.setAlignment(SWT.CENTER);
		lblUser.setBounds(534, 28, 284, 50);
		lblUser.setText("User: " + clientName);
		
		Composite composite_1 = new Composite(shell, SWT.NONE);
		composite_1.setBounds(10, 165, 1314, 464);
		
		Label lblChooseTopic = new Label(composite_1, SWT.NONE);
		lblChooseTopic.setFont(SWTResourceManager.getFont("Times New Roman", 14, SWT.NORMAL));
		lblChooseTopic.setBounds(26, 53, 129, 40);
		lblChooseTopic.setText("Choose topic: ");
		
		List list = new List(composite_1, SWT.BORDER | SWT.V_SCROLL | SWT.SINGLE);
		list.setFont(SWTResourceManager.getFont("Times New Roman", 14, SWT.NORMAL));
		list.setBounds(195, 25, 562, 88);
		
		//CODE HERE
		//Add list of topics
		//Sample code
		//for(String topic: topicList)
		//   List.add(topic)
		
		if (parts[0].contains("TL")) {
			for (int i = 1; i < parts.length; i++) {
				list.add(parts[i]);
				
			}
		}
		else {
			System.out.println("Problem at getting topic");
		}
		
		Color red = new Color(display, 255, 0, 0);
		
		Label lblPleaseChooseTopic = new Label(composite_1, SWT.NONE);
		lblPleaseChooseTopic.setFont(SWTResourceManager.getFont("Times New Roman", 12, SWT.NORMAL));
		lblPleaseChooseTopic.setBounds(195, 141, 562, 54);
		lblPleaseChooseTopic.setText("");
		
		Button btnNext = new Button(composite_1, SWT.NONE);
		btnNext.setFont(SWTResourceManager.getFont("Times New Roman", 12, SWT.NORMAL));
		btnNext.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int index = list.getSelectionIndex();
				if(index == -1) {
					lblPleaseChooseTopic.setText("Please choose topic!");
					lblPleaseChooseTopic.setForeground(red);
				} else {
					lblPleaseChooseTopic.setText("");
					
					//Get selected topic name
					Random random = new Random();
					String room = Integer.toString(random.nextInt(10000));
					String topic = list.getItem(index);
					try {
						client.dos.writeUTF(client.createRoomMsg(room, topic));
						System.out.println(client.dis.readUTF());
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					//***********************************************
					//CODE HERE
					//Create room and get roomID with selected topic
					
					
					//Go to start window
					try {
						for (Control kid : shell.getChildren()) {
							kid.dispose();
						}
						StartWindow window = new StartWindow();
						window.setShell(shell);
						window.setClientName(clientName);
						window.setRoom(room);
						window.open(client);
					} catch (Exception ex) {
						ex.printStackTrace();
					} 
				}
			}

			private String createRoom(String topic) {
				// TODO Auto-generated method stub
				return null;
			}
		});
		btnNext.setBounds(999, 349, 118, 47);
		btnNext.setText("Next");
		
		Button btnBack = new Button(composite_1, SWT.NONE);
		btnBack.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				//Go to previous window
//				try {
//					for (Control kid : shell.getChildren()) {
//				          kid.dispose();
//				    }
//					JoinGameWindow joinGameWindow = new JoinGameWindow();
//					joinGameWindow.setClientName(clientName);
//					joinGameWindow.setShell(shell);
//					joinGameWindow.open(client);
//				} catch (Exception ex) {
//					ex.printStackTrace();
//				}
			}
		});
		btnBack.setFont(SWTResourceManager.getFont("Times New Roman", 12, SWT.NORMAL));
		btnBack.setBounds(102, 349, 118, 49);
		btnBack.setText("Back");

	}
}
